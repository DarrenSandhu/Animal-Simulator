package com.example.simulator;
import java.util.List;
import java.util.Random;
import java.util.Iterator;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author Darren Sandhu and Marko Surbek
 * @date 04/03/2022
 */
public abstract class Animal implements Actor
{
    private Sex sex;
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    // The animal's age
    private int age;
    // The animals disease;
    private Disease animalInfected;
    // The animal's food level.
    private int foodLevel;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(boolean randomAge, Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
        sex = new Sex();
        animalInfected = new Disease();
        if(randomAge) {
            double rangeMin = 0.1;
            double rangeMax = getMaxAge();
            double randomValue = rangeMin + (rangeMax - rangeMin) * rand.nextDouble();
            age = rand.nextInt(getMaxAge());
            foodLevel = rand.nextInt(getFoodValue());
        }
        else {
            age = 0;
            foodLevel = getFoodValue();
        }
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    public void act(List<Actor> newAnimals)
    {
        int step = 0;
        incrementAge();
        incrementHunger();
        if(isAlive())  {
                giveBirth(newAnimals);            
                // Move towards a source of food if found.
                Location newLocation = findFood();
                if (step == 3){
                    removeDisease();
                }
                else if (animalInfected.isInfected() == true){
                    infectNearbyAnimals();
                    step ++;
                }
                
                if(newLocation == null) { 
                    // No food found - try to move to a free location.
                    newLocation = getField().freeAdjacentLocation(getLocation());
                }
                // See if it was possible to move.
                if(newLocation != null) {
                    setLocation(newLocation);
                }
                else {
                    // Overcrowding.
                    setDead();
                }
        }
    }
    
    /**
     * Look for availabe animals adjacent to the current location of the current
     * animal.
     * Only the first live animal is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    public Location findFood()
    {
        Field field = getField();
        Location location = getLocation();
        Object animal = field.getObjectAt(location);
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        // Checks if animal is a leopard or cheetah
        if (animal instanceof Leopard || animal instanceof Cheetah){
            while(it.hasNext()) {
                Location where = it.next();
                Object prey = field.getObjectAt(where);
                if(prey instanceof Deer){
                    Deer deer = (Deer) prey;
                    if(deer.isAlive()) { 
                        deer.setDead();
                        foodLevel = getFoodValue();
                        return where;
                    }
                }
                else if(prey instanceof Boar){
                    Boar boar = (Boar) prey;
                    if(boar.isAlive()) { 
                        boar.setDead();
                        foodLevel = getFoodValue();
                        return where;
                    }
                }
            }
        }
        // checks if animal is a tiger
        else if (animal instanceof Tiger){
            while(it.hasNext()) {
                Location where = it.next();
                Object prey = field.getObjectAt(where);
                if(prey instanceof Leopard) {
                    Leopard leopard = (Leopard) prey;
                    if(leopard.isAlive()) { 
                        leopard.setDead();
                        foodLevel = getFoodValue();
                        return where;
                    }
                }
                else if(prey instanceof Deer){
                    Deer deer = (Deer) prey;
                    if(deer.isAlive()) { 
                        deer.setDead();
                        foodLevel = getFoodValue();
                        return where;
                    }
                }
                else if(prey instanceof Cheetah){
                    Cheetah cheetah = (Cheetah) prey;
                    if(cheetah.isAlive()) { 
                        cheetah.setDead();
                        foodLevel = getFoodValue();
                        return where;
                    }
                }
                else if(prey instanceof Boar){
                    Boar boar = (Boar) prey;
                    if(boar.isAlive()) { 
                        boar.setDead();
                        foodLevel = getFoodValue();
                        return where;
                    }
                }
            }
        }
        // checks if animal is a deer or boar
        else if (animal instanceof Deer || animal instanceof Boar){
            while(it.hasNext()) {
                Location where = it.next();
                Object food = field.getObjectAt(where);
                if(food instanceof Plant){
                    Plant plant = (Plant) food;
                    if(plant.isAlive()) { 
                        plant.setDead();
                        foodLevel = getFoodValue();
                        return where;
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * Get food value of animal.
     */
    abstract public int getFoodValue();
    
    /**
     * Make this animal more hungry. This could result in the animal's death.
     */
    public void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    /**
     * Checks if animal is infected.
     * @return Returns weather the animal is infected or not.
     */
    public boolean infectedAnimal()
    {
        return animalInfected.isInfected();
    }
    
    /**
     * Makes animal infected.
     */
    public void makeAnimalInfected()
    {
        animalInfected.makeInfected();
    }
    
    /**
     * The animal makes all adjacent animals infected if the animal is infected.
     */
    public void infectNearbyAnimals()
    {
        Field x = getField();
        Location l = getLocation();
        List<Object> y = x.getObjectsAtAdjacentLocations(l);
        if (infectedAnimal() == true){
            for (Object animal : y){
                if (animal instanceof Animal){
                    Animal animals = (Animal) animal;
                    animals.makeAnimalInfected();
                }
            }
        }
    }
    
    /**
     * The infection effect if the animal is infected
     */
    public void infectionEffect()
    {
        increaseAge();
    }
    /**
     * Removes disease of the animal so they are no longer infected
     */
    public void removeDisease()
    {
        animalInfected.removeInfection();
    }
    
    /**
     * Returns wether animal is infected
     * @return The disease of the animal is returned
     */
    public Disease getDisease()
    {
        return animalInfected;
    }
    
    /**
     * Returns the food level of the animal.
     * @return The food level of the animal.
     */
    public int getFoodLevel()
    {
        return foodLevel;
    }
    
    /**
     * Returns the random generator.
     * @return The shared random generator.
     */
    public Random getRand()
    {
        return rand;
    }
    
    /**
     * Create a new animal. An animal may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the animal will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    abstract public Actor newActor(boolean randomAge, Field field, Location location);
    
    /**
     * Check whether or not this animal is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newActors A list to return newly born animals.
     */
    public void giveBirth(List<Actor> newActors)
    {
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        List<Location> nearbyAnimals = field.adjacentLocations(getLocation());
        String m = "male";
        String f = "female";
        int births = breed();
        if ((field.getSexOfObjectAtAdjacentLocation(getLocation()).contains(m) && field.getSexOfObjectAtLocation(getLocation()).equals(f)) || (field.getSexOfObjectAtAdjacentLocation(getLocation()).contains(f) && field.getSexOfObjectAtLocation(getLocation()).equals(m)) ){
            for(int b = 0; b < births && free.size() > 0; b++) {
                Location loc = free.remove(0);
                Actor young = newActor(false, field, loc);
                newActors.add(young);
            }
        }
    }
    
    /**
     * Returns the string of the sex of the animal.
     * @return The string of the sex of the animal.
     */
    public String isSex()
    {
        return sex.getSex();
    }
    
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && getRand().nextDouble() <= getBreedingProbability()) {
            births = getRand().nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }
    
    /**
     * Returns the age of the animal.
     * @return The age of the animal.
     */
    public int getAge()
    {
        return age;
    }
    
    /**
     * A animal can breed if it has reached the breeding age.
     */
    public boolean canBreed()
    {
        return getAge() >= getBreedingAge();
    }
    
    /**
     * Increase the age. This could result in the animals's death.
     */
    public void incrementAge()
    {
        age++;
        if(age > getMaxAge()) {
            setDead();
        }
    }
    
    /**
     * Get the max litter size of the animal.
     */
    abstract protected int getMaxLitterSize(); 
    
    /**
     * Get the breeding age of the animal.
     */
    abstract protected int getBreedingAge();
    
    /**
     * Get the minimum breeding probability of the animal.
     */
    abstract protected double getMinBreedingProb();
    
    /**
     * Get the max age of the animal.
     */
    abstract protected int getMaxAge();
    
    /**
     * Get the breeding probabilty of the animal.
     */
    abstract protected double getBreedingProbability();
    
    /**
     * Decrease the breeding probability of the animal.
     */
    abstract protected void decreaseBreedingProbability();
    
    protected void increaseAge()
    {
        age++;
    }
    
    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    public boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
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
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }
}
