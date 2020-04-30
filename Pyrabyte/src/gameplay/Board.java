
package gameplay;

import cards.Card;
import cards.GateCard;
import cards.InputCard;
import cards.Placeholder;
import communication.EventMessage;
import static gui.Main.ACTIVE_BUTTON_COLOR;
import static gui.Main.BUTTON_HEIGHT;
import static gui.Main.BUTTON_WIDTH;
import static gui.Main.CARD_HEIGHT;
import static gui.Main.CARD_WIDTH;
import static gui.Main.DEACTIVE_BUTTON_COLOR;
import static gui.Main.HEIGHT;
import static gui.Main.PADDING_X;
import static gui.Main.PADDING_Y;
import static gui.Main.WIDTH;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import utils.Utils.Remote;

import static gui.Window.MAIN;
import gui.buttons.Button;
import gui.buttons.PassTurnButton;
import java.util.LinkedList;
import java.util.List;



/**
 *
 * @author Charlie Hands
 */
public class Board extends MouseAdapter{
    
    
    public volatile BoardState boardState;
    
    public final Player local; 
    public volatile Player opponent;
    
    private Card dragging;
    
    public final PassTurnButton passTurnButton;
    
    public List<Button> btns = new LinkedList<>();
    
    public boolean isTurn;
    
    public Board(int inputNum, Player you, boolean isServer){
        if(isServer) boardState = new BoardState(inputNum);
        local = you;
        isTurn = isServer;
        
        passTurnButton = new PassTurnButton(WIDTH-CARD_WIDTH -PADDING_X, HEIGHT-CARD_HEIGHT-PADDING_Y - BUTTON_WIDTH,BUTTON_WIDTH,BUTTON_HEIGHT, ACTIVE_BUTTON_COLOR, DEACTIVE_BUTTON_COLOR, isTurn);
        
        btns.add(passTurnButton);
    }
    
    public void paint(Graphics2D g){
        boardState.paint(g);
        local.paint(g, opponent);
        btns.forEach(x -> x.paint(g));
    }
    
    @Override
    public void mouseClicked​(MouseEvent e){
        btns.forEach(x->x.checkClicked(e.getX(), e.getY()));
        if(local.deck.withinBounds(e.getX(), e.getY())&& isTurn){
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
        if(dragging!= null && isTurn){
            Card card = boardState.clickedOn(e.getX(), e.getY());
            if(card!=null){
                if(dragging instanceof GateCard && card instanceof GateCard){
                    if(boardState.addGateCard(local.isLeft,(GateCard)dragging,(GateCard)card))
                        updateHand(dragging);
                }else if(!(dragging instanceof GateCard)){
                    if(card instanceof InputCard){
                        ((InputCard) card).flip();
                        boardState.flipInput(boardState.left, true);
                        boardState.flipInput(boardState.right, false);
                        updateHand(dragging);
                    }else if(card instanceof GateCard && !(card instanceof Placeholder)){
                        if(card.isLeft==local.isLeft){
                            ((GateCard) card).output = !((GateCard) card).output;
                            boardState.cascadeRowDestruction(card.isLeft ? boardState.left : boardState.right, 
                                    card.isLeft, ((GateCard) card).boardY);
                            updateHand(dragging);
                        }
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
        local.hand.list.remove(card);
        System.out.println("update hand");
        sendAllInfo();
    }
    
    @Remote
    public void sendAllInfo(){
        MAIN.com.send(boardState);
        MAIN.com.send(local);
    }
    
    @Remote
    public void sendPassTurn(){
        MAIN.com.send(EventMessage.PASS);
    }
    
    @Remote
    public void sendWin(){
        MAIN.com.send(EventMessage.WIN);
    }
    
    public void switchTurn(boolean turn){
        if(!turn) sendPassTurn();
        isTurn = turn;
        passTurnButton.switchTurns(turn);
    }
    
}
