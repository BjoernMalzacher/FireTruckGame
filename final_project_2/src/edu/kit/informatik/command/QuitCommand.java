package edu.kit.informatik.command;

import edu.kit.informatik.Control;
import edu.kit.informatik.Parameter.ParameterBundle;
import edu.kit.informatik.exeption.GameExeption;

/**
 * @author Björn Malzacher
 * @version 1.0
 * this command class is used to execute the quit command
 *
 */
public class QuitCommand extends Command {
  
    /**
     * this Boolean is Used to run the Game. 
     * if the boolean is True the game is running. 
     * if false the program has stopped. 
     */
    public static boolean programmIsRunning = true;
    
    private static final String QUIT_COMMAND_REGEX = "quit"; 
    
    /**
     * Standard Constructor
     * @param control -creates a bidirectional connection to the control class.
     */
    public QuitCommand(Control control) {
        super(control);
        // TODO Auto-generated constructor stub
    }

    @Override public String getRegex() {
        return QUIT_COMMAND_REGEX;
    }

    @Override public void apply(ParameterBundle bundle) throws GameExeption {
        programmIsRunning = false; 

    }

}
