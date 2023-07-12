package edu.kit.informatik.vehicle;

import edu.kit.informatik.model.Player;

/**
 * @author Bjoern Malzacher
 * @version 1.0 
 * this class is used to differentiate between different vehicle. 
 * this vehicle is a Fire-truck hit has only the properties which a vehicle has.
 *
 */
public class FireTruck extends Vehicle {
    private static final String FIRETRUCK_REGEX = "([A-D][0-9]+)";

    /**
     * standart construcktor
     * @param owner -
     * @param vehicleNumber
     */
    public FireTruck(Player owner, int vehicleNumber) {
        super(owner, vehicleNumber);

    }

    /**
     * @return returns the regular expression representing the Class.
     */
    public static String getRegex() {
        return FIRETRUCK_REGEX;
    }

}
