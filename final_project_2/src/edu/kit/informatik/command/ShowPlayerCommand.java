package edu.kit.informatik.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.kit.informatik.Control;
import edu.kit.informatik.Terminal;
import edu.kit.informatik.Parameter.ParameterBundle;
import edu.kit.informatik.exeption.GameExeption;
import edu.kit.informatik.exeption.IllegalMoveExeption;
import edu.kit.informatik.model.Player;
import edu.kit.informatik.resourcess.ErrorMassanges;
import edu.kit.informatik.vehicle.Vehicle;

/**
 * /**
 * 
 * @author Björn Malzacher
 * @version 1.0 this command class is used to execute the Show-Player command
 *
 */
public class ShowPlayerCommand extends Command {
    private static final String SHOW_PLAYER_COMMAND_REGEX = "show-player";
    private static final String SEPARATOR = ",";

    /**
     * Standard Constructor
     * 
     * @param control -creates a bidirectional connection to the controll class.
     */
    public ShowPlayerCommand(Control control) {
        super(control);
        // TODO Auto-generated constructor stub
    }

    @Override public String getRegex() {
        // TODO Auto-generated method stub
        return SHOW_PLAYER_COMMAND_REGEX;
    }

    @Override public void apply(ParameterBundle bundle) throws GameExeption {

        Player player = control.getCurrentPlayer();
        if (control.isGameWon()) {
            throw new IllegalMoveExeption(ErrorMassanges.GAME_OVER);
        }
        if (player == null) {
            throw new IllegalMoveExeption(ErrorMassanges.GAME_OVER);
        }
        ArrayList<Vehicle> vehicleList = player.getVehicleList();
        sort(vehicleList);
        String line = player.getName() + SEPARATOR + player.getReputationPoints();
        Terminal.printLine(line);
        for (Vehicle vehicle : vehicleList) {
            String vehicleString = vehicle.toString() + SEPARATOR + vehicle.getWaterMark() + SEPARATOR
                    + vehicle.getActionPoints() + SEPARATOR + control.getYPossiton(vehicle) + SEPARATOR
                    + control.getXPossiton(vehicle);
            Terminal.printLine(vehicleString);
        }
    }

    // private-Methods

    private void sort(ArrayList<Vehicle> list) {
        Collections.sort(list, new Comparator<Vehicle>() {

            @Override public int compare(Vehicle o1, Vehicle o2) {
                int compare = o1.getowner().getName().compareTo(o2.getowner().getName());
                if (compare == 0) {
                    return o1.getVehicleNumber() - o2.getVehicleNumber();
                }
                return compare;
            }
        });
    }

}
