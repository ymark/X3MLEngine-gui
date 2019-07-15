package gr.forth.ics.isl.x3ml.x3mlengine.gui.exception;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public class GenericException extends Exception{
    
    public GenericException(String message){
        super(message);
    }
    
    public GenericException(String message, Throwable thr){
        super(message, thr);
    }
}