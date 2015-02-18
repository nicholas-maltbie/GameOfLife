/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Desktop.Action;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;

/**
 *
 * @author Nick_Pro
 */
public class LifePanel extends JPanel implements MouseListener, KeyListener
{
    public static enum Action {NOTHING, COPY};
    
    private boolean[][] clipboard;
    
    private Action action = Action.NOTHING;
    private World world;
    
    private Location select1;
    private Location select2;
    
    public LifePanel(World world)
    {
        this.world = world;
        addKeyListener(this);
        setFocusable(true);
        this.addMouseListener(this);
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        Rectangle area = world.getActiveArea();
        int width = (int)(this.getWidth()/ area.getWidth());
        int height = (int)(this.getHeight()/ area.getHeight());
        for(int x = 0; x < area.width; x++)
            for(int y = 0; y < area.height; y++)
            {
                if(world.isAlive(new Location(y+area.y, x+ area.x)))
                    g2.setColor(Color.BLACK);
                else
                    g2.setColor(Color.WHITE);
                g2.fill(new Rectangle(x*width, y*height, width, height));
            }
        
        g2.setColor(Color.YELLOW.darker().darker());
        if(select1 != null && select2 != null)
        {
            int x = select1.getCol();
            if(select2.getCol() < x)
                x = select2.getCol();
            int y = select1.getRow();
            if(select2.getRow() < y)
                y = select2.getRow();
            Rectangle select = new Rectangle(x*width,y*height, Math.abs(select1.getCol() - select2.getCol())*width, Math.abs(select1.getRow() - select2.getRow())*height);
            g2.draw(select);
        }
        
        g2.setColor(new Color(255,0,0,150));
        if(select1 != null) {
            g2.fill(new Ellipse2D.Float(select1.getCol()*width-width/2f, select1.getRow()*height-height/2f, (float)width,(float)height));
        }
        g2.setColor(new Color(0,0,255,150));
        if(select2 != null) {
            g2.fill(new Ellipse2D.Float(select2.getCol()*width-width/2f, select2.getRow()*height-height/2f, (float)width,(float)height));
        }
        //System.out.println(select1 + " " + select2);
        /*for(int x = 0; x < area.width; x++)
            for(int y = 0; y < area.height; y++)
            {
                g2.setColor(Color.GREEN);
                g2.draw(new Rectangle(x*width, y*height, width, height));
            }*/
    }
    
    public void setWorld(World world)
    {
        this.world = world;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() == 'c' && e.isControlDown())
        {
            if(select1 != null && select2 != null)
            {
                action = Action.COPY;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_DELETE)
        {
            if(select1 != null && select2 != null)
            {
                int row = select1.getRow();
                if(select2.getRow() < row)
                    row = select2.getRow();
                int col = select1.getCol();
                if(select2.getCol() < col)
                    col = select2.getCol();
                for(int r = 0; r < Math.abs(select1.getRow() - select2.getRow()); r++)
                    for(int c = 0; c < Math.abs(select1.getCol() - select2.getCol()); c++)
                        world.die(new Location(r+row, c+col));
            }
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e)
    {
        int x = e.getPoint().x;
        int y = e.getPoint().y;
        Rectangle area = world.getActiveArea();
        int width = (int)(this.getWidth()/ area.getWidth());
        int height = (int)(this.getHeight()/ area.getHeight());
        if(width == 0 || height == 0)
            return;
        
        if(e.isControlDown())
        {
            Location loc = new Location((y+height/2)/height, (x+width/2)/width);
            if(e.getButton() == MouseEvent.BUTTON1)
            {
                if(select1 == null || !select1.equals(loc))
                    select1 = loc;
                else
                    select1 = null;
            }
            else if(e.getButton() == MouseEvent.BUTTON3)
            {
                if(select2 == null || !select2.equals(loc))
                    select2 = loc;
                else
                    select2 = null;
            }
            this.repaint();
        }
        else if(e.getButton() == MouseEvent.BUTTON1)
        {
            Location loc = new Location(y/height, x/width);
            if(world.isAlive(loc))
                world.die(loc);
            else
                world.spawn(loc);
            this.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println(e);    
    }
}
