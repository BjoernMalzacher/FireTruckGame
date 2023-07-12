package edu.kit.informatik.model;

import edu.kit.informatik.exeption.IllegalMoveExeption;
import edu.kit.informatik.resourcess.ErrorMassanges;

/**
 * @author Bjoern Malzacher
 * @version 1.0
 * this enum is Used to check which state the ForestFields have 
 */
public enum State {

    /**
     * if a ForestField has this enum it is dry. 
     */
    dry("d"), 
    /**
     * if a ForestField has this enum it is wet.
     */
    wet("w"), 
    /**
     * if a ForestField has this enum it is slightly-burning.
     */
    slightlyBurning("+"), 
    /**
     * if a ForestField has this enum it is strong-burning.
     */
    StrongBurning("*");

    private String value;

    /**
     * Standard constructor.
     * @param t -the String value to the State.
     */
    State(String t) {
        value = t;
    }

    /**
     * @return returns the String Value of the State.
     */
    public String getValue() {
        return value;
    }
    
    /**
     * @return returns the state after is is extinguished one time.
     * @throws IllegalMoveExeption - throws an exception if the player trys to
     *                             extinguish a wet Field.
     */
    public State extingushFire() throws IllegalMoveExeption  {
        switch (this) {
            case wet:
                throw new IllegalMoveExeption(ErrorMassanges.IS_WET);
            case dry:
                return wet;
            case slightlyBurning:
                return wet;
            case StrongBurning:
                return slightlyBurning;
            default:
                return null;

        }

    }
    
    /**
     * @return returns the state of an Field after its burned one time.  
     */
    public State burning() {
        switch (this) {
            case wet:
                return dry;
            case dry:
                return slightlyBurning;
            case slightlyBurning:
                return StrongBurning;
            case StrongBurning:
                return StrongBurning;
            default:
                return null;

        }
    }

    /**
     * gets the Sate from there String value 
     * @param m - the String value of an State 
     * @return -the state to the given String value or null if there is no state to this String value.
     */
    public static State getStateFromString(String m) {
        if (dry.getValue().equals(m)) {
            return dry;
        } else if (wet.getValue().equals(m)) {
            return wet;
        } else if (slightlyBurning.getValue().equals(m)) {
            return slightlyBurning;
        } else if (StrongBurning.getValue().equals(m)) {
            return StrongBurning;
        }
        return null;
    }
}
