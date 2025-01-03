package com.example.simulator;
import java.util.Random;
/**
Class Sex - The random sex of of all the actors in a simulator.
 *
 * This class is part of the "Simulator" application. 
 * "Simulator" is a complex showcase, aimed at replicating the behaviour of a real
 * life animal ecosystem.
 *
 * A sex represents the sex of the actors which is either male or female, allowing
 * the actors to give birth.
 * 
 * @author Darren Sandhu and Marko Surbek
 * @date 04/03/2022
 * */
public class Sex
{
    // The sex field which is either male or female
    private String sex;
    /**
     * Create a new, random sex by invoking the randomSex() method
     */
    public Sex()
    {
        // initialise a random sex 
        randomSex();
        
    }
    
    /**
     * Creates a random string of male or female, each with an equal probability of 
     * occuring.
     * @return The sex.
     */
    public String randomSex()
    {
        Random rand = new Random();
        if (rand.nextInt((10-1)+1)+1 <= 5){
            sex = "male";
            return sex;
        }
        else{
            sex = "female";
            return sex;
        }
    }
    
    /**
     * Returns the sex of the current object
     * @return returns a string of the current sex object
     */
    public String getSex()
    {
        return sex;
    }
    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    
}
