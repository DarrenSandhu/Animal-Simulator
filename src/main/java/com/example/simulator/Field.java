package com.example.simulator;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


import java.util.ArrayList;

/**
 * Represent a rectangular grid of field positions.
 * Each position is able to store a single animal.
 * 
 * @author Darren Sandhu and Marko Surbek
 * @date 04/03/2022
 */
public class Field
{
    // A random number generator for providing random locations.
    private static final Random rand = Randomizer.getRandom();
    
    // The depth and width of the field.
    private int depth, width;
    // Storage for the animals.
    // private Object[][] field.
    private Object[][] field;
    
    /**
     * Represent a field of the given dimensions.
     * @param depth The depth of the field.
     * @param width The width of the field.
     */
    public Field(int depth, int width)
    {
        this.depth = depth;
        this.width = width;
        field = new Object[depth][width];
        
    }
    
    /**
     * Empty the field.
     */
    public void clear()
    {  
        for(int row = 0; row < depth; row++) {
            for(int col = 0; col < width; col++) {
                field[row][col] = null;
            }
        }
    }
    
    /**
     * Clear the given location.
     * @param location The location to clear.
     */
    public void clear(Location location)
    {
        field[location.getRow()][location.getCol()] = null;
    }
    
    /**
     * Place an animal at the given location.
     * If there is already an animal at the location it will
     * be lost.
     * @param animal The animal to be placed.
     * @param row Row coordinate of the location.
     * @param col Column coordinate of the location.
     */
    public void place(Object animal, int row, int col)
    {
        place(animal, new Location(row, col));
    }
    
    /**
     * Place an animal at the given location.
     * If there is already an animal at the location it will
     * be lost.
     * @param animal The animal to be placed.
     * @param location Where to place the animal.
     */
    public void place(Object animal, Location location)
    {
        field[location.getRow()][location.getCol()] = animal;
    }
    
    public void placePlant(Object plant, Location location)
    {
        Object animal = getObjectAt(location);
        // List<Object[][]> locations = new ArrayList<>();
        // Object[][] field2 = new Object[depth][width];
        if (!(animal == null)){
            field[location.getRow()][location.getCol()] = animal;
            
        }
    }
    
    // public void createListOfObjectsInLocation()
    // {
        // List<Object> list = new     
    // }
    
    /**
     * Return the animal at the given location, if any.
     * @param location Where in the field.
     * @return The animal at the given location, or null if there is none.
     */
    public Object getObjectAt(Location location)
    {
        return getObjectAt(location.getRow(), location.getCol());
    }
    
    /**
     * Return the animal at the given location, if any.
     * @param row The desired row.
     * @param col The desired column.
     * @return The animal at the given location, or null if there is none.
     */
    public Object getObjectAt(int row, int col)
    {
        return field[row][col];
    }
    
    /**
     * Generate a random location that is adjacent to the
     * given location, or is the same location.
     * The returned location will be within the valid bounds
     * of the field.
     * @param location The location from which to generate an adjacency.
     * @return A valid location within the grid area.
     */
    public Location randomAdjacentLocation(Location location)
    {
        List<Location> adjacent = adjacentLocations(location);
        return adjacent.get(0);
    }
    
    /**
     * Get a shuffled list of the free adjacent locations.
     * @param location Get locations adjacent to this.
     * @return A list of free adjacent locations.
     */
    public List<Location> getFreeAdjacentLocations(Location location)
    {
        List<Location> free = new LinkedList<>();
        List<Location> adjacent = adjacentLocations(location);
        for(Location next : adjacent) {
            if(getObjectAt(next) == null) {
                free.add(next);
            }
        }
        return free;
    }
    
    /**
     * Try to find a free location that is adjacent to the
     * given location. If there is none, return null.
     * The returned location will be within the valid bounds
     * of the field.
     * @param location The location from which to generate an adjacency.
     * @return A valid location within the grid area.
     */
    public Location freeAdjacentLocation(Location location)
    {
        // The available free ones.
        List<Location> free = getFreeAdjacentLocations(location);
        if(free.size() > 0) {
            return free.get(0);
        }
        else {
            return null;
        }
    }

    /**
     * Return a shuffled list of locations adjacent to the given one.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     * @param location The location from which to generate adjacencies.
     * @return A list of locations adjacent to that given.
     */
    public List<Location> adjacentLocations(Location location)
    {
        assert location != null : "Null location passed to adjacentLocations";
        // The list of locations to be returned.
        List<Location> locations = new LinkedList<>();
        if(location != null) {
            int row = location.getRow();
            int col = location.getCol();
            for(int roffset = -1; roffset <= 1; roffset++) {
                int nextRow = row + roffset;
                if(nextRow >= 0 && nextRow < depth) {
                    for(int coffset = -1; coffset <= 1; coffset++) {
                        int nextCol = col + coffset;
                        // Exclude invalid locations and the original location.
                        if(nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
                            locations.add(new Location(nextRow, nextCol));
                        }
                    }
                }
            }
            
            // Shuffle the list. Several other methods rely on the list
            // being in a random order.
            Collections.shuffle(locations, rand);
        }
        return locations;
    }
    
