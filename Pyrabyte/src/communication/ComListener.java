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

import cards.Card;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import gameplay.Board;
import gameplay.BoardState;
import gameplay.Deck;
import gameplay.Hand;
import gameplay.Player;
import static gui.Window.MAIN;
import java.io.IOException;
import java.util.LinkedList;

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
        if(ob instanceof String) System.out.println(ob);
        else if(ob instanceof Player){
            board.opponent = ((Player) ob);
        }else if(ob instanceof BoardState){
            board.boardState = ((BoardState) ob);
        }
    }

    @Override
    public void connected(Connection ctc){
        ctc.sendTCP("The other player has connected!");
        MAIN.com.send(MAIN.localPlayer);
        MAIN.start();
        
    }

    @Override
    public void disconnected(Connection ctc){
        System.out.println("The other player has disconnected!");
    }
    
    
    public void start(EndPoint end, int port){
        end.getKryo().register(String.class);
        end.getKryo().register(Player.class);
        end.getKryo().register(BoardState.class);
        end.getKryo().register(Hand.class);
        end.getKryo().register(Deck.class);
        end.getKryo().register(Card.class);
        end.getKryo().register(LinkedList.class);
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
