/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pyrabyte;

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
    
    private boolean running = true;
    
    public Renderer(Main m){
        main = m;
    }
    
    
    @Override
    public void run() {
        long time = System.currentTimeMillis();
        int frames = 0;
        
        while(running){
            if(frames <= 60){
                paint();
                frames++;
            }
            if(System.currentTimeMillis() - time >=1000){
                frames = 0;
                time = System.currentTimeMillis();
            }
        }
    }
    
    public void paint(){
        BufferStrategy bs =  main.getBufferStrategy();
        if(bs.getDrawGraphics() instanceof Graphics2D){
            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
            
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);


            g.dispose();
            bs.show();
        }
    }
    
}
