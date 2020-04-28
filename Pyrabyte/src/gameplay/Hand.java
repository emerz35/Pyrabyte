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
package gameplay;

import cards.Card;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import static gui.Main.*;
import java.io.Serializable;

/**
 *
 * @author Charlie Hands
 */
public class Hand implements Serializable{
    
    public final List<Card> list = new LinkedList<>();
    
    public void paint(Graphics2D g, boolean faceUp){
        if(faceUp) list.forEach(x->x.paint(g));
        else list.forEach(x->x.paintFaceDown(g));
    }
    
    public void addCard(Card card){
        list.add(card);
        setCardPositions();
    }
    
    public void setCardPositions(){
        int px = CARD_WIDTH + PADDING_X;
        int x = (WIDTH - (list.size()-1)*px)/2;
        for(int i = 0; i< list.size();i++){
            list.get(i).y = HEIGHT - (PADDING_Y + CARD_HEIGHT)*2;
            list.get(i).x = x + i*px - CARD_WIDTH/2;
        }
    }
    
    public void dragCard(Card card, int x, int y){
        card.x = x;
        card.y = y;
    }
     
}
