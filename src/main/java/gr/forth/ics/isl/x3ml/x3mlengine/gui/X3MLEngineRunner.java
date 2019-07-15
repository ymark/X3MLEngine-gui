package gr.forth.ics.isl.x3ml.x3mlengine.gui;

import gr.forth.ics.isl.x3ml.X3MLEngineFactory;
import java.io.File;
import java.util.Collection;
import lombok.Data;
import lombok.extern.log4j.Log4j;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
@Data @Log4j
public class X3MLEngineRunner {
    private final Collection<File> mappings;
    private final Collection<File> inputFiles;
    private final File generatorPolicyFile;
    private final String outputFolder;
    private final X3MLEngineFactory.OutputFormat outputFormat;
    private final int uuidSize;
    
    public X3MLEngineRunner(Collection<File> mappings, Collection<File> inputFiles, File generatorPolicyFile, String outputFolder, String outputFormat, String uuidSize){
        this.mappings=mappings;
        this.inputFiles=inputFiles;
        this.generatorPolicyFile=generatorPolicyFile;
        this.outputFolder=outputFolder;
        this.outputFormat=this.identifyOutputFormat(outputFormat);
        this.uuidSize=this.identifyUuidSize(uuidSize);
        
        System.out.println(this);
    }
    
    private X3MLEngineFactory.OutputFormat identifyOutputFormat(String outputFormatStr){
        switch(outputFormatStr){
            case Resources.RDF:
                return X3MLEngineFactory.OutputFormat.RDF_XML;
            case Resources.N3:
            case Resources.NTRIPLES:
                return X3MLEngineFactory.OutputFormat.NTRIPLES;
            case Resources.TRIG:
                return X3MLEngineFactory.OutputFormat.TRIG;
            case Resources.TURTLE:
                return X3MLEngineFactory.OutputFormat.TURTLE;
            default:
                String errorLabel="Unable to identify output format with string value ("+outputFormatStr+"). Using "+Resources.RDF+" output format.";
                log.error(errorLabel);
                GuiRunner.HTML_OUTPUT.append("<u>&bull;").append(Resources.GUI_LABELS_ERROR).append(": ").append("</u>").append(errorLabel).append("\n");
                return X3MLEngineFactory.OutputFormat.RDF_XML;
        }
    }
    
    private int identifyUuidSize(String uuidSizeStr){
        if(uuidSizeStr.equalsIgnoreCase(Resources.GUI_LABELS_DEFAULT)){
            return -1;
        }else{
            try{
                return Integer.parseInt(uuidSizeStr);
            }catch(NumberFormatException ex){
                String errorLabel="Unable to identify UUID size value ("+uuidSizeStr+"). Using the Default value.";
                log.error(errorLabel);
                GuiRunner.HTML_OUTPUT.append("<u>&bull;").append(Resources.GUI_LABELS_ERROR).append(": ").append("</u>").append(errorLabel).append("\n");
                return -1;
            }
        }
    }
    
    public void run(){
        GuiRunner.HTML_OUTPUT.append("Creating X3ML Engine instance\n");
        X3MLEngineFactory engineFactory=X3MLEngineFactory.create();
        
        GuiRunner.HTML_OUTPUT.append("Loading Resources to Engine\n");
        engineFactory.withInputFiles(this.inputFiles)
                     .withMappings(this.mappings)
                     .withGeneratorPolicy(this.generatorPolicyFile)
                     .withUuidSize(this.uuidSize)
                     .withOutput(this.generateOutputFilePath(), this.outputFormat);
        
        GuiRunner.HTML_OUTPUT.append("Transforming Data\n");
        engineFactory.execute();
        
        GuiRunner.HTML_OUTPUT.append("Finished Transformation\n");
    }
    
    private String generateOutputFilePath(){
        StringBuilder outputPathBuilder=new StringBuilder();
        if(!this.outputFolder.isBlank()){
            outputPathBuilder.append(this.outputFolder)
                             .append("\\");
        }
        outputPathBuilder.append(Resources.OUTPUT).append(".");
        switch(this.outputFormat){
            case RDF_XML: 
            case RDF_XML_PLAIN:
                outputPathBuilder.append(Resources.FILE_EXTENSION_RDF);
                break;
            case NTRIPLES:
                outputPathBuilder.append(Resources.FILE_EXTENSION_N3);
                break;
            case TRIG:
                outputPathBuilder.append(Resources.FILE_EXTENSION_TRIG);
                break;
            case TURTLE:
                outputPathBuilder.append(Resources.FILE_EXTENSION_TURTLE);
                break;
        }
        return outputPathBuilder.toString();
    }
}