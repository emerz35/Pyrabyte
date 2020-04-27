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

/**
 *
 * @author Charlie Hands
 */
public class Placeholder extends GateCard{

    public Placeholder(int x, int y, int w, int h) {
        super("Placeholder",x,y, w, h,false);
        
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(Color.BLACK);
        
        g.drawRect(x,y,width,height);
    }

    @Override
    public boolean compatible(boolean a, boolean b) {
        return true;
    }
    
}
