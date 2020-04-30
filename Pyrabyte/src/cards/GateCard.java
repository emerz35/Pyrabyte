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
public abstract class GateCard extends Card{
    
    
    public final boolean output;
    public boolean modOutput;
    
    public int boardX = -1, boardY = -1;
    

    public GateCard(String n, int w, int h, boolean out){
        super(n, w, h);
        output = out;
        modOutput = out;
    }
    
    public GateCard(String n, int x, int y, int w, int h, boolean out){
        super(n,x,y, w, h);
        output = out;
        modOutput = out;
    }
    
    public GateCard(String n, int w, int h){
        this(n, w, h, R.nextDouble()<0.5);
    }
    
    
    public abstract boolean compatible(boolean a, boolean b);
    
    
    public void defaultPaint(Graphics2D g, Color col){
        g.setColor(col);
        g.fillRect(x, y, width, height);
        paintBoolean(g, modOutput, x+3*width/8, y+3*height/8, col.darker(), col);
    }
    
}
