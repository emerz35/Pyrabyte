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
import static gui.Main.CARD_HEIGHT;
import static gui.Main.CARD_WIDTH;
import static gui.Main.HEIGHT;
import static gui.Main.PADDING_X;
import static gui.Main.PADDING_Y;
import static gui.Main.WIDTH;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Charlie Hands
 */
public class Hand {
    public final List<Card> list = new LinkedList<>();
    
    public void paint(Graphics2D g){
        list.forEach(x->x.paint(g));
    }
    
    public void addCard(Card card){
        list.add(card);
        setCardPositions();
    }
    
    public void setCardPositions(){
        int px = CARD_WIDTH + PADDING_X;
        int x = (WIDTH - (list.size()-1)*px)/2;
        for(int i = 0; i< list.size();i++){
            list.get(i).y = HEIGHT - PADDING_Y - CARD_HEIGHT;
            list.get(i).x = x + i*px;
        }
    }
    
    public void dragCard(Card card, int x, int y){
        card.x = x;
        card.y = y;
        
        
    }
     
}
