/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife;

/**
 *
 * @author Nick_Pro
 */
public class GameOfLife 
{
    
    public static void main(String[] args) 
    {
        World w = new World(100);
        Game game = new Game(w);
        game.start();
    }
    
}
