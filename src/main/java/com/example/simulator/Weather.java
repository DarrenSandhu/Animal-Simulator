package com.example.simulator;
import java.util.Random;

/**
Class Weather - The random weather of the simulator.
 *
 * This class is part of the "Simulator" application. 
 * "Simulator" is a complex showcase, aimed at replicating the behaviour of a real
 * life animal ecosystem.
 *
 * The weather represents the weather of the simulator which is either day or night, allowing
 * the actors to exhbit different behaviours during the different weathers.
 * 
 * @author Darren Sandhu and Marko Surbek
 * @date 04/03/2022
 * */
public class Weather
{
    // The fog weather.
    private boolean fog;
    // The clear weather.
    private boolean clear;
    // The rain weather.
    private boolean rain;
    
    /**
     * Create a new, random weather by invoking the randomWeather() method
     */
    public Weather()
    {
        randomWeather();
    }

    /**
     * Creates a random boolean of fog, clear or rain, each with an equal probability of 
     * occuring.
     * @return The weather.
     */
    public boolean randomWeather()
    {
        // put your code here
        Random rand = new Random();
        int num = rand.nextInt((12+1)+1)+1;
        if (num <= 4){
            fog = true;
            return fog;
        }
        else if(num > 4 & num <=8){
            clear = true;
            return clear;
        }
        else {
            rain = true;
            return rain;
        }
    }
    
    /**
     * Returns the weather as a string.
     * @return returns a string of the current weather.
     */
    public String getWeather()
    {
        String weather = new String();
    
        if (fog){
            weather = "fog";
        }
        else if (clear){
            weather = "clear";
        }
        else if (rain){
            weather = "rain";
        }
        return weather;
    }
    
    /**
     * Set the weather to the given string, provided it is either fog, clear or rain.
     * @param weather The weather of which to set the new weather.
     */
    public void setWeather(String weather)
    {
        if (weather.equals("fog")){
            fog = true;
            rain = false;
            clear = false;
        }
        else if (weather.equals("rain")){
            rain = true;
            fog = false;
            clear = false;
        }
        else if (weather.equals("clear")){
            clear = true;
            rain = false;
            fog = false;
        }
        else{
            System.out.println("Please enter either fog, clear or rain");
        }
    }
}
