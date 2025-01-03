package com.example.simulator;
import java.util.List;
import java.util.Random;
import java.util.Iterator;

/**
 * A simple model of a deer.
 * deers age, move, breed, and die.
 * 
 * @author Darren Sandhu and Marko Surbek
 * @date 04/03/2022
 */
public class Deer extends Animal
{
    // Characteristics shared by all deers (class variables).

    // The age at which a deer can start to breed.
    private static final int BREEDING_AGE = 2;
    // The age to which a deer can live.
    private static final int MAX_AGE = 200;
    // The likelihood of a deer breeding.
    private double BREEDING_PROBABILITY = 0.8;
    // The minimum probability of the liklihood of a deer breeding.
    private static final double MIN_BREEDING = 0.64;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
    // number of steps a fox can go before it has to eat again.
    private static final int MAX_FOOD_VALUE = 1000;

    /**
     * Create a new deer. A deer may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the deer will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Deer(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location);
    }
    
    /**
     * Returns a new actor object which is the instance of the class deer.
     * @param The randomAge, field and location of the new deer.
     * @return A new deer with the given randomAge, field and location.
     */
    public Actor newActor(boolean randomAge, Field field, Location location)
    {
        return new Deer(randomAge, field, location);
    }
    
    /**
     * Returns the max litter size.
     * @return The max litter size.
     */
    public int getMaxLitterSize()
    {
        return MAX_LITTER_SIZE;
    }
    
    /**
     * Returns the breeding probability.
     * @return The breeding probability.
     */
    public double getBreedingProbability()
    {
        return BREEDING_PROBABILITY;
    }
    
    /**
     * Returns the max food value.
     * @return The max food value.
     */

    public int getFoodValue()
    {
        return MAX_FOOD_VALUE;
    }
    
    /**
     * Decreases the breeding probability.
     */
    public void decreaseBreedingProbability()
    {
        BREEDING_PROBABILITY = BREEDING_PROBABILITY * 0.8;
    }
    
    /**
     * Returns the breeding age.
     * @return The breeding age.
     */
    public int getBreedingAge()
    {
        return BREEDING_AGE;        
    } 
    
    /**
     * Returns the minimum breeding probability.
     * @return The minimum breeding probability.
     */
    public double getMinBreedingProb()
    {
        return MIN_BREEDING;
    }
    
    /**
     * Returns the max age.
     * @return The max age.
     */
    public int getMaxAge()
    {
        return MAX_AGE;
    }
}
