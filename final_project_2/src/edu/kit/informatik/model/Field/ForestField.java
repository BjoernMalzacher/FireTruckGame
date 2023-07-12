package edu.kit.informatik.model.Field;

import java.util.ArrayList;

import edu.kit.informatik.model.State;
import edu.kit.informatik.vehicle.Vehicle;

/**
 * @author Bjoern Malzacher
 * @version 1.0 
 * this class is an Object on the Map named ForestField.
 * a Vehicle can stand on it and it can burn down represented as State.  
 *
 */
public class ForestField extends Field {
    private static final String FOREST_FIELD_REGEX = "(\\*|\\+|w|d)";
    private State state;
    private ArrayList<Vehicle> vehicleList;
    private boolean isAlreadyBurnt;
    
    /**
     * @param t
     */
    public ForestField(State t) {
        state = t;
        vehicleList = new ArrayList<Vehicle>();
        isAlreadyBurnt = false; 
    }
    
    /**
     * @return returns the regular expression representing the Class.
     */
    public static String getRegex() {
        return FOREST_FIELD_REGEX;
    }
    
    /**
     * @return returns true if the field burnt down this round.
     */
    public boolean isAlreadyBurnt() {
        return isAlreadyBurnt;
    }
    
    /**
     * @param isAlreadyBurnt resets if the field is already burned this round.
     */
    public void setAlreadyBurnt(boolean isAlreadyBurnt) {
        this.isAlreadyBurnt = isAlreadyBurnt;
    }

    /**
     * @return returns an Arralylist of vehicle which are standing on this field.
     */
    public ArrayList<Vehicle> getVehicleList() {
        return vehicleList;
    }

    /**
     * @param vehicle adds a new vehicle to this field.
     */
    public void addVehicle(Vehicle vehicle) {
        vehicleList.add(vehicle);
    }

    /**
     * @param vehicle checks if a vehicle is standing on this field.
     * @return returns true if it containsthis vehicle, false if not.
     */
    public boolean containsVehicle(Vehicle vehicle) {
        return vehicleList.contains(vehicle);
    }

    /**
     * @return returns the current state of this Field.
     */
    public State getState() {
        return state;
    }

    /**
     * @param vehicle removes a vehicle from this field.
     */
    public void removeVehicle(Vehicle vehicle) {
        vehicleList.remove(vehicle);
    }

    /**
     * @param state resets the state of the field to a new one 
     */
    public void setState(State state) {
        this.state = state;
    }

    @Override public String toString() {

        return state.getValue();
    }
}
