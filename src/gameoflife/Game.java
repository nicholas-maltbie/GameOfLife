/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Nick_Pro
 */
public class Game extends AbstractFSM<Game.State> implements ActionListener
{
    public static enum State {RUNNING, PAUSED, RESTART};
    
    public static final HashMap<State, EnumSet<State>> stateMap;
    
    static{
        stateMap = new HashMap<>();
        stateMap.put(State.RESTART, EnumSet.of(State.PAUSED));
        stateMap.put(State.PAUSED, EnumSet.of(State.RESTART, State.RUNNING));
        stateMap.put(State.RUNNING, EnumSet.of(State.PAUSED, State.RESTART));
    }
    
    private JButton restartButton;
    private JButton pausePlayButton;
    private JButton stepButton;
    private TextField interval;
    
    private TextField size;
    
    private Timer timer;
    private JFrame view;
    private LifePanel panel;
    private World world;
    
    public Game(World world)
    {
        super(stateMap, State.RESTART);
        this.world = world;
        panel = new LifePanel(world);
        view = new JFrame("Game of Life");
        Container c = view.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(panel, BorderLayout.CENTER);
        
        timer = new Timer();
        
        restartButton = new JButton("restart");
        pausePlayButton = new JButton("play");
        stepButton = new JButton("step");
        interval = new TextField("100", 10);
        size = new TextField("100", 10);
        
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPane.add(restartButton);
        buttonPane.add(stepButton);
        buttonPane.add(pausePlayButton);
        buttonPane.add(interval);
        buttonPane.add(size);
        
        restartButton.addActionListener(this);
        stepButton.addActionListener(this);
        pausePlayButton.addActionListener(this);
        
        c.add(buttonPane, BorderLayout.NORTH);
        view.setVisible(true);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setBounds(100,10, 1000,1000);
        
        view.addKeyListener(panel);
    }

    @Override
    protected void stateStarted(State state)
    {
        switch(state)
        {
            case RESTART:
                world = new World(Integer.parseInt(size.getText()));
                panel.setWorld(world);
                view.repaint();
                this.setState(State.PAUSED);
                break;
            case PAUSED:
                pausePlayButton.setText("play");
                timer.cancel();
                break;
            case RUNNING:
                pausePlayButton.setText("pause");
                timer = new Timer();
                timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            step();
                        }
                    }, 0, Integer.parseInt(interval.getText()));
                break;
        }
    }

    @Override
    protected void stateEnded(State state)
    {
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        switch(getState())
        {
            case PAUSED:
                if(e.getSource() == stepButton)
                    step();
                else if(e.getSource() == restartButton)
                    setState(State.RESTART);
                else if(e.getSource() == pausePlayButton)
                    setState(State.RUNNING);
                break;
            case RUNNING:
                if(e.getSource() == pausePlayButton)
                    setState(State.PAUSED);
                else if(e.getSource() == pausePlayButton)
                    setState(State.RESTART);
                break;
        }
    }
    
    public void step()
    {
        world.step();
        view.repaint();
    }
}
