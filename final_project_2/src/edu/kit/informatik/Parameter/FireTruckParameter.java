package edu.kit.informatik.Parameter;

import edu.kit.informatik.vehicle.FireTruck;

/**
 * @author Bjoern Malzacher
 * @version 1.0 Parameter-time to Pasre the User-parameter.
 * 
 *
 */
public class FireTruckParameter extends Parameter<FireTruck> {
    private static final String FIRETRUCK_PARAM_REG = " ([A-D][0-9]+).*";

    @Override public void setParameter(FireTruck param) {
        this.type = param;

    }

    /**
     * @return returns the regular expression representing the Class.
     */
    public static String getRegex() {
        return FIRETRUCK_PARAM_REG;
    }

}
