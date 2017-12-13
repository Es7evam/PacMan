package control;

import utils.Graph;
import elements.Element;
import elements.Dot;
import elements.Ghost;
import elements.Pacman;
import buttons.Text;
import elements.Blinky;
import elements.PowerDot;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Projeto de POO 2017
 * 
 * @author Luiz Eduardo
 * Baseado em material do Prof. Jose Fernando Junior
 */
public class GameController {
    private double time, timeStart, timePower = 10.0;
    private boolean power = false;
    
    public void drawAllElements(ArrayList<Element> elemArray, Graphics g){
        for(int i=elemArray.size() - 1; i>=0; i--){
            elemArray.get(i).autoDraw(g);
        }
    }
    public void processAllElements(ArrayList<Element> e, Graph map){
        if(e.isEmpty())
            return;
        
        Pacman lPacman = (Pacman)e.get(0);
        Blinky lBlinky = (Blinky)e.get(1);
        
        if(power){
            time = System.nanoTime() / 1000000000.0;
            if(time - timeStart >= timePower){
                power = false;
                lBlinky.setWeak(false);
                lPacman.resetCombo();
            }
        }
        
        lPacman.behavior(e, this);
        lBlinky.playBehavior(lPacman, map, e, this);
        
        Element eTemp;
        for(int i = 1; i < e.size(); i++){
            eTemp = e.get(i);
            if(lPacman.overlap(eTemp)){
                if(eTemp instanceof Dot){
                    lPacman.addScore(10);
                    e.remove(eTemp);
                }
                if (eTemp instanceof PowerDot){
                    time = System.nanoTime() / 1000000000.0;
                    timeStart = System.nanoTime() / 1000000000.0;
                    power = true;
                    lPacman.addScore(50);
                    e.remove(eTemp);
                    lBlinky.setWeak(true);
                }
                
                if(eTemp instanceof Ghost){
                    if(!((Ghost) eTemp).getWeak()){
                        lPacman.die();
                        ((Ghost) eTemp).exit();
                    }else if (((Ghost) eTemp).isAlive()){
                        ((Ghost) eTemp).kill();
                        lPacman.addScore(200);
                        lPacman.incrementCombo();
                    }
                }
            }
        }
        
        Text score = (Text)e.get(e.size()-1);
        score.changeText(Integer.toString(lPacman.getScore()), true);
       
    }
    public boolean isValidPosition(ArrayList<Element> elemArray, Element elem){
        Element elemAux;
        for(int i = 0; i < elemArray.size(); i++){
            elemAux = elemArray.get(i);            
            if(elemAux != elem && !elemAux.isTransposable()){
                if(elemAux.overlap(elem))
                    return false;
            }
        }        
        return true;
    }
}
