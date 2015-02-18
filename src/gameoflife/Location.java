/*
 *
 *Acquire
 *
 *This is a license header!!! :D
 *
 *
 *
 */
package gameoflife;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * A location is a position on a grid measure in rows and columns.
 * @author Maltbie_N
 */
public class Location 
{
    /**The immutable coordinates of a location within the grid.*/
    private int row, col;
    
    /**
     * Constructs a Location from a row a column.
     * @param row row of the new Location
     * @param col column of the new Location
     */
    public Location(int row, int col)
    {
        this.row = row;
        this.col = col;
    }
    
    /**
     * Constructs a copy of location.
     * @param location Location to replicate.
     */
    public Location(Location location)
    {
        this(location.row, location.col);
    }
    
    /**
     * Gets the row of the Location as an integer
     * @return Returns the row of the Location.
     */
    public int getRow()
    {
        return row;
    }
    
    /**
     * Gets the column of the Location as an integer
     * @return Returns the column of the Location.
     */
    public int getCol()
    {
        return col;
    }
    
    /**
     * Constructs a new Location that has been translated a number of 
     * rows and columns
     * @param dRows Distance traversed in rows; + right, - left.
     * @param dCols Distance traversed in columns; + up, - down.
     * @return Returns a translated Location.
     */
    public Location getTranslated(int dRows, int dCols)
    {
        return new Location(row + dRows, col + dCols);
    }
    
    /**
     * Will return a Collection of the four orthogonally adjacent locations to this
     * location.
     * @return the four orthogonally adjacent locations.
     */
    public Collection<Location> getOrthogonalLocations()
    {
        return new HashSet<>(Arrays.asList(getTranslated(1,0),getTranslated(-1,0)
            ,getTranslated(0,1),getTranslated(0,-1)));
    }
    
    /**
     * Will return a Collection of the eight adjacent locations to this location.
     * @return the eight adjacent locations.
     */
    public Collection<Location> getAdjacentLocations()
    {
        return new HashSet<>(Arrays.asList(
                getTranslated(1,0),
                getTranslated(1,1),
                getTranslated(1,-1),
                getTranslated(-1,1),
                getTranslated(-1,0),
                getTranslated(-1,-1),
                getTranslated(0,-1),
                getTranslated(0,1)
                ));
    }
    
    @Override
    public String toString()
    {
        return "Location row: " + row + ", col: " + col;
    }
    
    @Override
    public boolean equals(Object other)
    {
        if(other instanceof Location)
        {
            Location otherLocation = (Location) other;
            return otherLocation.row == row && otherLocation.col == col;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.row;
        hash = 97 * hash + this.col;
        return hash;
    }
}
