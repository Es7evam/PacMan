/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import control.GameController;
import java.util.ArrayList;
import utils.Consts;
import utils.Graph;

/**
 *
 * @author wln
 */
public class Inky extends Ghost{
    public Inky(Graph map, String... images){
        super(map, images);
        timeChase = 14.0;
        timeScat = 4.0;
        timeTrans = 5.0;
        for (int i=1; i<=4; i++){
            animLeft[0][i-1] = "inkyL" + i + ".png";
            animRight[0][i-1] = "inkyR" + i + ".png";
            animUp[0][i-1] = "inkyU" + i + ".png";
            animDown[0][i-1] = "inkyD" + i + ".png";
            animLeft[1][i-1] = "inkyL" + i + "a.png";
            animRight[1][i-1] = "inkyR" + i + "a.png";
            animUp[1][i-1] = "inkyU" + i + "a.png";
            animDown[1][i-1] = "inkyD" + i + "a.png";
            
            animInOut[i - 1] = "inkyP" + i + ".png";
            animInOut[i + 3] = "inkyP" + Integer.toString(i + 4) + ".png";
        }
        
        corner.setPosition(Consts.NUM_CELLS[1] - 3 - corner.getX(), corner.getY(), false);
    }
    
    public void playBehavior(Pacman pacman, Graph map, ArrayList<Element> e, GameController gm, Blinky b) {
        state = refreshState(pacman);
        if(!dead){
            if (state == 0) {
                if(calcDist(b) < 8.0)
                    setMovDirection(moveToTarget(pacman.getPosition(), map, weak));
                else
                    setMovDirection(moveToTarget(corner, map, weak));
                
                tryMove(e, gm);
            } else if (state == 1) {
                setMovDirection(moveToTarget(corner, map, weak));
                tryMove(e, gm);
            }
        } 
    }
}
