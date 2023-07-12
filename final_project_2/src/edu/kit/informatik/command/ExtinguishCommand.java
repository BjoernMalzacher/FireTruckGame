package edu.kit.informatik.command;

import edu.kit.informatik.Control;
import edu.kit.informatik.Terminal;
import edu.kit.informatik.Parameter.FireTruckParameter;
import edu.kit.informatik.Parameter.ParameterBundle;
import edu.kit.informatik.Parameter.PossitionXParameter;
import edu.kit.informatik.Parameter.PossitionYParameter;
import edu.kit.informatik.exeption.GameExeption;
import edu.kit.informatik.exeption.IllegalInputExeption;
import edu.kit.informatik.exeption.IllegalMoveExeption;
import edu.kit.informatik.model.Matchfield;
import edu.kit.informatik.model.Player;
import edu.kit.informatik.model.State;
import edu.kit.informatik.model.Field.Field;
import edu.kit.informatik.model.Field.ForestField;
import edu.kit.informatik.resourcess.ErrorMassanges;
import edu.kit.informatik.resourcess.Massenges;
import edu.kit.informatik.vehicle.FireTruck;

/**
 * @author Björn Malzacher
 * @version 1.0 this command class is used to execute the Extinguish-command.
 * 
 *
 */
public class ExtinguishCommand extends RestrictedCommand {

    private static final String EXTINGUISH_COMMAND_REGEX = "extinguish ([A-D][0-9]+),([0-9]+),([0-9]+)";
    private static final String SEPERATOR = ",";

    /**
     * Standard Constructor
     * 
     * @param control -creates a bidirectional connection to the control class.
     */
    public ExtinguishCommand(Control control) {
        super(control);
        // TODO Auto-generated constructor stub
    }

    @Override public String getRegex() {

        return EXTINGUISH_COMMAND_REGEX;
    }

    @Override public void apply(ParameterBundle bundle) throws GameExeption {
        FireTruck truck = null;
        int x = -1;
        int y = -1;
        for (int i = 0; i < bundle.size(); i++) {
            if (bundle.get(i) instanceof FireTruckParameter) {
                truck = (FireTruck) bundle.get(i).getParameter();
            } else if (bundle.get(i) instanceof PossitionXParameter) {
                x = (int) bundle.get(i).getParameter();
            } else if (bundle.get(i) instanceof PossitionYParameter) {
                y = (int) bundle.get(i).getParameter();
            }

        }
        if (x < 0 || y < 0 || truck == null) {
            throw new IllegalInputExeption(ErrorMassanges.FALSE_INPUT);
        } else if (x >= control.getMatchField().sizeX()) {
            throw new IllegalInputExeption(ErrorMassanges.FALSE_INPUT);
        } else if (y >= control.getMatchField().sizeY()) {
            throw new IllegalInputExeption(ErrorMassanges.FALSE_INPUT);
        }
        Player player = truck.getowner();
        if (!player.isFieldAlreadyExtinguished(truck, x, y)) {
            if (!control.getMatchField().extinguishFire(truck, x, y)) {
                throw new IllegalInputExeption(ErrorMassanges.CAN_NOT_EXTINGUISH_FIRE);
            } else {
                player.setFieldExtinguished(truck, x, y, true);
            }
        } else {
            throw new IllegalMoveExeption(ErrorMassanges.NOT_TWO_TIMES);
        }

        Field field = control.getMatchField().getField(x, y);
        if (!checkifWon()) {
            if (field instanceof ForestField) {
                ForestField fField = (ForestField) field;

                Terminal.printLine(fField.getState().getValue() + SEPERATOR + truck.getActionPoints());
            }
        } else {
            control.setGameWon(true);
            Terminal.printLine(Massenges.WON);
        }

    }

    private boolean checkifWon() {
        Matchfield matchfield = control.getMatchField();
        for (int i = 0; i < matchfield.sizeY(); i++) {
            for (int j = 0; j < matchfield.sizeX(); j++) {
                Field field = matchfield.getField(j, i);
                if (field instanceof ForestField) {
                    ForestField fField = (ForestField) field;
                    if (fField.getState() == State.StrongBurning || fField.getState() == State.slightlyBurning) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
