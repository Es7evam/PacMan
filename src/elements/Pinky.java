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
public class Pinky extends Ghost{
    public Pinky(Graph map, String... images){
        super(map, images);
        timeChase = 12.0;
        timeScat = 6.0;
        timeTrans = 7.0;
        for (int i=1; i<=4; i++){
            animLeft[0][i-1] = "pinkyL" + i + ".png";
            animRight[0][i-1] = "pinkyR" + i + ".png";
            animUp[0][i-1] = "pinkyU" + i + ".png";
            animDown[0][i-1] = "pinkyD" + i + ".png";
            animLeft[1][i-1] = "pinkyL" + i + "a.png";
            animRight[1][i-1] = "pinkyR" + i + "a.png";
            animUp[1][i-1] = "pinkyU" + i + "a.png";
            animDown[1][i-1] = "pinkyD" + i + "a.png";
            
            animInOut[i - 1] = "pinkyP" + i + ".png";
            animInOut[i + 3] = "pinkyP" + Integer.toString(i + 4) + ".png";
        }
        
        corner.setPosition(Consts.NUM_CELLS[1] - 3 - corner.getX(),Consts.NUM_CELLS[0] - 1 - corner.getY(), false);
    }
    
    public void playBehavior(Pacman pacman, Graph map, ArrayList<Element> e, GameController gm) {
        state = refreshState(pacman);
        if(!dead){
            if (state == 0) {
                int dir1 = moveToTarget(pacman.getPosition(), map, weak);
                
                if(dir1>=3)
                    setMovDirection(dir1);
                else{
                    double d = Math.random();
                    if(d>0.5)
                        setMovDirection(dir1);
                    else
                        setMovDirection(moveToTarget(corner, map, weak));
                }
                
                tryMove(e, gm);
            } else if (state == 1) {
                setMovDirection(moveToTarget(corner, map, weak));
                tryMove(e, gm);
            }
        } 
    }
}
