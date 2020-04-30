/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

/**
 *
 * @author Charlie Hands
 */
public class Renderer implements Runnable{

    private Main main;
    
    private final int fps = 60;
    private final long milliDelay = 1000/fps;
    
    private boolean running = true;
    
    
    public Renderer(Main m){
        main = m;
    }
    
    
    @Override
    public void run() {
        long time = System.currentTimeMillis();
        
        while(running){
            if(System.currentTimeMillis() - time >=milliDelay){
                paint();
                time = System.currentTimeMillis();
            }
        }
    }
    
    public void paint(){
        BufferStrategy bs =  main.getBufferStrategy();
        if(bs.getDrawGraphics() instanceof Graphics2D){
            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
            
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
            
            main.currentScreen.paint(g);

            g.dispose();
            bs.show();
        }
    }
    
}
