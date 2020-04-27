
package pyrabyte;

import java.awt.Graphics2D;



/**
 *
 * @author Charlie Hands
 */
public class Board {
    public BoardState boardState;
    
    public Player local, opponent;
    
    
    public Board(int inputNum){
        
    }
    
    public void paint(Graphics2D g){
        boardState.paint(g);
    }
}
