package control;

import utils.Graph;
import elements.Element;
import elements.Dot;
import elements.Ghost;
import elements.Pacman;
import buttons.Text;
import elements.Blinky;
import elements.Cherry;
import elements.Clyde;
import elements.Inky;
import elements.Pinky;
import elements.PowerDot;
import elements.Strawberry;
import java.awt.Graphics;
import java.util.ArrayList;
import utils.Consts;

/**
 * Projeto de POO 2017
 * 
 * @author Luiz Eduardo
 * Baseado em material do Prof. Jose Fernando Junior
 */
public class GameController {
    private double time, timeStart, timePower = 7.0;
    private double timeStartS = System.nanoTime() / 1000000000.0, timeLimitS = 75.0, timeStartC = System.nanoTime() / 1000000000.0, timeLimitC = 50.0;
    private boolean power = false;
    private Strawberry st;
    private Cherry ch;
    private int scoreCont = 1, scoreBonus = 10000;
    
    public void drawAllElements(ArrayList<Element> elemArray, ArrayList<Element> bricksArray, int control, Graphics g){
        for(int i=elemArray.size() - 1 - control; i>=0; i--){
            elemArray.get(i).autoDraw(g);
        }
        for(int i=bricksArray.size() - 1; i>=0; i--){
            bricksArray.get(i).autoDraw(g);
        }
        
        for(int i=elemArray.size() - 1; i>=elemArray.size() - control; i--){
            elemArray.get(i).autoDraw(g);
        }
    }
    public void processAllElements(ArrayList<Element> e, GameScreen gs, Graph map){
        time = System.nanoTime() / 1000000000.0;
        if(e.isEmpty())
            return;
        
        Pacman lPacman = (Pacman)e.get(0);
        Blinky lBlinky = (Blinky)e.get(1);
        Inky lInky = (Inky)e.get(2);
        Pinky lPinky = (Pinky)e.get(3);
        Clyde lClyde = (Clyde)e.get(4);
        
        if (st != null && st.timeOut()){
            e.remove(st);
            st = null;
        }
        if (ch != null && ch.timeOut()){
            e.remove(ch);
            ch = null;
        }
            
        if(time - timeStartS >= timeLimitS){
            st = new Strawberry();
            Element[] aux = {e.get(e.size()-1),e.get(e.size()-2),e.get(e.size()-3)};
            e.remove(aux[0]); e.remove(aux[1]); e.remove(aux[2]);
            e.add(st);
            e.add(aux[2]); e.add(aux[1]); e.add(aux[0]);
            int i = (int)Math.floor(Math.random() * (Consts.NUM_CELLS[1] - 2)), j = (int)Math.floor(Math.random() * Consts.NUM_CELLS[0]);
            st.setPosition(i, j);
            while (!isEmptyPosition(e, st) || (i>8 && i<11 && j>7 && j<11)){
                i = (int)Math.floor(Math.random() * (Consts.NUM_CELLS[1] - 2));
                j = (int)Math.floor(Math.random() * Consts.NUM_CELLS[0]);
                st.setPosition(i, j);
            }
            timeStartS = System.nanoTime() / 1000000000.0;
        }
        
        if(time - timeStartC >= timeLimitC){
            ch = new Cherry();
            Element[] aux = {e.get(e.size()-1),e.get(e.size()-2),e.get(e.size()-3)};
            e.remove(aux[0]); e.remove(aux[1]); e.remove(aux[2]);
            e.add(ch);
            e.add(aux[2]); e.add(aux[1]); e.add(aux[0]);

            int i = (int)Math.floor(Math.random() * (Consts.NUM_CELLS[1] - 2)), j = (int)Math.floor(Math.random() * Consts.NUM_CELLS[0]);
            ch.setPosition(i, j);
            while (!isEmptyPosition(e, ch) || (i>8 && i<11 && j>7 && j<11)){
                i = (int)Math.floor(Math.random() * (Consts.NUM_CELLS[1] - 2));
                j = (int)Math.floor(Math.random() * Consts.NUM_CELLS[0]);
                ch.setPosition(i, j);
            }
            timeStartC = System.nanoTime() / 1000000000.0;
        }
        
        if(power){
            if(time - timeStart >= timePower){
                power = false;
                lBlinky.setWeak(false);
                lInky.setWeak(false);
                lPinky.setWeak(false);
                lClyde.setWeak(false);
                lPacman.resetCombo();
            }
        }
        
        lPacman.behavior(e, this);
        lBlinky.playBehavior(lPacman, map, e, this);
        lInky.playBehavior(lPacman, map, e, this, lBlinky);
        lPinky.playBehavior(lPacman, map, e, this);
        lClyde.playBehavior(lPacman, map, e, this);
        Element eTemp;
        for(int i = 1; i < e.size(); i++){
            eTemp = e.get(i);
            
            if(lPacman.overlap(eTemp)){
                if(eTemp instanceof Dot){
                    lPacman.addScore(10);
                    e.remove(eTemp);
                    gs.decrementDotCont();
                }
                if(eTemp instanceof Strawberry){
                    lPacman.addScore(300);
                    e.remove(eTemp);
                    st = null;
                }
                if(eTemp instanceof Cherry){
                    lPacman.addScore(100);
                    e.remove(eTemp);
                    ch = null;
                }
                if (eTemp instanceof PowerDot){
                    time = System.nanoTime() / 1000000000.0;
                    timeStart = System.nanoTime() / 1000000000.0;
                    power = true;
                    lPacman.addScore(50);
                    e.remove(eTemp);
                    lBlinky.setWeak(true);
                    lInky.setWeak(true);
                    lPinky.setWeak(true);
                    lClyde.setWeak(true);
                    gs.decrementDotCont();
                }
                if(eTemp instanceof Ghost){
                    if(!((Ghost) eTemp).getWeak()){
                        lPacman.die();
                        lBlinky.exit();
                        lInky.exit();
                        lPinky.exit();
                        lClyde.exit();
                    }else if (((Ghost) eTemp).isAlive()){
                        ((Ghost) eTemp).kill();
                        lPacman.incrementCombo();
                        lPacman.addScore(200 * lPacman.getCombo());
                        
                    }
                }
            }
        }
        
        if (lPacman.getScore() == scoreCont * scoreBonus){
            lPacman.addLive();
            scoreCont++;
        }
        
        Text lives = (Text)e.get(e.size()-2);
        lives.changeText("x" + Integer.toString(lPacman.getLives() + 1), false);
        
        
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
    
    public boolean isEmptyPosition(ArrayList<Element> elemArray, Element elem){
        Element elemAux;
        for(int i = 0; i < elemArray.size(); i++){
            elemAux = elemArray.get(i);            
            if(elemAux != elem && elemAux.overlap(elem))
                return false;
        }        
        return true;
    }
}
