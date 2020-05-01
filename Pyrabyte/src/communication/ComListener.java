/*
 * Copyright (C) 2020 Adam Whittaker
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package communication;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import gameplay.Board;
import gameplay.BoardState;
import gameplay.Player;
import gui.screens.EndScreen;
import java.io.IOException;

import static gui.Window.MAIN;
import static utils.Serializer.fromByteArray;
import static utils.Serializer.toByteArray;

/**
 *
 * @author Adam Whittaker
 */
public class ComListener extends Listener{
    
    
    private final Board board;
    
    public ComListener(Board b){
        board = b;
    }
    
    @Override
    public void received(Connection ctc, Object ob){
        if(ob instanceof byte[]){
            ob = fromByteArray((byte[]) ob);
            if(ob instanceof EventMessage){
                if(ob.equals(EventMessage.PASS)) board.switchTurn(true);
                else if(ob.equals(EventMessage.WIN)) {
                    board.scoopCards();
                    MAIN.setScreen(new EndScreen("LOSE", board.nGBtn));
                }
                else if(ob.equals(EventMessage.NEWGAME)){
                    if(board.nGBtn.requestSent) board.newGame();
                    else board.nGBtn.recieveRequest();
                }
            }else if(ob instanceof String) System.out.println(ob);
            else if(ob instanceof Player){
                System.out.println("Recieved player");
                if(board.opponent == null){
                    board.opponent = ((Player) ob);
                    MAIN.start();
                }else board.opponent = ((Player) ob);
            }else if(ob instanceof BoardState){
                System.out.println("Recieved Board state");
                board.boardState = ((BoardState) ob);
            }else System.out.println("Recieved nothing");
        }
    }

    @Override
    public void connected(Connection ctc){
        ctc.sendTCP(toByteArray("The other player has connected!"));
        System.out.println("Sending everything...");
        board.sendAllInfo();
    }

    @Override
    public void disconnected(Connection ctc){
        System.out.println("The other player has disconnected!");
    }
    
    
    public void start(EndPoint end, int port){
        end.getKryo().register(byte[].class);
        end.addListener(this);
        if(end instanceof Server){
            try{
                ((Server) end).bind(port);
            }catch(IOException e){
                e.printStackTrace(System.err);
            }
        }
        end.start();
    }
    
}
