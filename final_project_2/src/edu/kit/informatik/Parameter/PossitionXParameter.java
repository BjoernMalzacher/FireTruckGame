package edu.kit.informatik.Parameter;

/**
 * @author Bjoern Malzacher
 * @version 1.0
 * Paramter-time to parse the User-parameter.
 * 
 *
 */
public class PossitionXParameter extends Parameter<Integer> {
    private  static final String X_POSSITION_PARAM_REGEX = ",([0-9]+)$";
    
    @Override public void setParameter(Integer param) {
        this.type = param;
    }
    
    /**
     * @return returns the regular expression representing the Class. 
     */
    public static String getRegex() {
        return X_POSSITION_PARAM_REGEX;
    }

}
