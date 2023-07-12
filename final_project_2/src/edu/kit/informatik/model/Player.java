package edu.kit.informatik.model;

import java.util.ArrayList;
import java.util.HashMap;

import edu.kit.informatik.vehicle.FireTruck;
import edu.kit.informatik.vehicle.Vehicle;

/**
 * @author Bjoern Malzacher
 * @version 1.0 this class represents the Players in this game.
 */
public class Player {
    private String name;
    private ArrayList<Vehicle> vehicleList = new ArrayList<Vehicle>();
    private int vehicleCount = 0;
    private int reputationPoints;
    private HashMap<Vehicle, boolean[][]> extinguishMap;
    private int extinguishArrayXsize;
    private int extinguishArrayYsize;

    /**
     * Standard constructor;
     * 
     * @param name - The name of the Player asString Value.
     * @param x
     * @param y
     */
    public Player(String name, int x, int y) {
        this.name = name;
        reputationPoints = 0;
        extinguishMap = new HashMap<Vehicle, boolean[][]>();
        extinguishArrayXsize = x;
        extinguishArrayYsize = y;
    }

    /**
     * this method is used to create a new Fire truck.
     * 
     * @return returns the created Firetruck.
     */
    public FireTruck createFireTruck() {
        FireTruck v = new FireTruck(this, vehicleCount);
        vehicleList.add(v);
        boolean[][] extinguisArray = new boolean[extinguishArrayYsize][extinguishArrayXsize];
        for (int i = 0; i < extinguisArray.length; i++) {
            for (int j = 0; j < extinguisArray[i].length; j++) {
                extinguisArray[i][j] = false;
            }
        }
        extinguishMap.put(v, extinguisArray);

        vehicleCount += 1;
        return v;
    }

    /**
     * @param vehicle
     */
    public void resetExtinguishMap(Vehicle vehicle) {
        boolean[][] extinguisArray = extinguishMap.get(vehicle);
        for (int i = 0; i < extinguisArray.length; i++) {
            for (int j = 0; j < extinguisArray[i].length; j++) {
                extinguisArray[i][j] = false;
            }
        }

    }

    /**
     * this method is just to check an vehicle has already extinguished a Forestfield.
     * @param vehicle -the vehicle.
     * @param x -the X-coordinate of the Forest Field
     * @param y -the Y-coordiante of the forest field.
     * @return true if is already extinguished from the vehicle, false if not.
     */
    public boolean isFieldAlreadyExtinguished(Vehicle vehicle, int x, int y) {
        return extinguishMap.get(vehicle)[y][x];
    }

    /**
     * this method is just to set that an vehicle has already extinguished a Forestfield.
     * @param vehicle -the vehicle.
     * @param x -the X-coordinate of the Forest Field.
     * @param y -the Y-coordinate of the forest field.
     * @param b -true if it has extinguished the fire, false if not.
     */
    public void setFieldExtinguished(Vehicle vehicle, int x, int y, boolean b) {
        extinguishMap.get(vehicle)[y][x] = b;
    }

    /**
     * this method is used to reduce the Ammount of Reputationpoints a player has
     * after he bought a new vehicle.
     * 
     * @param ammount - the cost of the vehicle.
     */
    public void reduceReputationPoints(int ammount) {
        reputationPoints -= ammount;
    }

    /**
     * this method is used to increase he amount of reputation-points after a fire
     * is extinguished.
     * 
     * @param ammount the amount of reputation-points as an reward.
     */
    public void addReputationPoint(int ammount) {
        reputationPoints += ammount;
    }

    @Override public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Player other = (Player) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    /**
     * removes a Vehicle from from the Intern Vehicle-list.
     * 
     * @param playerVehicle - the removed vehicle.
     */
    public void removeVehicle(Vehicle playerVehicle) {
        vehicleList.remove(playerVehicle);
    }

    // get/set-Methods

    /**
     * gets the Vehicle from the vehicle Number
     * 
     * @param vehcleNumber - the vehicle number of the vehicle
     * @return returns the searched vehicle or null if the vehicle does not exist.
     */
    public Vehicle getVehicle(int vehcleNumber) {
        Vehicle v = new Vehicle(this, vehcleNumber);
        for (Vehicle vehicle : vehicleList) {
            if (vehicle.equals(v)) {
                return vehicle;
            }
        }
        return null;
    }

    /**
     * 
     * @return returns the vehicle-list
     */
    public ArrayList<Vehicle> getVehicleList() {
        return vehicleList;
    }

    /**
     * @return returns the name if the Player
     */
    public String getName() {
        return name;
    }

    /**
     * @return returns the amount of reputation-points.
     */
    public int getReputationPoints() {
        return reputationPoints;
    }

}
