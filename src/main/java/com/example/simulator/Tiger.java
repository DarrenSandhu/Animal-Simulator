package com.example.simulator;
import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a tiger.
 * tigers age, move, breed, and die.
 *
 * @author Darren Sandhu and Marko Surbek
 * @date 04/03/2022
 */
public class Tiger extends Animal
{
    // Characteristics shared by all tigeres (class variables).
    
    // The age at which a tiger can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a tiger can live.
    private static final int MAX_AGE = 24;
    // The likelihood of a tiger breeding.
    private double BREEDING_PROBABILITY = 0.055;
    // The minimum probability of the liklihood of a tiger breeding.
    private static final double MIN_BREEDING = 0.05;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a tiger can go before it has to eat again.
    private static final int MAX_FOOD_VALUE = 100;
    

    /**
     * Create a tiger. A tiger can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the tiger will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Tiger(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location);
    }
    
    /**
     * Returns a new actor object which is the instance of the class tiger.
     * @param The randomAge, field and location of the new tiger.
     * @return A new tiger with the given randomAge, field and location.
     */
    public Actor newActor(boolean randomAge, Field field, Location location)
    {
        return new Tiger(randomAge, field, location);
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
     * Returns the breeding age.
     * @return The breeding age.
     */
    public int getBreedingAge()
    {
        return BREEDING_AGE;    
    }
    
    /**
     * Returns the max age.
     * @return The max age.
     */
    public int getMaxAge()
    {
        return MAX_AGE;
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
     * Decreases the breeding probability.
     */
    public void decreaseBreedingProbability()
    {
        BREEDING_PROBABILITY = BREEDING_PROBABILITY * 0.9;
    }
    
    /**
     * Returns the max food value.
     * @return The max food value.
     */
    public int getFoodValue()
    {
        return MAX_FOOD_VALUE;
    }
}
