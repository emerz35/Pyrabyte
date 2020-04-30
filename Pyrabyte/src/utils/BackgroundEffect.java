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

package utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import static gui.Main.*;

/**
 *
 * @author Adam Whittaker
 */
public abstract class BackgroundEffect{
    
    
    public Color col;
    public int x, y = 0, size;
    private double dx, dy;
    public double depth;
    
    
    public BackgroundEffect(int s){
        x = R.nextInt(WIDTH);
        depth = 0.4+R.nextDouble();
        int b = R.nextInt(30) + 40;
        col = new Color(b,b,b);
        size = s;
    }
    
    
    public abstract void paint(Graphics2D g);
    
    public void tick(double velx, double vely){
        double xC = velx*depth, yC = vely*depth;
        dx += xC%1.0;
        dy += yC%1.0;
        if(dx>=1){
            x += xC + 1;
            dx--;
        }else x += xC;
        if(dy>=1){
            y += yC + 1;
            dy--;
        }else y += yC;
    }
    
    public boolean outOfBounds(){
        return x+size<=0||x>WIDTH||y+size<=0||y>HEIGHT;
    }
    
    
    public static class Star extends BackgroundEffect{
        
        public Star(){
            super(R.nextInt(7) + 4);
        }

        @Override
        public void paint(Graphics2D ig){
            ig.setColor(col);
            ig.fillRect(x, y, size, size);
        }
    
    }
    
    public static class BlackHole extends BackgroundEffect{
        
        public BlackHole(){
            super(38);
        }

        @Override
        public void paint(Graphics2D g){
            g.setColor(col);
            g.fillOval(x, y, 38, 38);
            g.fillPolygon(new int[]{x+14,x,x}, new int[]{y,y+14,y}, 3);
            g.fillPolygon(new int[]{x+38,x+24,x+38}, new int[]{y+24,y+38,y+38}, 3);
            g.setColor(Color.BLACK);
            g.fillOval(x+3, y+3, 32, 32);
        }
        
    }
    
    public static class Galaxy extends BackgroundEffect{
        
        public Galaxy(){
            super(64);
        }
        
        @Override
        public void paint(Graphics2D g){
            g.setColor(col);
            g.fillOval(x, y, 64, 64);
            g.setColor(Color.BLACK);
            g.fillOval(x+6, y+6, 52, 52);
            g.setColor(col);
            g.fill(AffineTransform.getRotateInstance(Math.PI/4d, x+32, y+32).createTransformedShape(new Rectangle(x+27, y-6, 12, 74)));
        }
    
    }
    
}
