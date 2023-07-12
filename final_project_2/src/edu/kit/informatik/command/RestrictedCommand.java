package edu.kit.informatik.command;

import edu.kit.informatik.Control;
import edu.kit.informatik.Parameter.ParameterBundle;
import edu.kit.informatik.exeption.GameExeption;

/**
 * @author Bjoern Malzacher
 * @version 1.0
 * this class is used to differentiate between command which canbe used all the time and
 * restricted commands.
 *
 */
public class RestrictedCommand extends Command {

    /**
     * @param control
     */
    public RestrictedCommand(Control control) {
        super(control);
        // TODO Auto-generated constructor stub
    }

    @Override public String getRegex() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public void apply(ParameterBundle bundle) throws GameExeption {
        // TODO Auto-generated method stub

    }

}
