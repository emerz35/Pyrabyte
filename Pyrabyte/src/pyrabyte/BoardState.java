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

package pyrabyte;

import cards.Card;
import cards.GateCard;
import cards.InputCard;
import cards.Placeholder;
import java.awt.Graphics2D;
import java.util.List;
import java.io.Serializable;
import java.util.LinkedList;
import static pyrabyte.Main.CARD_HEIGHT;
import static pyrabyte.Main.CARD_WIDTH;

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
        
        for(int i = 0; i< inputNum;i++){
            input[i] = new InputCard(CARD_WIDTH, CARD_HEIGHT);
        }
        for(int i = 0; i< inputNum-1; i++){
            left[i] = new GateCard[inputNum - 1 - i];
            right[i] = new GateCard[inputNum - 1 - i];
            for(int j = 0; j < inputNum - 1 - i; j++){
                left[i][j] = new Placeholder(CARD_WIDTH,CARD_HEIGHT);
                right[i][j] = new Placeholder(CARD_WIDTH,CARD_HEIGHT);
            }
        }
    }
    
    public void flipInput(GateCard[][] board, boolean isLeft){
        for(int i = 0; i< board[0].length;i++){
            if(!board[0][i].compatible(isLeft? input[i].left:input[i].right,isLeft? input[i+1].left:input[i+1].right))
                board[0][i] = new Placeholder(CARD_WIDTH,CARD_HEIGHT);
        }
        
        for(int i = 1; i< board.length;i++){
            for(int j = 0; j< board[i].length;j++){
                if(board[i-1][j] instanceof Placeholder || board[i-1][j+1] instanceof Placeholder)
                    board[i][j] = new Placeholder(CARD_WIDTH,CARD_HEIGHT);
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
    
    public boolean addCard(GateCard[][] board, boolean isLeft, int x, int y, GateCard card){
        if(checkCompatibility(board, isLeft,x,y,card)){
            card.x = board[y][x].x;
            card.y = board[y][x].y;
        
            board[y][x] = card;
            return true;
        }
        else return false;
    }
    
    public boolean checkCompatibility(GateCard[][] board, boolean isLeft, int x, int y, GateCard card){
        if(y == 0) return board[y][x].compatible(isLeft? input[x].left:input[x].right,isLeft? input[x+1].left:input[x+1].right);
        else 
            return !(board[y-1][x] instanceof Placeholder || board[y-1][x+1] instanceof Placeholder) 
                    && board[y][x].compatible(isLeft? left[y-1][x].output:right[y-1][x].output, isLeft? left[y-1][x+1].output:right[y-1][x+1].output);
    }
}
