
package gui;

import gameplay.Board;
import communication.ComCom;
import java.awt.Canvas;
import java.util.Random;

import static communication.ComCom.createComInstance;
import gameplay.Player;

/**
 *
 * @author Charlie Hands
 */
public class Main extends Canvas{
    
    
    public static final Random R = new Random();
    
    public final static int WIDTH = 1240, HEIGHT = 800, CARD_WIDTH = 64,
            CARD_HEIGHT = 64, PADDING_X = CARD_WIDTH/4, PADDING_Y = CARD_HEIGHT/4; 
    /*static{
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = dim.width;
        HEIGHT = dim.height;
    }*/
    
    ComCom com;
    Window window;
    Thread renderThread;
    Renderer renderer;
    Board boardUwu;
    Player localPlayer;
    
    public Main(String ip, int port, boolean isServer){
        com = createComInstance(isServer, port);
        
        localPlayer = new Player(isServer);
        
        boardUwu = new Board(5, localPlayer);
        
        this.addMouseListener(boardUwu);
        this.addMouseMotionListener(boardUwu);
        
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
        Main m = new Main("", 0, false);
        /*Scanner scan = new Scanner(System.in);
        System.out.println("Enter ip...");
        String ip = scan.nextLine();
        int port = 27960;
        
        
        
        try{
            m.com.connect(5000, ip, port);
        }catch(IOException e){
            e.printStackTrace(System.err);
        }
        
        System.out.println("Recieving text input...");
        
        while(true){
            m.com.send("Charlie: " + scan.nextLine());
        }*/
    }
    
}
