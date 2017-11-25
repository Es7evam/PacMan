/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stages;

import control.GameScreen;
import elements.Pacman;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import control.Level;
import java.util.ArrayList;
import utils.Consts;

/**
 *
 * @author wln
 */
public class LevelStage extends Stage{
     private Pacman pacman;
     private Level level;
     private ArrayList<Integer> moveStack;
    
    public LevelStage(String name, Level level){
        super(name);
        this.level = level;
        moveStack = new ArrayList<Integer>();
        
         /*Cria e adiciona elementos*/
        pacman = new Pacman("pacmanL1.png");
        pacman.setPosition(Consts.PAC_POS[0], Consts.PAC_POS[1]);
        this.addElement(pacman);
        
        for (int i=0; i<level.getSize(); i++){
            addElement(level.getElement(i));
        }
    }
    
    public void addMove(int i){
        moveStack.add(i);
    }
    
    public void removeMove(Integer i){
        moveStack.remove(i);
    }
    
    public int getMove(){
        if(!moveStack.isEmpty())
            return moveStack.get(moveStack.size() - 1);
        else
            return -1;
    }
    
    @Override
    public void getKey(KeyEvent e, GameScreen gs){
        if (e.getKeyCode() == KeyEvent.VK_UP && pacman.getTryMove() != Pacman.MOVE_UP){
            pacman.setMovDirection(Pacman.MOVE_UP);
            pacman.setCurrentMove(Pacman.MOVE_UP);
            if(moveStack.isEmpty() || getMove() != Pacman.MOVE_UP){
                addMove(Pacman.MOVE_UP);
                if(!moveStack.isEmpty())
                    pacman.setSwitchMove(Pacman.MOVE_UP);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && pacman.getTryMove() != Pacman.MOVE_DOWN) {
            pacman.setMovDirection(Pacman.MOVE_DOWN);
            pacman.setCurrentMove(Pacman.MOVE_DOWN);
            if(moveStack.isEmpty() || getMove() != Pacman.MOVE_DOWN){
                addMove(Pacman.MOVE_DOWN);
                if(!moveStack.isEmpty())
                    pacman.setSwitchMove(Pacman.MOVE_DOWN);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && pacman.getTryMove() != Pacman.MOVE_LEFT) {
            pacman.setMovDirection(Pacman.MOVE_LEFT);
            pacman.setCurrentMove(Pacman.MOVE_LEFT);
            if(moveStack.isEmpty() || getMove() != Pacman.MOVE_LEFT){
                addMove(Pacman.MOVE_LEFT);
                if(!moveStack.isEmpty())
                    pacman.setSwitchMove(Pacman.MOVE_LEFT);
            }
        }else if (e.getKeyCode() == KeyEvent.VK_RIGHT && pacman.getTryMove() != Pacman.MOVE_RIGHT) {
            pacman.setMovDirection(Pacman.MOVE_RIGHT);
            pacman.setCurrentMove(Pacman.MOVE_RIGHT);
            if(moveStack.isEmpty() || getMove() != Pacman.MOVE_RIGHT){
                addMove(Pacman.MOVE_RIGHT);
                if(!moveStack.isEmpty())
                    pacman.setSwitchMove(Pacman.MOVE_RIGHT);
            }
        }else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            pacman.setMovDirection(Pacman.STOP);
            pacman.setCurrentMove(Pacman.STOP);
        }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
             MenuStage menu = new MenuStage("Menu");
             gs.setStage(menu);
        }
    }
    
    public void releaseKey(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_UP){
            int aux = getMove();
            removeMove(Pacman.MOVE_UP);
            if(aux == Pacman.MOVE_UP && !moveStack.isEmpty()){
                pacman.setMovDirection(getMove());
                pacman.setCurrentMove(getMove());
            }
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            int aux = getMove();
            removeMove(Pacman.MOVE_DOWN);
            if(aux == Pacman.MOVE_DOWN && !moveStack.isEmpty()){
                pacman.setMovDirection(getMove());
                pacman.setCurrentMove(getMove());
            }
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            int aux = getMove();
            removeMove(Pacman.MOVE_LEFT);
            if(aux == Pacman.MOVE_LEFT && !moveStack.isEmpty()){
                pacman.setMovDirection(getMove());
                pacman.setCurrentMove(getMove());
            }
        }else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            int aux = getMove();
            removeMove(Pacman.MOVE_RIGHT);
            if(aux == Pacman.MOVE_RIGHT && !moveStack.isEmpty()){
                pacman.setMovDirection(getMove());
                pacman.setCurrentMove(getMove());
            }
        }
    }

    @Override
    public void getClick(MouseEvent me, GameScreen gs) {
    }
}