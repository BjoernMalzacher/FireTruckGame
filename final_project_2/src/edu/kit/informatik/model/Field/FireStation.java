package edu.kit.informatik.model.Field;

import edu.kit.informatik.model.Player;

/**
 * @author Bjoern Malzacher 
 * @version 1.0
 * this class is an Object on the Map named FireStation. 
 * a vehicle can refill itself if it stands next to it.
 * everz plazer owns one firestation.
 *
 */
public class FireStation extends Field {
    private static final String FIRE_STATION_REGEX = "[A-D]";
    private Player player;

    /**
     * standard constructor.
     * @param player
     */
    public FireStation(Player player) {
        this.player = player;
    }
    
    /**
     * @return return a regular expression representing the Class. 
     */
    public static String getRegex() {
        return FIRE_STATION_REGEX;
    }

    /**
     * 
     * @return returns the Player which owns the Firestation.
     */
    public Player getPlayer() {
        return player;
    }

    @Override public String toString() {
        return player.getName();
    }
}
