package com.example.simulator;
import java.util.Random;

/**
Class Day - The random day of the simulator.
 *
 * This class is part of the "Simulator" application. 
 * "Simulator" is a complex showcase, aimed at replicating the behaviour of a real
 * life animal ecosystem.
 *
 * A day represents the day of the simulator which is either day or night, allowing
 * the actors to rest.
 * 
 * @author Darren Sandhu and Marko Surbek
 * @date 04/03/2022
 * */
public class Day
{
    // True if it is day.
    private boolean day; 
    // True if it is night.
    private boolean night;

    /**
     * Create a new, random day by invoking the randomDay() method
     */
    public Day()
    {
        // initialise instance variables
        randomTimeOfDay();
    }

    /**
     * Creates a random time of day, each with an equal probability of occuring.
     */
    public void randomTimeOfDay()
    {
        // put your code here
        Random rand = new Random();
        if (rand.nextInt((10-1)+1)+1 <= 5){
            day = true;
        }
        else{
            night = true;
        }
    }
    
    /**
     * Returns a string of the time of day.
     * @return The time of day.
     */
    public String getTimeOfDay()
    {
        String dayTime = new String();
        if (day) {
            dayTime = "day";
            return dayTime;
        }
        else {
            dayTime = "night";
            return dayTime;
        }
    }
    
    /**
     * Set the day to the given string, provided it is either night or day.
     * @param theDay The day of which to set the new day.
     */
    public void setDay(String theDay)
    {
        if (theDay.equals("day")){
            day = true;
            night = false;
        }
        else if (theDay.equals("night")){
            night = true;
            day = false;
        }
        else{
            System.out.println("Please choose either night or day.");
        }
    }
}
