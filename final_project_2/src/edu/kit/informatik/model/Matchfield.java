package edu.kit.informatik.model;

import edu.kit.informatik.Control;
import edu.kit.informatik.exeption.IllegalMoveExeption;
import edu.kit.informatik.model.Field.Field;
import edu.kit.informatik.model.Field.FirePonds;
import edu.kit.informatik.model.Field.FireStation;
import edu.kit.informatik.model.Field.ForestField;
import edu.kit.informatik.resourcess.ErrorMassanges;
import edu.kit.informatik.vehicle.FireTruck;
import edu.kit.informatik.vehicle.Vehicle;

/**
 * @author Bjoern Malzacher
 * @version 1.0 This class is the Map of the game.
 * 
 *
 */
public class Matchfield {
    private Field[][] playGround;
    private Control control;

    /**
     * This constructor creates Map with the size mXn
     * 
     * @param m       - Y-size of the Map.
     * @param n       - X-size of the Map.
     * @param control - creates a bidirectional connection to the control class
     */
    public Matchfield(int m, int n, Control control) {
        playGround = new Field[m][n];
        this.control = control;
    }

    /**
     * this Method is used to find the Field on which a truck is positioned
     * 
     * @param truck - the truck used to find the field on which is it positioned.
     * @return returns the field on which the truck is positioned, returns null if
     *         the truck does not exist.
     */
    public ForestField findFireTruck(FireTruck truck) {
        for (Field[] fields : playGround) {
            for (Field field : fields) {
                if (field instanceof ForestField) {
                    ForestField fField = (ForestField) field;
                    if (fField.containsVehicle(truck)) {
                        return fField;
                    }
                }
            }
        }
        return null;
    }

    /**
     * this Method is used to Extinguish a Fire on a given Position with a given
     * vehicle.
     * 
     * @param vehicle - the vehicle which should Extinguish the fire.
     * @param x       - the X coordinate of the fire
     * @param y       -the Y coordinate of the fire
     * @return returns true if its possible to extinguish the fire , false if not.
     * @throws IllegalMoveExeption -throws an Exception if the vehicle can not
     *                             Extinguish the Fire because of resource problems.
     */
    public boolean extinguishFire(Vehicle vehicle, int x, int y) throws IllegalMoveExeption {

        if (!vehicle.isActionPossible()) {

            throw new IllegalMoveExeption(ErrorMassanges.NO_ACTIONPOINTS);
        }
        if (!vehicle.isExtingushingPossible()) {
            throw new IllegalMoveExeption(ErrorMassanges.NO_WATERMARK);
        }
        if (!(getField(x, y) instanceof ForestField)) {
            throw new IllegalMoveExeption(ErrorMassanges.IS_NOT_FOREST);
        }

        if (isVehicleNextToFire(vehicle, x, y)) {
            ForestField fField = (ForestField) getField(x, y);
            if (fField.getState() == State.slightlyBurning || fField.getState() == State.StrongBurning) {
                control.getCurrentPlayer().addReputationPoint(1);
            }
            fField.setState(fField.getState().extingushFire());
            vehicle.extinguishing();
            vehicle.actionPerformed();
            return true;
        }
        return false;
    }

    /**
     * @return returns the size of the Map in X direction.
     */
    public int sizeX() {
        return playGround[0].length;
    }

    /**
     * 
     */
    public void resetBurn() {
        for (int i = 0; i < sizeY(); i++) {
            for (int j = 0; j < sizeX(); j++) {
                Field field = getField(j, i);
                if (field instanceof ForestField) {
                    ForestField fField = (ForestField) field;
                    fField.setAlreadyBurnt(false);
                }
            }
        }
    }

    /**
     * @return returns the size of the Map in X direction.
     */
    public int sizeY() {
        return playGround.length;
    }

    // get/set- Methods

