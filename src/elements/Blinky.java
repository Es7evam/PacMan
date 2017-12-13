/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import control.GameController;
import java.util.ArrayList;
import utils.Graph;

/**
 *
 * @author wln
 */
public class Blinky extends Ghost{
    public Blinky(Graph map, String... images){
        super(map, images);
        for (int i=1; i<=4; i++){
            animLeft[0][i-1] = "blinkyL" + i + ".png";
            animRight[0][i-1] = "blinkyR" + i + ".png";
            animUp[0][i-1] = "blinkyU" + i + ".png";
            animDown[0][i-1] = "blinkyD" + i + ".png";
            animLeft[1][i-1] = "blinkyL" + i + "a.png";
            animRight[1][i-1] = "blinkyR" + i + "a.png";
            animUp[1][i-1] = "blinkyU" + i + "a.png";
            animDown[1][i-1] = "blinkyD" + i + "a.png";
            
            animInOut[i - 1] = "blinkyP" + i + ".png";
            animInOut[i + 3] = "blinkyP" + Integer.toString(i + 4) + ".png";
        }
    }
    
    public void playBehavior(Pacman pacman, Graph map, ArrayList<Element> e, GameController gm) {
        state = refreshState(pacman);
        
        if(!dead){
            if (state == 0) {
                setMovDirection(moveToTarget(pacman.getPosition(), map, weak));
                tryMove(e, gm);
            } else if (state == 1) {
                setMovDirection(moveToTarget(corner, map, weak));
                tryMove(e, gm);
            }
        } 
    }
}
