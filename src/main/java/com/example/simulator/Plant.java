package com.example.simulator;
import java.util.Random;
import java.util.List;

/**
 * Write a description of class Plant here.
 *
 * @author Darren Sandhu and Marko Surbek
 * @date 04/03/2022
 */
public class Plant implements Actor
{
    // Characteristics shared by all plants (class variables).
    // Whether the plant is alive or not.
    private boolean alive;
    // The plant's field.
    private Field field;
    // The plant's position in the field.
    private Location location;
    // The plant's age
    private double age;
    // The age at which a plant can start to breed.
    private static final int BREEDING_AGE = 8;
    // The age to which a plant can live.
    private static final int MAX_AGE = 10;
    // The likelihood of a plant breeding.
    private double BREEDING_PROBABILITY = 0.1;
    // The minimum probability of the liklihood of a plant breeding.
    private static final double MIN_BREEDING = 0.08;
    // The maximum number of births.
    private static final int MAX_SEED_SIZE = 2;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // The plants disease;
    private Disease plantInfected;
    
    /**
     * Constructor for objects of class Plant
     */
    public Plant(boolean randomAge, Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
        else {
            age = 0;
        }
        plantInfected = new Disease();
    }
    
    public void act(List<Actor> newPlants)
    {
        incrementGrowth();
        if (isAlive()){
            giveBirth(newPlants);
            if (infectedPlant()){
                infectNearbyPlants();
            }
        }
    }
    
    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, location);
    }
    
    public void giveBirth(List<Actor> newPlants)
    {
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(location);
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Plant young = new Plant(false, field, loc);
            newPlants.add(young);
        }
    }
    
    public int breed()
    {
        int seeds = 0;
        if(canBreed() && getRand().nextDouble() <= getBreedingProbability()) {
            seeds = getRand().nextInt(MAX_SEED_SIZE) + 1;
        }
        return seeds;
    }
    
    public void incrementGrowth()
    {
        age ++;
        if (age > MAX_AGE){
            setDead();
        }
    }
    
    /**
     * Indicate that the plant is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }
    
    /**
     * Check whether the plant is alive or not.
     * @return true if the plant is still alive.
     */
    public boolean isAlive()
    {
        return alive;
    }
    
    public boolean canBreed()
    {
        return age >= getBreedingAge();
    }
    
    public int getMaxSeedSize()
    {
        return MAX_SEED_SIZE;
    }
    
    public Random getRand()
    {
        return rand;
    }
    
    public double getBreedingProbability()
    {
        return BREEDING_PROBABILITY;
    }
    
    public boolean infectedPlant()
    {
        return plantInfected.isInfected();
    }
    
    public void makePlantInfected()
    {
        plantInfected.makeInfected();
        infectionEffect();
    }
    
    public void infectNearbyPlants()
    {
        Field x = getField();
        Location l = getLocation();
        List<Object> y = x.getObjectsAtAdjacentLocations(l);
        if (infectedPlant() == true){
            for (Object object : y){
                if (object instanceof Plant){
                    Plant plant = (Plant) object;
                    plant.makePlantInfected();
                }
            }
        }
    }
    
    public void infectionEffect()
    {
        decreaseBreedingProbability();
    }
    
    public void decreaseBreedingProbability()
    {
        BREEDING_PROBABILITY = BREEDING_PROBABILITY * 0.9;
    }
    
    public Disease getDisease()
    {
        return plantInfected;
    }
    
    public int getBreedingAge()
    {
        return BREEDING_AGE;        
    } 
    
    public Location getLocation()
    {
        return location;
    }
    
    public double getMinBreedingProb()
    {
        return MIN_BREEDING;
    }
    
    public double getMaxAge()
    {
        return MAX_AGE;
    }
    
    public Field getField()
    {
        return field;
    }
}
