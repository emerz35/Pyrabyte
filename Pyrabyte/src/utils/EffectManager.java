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

import java.awt.Graphics2D;
import java.util.LinkedList;
import utils.BackgroundEffect.BlackHole;
import utils.BackgroundEffect.Galaxy;
import utils.BackgroundEffect.Star;

import static gui.Main.R;

/**
 *
 * @author Adam Whittaker
 */
public class EffectManager{
        
        
    public LinkedList<BackgroundEffect> effects = new LinkedList<>();
    public int cap;
    public double intensity, velx = 0.0, vely = 0.03;
    
    
    public EffectManager(int c, double i){
        cap = c;
        intensity = i;
    }


    public void setVelocity(double vx, double vy){
        velx = vx;
        vely = vy;
    }

    public void spawn(){
        double d = R.nextDouble();
        if(d<0.95) effects.add(new Star());
        else if(d<0.985) effects.add(new Galaxy());
        else effects.add(new BlackHole());
    }

    public void paintAndTick(Graphics2D g){
        /*if(effects.size()<cap) */if(R.nextDouble()<intensity) spawn();
        effects.stream().forEach(e -> {
            e.paint(g);
            e.tick(velx, vely);
        });
        effects.removeIf(e -> e.outOfBounds());
    }

    public void paint(Graphics2D ig){
        effects.stream().forEach(e -> {
            e.paint(ig);
        });
    }
    
}
