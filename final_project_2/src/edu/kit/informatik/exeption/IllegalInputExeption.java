package edu.kit.informatik.exeption;

/**
 * @author Bjoern Malzacher
 * @version 1.0
 * this exeption is thrown if the game as an internal error build on the User input.
 *
 */
public class IllegalInputExeption extends GameExeption {

   
    private static final long serialVersionUID = 1L;

    /**
     * Standard constructor
     * @param massage - Error massage
     */
    public IllegalInputExeption(String massage) {
        super(massage);
        // TODO Auto-generated constructor stub
    }

}
