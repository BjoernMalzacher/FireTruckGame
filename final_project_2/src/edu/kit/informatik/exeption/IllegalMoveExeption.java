package edu.kit.informatik.exeption;

/**
 * @author Bjoern Malzacher 
 * @version 1.0
 * This Exception is thrown if the game has an Internal Error.
 *
 */
public class IllegalMoveExeption extends GameExeption {

  
    private static final long serialVersionUID = 1L;

    /**
     * Standard Constructor
     * @param massage -Error-massage
     */
    public IllegalMoveExeption(String massage) {
        super(massage);
      
    }
}
