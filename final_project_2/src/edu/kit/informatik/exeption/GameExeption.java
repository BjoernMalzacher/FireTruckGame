package edu.kit.informatik.exeption;

/**
 * @author Bjoern Malzacher 
 * @version 1.0
 * this kind of exception is Thrown if the game has an internal Error. 
 */
public class GameExeption extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    /**
     * Standart constructor.
     * @param massage - Error-Massage
     */
    public GameExeption(String massage) {
        super(massage);
    }

}
