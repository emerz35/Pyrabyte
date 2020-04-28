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
import cards.GateCard;
import cards.InputCard;
import cards.Placeholder;
import cards.assets.AND;
import cards.assets.NOT;
import cards.assets.OR;
import cards.assets.XOR;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import gameplay.*;
import java.io.IOException;
import java.util.LinkedList;

import static gui.Window.MAIN;

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
        JavaSerializer serial = new JavaSerializer();
        end.getKryo().register(String.class, serial);
        end.getKryo().register(Player.class, serial);
        end.getKryo().register(BoardState.class, serial);
        end.getKryo().register(Hand.class, serial);
        end.getKryo().register(Deck.class, serial);
        end.getKryo().register(Card.class, serial);
        end.getKryo().register(GateCard.class, serial);
        end.getKryo().register(InputCard.class, serial);
        end.getKryo().register(OR.class, serial);
        end.getKryo().register(XOR.class, serial);
        end.getKryo().register(NOT.class, serial);
        end.getKryo().register(AND.class, serial);
        end.getKryo().register(Placeholder.class, serial);
        end.getKryo().register(LinkedList.class, serial);
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
