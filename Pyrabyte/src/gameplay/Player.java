
package gameplay;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.Serializable;

import static gui.Main.*;


/**
 *
 * @author Charlie Hands
 */
public final class Player implements Serializable{
    
    
    private static final long serialVersionUID = 46712984213L;
    private static final AffineTransform REFLECTION = AffineTransform.getTranslateInstance(0, -HEIGHT/2);
    static{
        REFLECTION.concatenate(new AffineTransform(1,0,0,0,-1,0));
        REFLECTION.concatenate(AffineTransform.getTranslateInstance(0, HEIGHT/2));
    }
    
    Deck deck;
    Hand hand;
    
    public final boolean isLeft;
    
    
    public Player(){
        this(true);
    }
    
    public Player(boolean isLeft){
        this.isLeft = isLeft;
        hand = new Hand();
        deck = new Deck(WIDTH-CARD_WIDTH -PADDING_X, HEIGHT-CARD_HEIGHT-PADDING_Y,CARD_WIDTH,CARD_HEIGHT, 30);
    }
    
    
    public void paint(Graphics2D g, Player opponent){
        paintSelf(g);
        g.setTransform(REFLECTION);
        paintOpponent(g, opponent);
        g.setTransform(new AffineTransform());
        
    }
    
    public void paintSelf(Graphics2D g){
        deck.paint(g);
        hand.paint(g, true);
    }
    
    public void paintOpponent(Graphics2D g, Player opponent){
        opponent.deck.paint(g);
        opponent.hand.paint(g, false);
    }
    
    public void getCardToHand(){
        hand.addCard(deck.cards.removeFirst());
    }
    
}
