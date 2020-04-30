
package gameplay;

import cards.Card;
import cards.GateCard;
import cards.Modifier;
import communication.EventMessage;
import gui.buttons.Button;
import gui.buttons.PassTurnButton;
import gui.screens.EndScreen;
import gui.screens.Screen;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import utils.EffectManager;
import utils.Utils.Remote;

import static gui.Main.*;
import static gui.Window.MAIN;
import gui.buttons.NewGameButton;



/**
 *
 * @author Charlie Hands
 */
public class Board extends MouseAdapter implements Screen{
    
    
    public volatile BoardState boardState;
    private final EffectManager background = new EffectManager(14, 0.01);
    public final Player local; 
    public volatile Player opponent;    
    private Card dragging;    
    public final PassTurnButton passTurnButton;    
    public List<Button> btns = new LinkedList<>();
    public boolean isTurn;
    
    public NewGameButton nGBtn; 
    
    private int inputNum;
    
    public Board(int inputNum, Player you, boolean isServer){
        this.inputNum = inputNum;
        if(isServer) boardState = new BoardState(inputNum);
        local = you;
        isTurn = isServer;
        
        passTurnButton = new PassTurnButton(WIDTH-CARD_WIDTH -PADDING_X, HEIGHT-CARD_HEIGHT-PADDING_Y - BUTTON_WIDTH,
                BUTTON_WIDTH,BUTTON_HEIGHT, ACTIVE_BUTTON_COLOR, DEACTIVE_BUTTON_COLOR, isTurn);
        
        btns.add(passTurnButton);
        
        nGBtn = new NewGameButton(WIDTH/2, 5*HEIGHT/6,BUTTON_WIDTH*2, BUTTON_HEIGHT*2, this);
    }
    
    @Override
    public void paint(Graphics2D g){
        background.paintAndTick(g);
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
        if(dragging!= null){
            Card card = boardState.clickedOn(e.getX(), e.getY());
            if(card!=null && isTurn){
                if(dragging instanceof GateCard && card instanceof GateCard){
                    if(boardState.addGateCard(local.isLeft,(GateCard)dragging,(GateCard)card)){
                        updateHand(dragging);
                        if(((GateCard)card).boardY >= boardState.input.length - 2) registerWin();
                    }
                }else if(!(dragging instanceof GateCard)){
                    if(dragging instanceof Modifier){
                        if(((Modifier) dragging).isTarget(card)){
                            ((Modifier) dragging).effect(card, this);
                            card.modifiers.add((Modifier) dragging);
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
    public void registerWin(){
        scoopCards();
        MAIN.com.send(EventMessage.WIN);
        MAIN.setScreen(new EndScreen("WIN", nGBtn));
    }
    
    @Remote
    public void switchTurn(boolean turn){
        if(!turn) sendPassTurn();
        isTurn = turn;
        passTurnButton.switchTurns(turn);
    }
    
    @Remote
    public void newGame(){
        if(isTurn){
            boardState = new BoardState(inputNum);
            sendAllInfo();
        }
        local.getMultipleCardsToHand(inputNum-1);
        nGBtn = new NewGameButton(WIDTH/2, 5*HEIGHT/6,BUTTON_WIDTH*2, BUTTON_HEIGHT*2, this);
        MAIN.setScreen(this);
    }
    
    public void scoopCards(){
        boardState.scoopBoard(local.isLeft, local.deck);
        local.hand.scoopHand(local.deck);

    }
}
