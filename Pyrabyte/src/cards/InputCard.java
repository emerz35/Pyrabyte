/*
 * Copyright (C) 2020 Adam
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

package cards;

import java.awt.Color;
import java.awt.Graphics2D;

import static gui.Main.R;

/**
 *
 * @author Adam Whittaker
 */
public class InputCard extends Card{
    
    
    private static final double SWITCH_CHANCE = 0.5;
    
    public boolean left, right;
    

    public InputCard(int x, int y, int w, int h, boolean l, boolean r){
        super( "Base",x,y, w, h);
        left = l;
        right = r;
    }
    
    public InputCard(int x, int y, int w, int h){
        this(x,y,w, h, R.nextDouble()<0.5, R.nextDouble()<0.5);
        if(left==right && R.nextDouble()<SWITCH_CHANCE) left = !right;
    }

    
    @Override
    public void paint(Graphics2D g){
        g.setColor(Color.GRAY);
        g.fillRect(x, y, width, height);
        paintBoolean(g, left, x, y+3*height/8, Color.BLACK, Color.GRAY);
        paintBoolean(g, right, x+3*width/4, y+3*height/8, Color.BLACK, Color.GRAY);
    }
    
    public void flip(){
        boolean temp = left;
        left = right;
        right = temp;
    }
    
}
