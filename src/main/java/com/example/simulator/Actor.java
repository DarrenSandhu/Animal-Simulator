package com.example.simulator;
import java.util.List;

/**
 * Constructs the animal's two functions of the animals.
 * Birthing new offspring and checking whether select animals are still alive.
 *
 * @author Darren Sandhu and Marko Surbek
 * @date 04/03/2022
 */
public interface Actor
{
    
    /**
     * Method for adding newly introduced animals to the simulation
     */
    void act(List<Actor> actors);
    
    /**
     * Method for whether the animal is still alive
     * 
     */
    boolean isAlive();
}
