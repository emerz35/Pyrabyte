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
import cards.assets.AND;
import static gui.Main.CARD_HEIGHT;
import static gui.Main.CARD_WIDTH;
import static gui.Main.R;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import logic.Collision;

/**
 *
 * @author Charlie Hands
 */
public class Deck extends Collision{

    public List<Card> cards = new LinkedList<>();
    
    public Deck(int x, int y, int w, int h) {
        super(x, y, w, h);
    }
    
    public Deck(int x, int y, int w, int h, int cardNum, Player local){
        this(x,y,w,h);
        
        Card temp;
        for(int i = 0; i < cardNum; i++){
            temp = new AND(CARD_WIDTH,CARD_HEIGHT);
            temp.player = local;
            cards.add(temp);
        }
    }

    public void paint(Graphics2D g){
        g.setColor(Color.white);
        
        g.fillRect(x, y, width, height);
    }
    
    public void shuffle(){
        Collections.shuffle(cards, R);
    }
}
