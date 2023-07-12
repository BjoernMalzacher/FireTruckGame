package edu.kit.informatik.command;

import edu.kit.informatik.Control;
import edu.kit.informatik.Terminal;
import edu.kit.informatik.Parameter.ParameterBundle;
import edu.kit.informatik.exeption.GameExeption;
import edu.kit.informatik.resourcess.Massenges;

/**
 * @author Björn Malzacher
 * @version 1.0 this command class is used to execute the reset command
 *
 */
public class ResetCommand extends Command {
    private static final String RESET_COMMAND_REGEX = "reset";

    /**
     * Standard Constructor
     * 
     * @param control -creates a bidirectional connection to the control class.
     */
    public ResetCommand(Control control) {
        super(control);
        // TODO Auto-generated constructor stub
    }

    @Override public String getRegex() {
        // TODO Auto-generated method stub
        return RESET_COMMAND_REGEX;
    }

    @Override public void apply(ParameterBundle bundle) throws GameExeption {
        control.reloadMatch();
        Terminal.printLine(Massenges.OK);

    }

}
