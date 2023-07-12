package edu.kit.informatik.vehicle;

import edu.kit.informatik.exeption.IllegalMoveExeption;
import edu.kit.informatik.model.Player;
import edu.kit.informatik.resourcess.ErrorMassanges;

/**
 * @author Bjoern Malzacher
 * @version 1.0
 *
 */
public class Vehicle {
    private Player owner;
    private int vehicleNumber;
    private int waterMark;
    private int actionPoints;
    private boolean actionPerformed;
    private final int startValue = 3;

    /**
     * Standart constructor
     * @param owner -owner of this Vehicle.
     * @param vehicleNumber - ID-Number of the vehicle
     */
    public Vehicle(Player owner, int vehicleNumber) {
        this.owner = owner;
        this.vehicleNumber = vehicleNumber;
        waterMark = startValue;
        actionPoints = startValue;
    }

    /**
     * @return returns the amount of Watermarks this vehicle has.
     */
    public int getWaterMark() {
        return waterMark;
    }

    /**
     * @return returns the amount of Action-points this vehicle has.
     */
    public int getActionPoints() {
        return actionPoints;
    }

    /**
     * refills the Watermarks of the vehicle
     * @return returns true if a a refill was possible, false if the vehicle has maximal watermarks.
     */
    public boolean refill() {
        if (waterMark != startValue) {
            this.waterMark = startValue;
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return returns true if any actionpoints are left, false if not
     */
    public boolean isActionPossible() {
        if (actionPoints >= 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * reduces the amount of action points by one.
     */
    public void actionPerformed() {
        actionPoints -= 1;
        actionPerformed = true;

    }
    
    /**
     * resets an boolean value.
     * this method is only used after the turn has ended.
     */
    public void resetActionPerformed() {
        actionPerformed = false; 
    }
    
    /**
     * @return returns true if the vehicle has already performed an action, false if not.
     */
    public boolean isActionPerformed() {
        return actionPerformed;
    }

    /**
     * @return returns the player who owns this vehicle
     */
    public Player getowner() {
        return owner;
    }

    /**
     * 
     * @return returns true if any watermarks are left, false if not.
     */
    public boolean isExtingushingPossible() {
        if (waterMark > 0) {

            return true;
        } else {
            return false;
        }
    }

    /**
     * reduces the watermarks by 1.
     */
    public void extinguishing() {
        waterMark -= 1;
    }

    /**
     * @return returns the number of the vehicle
     */
    public int getVehicleNumber() {
        return vehicleNumber;
    }

    /**
     * this method is used after a this vehicle has moved.
     * 
     * @throws IllegalMoveExeption - this exeption is thrown if there are no acten
     *                             points left.
     */
    public void move() throws IllegalMoveExeption {
        if (actionPoints > 0) {
            actionPoints -= 1;

        } else {
            throw new IllegalMoveExeption(ErrorMassanges.NO_ACTIONPOINTS);
        }
    }

    /**
     * this method reloads the action-points.
     */
    public void reloadActionPoints() {
        actionPoints = startValue;
    }

    @Override public String toString() {
        return owner.getName() + vehicleNumber;
    }

    @Override public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((owner == null) ? 0 : owner.hashCode());
        result = prime * result + vehicleNumber;
        return result;
    }

    @Override public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        Vehicle other = (Vehicle) obj;
        if (owner == null) {
            if (other.owner != null)
                return false;
        } else if (!owner.equals(other.owner))
            return false;
        if (vehicleNumber != other.vehicleNumber)
            return false;
        return true;
    }

}
