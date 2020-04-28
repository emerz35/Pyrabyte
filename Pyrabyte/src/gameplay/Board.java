
package gameplay;

import cards.Card;
import cards.GateCard;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



/**
 *
 * @author Charlie Hands
 */
public class Board extends MouseAdapter{
    public BoardState boardState;
    
    public final Player local; 
    public Player opponent;
    
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
        if(local.deck.withinBounds(e.getX(), e.getY()))
            local.getCardToHand();
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
            if(card!=null && dragging instanceof GateCard && card instanceof GateCard /*&& card.player.equals(local)*/){
                if(boardState.addCard(local.isLeft,(GateCard)dragging,(GateCard)card))
                    local.hand.list.remove(card);
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
}