    /**
     * returns the X-coordinate of an Field.
     * 
     * @param field - The Field you want to know the coordinate.
     * @return returns an Integer representing the X-coordinate.
     */
    public int getXCoordianteOfField(Field field) {
        for (int i = 0; i < sizeY(); i++) {
            for (int j = 0; j < sizeX(); j++) {
                if (field.equals(getField(j, i))) {
                    return j;
                }
            }
        }
        return -1;
    }

    /**
     * returns the X-coordinate of an Field.
     * 
     * @param field - The Field you want to know the coordinate.
     * @return returns an Integer representing the X-coordinate.
     */
    public int getYCoordianteOfField(Field field) {
        for (int i = 0; i < sizeY(); i++) {
            for (int j = 0; j < sizeX(); j++) {
                if (field.equals(getField(j, i))) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * gives a Field at the X and Y coordinate.
     * 
     * @param x - X-coordinate of the Field
     * @param y - Y-coordinate of the Field.
     * @return -returns the FIeld on position (x,y).
     */
    public Field getField(int x, int y) {
        return playGround[y][x];
    }

    /**
     * sets a new Field at the X,Y coordinate. a Field can not be reset it can only
     * set at the start of the game.
     * 
     * @param x        - the X position of the Field
     * @param y        - the Y- position of the Field.
     * @param newField - the Field object you want to set.
     * @return returns true if the Field is set, false if it is already a FIeld
     */
    public boolean setField(int x, int y, Field newField) {
        if (playGround[y][x] == null) {

            playGround[y][x] = newField;
            return true;
        }
        return false;
    }

    /**
     * checks if a certain vehicle is next to a Burning Field.
     * 
     * @param vehicle -- the vehicle.
     * @param x       - the X-position of the fire
     * @param y       - the Y-position of the fire
     * @return returns true if the vehicle is next to the fire, false if not.
     */
    public boolean isVehicleNextToFire(Vehicle vehicle, int x, int y) {
        if (containsVehicle(vehicle, x + 1, y) || containsVehicle(vehicle, x, y + 1)) {
            return true;
        } else if (containsVehicle(vehicle, x - 1, y) || containsVehicle(vehicle, x, y - 1)) {
            return true;
        } else if (containsVehicle(vehicle, x + 1, y + 1) || containsVehicle(vehicle, x + 1, y + 1)) {
            return true;
        } else if (containsVehicle(vehicle, x - 1, y + 1) || containsVehicle(vehicle, x + 1, y - 1)) {
            return true;
        }
        return false;
    }

    /**
     * checks if a Fire-Station or a Fire-Pond is next to a (x,y) posstion.
     * 
     * @param x -X-coordinate
     * @param y -Y-coordiante
     * @return returns true if its next to it, false if not.
     */
    public boolean isRefillPossible(int x, int y) {
        if (isStationOrPond(x, y + 1) || isStationOrPond(x + 1, y) || isStationOrPond(x, y - 1)) {
            return true;
        } else if (isStationOrPond(x - 1, y) || isStationOrPond(x - 1, y - 1) || isStationOrPond(x + 1, y + 1)) {
            return true;
        } else if (isStationOrPond(x - 1, y + 1) || isStationOrPond(x + 1, y - 1)) {
            return true;
        }
        return false;

    }

    // private-methods

    private boolean containsVehicle(Vehicle vehicle, int x, int y) {
        if ((x < 0 || y < 0) || (x >= sizeX() || y >= sizeY())) {
            return false;
        }
        if (getField(x, y) instanceof ForestField) {
            ForestField fField = (ForestField) getField(x, y);
            for (Vehicle vehcile : fField.getVehicleList()) {
                if (vehcile.equals(vehicle)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isStationOrPond(int x, int y) {
        if (x >= 0 && y >= 0 && x < sizeX() && y < sizeY()) {
            if (getField(x, y) instanceof FirePonds) {
                return true;
            } else if (getField(x, y) instanceof FireStation) {
                FireStation station = (FireStation) getField(x, y);
                if (station.getPlayer().equals(control.getCurrentPlayer())) {
                    return true;
                }
            }
        }
        return false;

    }
}
