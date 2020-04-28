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
import cards.assets.NOT;
import cards.assets.OR;
import cards.assets.XOR;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Collections;
import java.util.LinkedList;
import logic.Collision;
import utils.Distribution;

import static gui.Main.*;

/**
 *
 * @author Charlie Hands
 */
public class Deck extends Collision{

    
    /**
     * 0: AND
     * 1: NOT
     * 2: OR
     * 3: XOR
     */
    private static final Distribution<Integer> DISTRIBUTION = new Distribution<>(new Integer[]{0,1,2,3,4}, new double[]{4, 2, 5, 3});
    
    public LinkedList<Card> cards = new LinkedList<>();
    
    
    /**
     * Empty Deck.
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public Deck(int x, int y, int w, int h) {
        super(x, y, w, h);
    }
    
    public Deck(int x, int y, int w, int h, int cardNum){
        this(x,y,w,h);
        
        Card temp;
        for(int i = 0; i < cardNum; i++){
            switch(DISTRIBUTION.next()){
                case 0: temp = new AND(CARD_WIDTH, CARD_HEIGHT); 
                    break;
                case 1: temp = new NOT(CARD_WIDTH, CARD_HEIGHT); 
                    break;
                case 2: temp = new OR(CARD_WIDTH, CARD_HEIGHT); 
                    break;
                default: temp = new XOR(CARD_WIDTH, CARD_HEIGHT); 
                    break;
            }
            cards.add(temp);
        }
    }

    
    public void paint(Graphics2D g){
        g.setColor(Color.WHITE);
        
        if(!cards.isEmpty()) g.fillRect(x, y, width, height);
    }
    
    public void shuffle(){
        Collections.shuffle(cards, R);
    }
    
}
