
package gui;

import communication.ComCom;
import gameplay.Board;
import gameplay.Player;
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
    
    public final static int WIDTH = 1240, HEIGHT = 800, CARD_WIDTH = 64,
            CARD_HEIGHT = 64, PADDING_X = CARD_WIDTH/4, PADDING_Y = CARD_HEIGHT/4; 
    /*static{
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = dim.width;
        HEIGHT = dim.height;
    }*/
    
    public final ComCom com;
    protected final Window window;
    private final Thread renderThread;
    private final Renderer renderer;
    protected final Board boardUwu;
    public final Player localPlayer;
    
    public Main(String ip, int port, boolean isServer){
        
        localPlayer = new Player(isServer);
        
        boardUwu = new Board(5, localPlayer);
        
        com = createComInstance(boardUwu, isServer, port);
        
        this.addMouseListener(boardUwu);
        this.addMouseMotionListener(boardUwu);
        
        renderer = new Renderer(this);
        renderThread = new Thread(renderer);
        
        window  = new Window("Pyrabyte", WIDTH,HEIGHT, this);
    }
    
    public void start(){
        createBufferStrategy(4);
        renderThread.setDaemon(true);
        renderThread.start();
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int port = 27960;
        Main m = new Main("", port, false);

        
        Scanner scan = new Scanner(System.in);

        //System.out.println("Enter ip uwu:");
        String ip = scan.nextLine();
        
        try{
            m.com.connect(5000, ip, port);

        }catch(IOException e){
            e.printStackTrace(System.err);
        }
        
        
        System.out.println("Recieving text input...");
        
        while(true){
            m.com.send(scan.nextLine());
        }
    }
    
}
