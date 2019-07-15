package gr.forth.ics.isl.x3ml.x3mlengine.gui;

import java.io.File;
import java.util.Collection;
import lombok.Data;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
@Data
public class X3MLEngineRunner {
    private final Collection<File> mappings;
    private final Collection<File> inputFiles;
    private final File generatorPolicyFile;
    private final String outputFolder;
    private final String outputFormat;
    private final String uuidSize;
    
    public X3MLEngineRunner(Collection<File> mappings, Collection<File> inputFiles, File generatorPolicyFile, String outputFolder, String outputFormat, String uuidSize){
        this.mappings=mappings;
        this.inputFiles=inputFiles;
        this.generatorPolicyFile=generatorPolicyFile;
        this.outputFolder=outputFolder;
        this.outputFormat=outputFormat;
        this.uuidSize=uuidSize;
        
        System.out.println(this);
    }
}