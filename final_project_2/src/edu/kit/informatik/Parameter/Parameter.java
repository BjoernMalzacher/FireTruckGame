package edu.kit.informatik.Parameter;

/**
 * This and the command class was Inspired of the Solution from exercise sheet number 4 task b. 
 * this super class is used to save the parameter of the classes which inherits this class. 
 * this class and all which inherits this class are only used to be saved in the ParameterBundle class. 
 * @author Bjoern Malzacher
 * @version 1.0
 * @param <T> the type of object which will be saved in this Parameter class  
 */
public abstract class Parameter<T> {
    /**
     * the saved value which is saved in this Parameter class. 
     */
    protected T type;
    
   
  
    /**
     * Standard get Method 
     * @return returns the value of the Parameter 
     */
    public T getParameter() {
        return this.type;
    }
    
    
    /**
     * Overwrites the saved value with a new one which has the same type like as the old one.
     * @param param - the new value.
     */
    public abstract void setParameter(T param);
   
}
