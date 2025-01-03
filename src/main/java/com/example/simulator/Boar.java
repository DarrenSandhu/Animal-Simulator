package com.example.simulator;
import java.util.List;
import java.util.Random;
import java.util.Iterator;

/**
 * A simple model of a boar.
 * boars age, move, breed, and die.
 * 
 * @author Darren Sandhu and Marko Surbek
 * @date 04/03/2022
 */
public class Boar extends Animal
{
    // Characteristics shared by all boars (class variables).

    // The age at which a boar can start to breed.
    private static final int BREEDING_AGE = 2;
    // The age to which a boar can live.
    private static final int MAX_AGE = 200;
    // The likelihood of a boar breeding.
    private double BREEDING_PROBABILITY = 0.8;
    // The minimum probability of the liklihood of a boar breeding.
    private static final double MIN_BREEDING = 0.64;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
    // number of steps a fox can go before it has to eat again.
    private static final int MAX_FOOD_VALUE = 1000;
    
    /**
     * Create a new boar. A boar may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the boar will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Boar(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location);
    }
    
    /**
     * Returns a new actor object which is the instance of the class boar.
     * @param The randomAge, field and location of the new boar.
     * @return A new boar with the given randomAge, field and location.
     */
    public Actor newActor(boolean randomAge, Field field, Location location)
    {
        return new Boar(randomAge, field, location);
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
     * Decreases the breeding probability.
     */
    public void decreaseBreedingProbability()
    {
        BREEDING_PROBABILITY = BREEDING_PROBABILITY * 0.8;
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
     * Returns the minimum breeding probability.
     * @return The minimum breeding probability.
     */
    public double getMinBreedingProb()
    {
        return MIN_BREEDING;
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
}
