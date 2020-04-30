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

import cards.*;
import gameplay.Board;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Adam Whittaker
 */
public class NOT extends Card implements Modifier{


    public NOT(int w, int h){
        super("NOT", w, h);
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
    public void effect(Card c, Board b){
        if(c instanceof GateCard){
            ((GateCard) c).modOutput = !((GateCard) c).modOutput;
            b.boardState.cascadeRowDestruction(c.isLeft ? b.boardState.left : b.boardState.right, 
                        c.isLeft, ((GateCard) c).boardY+1);
        }else if(c instanceof InputCard){
            ((InputCard) c).flip();
            b.boardState.flipInput(b.boardState.left, true);
            b.boardState.flipInput(b.boardState.right, false);
        }
        
    }

    @Override
    public boolean isTarget(Card c){
        return c instanceof InputCard || (isLeft == c.isLeft && c instanceof GateCard && !(c instanceof Placeholder));
    }

    @Override
    public void paintEffect(Graphics2D g, int x, int y){
        g.setColor(Color.RED.darker());
        g.fillRect(x, y, width/6, height);
        g.fillRect(x+5*width/6, y, width/6, height);
        g.fillRect(x+width/6, y, 2*width/3, height/6);
        g.fillRect(x+width/6, y+5*height/6, 2*width/3, height/6);
    }
    
}
