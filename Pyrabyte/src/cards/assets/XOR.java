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

package cards.assets;

import cards.GateCard;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Adam Whittaker
 */
public class XOR extends GateCard{
        

    public XOR(int w, int h){
        super("XOR", w, h);
    }
    

    @Override
    public boolean compatible(boolean a, boolean b){
        return ((a||b) && !(a&&b))==output;
    }

    @Override
    public void paint(Graphics2D g){
        defaultPaint(g, Color.ORANGE);
    }
    
}
