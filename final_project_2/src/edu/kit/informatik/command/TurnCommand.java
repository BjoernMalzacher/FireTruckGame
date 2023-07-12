package edu.kit.informatik.command;

import edu.kit.informatik.Control;
import edu.kit.informatik.Terminal;
import edu.kit.informatik.Parameter.ParameterBundle;
import edu.kit.informatik.exeption.GameExeption;
import edu.kit.informatik.exeption.IllegalMoveExeption;
import edu.kit.informatik.model.Player;
import edu.kit.informatik.resourcess.ErrorMassanges;
import edu.kit.informatik.vehicle.Vehicle;

/**
 * @author Björn Malzacher
 * @version 1.0 this command class is used to execute the Turn command
 *
 */
public class TurnCommand extends RestrictedCommand {
    private static final String TURN_COMMAND_REGEX = "turn";

    /**
     * Standard Constructor
     * 
     * @param control -creates a bidirectional connection to the control class.
     */
    public TurnCommand(Control control) {
        super(control);
        // TODO Auto-generated constructor stub
    }

    @Override public String getRegex() {
        // TODO Auto-generated method stub
        return TURN_COMMAND_REGEX;
    }

    @Override public void apply(ParameterBundle bundle) throws GameExeption {
        if (!control.isGameWon()) {

            if (control.getPlayerList().isEmpty()) {
                throw new IllegalMoveExeption(ErrorMassanges.GAME_OVER);
            }
            Player player = control.getCurrentPlayer();

            for (Vehicle vehicle : player.getVehicleList()) {
                vehicle.resetActionPerformed();
                vehicle.reloadActionPoints();
                player.resetExtinguishMap(vehicle);
            }

            Terminal.printLine(control.nextPlayer().getName());

        } else {
            throw new IllegalMoveExeption(ErrorMassanges.GAME_OVER);
        }
    }

}
