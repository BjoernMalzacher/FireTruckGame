package edu.kit.informatik.command;

import edu.kit.informatik.Control;
import edu.kit.informatik.Terminal;
import edu.kit.informatik.Parameter.ParameterBundle;
import edu.kit.informatik.exeption.GameExeption;
import edu.kit.informatik.model.Matchfield;
import edu.kit.informatik.model.State;

/**
 * @author Björn Malzacher
 * @version 1.0
 * this command class is used to execute the Show-Board command.
 *
 */
public class ShowBoradCommad extends Command {
    private static final String SHOW_BOARD_COMMAND_REGEX = "show-board";
    private static final String SEPERATOR = ",";
    private static final String NOT_ON_FIRE = "x";
    private static final String STRING_STARTVALUE = "";

    /**
     * Standard Constructor
     * @param control -creates a bidirectional connection to the control class.
     */
    public ShowBoradCommad(Control control) {
        super(control);
        // TODO Auto-generated constructor stub
    }

    @Override public String getRegex() {
        // TODO Auto-generated method stub
        return SHOW_BOARD_COMMAND_REGEX;
    }

    @Override public void apply(ParameterBundle bundle) throws GameExeption {
        Matchfield matchField = control.getMatchField();
        String line = STRING_STARTVALUE;
        for (int i = 0; i < matchField.sizeY(); i++) {
            for (int j = 0; j < matchField.sizeX(); j++) {

                String fieldString = matchField.getField(j, i).toString();
                String fire1 = State.slightlyBurning.getValue();
                String fire2 = State.StrongBurning.getValue();
                if (fieldString.equals(fire1) || fieldString.equals(fire2)) {
                    line += matchField.getField(j, i).toString();
                } else {
                    line += NOT_ON_FIRE;
                }
                if (j < matchField.sizeX() - 1) {
                    line += SEPERATOR;
                }

            }
            Terminal.printLine(line);
            line = STRING_STARTVALUE;
        }
    }

}
