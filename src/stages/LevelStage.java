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
import elements.Blinky;
import elements.Ghost;
import java.util.ArrayList;
import utils.Consts;
import buttons.Text;
import elements.Clyde;
import elements.Inky;
import elements.Pinky;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Logger;

/**
 *
 * @author wln
 */
public class LevelStage extends Stage{
     private Pacman pacman;
     private Ghost[] ghosts;
     private Level level;
     private ArrayList<Integer> moveStack;
    
    public LevelStage(String name, Level level){
        super(name);
        this.level = level;
        moveStack = new ArrayList<Integer>();
        ghosts = new Ghost[4];
        
         /*Cria e adiciona elementos*/
        pacman = new Pacman("pacmanR1.png","pacmanR2.png","pacmanR3.png","pacmanR2.png");
        pacman.setPosition(Consts.PAC_POS[0], Consts.PAC_POS[1]);
        this.addElement(pacman);
        
        ghosts[0] = new Blinky(level.getMap(),"blinkyR1.png","blinkyR2.png","blinkyR3.png","blinkyR4.png");
        ghosts[0].setPosition(4, 9);
        this.addElement(ghosts[0]);
        
        ghosts[1] = new Inky(level.getMap(),"inkyR1.png","inkyR2.png","inkyR3.png","inkyR4.png");
        ghosts[1].setPosition(4, 9);
        this.addElement(ghosts[1]);
        
        ghosts[2] = new Pinky(level.getMap(),"pinkyR1.png","pinkyR2.png","pinkyR3.png","pinkyR4.png");
        ghosts[2].setPosition(4, 9);
        this.addElement(ghosts[2]);
        
        ghosts[3] = new Clyde(level.getMap(),"clydeR1.png","clydeR2.png","clydeR3.png","clydeR4.png");
        ghosts[3].setPosition(4, 9);
        this.addElement(ghosts[3]);
        
        for (int i=0; i<level.getSize(); i++){
            addElement(level.getElement(i));
        }
        Text levelName = new Text("char_", level.getName());
        levelName.setPosition(Consts.NUM_CELLS[1] - 2, 0);
        this.addElement(levelName);
        
        Text lives = new Text("pacmanRd3.png", "x3");
        lives.setPosition(Consts.NUM_CELLS[1] - 2, Consts.NUM_CELLS[0] - 4);
        this.addElement(lives);
        
        Text score = new Text("char_","00000000");
        score.setPosition(Consts.NUM_CELLS[1] - 1, Consts.NUM_CELLS[0] - 10);
        this.addElement(score);
    }
    
    public Level getLevel(){
        return level;
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
        }else if (e.getKeyCode() == KeyEvent.VK_S) {
             saveGame();
        }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
             MenuStage menu = new MenuStage("Menu");
             gs.setStage(menu);
        }
    }
    
    public void saveGame(){
         try {
            FileOutputStream fileOut = new FileOutputStream(new java.io.File(".").getCanonicalPath() + "/save/save.txt");
            ObjectOutputStream out;
            out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            System.out.println("Game Saved");
         } catch (FileNotFoundException ex) {
             Logger.getLogger(LevelStage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         } catch (IOException ex) {
                 Logger.getLogger(LevelStage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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