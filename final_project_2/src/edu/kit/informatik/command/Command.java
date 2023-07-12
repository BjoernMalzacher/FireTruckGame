package edu.kit.informatik.command;

import edu.kit.informatik.Control;
import edu.kit.informatik.Parameter.ParameterBundle;
import edu.kit.informatik.exeption.GameExeption;


/**
 * @author Bjoern Malzacher
 * @version 1.0
 *
 */
public abstract class Command {
 
    /**
     * Bidirectional Connection to the Control-Class 
     */
    protected Control control;
    

    /**
     * Standard constructor for the Command class.
     * 
     * @param control - Bidirectional connection to the Command Class
     */
    public Command(Control control) {
        this.control = control;
    }

    /**
     * returns a regular expression as a String value.
     * @return returns the regular expression of the used Command
     */
    public abstract String getRegex();

    /**
     * Execution of the Command.
     * The needed parameter to execute the Command are saved in the ParameterBundle.

     * @param bundle - Bundle of needed Parameter
     * @throws throws a GraphExeption if the ParameterBundle has false Parameter.
     */
    public abstract void apply(ParameterBundle bundle) throws GameExeption;
 


}
