package com.example.simulator;
import java.util.Random;

/**
 * Write a description of class Disease here.
 *
 * @author Darren Sandhu and Marko Surbek
 * @date 04/03/2022
 */
public class Disease
{
    // The disease probability.
    private double diseaseProbability;
    // True if is infected.
    private boolean infected;

    /**
     * Constructor for objects of class Disease
     */
    public Disease()
    {
        // initialise instance variables
        randomInfected();
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void makeInfected()
    {
        // put your code here
        infected = true;
    }
    
    public void randomInfected()
    {
        Random rand = new Random();
        diseaseProbability = rand.nextDouble();
        if (diseaseProbability > 0.98){
            infected = true;
        }
    }
    
    public void removeInfection()
    {
        infected = false;
    }
    
    public boolean isInfected()
    {
        if (infected){
            return true;
        }
        return false;
    }
    
    public String stringInfection()
    {
        String infection = new String();
        if (isInfected()){
            infection = "true";
        }
        else {
            infection = "false";
        }
        return infection;
    }
}
