/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife;

import java.awt.Rectangle;

/**
 * This represents a world that cells live in. Each location always has 
 * @author Nick_Pro
 */
public class World
{
    private boolean[][] world;
    
    public World(int size)
    {
        world = new boolean[size][size];
    }
    
    public boolean isAlive(Location l)
    {
        return world[l.getRow()][l.getCol()];
    }
    
    public void toggle(Location l)
    {
        world[l.getRow()][l.getCol()] = !isAlive(l);
    }
    
    public void die(Location l)
    {
        world[l.getRow()][l.getCol()] = false;
    }
    
    public void spawn(Location l)
    {
        world[l.getRow()][l.getCol()] = true;
    }
    
    public Rectangle getActiveArea()
    {
        /*&int uppermost = world.length;
        int lowest = -1;
        int rightmost = -1;
        int leftmost = world[0].length;
        
        for(int row = 0; row < world.length; row++)
            for(int col = 0; col < world[0].length; col++)
                if(isAlive(new Location(row,col)))
                {
                    if(row < uppermost)
                        uppermost = row;
                    if(row > lowest)
                        lowest = row;
                    if(col < leftmost)
                        leftmost = col;
                    if(col > rightmost)
                        rightmost = col;
                }
        
        if(lowest != -1)
            return new Rectangle(leftmost, uppermost, rightmost-leftmost, lowest-uppermost);*/
        return new Rectangle(0,0,world.length, world[0].length);
        //return null;
    }
    
    public boolean isWithinGrid(Location loc)
    {
        return loc.getRow() > -1 && loc.getRow() < world.length && loc.getCol() > -1 && loc.getCol() < world[0].length;
    }
    
    public Location putWithinGrid(Location loc)
    {
        if(isWithinGrid(loc))
            return loc;
        
        int row = loc.getRow();
        int col = loc.getCol();
        
        while(row < 0)
            row += world.length;
        if(row >= world.length)
            row %= world.length;
        
        while(col < 0)
            col += world[0].length;
        if(col >= world[0].length)
            col %= world[0].length;
        
        return new Location(row, col);
    }
    
    public int getAliveAround(Location loc)
    {
        int alive = 0;
        for(Location n : loc.getAdjacentLocations())
        {
            n = putWithinGrid(n);
            if(isAlive(n))
                alive++;
        }
        return alive;
    }
    
    public void step()
    {
        boolean[][] copy = new boolean[world.length][world[0].length];
        for(int row = 0; row < world.length; row++)
            for(int col = 0; col < world[0].length; col++)
            {
                Location loc = new Location(row, col);
                if(isAlive(loc))
                {
                    int nebighours = getAliveAround(loc);
                    if(nebighours == 2 || nebighours == 3)
                        copy[row][col] = true;
                }
                else
                {
                    if(getAliveAround(loc) == 3)
                        copy[row][col] = true;
                }
            }
        world = copy;
    }

    @Override
    public String toString()
    {
        String str = "";
        for(int row = 0; row < world.length; row++)
        {
            for(int col = 0; col < world[0].length; col++)
            {
                if(isAlive(new Location(row,col)))
                    str += "●";
                else
                    str += " ";
            }
            str += "\n";
        }
        return str;
    }
}
