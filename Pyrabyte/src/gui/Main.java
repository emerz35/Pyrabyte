
package gui;

import communication.ComCom;
import gameplay.Board;
import gameplay.Player;
import gui.screens.Screen;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
    
    public final static int WIDTH = 1240, HEIGHT = 800, CARD_WIDTH = 72,
            CARD_HEIGHT = 72, PADDING_X = CARD_WIDTH/4, PADDING_Y = CARD_HEIGHT/4,
            BUTTON_WIDTH = 64, BUTTON_HEIGHT = 32;  
    
    public final static Color ACTIVE_BUTTON_COLOR = Color.CYAN, DEACTIVE_BUTTON_COLOR = Color.LIGHT_GRAY;
    /*static{
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = dim.width;
        HEIGHT = dim.height;
    }*/
    
    public final ComCom com;
    protected final Window window;
    private final Thread renderThread;
    private final Renderer renderer;
    public final Board boardUwu;
    public final Player localPlayer;
   
    protected Screen currentScreen;
    
    
    public Main(String ip, int port, boolean isServer, int inputNum){
        
        localPlayer = new Player(isServer, inputNum);
        
        boardUwu = new Board(inputNum, localPlayer, isServer);
        setScreen(boardUwu);
        
        com = createComInstance(boardUwu, isServer, port);
        
        renderer = new Renderer(this);
        renderThread = new Thread(renderer);
        
        window  = new Window("Pyrabyte", WIDTH,HEIGHT, this);
    }
    
    
    public void start(){
        createBufferStrategy(4);
        renderThread.setDaemon(true);
        renderThread.start();
    }
    
    public final void setScreen(Screen sc){
        if(currentScreen instanceof MouseAdapter){
            this.removeMouseListener((MouseListener)currentScreen);
            this.removeMouseMotionListener((MouseMotionListener)currentScreen);
        }
        currentScreen = sc;
        if(currentScreen instanceof MouseAdapter){
            this.addMouseListener((MouseListener)currentScreen);
            this.addMouseMotionListener((MouseMotionListener)currentScreen);
        }
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int port = 27960;
        Main m = new Main("", port, false, 5);

        
        Scanner scan = new Scanner(System.in);

        //System.out.println("Enter ip uwu:");
        String ip = "84.64.16.213";//scan.nextLine();
        
        try{
            m.com.connect(5000, ip, port);

        }catch(IOException e){
            e.printStackTrace(System.err);
        }
        
        
        System.out.println("Recieving text input...");
        
        String mes;
        while(true){
            mes = scan.nextLine();
            if(mes.equals("/send")){
                System.out.println("Sending everything...");
                m.boardUwu.sendAllInfo();
            }else m.com.send(mes);
        }
    }
    
}
