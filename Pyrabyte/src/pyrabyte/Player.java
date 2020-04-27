
package pyrabyte;

import cards.Card;
import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author Charlie Hands
 */
public final class Player implements Serializable{
    
    
    private static final long serialVersionUID = 46712984213L;
    
    
    public final LinkedList<Card> hand = new LinkedList<>();
    
    
}
