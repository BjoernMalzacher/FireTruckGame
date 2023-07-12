package edu.kit.informatik.command;

import edu.kit.informatik.Control;
import edu.kit.informatik.Terminal;
import edu.kit.informatik.Parameter.FireTruckParameter;
import edu.kit.informatik.Parameter.ParameterBundle;
import edu.kit.informatik.exeption.GameExeption;
import edu.kit.informatik.exeption.IllegalMoveExeption;
import edu.kit.informatik.model.Matchfield;
import edu.kit.informatik.model.Field.ForestField;
import edu.kit.informatik.resourcess.ErrorMassanges;
import edu.kit.informatik.vehicle.FireTruck;
import edu.kit.informatik.vehicle.Vehicle;

/**
 * @author Björn Malzacher
 * @version 1.0 this command class is used to execute the Refill command
 *
 */
public class RefillCommand extends Command {
    private static final String REFILL_COMMAND_REGEX = "refill ([A-D][0-9]+)$";

    /**
     * Standard Constructor
     * 
     * @param control -creates a bidirectional connection to the control class.
     */
    public RefillCommand(Control control) {
        super(control);
        // TODO Auto-generated constructor stub
    }

    @Override public String getRegex() {
        // TODO Auto-generated method stub
        return REFILL_COMMAND_REGEX;
    }

    @Override public void apply(ParameterBundle bundle) throws GameExeption {

        Vehicle truck = null;
        for (int i = 0; i < bundle.size(); i++) {
            if (bundle.get(i) instanceof FireTruckParameter) {
                truck = (FireTruck) bundle.get(i).getParameter();
            }
        }
        if (truck.getActionPoints() <= 0) {
            throw new IllegalMoveExeption(ErrorMassanges.NO_ACTIONPOINTS);
        }
        if (!control.isGameWon()) {
            if (!refillVehicle(truck)) {
                throw new IllegalMoveExeption(ErrorMassanges.REFILL_NOT_POSSIBLE);
            }
        } else {
            throw new IllegalMoveExeption(ErrorMassanges.GAME_OVER);
        }
    }

    // private Methods

    private boolean refillVehicle(Vehicle vehicle) {

        Matchfield matchField = control.getMatchField();
        for (int i = 0; i < matchField.sizeY(); i++) {
            for (int j = 0; j < matchField.sizeX(); j++) {
                if (matchField.getField(j, i) instanceof ForestField) {
                    ForestField field = (ForestField) matchField.getField(j, i);
                    if (containsFireTruck(field, vehicle) && matchField.isRefillPossible(j, i)) {
                        if (vehicle.refill()) {
                            vehicle.actionPerformed();
                            Terminal.printLine(vehicle.getActionPoints());
                            return true;
                        } else {
                            throw new IllegalMoveExeption(ErrorMassanges.ENOTH_WATER);
                        }
                    }
                }

            }
        }
        return false;
    }

    private boolean containsFireTruck(ForestField field, Vehicle vehicle) {
        return field.getVehicleList().contains(vehicle);
    }

}
