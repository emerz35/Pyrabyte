
package gameplay;

import cards.Card;
import cards.GateCard;
import cards.InputCard;
import cards.Placeholder;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import utils.Utils.Remote;

import static gui.Window.MAIN;



/**
 *
 * @author Charlie Hands
 */
public class Board extends MouseAdapter{
    
    
    public volatile BoardState boardState;
    
    public final Player local; 
    public volatile Player opponent;
    
    private Card dragging;
    
    
    public Board(int inputNum, Player you){
        boardState = new BoardState(inputNum);
        local = you;
    }
    
    public void paint(Graphics2D g){
        boardState.paint(g);
        local.paint(g, opponent);
    }
    
    @Override
    public void mouseClicked​(MouseEvent e){
        if(local.deck.withinBounds(e.getX(), e.getY())){
            local.getCardToHand();
            MAIN.com.send(local);
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e){
        local.hand.list.stream().filter((c) -> (c.withinBounds(e.getX(), e.getY()))).forEachOrdered((c) -> {
            dragging = c;
        });
    }
    
    @Override
    public void mouseReleased(MouseEvent e){
        if(dragging!= null){
            Card card = boardState.clickedOn(e.getX(), e.getY());
            if(card!=null){
                if(dragging instanceof GateCard && card instanceof GateCard){
                    if(boardState.addGateCard(local.isLeft,(GateCard)dragging,(GateCard)card))
                        local.hand.list.remove(dragging);
                }else if(!(dragging instanceof GateCard)){
                    if(card instanceof InputCard){
                        ((InputCard) card).flip();
                        boardState.flipInput(boardState.left, true);
                        boardState.flipInput(boardState.right, false);
                        local.hand.list.remove(dragging);
                    }else if(card instanceof GateCard && !(card instanceof Placeholder)){
                        ((GateCard) card).output = !((GateCard) card).output;
                        local.hand.list.remove(dragging);
                    }
                }
            }
            local.hand.setCardPositions();
            dragging = null;            
        }
    }
    
    @Override
    public void mouseDragged(MouseEvent e){
        if(dragging != null){
            local.hand.dragCard(dragging,e.getX(), e.getY());
        }
    }
    
    @Remote
    public void updateHand(Card card){
        local.hand.list.remove(dragging);
        MAIN.com.send(boardState);
        MAIN.com.send(local);
    }
    
}
