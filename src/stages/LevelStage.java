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

/**
 *
 * @author wln
 */
public class LevelStage extends Stage{
     private Pacman pacman;
    
    public LevelStage(String name){
        super(name);
        
        /*Cria e adiciona elementos*/
        pacman = new Pacman("pacmanL1.png");
        pacman.setPosition(0, 0);
        this.addElement(pacman);
    }
    
    @Override
    public void getKey(KeyEvent e, GameScreen gs){
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            pacman.setMovDirection(Pacman.MOVE_UP);
            pacman.setCurrentMove(Pacman.MOVE_UP);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            pacman.setMovDirection(Pacman.MOVE_DOWN);
            pacman.setCurrentMove(Pacman.MOVE_DOWN);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            pacman.setMovDirection(Pacman.MOVE_LEFT);
            pacman.setCurrentMove(Pacman.MOVE_LEFT);
        }else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            pacman.setMovDirection(Pacman.MOVE_RIGHT);
            pacman.setCurrentMove(Pacman.MOVE_RIGHT);
        }else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            pacman.setMovDirection(Pacman.STOP);
            pacman.setCurrentMove(Pacman.STOP);
        }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
             MenuStage menu = new MenuStage("Menu");
             gs.setStage(menu);
        }
    }

    @Override
    public void getClick(MouseEvent me, GameScreen gs) {
    }
}