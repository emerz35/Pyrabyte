/*
 * Copyright (C) 2020 Charlie Hands
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
package gui.buttons;

import java.awt.Color;
import java.awt.Graphics2D;
import logic.Collision;

/**
 *
 * @author Charlie Hands
 */
public abstract class Button extends Collision{
    
    protected Color color;
    
    public Button(int x, int y, int w, int h, Color c) {
        super(x, y, w, h);
        color = c;
    }
    
    public abstract void run();
    
    public void paint(Graphics2D g){
        g.setColor(color);
        
        g.fill3DRect(x, y, width, height, true);
    }
    
    public void checkClicked(int mx, int my){
        if(withinBounds(mx,my)) run();
    }
}
