
package pyrabyte;

import communication.ComCom;
import java.awt.Canvas;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import static communication.ComCom.createComInstance;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.xml.parsers.ParserConfigurationException;
import org.bitlet.weupnp.GatewayDevice;
import org.bitlet.weupnp.GatewayDiscover;
import org.bitlet.weupnp.PortMappingEntry;
import org.xml.sax.SAXException;

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
        com = createComInstance(isServer);
        
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
    public static void main(String[] args) throws SocketException, IOException, UnknownHostException, SAXException, ParserConfigurationException, InterruptedException{
        Main m = new Main("", 0, false);
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter ip...");
        String ip = scan.nextLine();
        int port = 27960;  
        
        /*GatewayDiscover discover = new GatewayDiscover();
        
        discover.discover();
        GatewayDevice d = discover.getValidGateway();
        if(d != null){
            InetAddress localAddress = d.getLocalAddress();
            //String externalIPAddress = d.getExternalIPAddress();
            PortMappingEntry portMapping = new PortMappingEntry();
            if (!d.getSpecificPortMappingEntry(port,"TCP",portMapping)) {
                System.out.println("Port was already mapped. Aborting test.");    
            } else {
                System.out.println("Sending port mapping request");
                
                if (!d.addPortMapping(port, port,localAddress.getHostAddress(),"TCP","test")) {
                    System.out.println("Port mapping attempt failed");
                    System.out.println("Test FAILED");
                } else {
                    
                    Thread.sleep(1000*5);
                    d.deletePortMapping(port,"TCP");

                    System.out.println("Port mapping removed");
                    System.out.println("Test SUCCESSFUL");
                }
            }
                
        }*/
        
        
        
        try{
            m.com.connect(5000, ip, port);
        }catch(IOException e){
            e.printStackTrace(System.err);
        }
        
        System.out.println("Recieving text input...");
        
        while(true){
            m.com.send("Charlie: " + scan.nextLine());
        }
    }
    
}
