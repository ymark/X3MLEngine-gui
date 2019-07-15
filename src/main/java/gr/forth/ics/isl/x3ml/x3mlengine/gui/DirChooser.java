package gr.forth.ics.isl.x3ml.x3mlengine.gui;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class DirChooser extends JPanel{
    private final JFileChooser dirChooser;
    
    public DirChooser(boolean selectFiles){
        this.dirChooser=new JFileChooser();
        if(selectFiles){
            this.dirChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            this.dirChooser.setMultiSelectionEnabled(true);
        }else{
            this.dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            this.dirChooser.setMultiSelectionEnabled(false);
        }
    }
    
    public String getFolderPath(){
        int retVal=this.dirChooser.showOpenDialog(this);
        if(retVal==JFileChooser.APPROVE_OPTION){
            return dirChooser.getSelectedFile().getAbsolutePath();
        }else{
            return "";
        }
    }
    
    public Collection<String> getSelectedPaths(){
        Collection<String> retCollection=new HashSet<>();
        int retVal=this.dirChooser.showOpenDialog(this);
        if(retVal==JFileChooser.APPROVE_OPTION){
            for(File selectedFile : dirChooser.getSelectedFiles()){
                retCollection.add(selectedFile.getAbsolutePath());
            }
        }
        return retCollection;
    }
    
    public Collection<File> getSelectedFiles(){
        int retVal=this.dirChooser.showOpenDialog(this);
        if(retVal==JFileChooser.APPROVE_OPTION){
            return Arrays.asList(dirChooser.getSelectedFiles());
        }else{
            return new HashSet<>();
        }
    }
}