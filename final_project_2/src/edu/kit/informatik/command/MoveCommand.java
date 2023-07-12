package edu.kit.informatik.command;

import edu.kit.informatik.Control;
import edu.kit.informatik.Terminal;
import edu.kit.informatik.Path;
import edu.kit.informatik.Parameter.FireTruckParameter;
import edu.kit.informatik.Parameter.ParameterBundle;
import edu.kit.informatik.Parameter.PossitionXParameter;
import edu.kit.informatik.Parameter.PossitionYParameter;
import edu.kit.informatik.exeption.GameExeption;
import edu.kit.informatik.exeption.IllegalInputExeption;
import edu.kit.informatik.exeption.IllegalMoveExeption;
import edu.kit.informatik.model.Matchfield;
import edu.kit.informatik.model.Field.Field;
import edu.kit.informatik.model.Field.ForestField;
import edu.kit.informatik.resourcess.ErrorMassanges;
import edu.kit.informatik.resourcess.Massenges;
import edu.kit.informatik.vehicle.FireTruck;

/**
 * @author Björn Malzacher
 * @version 1.0 this command class is used to execute the move-command.
 *
 */
public class MoveCommand extends RestrictedCommand {
    private static final String MOVE_COMMAND_REGEX = "move ([A-D][0-9]+),([0-9]+),([0-9]+)";

    /**
     * Standard Constructor
     * 
     * @param control -creates a bidirectional connection to the controll class.
     */
    public MoveCommand(Control control) {
        super(control);

    }

    @Override public String getRegex() {

        return MOVE_COMMAND_REGEX;
    }

    @Override public void apply(ParameterBundle bundle) throws GameExeption {

        FireTruck truck = null;
        int x = 0;
        int y = 0;
        for (int i = 0; i < bundle.size(); i++) {
            if (bundle.get(i) instanceof FireTruckParameter) {
                truck = (FireTruck) bundle.get(i).getParameter();
            } else if (bundle.get(i) instanceof PossitionXParameter) {
                x = (int) bundle.get(i).getParameter();
            } else if (bundle.get(i) instanceof PossitionYParameter) {
                y = (int) bundle.get(i).getParameter();
            }
        }
        if (truck == null || !truck.getowner().equals(control.getCurrentPlayer())) {
            throw new IllegalInputExeption(ErrorMassanges.NOT_YOUR_TRUCK);
        }
        if (!truck.isActionPerformed()) {
            move(truck, x, y);
        } else {
            throw new IllegalMoveExeption(ErrorMassanges.ACTION_PERFORMED);
        }
    }

    private void move(FireTruck truck, int x, int y) {
        Matchfield matchField = control.getMatchField();
        ForestField oldField = matchField.findFireTruck(truck);
        if (oldField == null) {
            throw new IllegalInputExeption(ErrorMassanges.FIRETRUCK_NOT_EXISTING);
        }
        if (x < 0 || y < 0 || x >= matchField.sizeX() || y >= matchField.sizeY()) {
            throw new IllegalInputExeption(ErrorMassanges.FALSE_INPUT);
        }
        Field field = control.getMatchField().getField(x, y);
        ForestField newField = null;
        if (field instanceof ForestField) {
            newField = (ForestField) field;
        } else {
            throw new IllegalMoveExeption(ErrorMassanges.ONLY_FOREST_FIELDS);
        }

        Path path = new Path(matchField);
        if (path.isReachable(oldField, newField)) {
            oldField.removeVehicle(truck);
            newField.addVehicle(truck);
            truck.move();
            Terminal.printLine(Massenges.OK);
        } else {
            throw new IllegalMoveExeption(ErrorMassanges.POSSITION_NOT_REACHABLE);
        }

    }
}
