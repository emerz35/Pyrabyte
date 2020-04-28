
package gameplay;

import static gui.Main.CARD_HEIGHT;
import static gui.Main.CARD_WIDTH;
import static gui.Main.HEIGHT;
import static gui.Main.PADDING_X;
import static gui.Main.PADDING_Y;
import static gui.Main.WIDTH;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.Serializable;


/**
 *
 * @author Charlie Hands
 */
public final class Player implements Serializable{
    
    Deck deck;
    Hand hand;
    
    public final boolean isLeft;
    
    private static final long serialVersionUID = 46712984213L;
    
    public Player(boolean isLeft){
        this.isLeft = isLeft;
        hand = new Hand();
        deck = new Deck(WIDTH-CARD_WIDTH -PADDING_X, HEIGHT-CARD_HEIGHT-PADDING_Y,CARD_WIDTH,CARD_HEIGHT, 30, this);
    }
    
    public void paint(Graphics2D g, Player opponent){
        paintSelf(g);
        g.setTransform(AffineTransform.getQuadrantRotateInstance(2, WIDTH/2, HEIGHT/2));
        //opponent.paintSelf(g);
        g.setTransform(new AffineTransform());
    }
    
    public void paintSelf(Graphics2D g){
        deck.paint(g);
        hand.paint(g);
    }
    
    public void getCardToHand(){
        hand.addCard(deck.cards.remove(0));
    }
}
