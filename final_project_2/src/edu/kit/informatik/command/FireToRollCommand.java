package edu.kit.informatik.command;

import edu.kit.informatik.Control;
import edu.kit.informatik.Terminal;
import edu.kit.informatik.Parameter.NumberParameter;
import edu.kit.informatik.Parameter.ParameterBundle;
import edu.kit.informatik.exeption.GameExeption;
import edu.kit.informatik.exeption.IllegalInputExeption;
import edu.kit.informatik.exeption.IllegalMoveExeption;
import edu.kit.informatik.model.Matchfield;
import edu.kit.informatik.model.State;
import edu.kit.informatik.model.Field.Field;
import edu.kit.informatik.model.Field.ForestField;
import edu.kit.informatik.resourcess.ErrorMassanges;
import edu.kit.informatik.resourcess.Massenges;

/**
 * @author Björn Malzacher
 * @version 1.0 this command class is used to execute the fire-to-roll command
 *
 */
public class FireToRollCommand extends Command {
    private static final String FIRE_TO_ROLL_COMMAND_REGEX = "fire-to-roll ([1-6])$";
    private static final int NORTH = 2;
    private static final int SOUTH = 4;
    private static final int WEST = 3;
    private static final int EAST = 5;
    private static final int EVERYWHERE = 1;
    private static final int NOTHING = 6;

    /**
     * Standard Constructor
     * 
     * @param control -creates a bidirectional connection to the control class.
     */
    public FireToRollCommand(Control control) {
        super(control);
    }

    @Override public String getRegex() {
        return FIRE_TO_ROLL_COMMAND_REGEX;
    }

    @Override public void apply(ParameterBundle bundle) throws GameExeption {
        if (!control.isNewRound()) {
            throw new IllegalMoveExeption("you can only use this command after a round.");
        }
        int number = -1;
        for (int i = 0; i < bundle.size(); i++) {
            if (bundle.get(i) instanceof NumberParameter) {
                number = (int) bundle.get(i).getParameter();
            }
        }
        if (number < 0) {
            throw new IllegalInputExeption(ErrorMassanges.FALSE_NUMBER);
        }
        if (number != NOTHING) {
            slightlytoStrong();
        }
        Matchfield mField = control.getMatchField();
        for (int i = 0; i < mField.sizeY(); i++) {
            for (int j = 0; j < mField.sizeX(); j++) {
                if (mField.getField(j, i) instanceof ForestField) {
                    ForestField fField = (ForestField) mField.getField(j, i);
                    if (fField.getState() == State.StrongBurning && !fField.isAlreadyBurnt()) {
                        fireSpread(j, i, number);

                    }
                }
            }

        }

        if (!control.getPlayerList().isEmpty()) {
            if (!control.throwOutPlayer()) {
                Terminal.printLine(Massenges.OK);

            } else {
                if (control.getNextPlayer() != null) {
                    Terminal.printLine(control.getNextPlayer().getName());
                }
            }
        } else {
            Terminal.printLine(Massenges.LOSE);
        }
        control.getMatchField().resetBurn();
        control.setNewRound(false);
    }

    // private-Methods

    private void fireSpread(int x, int y, int number) {

        switch (number) {
            case NORTH:
                burningField(x, y - 1);
                break;
            case SOUTH:
                burningField(x, y + 1);

                break;
            case WEST:
                burningField(x - 1, y);
                break;
            case EAST:
                burningField(x + 1, y);
                break;
            case EVERYWHERE:
                burningField(x - 1, y);
                burningField(x + 1, y);
                burningField(x, y - 1);
                burningField(x, y + 1);
                break;
            default:
                break;
        }

    }

    private void slightlytoStrong() {
        for (int k = 0; k < control.getMatchField().sizeY(); k++) {
            for (int k2 = 0; k2 < control.getMatchField().sizeX(); k2++) {
                Field field = control.getMatchField().getField(k2, k);
                if (field instanceof ForestField) {
                    ForestField fField = (ForestField) field;
                    if (fField.getState() == State.slightlyBurning) {
                        fField.setState(fField.getState().burning());
                        burnDownVehicle(fField);
                        fField.setAlreadyBurnt(true);
                    }
                }
            }
        }
    }

    private void burningField(int x, int y) {
        Matchfield mField = control.getMatchField();
        if (x >= 0 && y >= 0) {
            if (x < mField.sizeX() && y < mField.sizeY()) {
                Field field = mField.getField(x, y);
                if (field instanceof ForestField) {
                    ForestField fField = (ForestField) field;
                    if (!fField.isAlreadyBurnt()) {
                        fField.setState(fField.getState().burning());
                        burnDownVehicle(fField);
                        if (fField.getState() != State.StrongBurning) {
                            fField.setAlreadyBurnt(true);
                        }
                    }
                }
            }
        }
    }

    private void burnDownVehicle(ForestField fField) {
        if (fField.getState() == State.StrongBurning) {

            fField.getVehicleList().clear();
            control.clearBurnedTrucks();
        }
    }

}
