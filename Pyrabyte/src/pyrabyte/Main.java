
package pyrabyte;

import java.awt.Canvas;

/**
 *
 * @author Charlie Hands
 */
public class Main extends Canvas{
    
    public static int WIDTH = 1240, HEIGHT = 800;
    
    Window window;
    Thread renderThread;
    Renderer renderer;
    
    public Main(String ip, int port, boolean isServer){
        
        
        window  = new Window("Pyrabyte", WIDTH,HEIGHT, this);
    }
    
    public void start(){
        createBufferStrategy(4);
        renderer = new Renderer(this);
        renderThread = new Thread(renderer);
        renderThread.setDaemon(true);
        renderThread.start();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Main("", 0, true);
    }
    
}
