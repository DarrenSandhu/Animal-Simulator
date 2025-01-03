package com.example.simulator;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author Darren Sandhu and Marko Surbek
 * @date 04/03/2022
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a fox will be created in any given grid position.
    private static final double LEOPARD_CREATION_PROBABILITY = 0.09;
    // The probability that a fox will be created in any given grid position.
    private static final double CHEETAH_CREATION_PROBABILITY = 0.09;
    // The probability that a rabbit will be created in any given grid position.
    private static final double DEER_CREATION_PROBABILITY = 0.4;
    // The probability that a rabbit will be created in any given grid position.
    private static final double BOAR_CREATION_PROBABILITY = 0.4;
    // The probability that a rabbit will be created in any given grid position.
    private static final double TIGER_CREATION_PROBABILITY = 0.02; 
    // The probability that a rabbit will be created in any given grid position.
    private static final double PLANT_CREATION_PROBABILITY = 0.8; 
    
    // List of animals in the field.
    private List<Actor> actors;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // Counter used to initiate or disable behaviour in animals 
    private int behaviourCounter;
    // Counter for day/night behaviour when mutliple steps is processed
    private int numBehaviourCounter;
    
    private String countDisease;

    // Whether the simulation is paused or not
    private final AtomicBoolean pause = new AtomicBoolean(false);

    private Weather weather;

    private Day day;

    private FieldStats stats;


    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
        
    }
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        actors = new ArrayList<>();
        field = new Field(depth, width);
        stats = new FieldStats();
        weather = new Weather();
        day = new Day();
        countDisease = getNumberofInfected();
        step = 0;
        pause.set(false);
        // Create a view of the state of each location in the field.
    
        populate();
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        synchronized (pause) { // Ensure thread-safe reset
            pause.set(true); // Pause the simulation explicitly
            step = 0;
            behaviourCounter = 0;
            actors.clear(); // Clear all current actors
            populate(); // Repopulate the field
            pause.set(false); // Allow the simulation to resume
        }
    }


    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(4000);
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        while (step <= numSteps) {
            if (getPauseStatus()) {
                // Wait until pause is released
                synchronized (pause) {
                    try {
                        pause.wait(); // Wait until `resumeSimulation()` notifies
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // Handle interrupt properly
                    }
                }
            }
            simulateOneStep();
            countDisease = getNumberofInfected();
            delay(60); // Optional: Slow down the simulation for visibility
        }
    }

    /**
     * Pause the simulation.
     */
    public void pauseSimulation()
    {
        pause.set(true);
    }

    /**
     * Resume the simulation.
     */
    public void resumeSimulation()
    {
        synchronized (pause) {
            pause.set(false);
            pause.notifyAll(); // Notify all waiting threads
        }
    }

    /**
     * Get the current pause status of the simulation.
     */
    public boolean getPauseStatus()
    {
        return pause.get();
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep() 
    {
        step++;
        behaviourCounter++;        

        // Toggle day/night every 12 steps
        if (behaviourCounter % 12 == 0) {
            if (day.getTimeOfDay().equals("day")) {
                makeDay("night");
            } else {
                makeDay("day");
            }
            Weather newWeather = new Weather();
            makeWeather(newWeather.getWeather());
        }

        // Synchronize access to shared resources
        List<Actor> newActors = new ArrayList<>();
        List<Actor> actorsToRemove = new ArrayList<>();

        if (day.getTimeOfDay().equals("day")) {
            synchronized (actors) {
                Iterator<Actor> iterator = actors.iterator();
                while (iterator.hasNext()) {
                    Actor actor = iterator.next();

                    if (returnWeather().equals("clear")) {
                        actor.act(newActors);
                    } else {
                        if (actor instanceof Animal) {
                            Animal animal = (Animal) actor;
                            if ((animal.getAge() >= animal.getMaxAge() / 2) || 
                                (animal.getBreedingProbability() <= animal.getMinBreedingProb())) {
                                animal.act(newActors);
                            } else {
                                animal.increaseAge();
                                animal.decreaseBreedingProbability();
                                animal.act(newActors);
                            }
                        } else if (actor instanceof Plant) {
                            actor.act(newActors);
                        }
                    }

                    if (!actor.isAlive()) {
                        actorsToRemove.add(actor);
                    }
                }

                actors.removeAll(actorsToRemove);  // Safely remove actors
            }
        }

        // Safely add new actors
       
        actors.addAll(newActors);
        
    }
    
        
    
    
    /**
     * Randomly populate the field with foxes and rabbits.
     */
    protected void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= LEOPARD_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Leopard leopard = new Leopard(true, field, location);
                    actors.add(leopard);
                }
                else if(rand.nextDouble() <= CHEETAH_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Cheetah cheetah = new Cheetah(true, field, location);
                    actors.add(cheetah);
                }
                else if(rand.nextDouble() <= DEER_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Deer deer = new Deer(true, field, location);
                    actors.add(deer);
                }
                else if(rand.nextDouble() <= BOAR_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Boar boar = new Boar(true, field, location);
                    actors.add(boar);
                }
                else if(rand.nextDouble() <= TIGER_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Tiger tiger = new Tiger(true, field, location);
                    actors.add(tiger);
                }
                else if(rand.nextDouble() <= PLANT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Plant plant = new Plant(true, field, location);
                    actors.add(plant);
                }
                // else leave the location empty.
            }
        }
    }
    
    /**
     * Return the field.
     * @return The field.
     */
    public Field getField()
    {
        return field;
    }

    /**
     * Get the current step of the simulation.
     * @return The current step of the simulation.
     */
    public int getStep()
    {
        return step;
    }

    /**
     * Get the current day of the simulation.
     * @return The current day of the simulation.
     */
    public Day getDay()
    {
        return day;
    }
    
    /**
     * Return string of the the weather.
     * @return The string of the weather.
     */
    public String returnWeather()
    {
        return weather.getWeather();
    }
    
    /**
     * Change weather to the string in the parameter.
     * @param The weather you want to change the current weather to.
     */
    public void makeWeather(String newWeather)
    {
        weather.setWeather(newWeather);
    }
    
    /**
     * Change day to the string in the parameter.
     * @param The day you want to change the current day to.
     */
    public void makeDay(String newDay)
    {
        day.setDay(newDay);

    }
    
    /**
     * Return a string of the number of infected animals.
     * @return The string of the number of infected animals.
     */
    public String getNumberofInfected()
    {
        int infected = field.countDisease();
        String x = Integer.toString(infected);
        return x;
    }

    /**
     * Update the statistics of the field.
     */
    public void updateStats()
    {
        stats.reset();
        List<Actor> actorsCopy = new ArrayList<>(actors);  // Make a copy of the list
        for (Actor actor : actorsCopy) {
            if (actor instanceof Animal) {
                Animal animal = (Animal) actor;
                stats.incrementCount(animal.getClass());
            }
        }
        stats.countFinished();
    }

    /**
     * Get animals and location of the animals in the field.
     * @return
     */
    public String getLocations() 
    {
        List<Pair<Object, Location>> animalLocations = field.getObjectAtAllLocationWithLocation();
        StringBuilder result = new StringBuilder("Locations:\n");
        
        for (Pair<Object, Location> pair : animalLocations) {
            if(animalLocations.size() > 0) {
                if (pair.getFirst() instanceof Animal) {
                    Object animal = pair.getFirst().getClass().getSimpleName();
                    result.append("Animal: ").append(animal);
                }
                else if (pair.getFirst() instanceof Plant) {
                    Object plant = pair.getFirst().getClass().getSimpleName();
                    result.append("Plant: ").append(plant);
                }
                else{
                    result.append("Empty");
                }
                Location location = pair.getSecond();
                
                // Customize the format as you wish; assuming animal and location have toString() implemented:
                result.append(" at Location: ").append(location)
                    .append("\n");
            }
        }
        
        return result.toString();
    }

    /**
     * Return a string of the population details.   
     */
    public String getPopulationDetails()
    {
        updateStats();
        return stats.getPopulationDetails(field);
    }

    /**
     * Return a string of the simulation status.
     * @return The string of the simulation status.
     */
    public String getSimulationStatus()
    {
        String status = "Step: " + getStep() + " Weather: " + capitalizeFirstLetter(returnWeather()) + " Time of Day: " + capitalizeFirstLetter(getDay().getTimeOfDay()) + " Number of Infected: " + getNumberofInfected() + " " + getPopulationDetails();
        return status;
    }
    // public SimulationStatus getSimulationStatus()
    // {
    //     return simulationStatus;
    // }

    /**
     * Capitalize the first letter of a string.
     * @param str The string to capitalize the first letter of.
     * @return The string with the first letter capitalized.
     */
    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    
    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
}
    