package edu.kit.informatik.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.kit.informatik.Control;
import edu.kit.informatik.Terminal;
import edu.kit.informatik.Parameter.ParameterBundle;
import edu.kit.informatik.Parameter.PossitionXParameter;
import edu.kit.informatik.Parameter.PossitionYParameter;
import edu.kit.informatik.exeption.GameExeption;
import edu.kit.informatik.exeption.IllegalInputExeption;
import edu.kit.informatik.model.Matchfield;
import edu.kit.informatik.model.Field.Field;
import edu.kit.informatik.model.Field.FirePonds;
import edu.kit.informatik.model.Field.FireStation;
import edu.kit.informatik.model.Field.ForestField;
import edu.kit.informatik.resourcess.ErrorMassanges;
import edu.kit.informatik.vehicle.Vehicle;

/**
 * @author Björn Malzacher
 * @version 1.0 this command class is used to execute the Show-field-command
 *
 */
public class ShowFieldCommand extends Command {
    private static final String SHOW_FIELD_COMMAND_REGEX = "show-field ([0-9]+),([0-9]+)";
    private static final String SEPERATOR = ",";

    /**
     * Standard Constructor
     * 
     * @param control -creates a bidirectional connection to the controll class.
     */
    public ShowFieldCommand(Control control) {
        super(control);
        // TODO Auto-generated constructor stub
    }

    @Override public String getRegex() {
        // TODO Auto-generated method stub
        return SHOW_FIELD_COMMAND_REGEX;
    }

    @Override public void apply(ParameterBundle bundle) throws GameExeption {
        int x = 0;
        int y = 0;
        for (int i = 0; i < bundle.size(); i++) {
            if (bundle.get(i) instanceof PossitionXParameter) {
                x = (int) bundle.get(i).getParameter();
            } else if (bundle.get(i) instanceof PossitionYParameter) {
                y = (int) bundle.get(i).getParameter();
            }
        }
        Matchfield matchfield = control.getMatchField();
        if (x < 0 || y < 0 || x >= matchfield.sizeX() || y >= matchfield.sizeY()) {
            throw new IllegalInputExeption(ErrorMassanges.FALSE_INPUT);
        }
        Field field = matchfield.getField(x, y);
        if (field instanceof ForestField) {
            ForestField fField = (ForestField) field;
            String line = "";
            line = fField.getState().getValue();
            for (Vehicle vehicle : sortVehicleList(fField.getVehicleList())) {
                line += SEPERATOR + vehicle.toString();

            }
            Terminal.printLine(line);
        } else if (field instanceof FireStation) {
            FireStation fStation = (FireStation) field;
            Terminal.printLine(fStation.getPlayer().getName());
        } else if (field instanceof FirePonds) {
            FirePonds fPond = (FirePonds) field;
            Terminal.printLine(fPond.toString());
        }

    }

    // private-Methods

    private ArrayList<Vehicle> sortVehicleList(ArrayList<Vehicle> list) {
        Collections.sort(list, new Comparator<Vehicle>() {

            @Override public int compare(Vehicle o1, Vehicle o2) {
                int i = o1.getowner().getName().compareTo(o2.getowner().getName());
                if (i == 0) {
                    i = o1.getVehicleNumber() - o2.getVehicleNumber();
                }
                return i;
            }
        });
        return list;
    }

}
