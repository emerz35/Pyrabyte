
package pyrabyte;

import communication.ComCom;
import java.awt.Canvas;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import static communication.ComCom.createComInstance;

/**
 *
 * @author Charlie Hands
 */
public class Main extends Canvas{
    
    
    public static final Random R = new Random();
    
    public final static int WIDTH = 1240, HEIGHT = 800;
    /*static{
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = dim.width;
        HEIGHT = dim.height;
    }*/
    
    ComCom com;
    Window window;
    Thread renderThread;
    Renderer renderer;
    
    public Main(String ip, int port, boolean isServer){
        com = createComInstance(isServer, port);
        
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
        Main m = new Main("", 0, true);
        
        try{
            m.com.connect(5000, "94.10.98.123", 27960);
        }catch(IOException e){
            e.printStackTrace(System.err);
        }
        
        System.out.println("Recieving text input...");
        Scanner scan = new Scanner(System.in);
        while(true){
            m.com.send(scan.nextLine());
        }
    }
    
}
