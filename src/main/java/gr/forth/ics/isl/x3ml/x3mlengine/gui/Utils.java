package gr.forth.ics.isl.x3ml.x3mlengine.gui;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import gr.forth.ics.isl.x3ml.x3mlengine.gui.exception.GenericException;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import lombok.extern.log4j.Log4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
@Log4j
public class Utils {
    public static Multimap<X3MLResourceType,Pair<File,String>> identifyFileResources(Collection<File> files){
        Multimap<X3MLResourceType,Pair<File,String>> retMap=HashMultimap.create();
        for(File file : files){
            if(FilenameUtils.getExtension(file.getName()).equalsIgnoreCase(Resources.EXTENSION_X3ML)){
                retMap.put(X3MLResourceType.X3ML_MAPPINGS, Pair.of(file, file.getName()));
            }else if(FilenameUtils.getExtension(file.getName()).equalsIgnoreCase(Resources.EXTENSION_XML)){
                try{
                    if(isGeneratorPolicyFile(file)){
                        retMap.put(X3MLResourceType.GENERATOR_POLICY, Pair.of(file, file.getName()));
                    }else{
                        retMap.put(X3MLResourceType.XML_INPUT, Pair.of(file, file.getName()));
                    }
                }catch(GenericException ex){
                    log.error("An error occured while parsing XML file "+file.getAbsolutePath()+" Skipping this file. ",ex);
                }
            }else{
                retMap.put(X3MLResourceType.UNDEF, Pair.of(file, file.getName()));
            }
        }
        return retMap;
    }
    
    public static String beautifyFileResourcesLabels(Multimap<X3MLResourceType,Pair<File,String>> fileResources){
        StringBuilder htmlBuilder=new StringBuilder();
        htmlBuilder.append("<html>")
                   .append("&bull;").append("<u>").append(X3MLResourceType.X3ML_MAPPINGS.toString()).append("</u>").append(": ");
        if(fileResources.containsKey(X3MLResourceType.X3ML_MAPPINGS)){
            htmlBuilder.append(fileResources.get(X3MLResourceType.X3ML_MAPPINGS).stream().map(Pair::getRight).collect(Collectors.toList())).append("<br>");
        }else{
            htmlBuilder.append("-").append("<br>");
        }
        htmlBuilder.append("&bull;").append("<u>").append(X3MLResourceType.XML_INPUT.toString()).append("</u>").append(": ");
        if(fileResources.containsKey(X3MLResourceType.XML_INPUT)){
            htmlBuilder.append(fileResources.get(X3MLResourceType.XML_INPUT).stream().map(Pair::getRight).collect(Collectors.toList())).append("<br>");
        }else{
            htmlBuilder.append("-").append("<br>");
        }
        htmlBuilder.append("&bull;").append("<u>").append(X3MLResourceType.GENERATOR_POLICY.toString()).append("</u>").append(": ");
        if(fileResources.containsKey(X3MLResourceType.GENERATOR_POLICY)){
            htmlBuilder.append(fileResources.get(X3MLResourceType.GENERATOR_POLICY).stream().map(Pair::getRight).collect(Collectors.toList())).append("<br>");
        }else{
            htmlBuilder.append("-").append("<br>");
        }
        if(fileResources.containsKey(X3MLResourceType.UNDEF)){
            htmlBuilder.append("&bull;").append("<u>").append(X3MLResourceType.UNDEF.toString()).append("</u>").append(": ")
                       .append(fileResources.get(X3MLResourceType.UNDEF).stream().map(Pair::getRight).collect(Collectors.toList())).append("<br>");
        }
        htmlBuilder.append("</html>");
        return htmlBuilder.toString();
    }
    
    private static boolean isGeneratorPolicyFile(File file) throws GenericException{
        try{
            DocumentBuilderFactory factory =
            DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            return (doc.getDocumentElement().getNodeName().equalsIgnoreCase(Resources.XML_ELEMENT_GENERATOR_POLICY));
        }catch(IOException | ParserConfigurationException | SAXException ex){
            throw new GenericException("An error occured while parsing XML file "+file.getAbsolutePath()+" Skipping this file. ",ex);
        }
    }
}