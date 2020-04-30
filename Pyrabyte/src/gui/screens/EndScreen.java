/*
 * Copyright (C) 2020 Adam Whittaker
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package gui.screens;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static gui.Main.HEIGHT;
import static gui.Main.WIDTH;
import gui.buttons.NewGameButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Adam Whittaker
 */
public class EndScreen extends MouseAdapter implements Screen{
    

    /**
     * CIRCLE_DIAMETER: The diameter of the loading circle.
     * CIRCLE_WIDTH: The width of the loading circle's line.
     * CIRCLE_COLOR: The color of the loading circle.
     * LOADING_TEXT_FONT: The font of the loading message.
     * ANGULAR_VELOCITY: The angular velocity of the negative space within the
     * loading circle.
     * RECTANGLE_HEIGHT: The height of the negative space rectangle.
     * RECTANGLE: The negative space rectangle.
     * rotation: The transform representing the current orientation of the 
     * negative space rectangle.
     * message: What the program is currently doing.
     */
    private final static int CIRCLE_DIAMETER = 112*HEIGHT/1080, 
            CIRCLE_WIDTH = 8*HEIGHT/1080;
    private final static Color BACKGROUND_COLOR = Color.BLACK, 
            CIRCLE_COLOR = Color.WHITE;
    private final static Font LOADING_TEXT_FONT = new Font(Font.MONOSPACED, Font.BOLD, 36*HEIGHT/1080);
    
    private final static double ANGULAR_VELOCITY = 0.04;
    private final static int RECTANGLE_HEIGHT = CIRCLE_WIDTH*3;
    private final static Rectangle RECTANGLE = new Rectangle(
            (WIDTH-CIRCLE_DIAMETER)/2, (HEIGHT-RECTANGLE_HEIGHT)/2,
            CIRCLE_DIAMETER, RECTANGLE_HEIGHT);
    
    private final AffineTransform rotation = new AffineTransform();
    private final String message;

    private final NewGameButton nGBtn;
    
    public EndScreen(String mes, NewGameButton btn){
        message = mes;
        nGBtn = btn;
    }
    
    
    @Override
    public void paint(Graphics2D g){
        //Rotates the circle.
        updateAngle();
        //Paints the background.
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        //Paints the rotating loading circle.
        g.setColor(CIRCLE_COLOR);
        g.fillOval((WIDTH-CIRCLE_DIAMETER)/2, (HEIGHT-CIRCLE_DIAMETER)/2, 
                CIRCLE_DIAMETER, CIRCLE_DIAMETER);
        g.setColor(BACKGROUND_COLOR);
        g.fillOval((WIDTH-CIRCLE_DIAMETER)/2 + CIRCLE_WIDTH, 
                (HEIGHT-CIRCLE_DIAMETER)/2 + CIRCLE_WIDTH, 
                CIRCLE_DIAMETER - 2*CIRCLE_WIDTH, 
                CIRCLE_DIAMETER - 2*CIRCLE_WIDTH);
        g.fill(rotation.createTransformedShape(RECTANGLE));
        //Paints the loading message.
        g.setFont(LOADING_TEXT_FONT);
        g.setColor(CIRCLE_COLOR);
        
        g.setTransform(rotation);
        
        FontMetrics f = g.getFontMetrics();
        g.drawString(message, (WIDTH - f.stringWidth(message))/2, HEIGHT/2 + f.getDescent());
        g.setTransform(new AffineTransform());
        nGBtn.paint(g);
    }
    
    /**
     * Updates the rotation angle of the negative space rectangle.
     * @param frames The number of frames since the last render tick.
     */
    private void updateAngle(){
        rotation.rotate(ANGULAR_VELOCITY, WIDTH/2, HEIGHT/2);
    }
    
    @Override
    public void mouseClicked(MouseEvent e){
        nGBtn.checkClicked(e.getX(), e.getY());
    }
    
}
