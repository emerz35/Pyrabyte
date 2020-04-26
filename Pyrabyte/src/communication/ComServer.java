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

import com.esotericsoftware.kryonet.Server;
import java.io.IOException;

/**
 *
 * @author Adam Whittaker
 */
public class ComServer implements ComCom{
    
    
    private final Server server;
    
    
    public ComServer(int port){
        server = new Server();
        new ComListener().start(server, port);
    }

    @Override
    public void send(Object ob){
        server.sendToAllTCP(ob);
    }

    @Override
    public void connect(int timeout, String ip, int port) throws IOException{}
    
}
