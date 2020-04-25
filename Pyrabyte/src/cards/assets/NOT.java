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

import cards.Card;
import cards.GateCard;
import cards.InputCard;
import cards.Modifier;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Adam Whittaker
 */
public class NOT extends Card implements Modifier{


    public NOT(String n, int w, int h){
        super(n, w, h);
    }
    

    @Override
    public void paint(Graphics2D g){
        g.setColor(Color.RED.darker());
        g.fillRect(x, y, width, height);
        g.setColor(Color.RED);
        g.fillPolygon(  new int[]{x+width/3, x+2*width/3, x+width/2}, 
                        new int[]{y+height/3, y+height/3, y+2*height/3}, 3);
    }

    @Override
    public void effect(Card c){
        if(c instanceof GateCard) ((GateCard) c).output = !((GateCard) c).output;
        else if(c instanceof InputCard) ((InputCard) c).flip();
    }

    @Override
    public boolean isTarget(Card c){
        return /*player == c.player && */(c instanceof GateCard || c instanceof InputCard);
    }
    
}
