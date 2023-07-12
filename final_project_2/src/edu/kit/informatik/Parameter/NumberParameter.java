package edu.kit.informatik.Parameter;

/**
 * @author Bjoern Malzacher
 * @version 1.0
 * Parameter-time to Parse the User-parameter.
 * 
 *
 */
public class NumberParameter extends Parameter<Integer> {
    private static final String NUMBER_PARAM_REGEX = " ([1-6])$";
    
    @Override public void setParameter(Integer param) {
        this.type = param;

    }
    
    /**
     * @return returns the regular expression representing the Class. 
     */
    public static String getRegex() {
        return NUMBER_PARAM_REGEX;
    }

}
