
package cards;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;
import logic.Collision;

/**
 *
 * @author Adam Whittaker
 */
public abstract class Card extends Collision{
    
    
    public String name;
    public final LinkedList<Modifier> modifiers = new LinkedList<>();
    public boolean isLeft;
    
    
    public Card(String n, int w, int h){
        this(n, 0, 0, w, h);
    }
    
    public Card(String n, int x, int y, int w, int h){
        super(x,y,w,h);
        name = n;
    }
    
    protected abstract void paint(Graphics2D g);
    
    public void paintCard(Graphics2D g){
        paint(g);
        modifiers.forEach(mod -> mod.paintEffect(g, x, y));
    }
    
    public void paintBoolean(Graphics2D g, boolean on, int tx, int ty, Color col, Color background){
        g.setColor(col);
        g.fillRect(tx, ty, width/4, height/4);
        if(!on){
            g.setColor(background);
            g.fillRect(tx+width/16, ty+height/16, width/8, height/8);
        }
    }
    
    public void paintFaceDown(Graphics2D g){
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
    }
    
}
