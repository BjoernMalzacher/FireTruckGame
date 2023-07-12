package edu.kit.informatik.model.Field;

/**
 * @author Bjoern Malzacher
 * @version 1.0 
 * this class is an Object on the Map named FirePondes. 
 * a vehicle can refill itself if it stands next to it.
 */
public class FirePonds extends Field {
    private static final String FIRE_PONDS_REGEX = "L";
    private static final String STRING_VALUE = "L";

    /**
     * 
     * @return returns the regular expression representing the Class.
     */
    public static String getRegex() {
        return FIRE_PONDS_REGEX;
    }

    @Override public String toString() {
        return STRING_VALUE;
    }
}
