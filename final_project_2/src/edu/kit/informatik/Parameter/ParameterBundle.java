package edu.kit.informatik.Parameter;


import java.util.ArrayList;
import java.util.List;

/**
 * This class is inspired by the solution from exercise sheet number 4 task b. 
 *  A Bundle of Object which inherit the Parameter class. 
 *  
 *  
 * @author Bjoern Malzacher 
 * @version 1.0
 *
 */
public class ParameterBundle {
    private List<Parameter<?>> list = new ArrayList<>();

    /**
     * returns the parameter object at the i position.
     * @param i - position 
     * @return returns the Parameter object at position i; 
     */
    public  Parameter<?> get(int i ) {
        return list.get(i);
    }
    
    /**
     *  adding a new parameter-object to the bundle.
     * @param value - a Object which inherits the Parameter class.
     */
    public void add(Parameter<?> value ) {
        list.add(value);
    }
    
    /**
     * returns the ammount of elements in this bundle as integer value.
     * @return ammount of elements.
     */
    public int size() {
        return list.size();
    }
    
    /**
     * Adding every element from the Parameter bundle to this ParameterBundle.
     * @param bundle - the bundle of Elements which will be added in this Bundle.
     */
    public void mergeBundles(ParameterBundle bundle) {
        for (int i = 0; i < bundle.size(); i++) {
            list.add(bundle.get(i));
        }
    }
}
