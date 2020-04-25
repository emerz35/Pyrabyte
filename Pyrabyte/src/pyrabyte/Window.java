package pyrabyte;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Charlie Hands
 */
public class Window {
    public JFrame frame;
    
    public Window(String name, int width, int height, Main main){
        frame = new JFrame(name);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setResizable(false);
        Dimension size = new Dimension(width,height);
        frame.setPreferredSize(size);
        frame.setMinimumSize(size);
        frame.setMaximumSize(size);
        frame.setLocationRelativeTo(null);
        
        frame.setVisible(true);
        
        frame.add(main);
        main.start();
    }
}
