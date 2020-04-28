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
package cards;

import java.awt.Color;
import java.awt.Graphics2D;
import logic.Collision;

/**
 *
 * @author Charlie Hands
 */
public class Placeholder extends GateCard{
    
    
    private final static Color COLOR = new Color(124, 80, 60);

    
    public Placeholder(int x, int y, int w, int h, int bx, int by, boolean isLeft){
        super("Placeholder",x,y, w, h,false);
        boardX = bx;
        boardY = by;
        this.isLeft = isLeft;
    }
    
    public Placeholder(Collision col, int bx, int by, boolean isLeft){
        this(col.x+col.width/2, col.y+col.height/2, col.width, col.height, bx, by, isLeft);
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(COLOR);
        
        g.drawRect(x,y,width,height);
    }

    @Override
    public boolean compatible(boolean a, boolean b) {
        return true;
    }
    
}
