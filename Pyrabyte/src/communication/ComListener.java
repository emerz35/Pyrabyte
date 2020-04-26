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
import java.io.IOException;
import pyrabyte.BoardState;
import pyrabyte.Player;

/**
 *
 * @author Adam Whittaker
 */
public class ComListener extends Listener{
    
    @Override
    public void received(Connection ctc, Object ob){
        if(ob instanceof String) System.out.println(ob);
        else if(ob instanceof Player){
        
        }else if(ob instanceof BoardState){
            
        }
    }

    @Override
    public void connected(Connection ctc){
        ctc.sendTCP("The other player has connected!");
    }

    @Override
    public void disconnected(Connection ctc){
        System.out.println("The other player has disconnected!");
    }
    
    
    public void start(EndPoint end){
        end.getKryo().register(String.class);
        end.getKryo().register(Player.class);
        end.getKryo().register(BoardState.class);
        end.addListener(this);
        if(end instanceof Server){
            try{
                ((Server) end).bind(27960);
            }catch(IOException e){
                e.printStackTrace(System.err);
            }
        }
        end.start();
    }
    
}
