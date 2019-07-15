package gr.forth.ics.isl.x3ml.x3mlengine.gui;

/**
 * @author Yannis Marketakis (marketak 'at' ics 'dot' forth 'dot' gr)
 */
public enum X3MLResourceType {
    X3ML_MAPPINGS,
    XML_INPUT,
    GENERATOR_POLICY,
    UNDEF ;
    
    @Override
    public String toString(){
        switch(this){
            case X3ML_MAPPINGS:
                return "X3ML mappings";
            case XML_INPUT:
                return "Input";
            case GENERATOR_POLICY:
                return "Generator Policy";
            case UNDEF:
            default:
                return "Uknown";
        }
    }
}