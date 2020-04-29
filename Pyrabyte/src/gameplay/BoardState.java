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

package gameplay;

import cards.Card;
import cards.GateCard;
import cards.InputCard;
import cards.Placeholder;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import static gui.Main.*;

/**
 *
 * @author Adam Whittaker
 */
public final class BoardState implements Serializable{
    

    private static final long serialVersionUID = 4578768L;
    
    public final List<Card> cards = new LinkedList<>();
    
    public GateCard[][] left, right;
            
    public InputCard input[];
   
    
    public BoardState(int inputNum){
        input = new InputCard[inputNum];
        left = new GateCard[inputNum-1][];
        right = new GateCard[inputNum-1][];
        
        int py = (CARD_HEIGHT + PADDING_Y), px = CARD_WIDTH + PADDING_X;
        int y = (HEIGHT - (inputNum-1)*py)/2;
                
        for(int i = 0; i< inputNum;i++){
            input[i] = new InputCard(WIDTH/2, y + i*py, CARD_WIDTH, CARD_HEIGHT);
        }
        for(int i = 0; i< inputNum-1; i++){
            left[i] = new GateCard[inputNum - 1 - i];
            right[i] = new GateCard[inputNum - 1 - i];
            for(int j = 0; j < left[i].length; j++){
                left[i][j] = new Placeholder(WIDTH/2-(i+1)*px, y + (i+1)*py/2 + j*py, CARD_WIDTH,CARD_HEIGHT,j,i, true);
                right[i][j] = new Placeholder(WIDTH/2+(i+1)*px, y + (i+1)*py/2 + j*py, CARD_WIDTH,CARD_HEIGHT,j,i, false);
            }
        }
    }
    
    
    public void flipInput(GateCard[][] board, boolean boardIsLeft){
        for(int i = 0; i< board[0].length;i++){
            if(!board[0][i].compatible(boardIsLeft? input[i].left:input[i].right,boardIsLeft? input[i+1].left:input[i+1].right))
                board[0][i] = new Placeholder(board[0][i],i,0, board[0][i].isLeft);
        }
        
        cascadeRowDestruction(board, boardIsLeft, 1);
    }
    
    public void cascadeRowDestruction(GateCard[][] board, boolean boardIsLeft, int start){
        for(int i = start; i< board.length;i++){
            for(int j = 0; j< board[i].length;j++){
                if(board[i-1][j] instanceof Placeholder || board[i-1][j+1] instanceof Placeholder)
                    board[i][j] = new Placeholder(board[i][j], j,i, board[j][i].isLeft);
            }
        }
    }
    
    public void paint(Graphics2D g){
        for(GateCard[] row : left){
            for(GateCard c: row){
                c.paint(g);
            }
        }
        
        for(GateCard[] row : right){
            for(GateCard c: row){
                c.paint(g);
            }
        }
        
        for(InputCard c: input){
            c.paint(g);
        }
    }
    
    public boolean addGateCard(boolean playerIsLeft, GateCard card, GateCard toReplace){
        GateCard[][] board = toReplace.isLeft? left:right;
        
        if(checkCompatibility(board, toReplace.isLeft, playerIsLeft, toReplace.boardX, toReplace.boardY, card)){
            card.x = toReplace.x;
            card.y = toReplace.y;
        
            board[toReplace.boardY][toReplace.boardX] = card;
            
            card.boardX = toReplace.boardX;
            card.boardY = toReplace.boardY;
            
            card.isLeft = toReplace.isLeft;
            return true;
        }
        else return false;
    }
    
    public boolean checkCompatibility(GateCard[][] board, boolean boardIsLeft, boolean playerIsLeft, int x, int y, GateCard card){
        if(playerIsLeft != boardIsLeft) return false;
        else if(y == 0){
            return card.compatible(boardIsLeft? input[x].left:input[x].right,boardIsLeft? input[x+1].left:input[x+1].right);
        }else{
            return !(board[y-1][x] instanceof Placeholder || board[y-1][x+1] instanceof Placeholder) 
                    && card.compatible(boardIsLeft? left[y-1][x].output:right[y-1][x].output, boardIsLeft? left[y-1][x+1].output:right[y-1][x+1].output);
        }
    }
    
    public Card clickedOn(int x, int y){
        for(Card c : input) if(c.withinBounds(x, y)) return c;
        for(Card[] row : left) for(Card c : row) if(c.withinBounds(x, y)) return c;
        for(Card[] row : right) for(Card c : row) if(c.withinBounds(x, y)) return c;
        return null;
    }
    
}