    /**
     * Return a list of objects adjacent to the given location.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     * @param location The location from which to generate adjacent objetcs.
     * @return A list of objects adjacent to the given location.
     */
    public List<Object> getObjectsAtAdjacentLocations(Location location)
    {
        // The list of locations.
        List<Location> nearbyObjects = adjacentLocations(location);
        // The list of objects to be returned
        List<Object> y = new ArrayList<Object>();
        for (int i = 0; i < nearbyObjects.size(); i ++){
            Location x = nearbyObjects.get(i);
            y.add(getObjectAt(x));
        }
        return y;
    }
    
    /**
     * Return a list of the sex of objects adjacent to the given location.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     * @param location The location from which to generate adjacent sex of objects.
     * @return A list of the sex of all objects  adjacent to the given location.
     */
    public List<String> getSexOfObjectAtAdjacentLocation(Location location)
    {
        // The list of objects adjacent to given location.
        List<Object> y = getObjectsAtAdjacentLocations(location);
        // The list of the sex of all the adjacent objects to be returned
        List<String> j = new ArrayList<String>();
        for (int i = 0; i < y.size(); i ++){
            Object x = y.get(i);
            // Checks if object is an instance of animal.
            if (x instanceof Animal){
                Animal animal = (Animal) x;
                j.add(animal.isSex());
            }
        }
        return j;
    }
    
    public List<Object> getObjectAtAllLocation()
    {
        List<Object> x = new ArrayList<>();
        for(int row = 0; row < depth; row++) {
            for(int col = 0; col < width; col++) {
                x.add(getObjectAt(row, col));
            }
        }
        return x;
    }


public List<Pair<Object, Location>> getObjectAtAllLocationWithLocation() 
    {
        List<Pair<Object, Location>> result = new ArrayList<>();
        
        for (int row = 0; row < depth; row++) {
            for (int col = 0; col < width; col++) {
                Object objAtLocation = getObjectAt(row, col);
                Location location = new Location(row, col);
                result.add(new Pair<>(objAtLocation, location));  // Add the Pair to the result list
            }
        }
        
        return result;
    }

    
    public List<String> getDiseaseOfObjectAtAllLocation()
    {
        List <Object> objects = getObjectAtAllLocation();
        List<String> diseases = new ArrayList<>();
        for (Object object : objects){
            if (object instanceof Animal){
                Animal animal = (Animal) object; 
                String y = animal.getDisease().stringInfection();
                diseases.add(y);
            }
            else if (object instanceof Plant){
                Plant plant = (Plant) object; 
                String y = plant.getDisease().stringInfection();
                diseases.add(y);
            }
        }
        return diseases;
    }
    
    public int countDisease()
    {
        List<String> countDisease = getDiseaseOfObjectAtAllLocation();
        int count = 0;
        for (String disease : countDisease){
            if (disease.equals("true")){
                count++;
            }
        }
        return count;
    }
    
    /**
     * Return a list of strings adjacent to the given one.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     * @param x The list of all the sex.
     * @return A list of strings of the sex from given sex list.
     */
    public List<String> getOneSex(List<Sex> x)
    {
        // The list of all the sex to be returned
        List<String> sexes = new ArrayList<String>();
        for (int i = 0; i < x.size(); i++){
            Sex y = x.get(i);
            sexes.add(y.getSex());
        }
        return sexes;
    }
    
    /**
     * Return the sex of an animal at the given location.
     * All locations will lie within the grid.
     * @param location The location from which to generate the sex of the object.
     * @return The sex of the object.
     */
    public String getSexOfObjectAtLocation(Location location)
    {
        // The object at the given location.
        Object x = getObjectAt(location);
        if (x instanceof Animal){
                Animal animal = (Animal) x;
                return animal.isSex(); // returns the sex of the object
        }
        return null;
    }
    
    /**
     * Return the depth of the field.
     * @return The depth of the field.
     */
    public int getDepth()
    {
        return depth;
    }
    
    /**
     * Returns the field.
     * @return The field object.
     */
    public Object[][] returnField()
    {
        return field;
    }

    /**
     * Return boolean wetaher the location is empty or not.
     * @return
     */
    public boolean isEmpty(Location location)
    {
        return getObjectAt(location) == null;
    }
    
    /**
     * Return the width of the field.
     * @return The width of the field.
     */
    public int getWidth()
    {
        return width;
    }
}
