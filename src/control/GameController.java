package control;

import utils.Graph;
import elements.Element;
import elements.Dot;
import elements.Ghost;
import elements.Pacman;
import buttons.Text;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Projeto de POO 2017
 * 
 * @author Luiz Eduardo
 * Baseado em material do Prof. Jose Fernando Junior
 */
public class GameController {
    public void drawAllElements(ArrayList<Element> elemArray, Graphics g){
        for(int i=0; i<elemArray.size(); i++){
            elemArray.get(i).autoDraw(g);
        }
    }
    public void processAllElements(ArrayList<Element> e, Graph map){
        if(e.isEmpty())
            return;
        
        Pacman lPacman = (Pacman)e.get(0);
        Ghost lBlinky = (Ghost)e.get(e.size() - 2);
        
        lPacman.TryToMove(e, this);
        lBlinky.playBehavior(lPacman, map, e, this);
        
        Element eTemp;
        for(int i = 1; i < e.size(); i++){
            eTemp = e.get(i);
            if(lPacman.overlap(eTemp)){
                if(eTemp instanceof Dot){
                    lPacman.addScore(10);
                    e.remove(eTemp);
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
