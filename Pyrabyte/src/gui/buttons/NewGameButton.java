/*
 * Copyright (C) 2020 Charlie Hands
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
package gui.buttons;

import communication.EventMessage;
import gameplay.Board;
import static gui.Main.ACTIVE_BUTTON_COLOR;
import static gui.Main.DEACTIVE_BUTTON_COLOR;
import static gui.Window.MAIN;
import java.awt.Color;

/**
 *
 * @author Charlie Hands
 */
public class NewGameButton extends Button{
    
    private static final Color startColor = ACTIVE_BUTTON_COLOR,
            requestedColor = DEACTIVE_BUTTON_COLOR, opponentRequestedColor = Color.RED;
    
    public boolean requestSent = false, requestRecieved = false;
    
    private Board board;
    
    public NewGameButton(int x, int y, int w, int h, Board b) {
        super(x, y, w, h, startColor);
        board = b;
    }

    @Override
    public void run() {
        if(!requestSent && !requestRecieved){
            color = requestedColor;
            MAIN.com.send(EventMessage.NEWGAME);
            requestSent = true;
        }else if(requestRecieved){
            board.newGame();
        }
        
    }
    
    public void recieveRequest(){
        color = opponentRequestedColor;
        requestRecieved = true;
    }
}
