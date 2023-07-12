package edu.kit.informatik;

import edu.kit.informatik.exeption.IllegalMoveExeption;
import edu.kit.informatik.model.Matchfield;
import edu.kit.informatik.model.State;
import edu.kit.informatik.model.Field.Field;
import edu.kit.informatik.model.Field.ForestField;

/**
 * @author Bjoern Malzacher
 * @version 1.0 this class is used to check if a Vehicle can reach curtain
 *          position
 *
 */
public class Path {
    private Matchfield matchField;

    /**
     * Standard constructor
     * 
     * @param field -the Map on which the the path is calculated.
     */
    public Path(Matchfield field) {
        matchField = field;
    }

    /**
     * checks if a vehicle can reach a curtain position.
     * 
     * @param startField - Start-position of the vehicle.
     * @param endField   - end-positiFon of the vehicle.
     * @return true if the vehicle can reach endField, false if not.
     */
    public boolean isReachable(ForestField startField, ForestField endField) {
        if (startField.equals(endField)) {
            throw new IllegalMoveExeption("you are already on this field");
        }
        return checkdirections(startField, endField);

    }

    private boolean checkdirections(Field field, ForestField endField) {
        int x = matchField.getXCoordianteOfField(field);
        int y = matchField.getYCoordianteOfField(field);

        if (y - 1 >= 0) {
            Field northField = matchField.getField(x, y - 1);
            if (checkFieldPassable(northField)) {
                if (northField.equals(endField)) {
                    if (checkFieldStayable(northField)) {
                        return true;
                    } else {
                        return false;
                    }

                } else {
                    if (checkdirectionsSecondTime(northField, endField)) {
                        return true;
                    }
                }
            }
        }
        if (y + 1 < matchField.sizeY()) {
            Field southField = matchField.getField(x, y + 1);
            if (checkFieldPassable(southField)) {
                if (southField.equals(endField)) {
                    if (checkFieldStayable(southField)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if (checkdirectionsSecondTime(southField, endField)) {
                        return true;
                    }
                }
            }
        }
        if (x - 1 >= 0) {
            Field westField = matchField.getField(x - 1, y);
            if (checkFieldPassable(westField)) {
                if (westField.equals(endField)) {
                    if (checkFieldStayable(westField)) {
                        return true;
                    } else {
                        return false;
                    }

                } else {
                    if (checkdirectionsSecondTime(westField, endField)) {
                        return true;
                    }
                }
            }
        }
        if (x + 1 < matchField.sizeX()) {
            Field eastField = matchField.getField(x + 1, y);
            if (checkFieldPassable(eastField)) {
                if (eastField.equals(endField)) {
                    if (checkFieldStayable(eastField)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if (checkdirectionsSecondTime(eastField, endField)) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    private boolean checkdirectionsSecondTime(Field field, ForestField endField) {
        int x = matchField.getXCoordianteOfField(field);
        int y = matchField.getYCoordianteOfField(field);

        if (y - 1 >= 0) {
            Field northField = matchField.getField(x, y - 1);

            if (checkFieldStayable(northField)) {
                if (northField.equals(endField)) {
                    return true;
                }
            }

        }
        if (y + 1 < matchField.sizeY()) {
            Field southField = matchField.getField(x, y + 1);

            if (checkFieldStayable(southField)) {
                if (southField.equals(endField)) {
                    return true;
                }
            }
        }
        if (x - 1 >= 0) {

            Field westField = matchField.getField(x - 1, y);
            if (checkFieldStayable(westField)) {
                if (westField.equals(endField)) {
                    return true;
                }
            }
        }
        if (x + 1 < matchField.sizeX()) {

            Field eastField = matchField.getField(x + 1, y);

            if (checkFieldStayable(eastField)) {
                if (eastField.equals(endField)) {
                    return true;
                }
            }
        }
        return false;

    }

    private boolean checkFieldStayable(Field field) {
        if (field instanceof ForestField) {
            ForestField fField = (ForestField) field;
            if (fField.getState() != State.StrongBurning && fField.getState() != State.slightlyBurning) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean checkFieldPassable(Field field) {
        if (field instanceof ForestField) {
            ForestField fField = (ForestField) field;
            if (fField.getState() != State.StrongBurning) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
