package edu.kit.informatik;

import java.util.ArrayList;

import edu.kit.informatik.model.State;
import edu.kit.informatik.model.Field.FirePonds;
import edu.kit.informatik.vehicle.FireTruck;

/**
 * @author Björn Malzacher
 * @version 1.0 
 * This class is used to check if the game has the right properties
 * so it can be played.
 *
 */
public class InitializationValues {
    
    /**
     * String-Value of Player A
     */
    public static final String PLAYER_A_NAME = "A";
    /**
     * String-Value of Player B
     */
    public static final String PLAYER_B_NAME = "B";
    /**
     * String-Value of Player C
     */
    public static final String PLAYER_C_NAME = "C";
    /**
     * String-Value of Player D
     */
    public static final String PLAYER_D_NAME = "D";
    /**
     * String-Value of the field Fire-ponds
     */
    public static final String FIRE_PONDS_NAME = "L";
    /**
     * regular expression of an Firetruck. This is used for creating the Map.
     */
    public static final String INITIALIZING_FIRETRUCKS_REGEX = "[A-D][0]";
    private static final String FIRST_FIRE_TRUCK_NUMBER = "0";
    
    /**
     * checks if the input Parameter which are used to create the Map of the game
     * have the right properties
     * 
     * @param m    - Y-size of the Map
     * @param n    - X-size of the Map
     * @param list -String Arraylist which representing the Fields.
     * @return true if every check is correct, false if the input Paramter can not
     *         create a Game-map.
     */
    public static boolean isPlayingboard(int m, int n, ArrayList<String> list) {
        if (!list.get(0).equals(PLAYER_A_NAME)) {
            return false;
        } else if (!list.get(n - 1).equals(PLAYER_D_NAME)) {
            return false;
        } else if (!list.get((m - 1) * n).equals(PLAYER_C_NAME)) {
            return false;
        } else if (!list.get((m * n) - 1).equals(PLAYER_B_NAME)) {
            return false;
        } else if (!list.get((n / 2)).equals(FIRE_PONDS_NAME)) {
            return false;
        } else if (!list.get(n * (m / 2)).equals(FIRE_PONDS_NAME)) {
            return false;
        } else if (!list.get(n * (m / 2) + (n - 1)).equals(FIRE_PONDS_NAME)) {
            return false;
        } else if (!list.get(((m - 1) * n) + n / 2).equals(FIRE_PONDS_NAME)) {
            return false;
        } else if (!list.get(n + 1).equals(PLAYER_A_NAME + FIRST_FIRE_TRUCK_NUMBER)) {
            return false;
        } else if (!list.get((n + n) - 2).equals(PLAYER_D_NAME + FIRST_FIRE_TRUCK_NUMBER)) {
            return false;
        } else if (!list.get((m * n) - (n + 2)).equals(PLAYER_B_NAME + FIRST_FIRE_TRUCK_NUMBER)) {
            return false;
        } else if (!list.get(((m * n) - 2 * n) + 1).equals(PLAYER_C_NAME + FIRST_FIRE_TRUCK_NUMBER)) {
            return false;
        }
        int countFireTrucks = 0;
        int countFirePonds = 0;
        boolean slightly = true;
        boolean strong = true;
        for (String string : list) {
            if (string.matches(FireTruck.getRegex())) {
                countFireTrucks += 1;
            }
            if (string.matches(FirePonds.getRegex())) {
                countFirePonds += 1;
            }
            if (string.equals(State.slightlyBurning.getValue())) {
                slightly = false;
            }
            if (string.equals(State.StrongBurning.getValue())) {
                strong = false;
            }

        }
        if (countFireTrucks != 4 || countFirePonds != 4 || slightly || strong) {
            return false;
        }

        return true;
    }
}
