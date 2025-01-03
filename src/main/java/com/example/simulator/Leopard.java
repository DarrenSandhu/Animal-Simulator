package com.example.simulator;
import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a leopard.
 * leopardes age, move, eat rabbits, and die.
 * 
 * @author Darren Sandhu and Marko Surbek
 * @date 04/03/2022
 */
public class Leopard extends Animal
{
    // Characteristics shared by all leopard (class variables).
    
    // The age at which a leopard can start to breed.
    private static final int BREEDING_AGE = 3;
    // The age to which a leopard can live.
    private static final int MAX_AGE = 17;
    // The likelihood of a leopard breeding.
    private double BREEDING_PROBABILITY = 0.1;
    // The minimum probability of the liklihood of a leopard breeding.
    private static final double MIN_BREEDING = 0.08;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a leopard can go before it has to eat again.
    private static final int MAX_FOOD_VALUE = 100;

    /**
     * Create a leopard. A leopard can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the leopard will have  random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Leopard(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location);
    }
    
    /**
     * Returns a new actor object which is the instance of the class leopard.
     * @param The randomAge, field and location of the new leopard.
     * @return A new leopard with the given randomAge, field and location.
     */
    public Actor newActor(boolean randomAge, Field field, Location location)
    {
        return new Leopard(randomAge, field, location);
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
        BREEDING_PROBABILITY = BREEDING_PROBABILITY * 0.9;
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
     * Returns the max food value.
     * @return The max food value.
     */
    public int getFoodValue()
    {
        return MAX_FOOD_VALUE;
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
