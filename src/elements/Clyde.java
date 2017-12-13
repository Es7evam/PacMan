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
public class Clyde extends Ghost{
    public Clyde(Graph map, String... images){
        super(map, images);
        timeChase = 18.0;
        timeScat = 3.0;
        timeTrans = 2.0;
        for (int i=1; i<=4; i++){
            animLeft[0][i-1] = "clydeL" + i + ".png";
            animRight[0][i-1] = "clydeR" + i + ".png";
            animUp[0][i-1] = "clydeU" + i + ".png";
            animDown[0][i-1] = "clydeD" + i + ".png";
            animLeft[1][i-1] = "clydeL" + i + "a.png";
            animRight[1][i-1] = "clydeR" + i + "a.png";
            animUp[1][i-1] = "clydeU" + i + "a.png";
            animDown[1][i-1] = "clydeD" + i + "a.png";
            
            animInOut[i - 1] = "clydeP" + i + ".png";
            animInOut[i + 3] = "clydeP" + Integer.toString(i + 4) + ".png";
        }
        
        corner.setPosition(corner.getX(),Consts.NUM_CELLS[0] - 1 - corner.getY(), false);
    }
    
    public void playBehavior(Pacman pacman, Graph map, ArrayList<Element> e, GameController gm) {
        state = refreshState(pacman);
        if(!dead){
            if (state == 0) { System.out.println(calcDist(pacman));
                if(calcDist(pacman) > 8.0)
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
