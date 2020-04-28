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

package logic;

import java.io.Serializable;

/**
 *
 * @author Adam
 */
public abstract class Collision implements Serializable{
    
    
    public int x, y, width, height;
    
    
    /**
     * Creates a new instance.
     * @param x The x of the CENTRE of the rectangle.
     * @param y The y of the CENTRE of the rectangle.
     * @param w Width
     * @param h Height
     */
    public Collision(int x, int y, int w, int h){
        this.x = x - w/2;
        this.y = y - h/2;
        width = w;
        height = h;
    }
    
    
    /**
     * Checks whether the given point is within this collision object.
     * @param eX The x of the event.
     * @param eY The y of the event.
     * @return true if it is.
     */
    public boolean withinBounds(int eX, int eY){
        return withinBounds(eX, eY, x, y, width, height);
    }
    
    /**
     * Checks whether the given point is within the given rectangle.
     * @param eX The x of the event.
     * @param eY The y of the event.
     * @param x The top-left x of the rectangle.
     * @param y The top-left y of the rectangle.
     * @param w The width of the rectangle.
     * @param h The height of the rectangle.
     * @return true if it is.
     */
    public static boolean withinBounds(int eX, int eY, int x, int y, int w, int h){
        return eX>x && eX<x+w && eY>y && eY<y+h;
    }
    
}
