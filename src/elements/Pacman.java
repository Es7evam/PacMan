package elements;

import utils.Drawing;
import control.GameController;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import utils.Consts;

/**
 * Projeto de POO 2017
 * 
 * @author Luiz Eduardo
 * Baseado em material do Prof. Jose Fernando Junior
 */
public class Pacman extends AnimatedElement  implements Serializable{
    private int currentMove = 0;
    private int switchMove = 0;
    private boolean alternative;
    
    private int score;
    
    public Pacman(String... images) {
        super(5,images);
        score = 0;
        alternative = false;
        speed = 0.068;
    }
    
    @Override
    public void autoDraw(Graphics g){
        super.autoDraw(g);
    }

    
    
    public void setMovDirection(int direction) {
        movDirection = direction;
    }
    
    public int getScore(){
        return score;
    }
    
    public void addScore (int bonus){
        score += bonus;
    }
    
    public int getLastMove(){
        return lastMove;
    }
    
    public void setLastMove(int value){
        lastMove = value;
    }
    
    public int getCurrentMove(){
        return currentMove;
    }
    
    public void setCurrentMove(int value){
        currentMove = value;
    }
    
    public int getSwitchMove(){
        return switchMove;
    }
    
    public void setSwitchMove(int value){
        switchMove = value;
    }
    
     public int getTryMove(){
        return tryMove;
    }
    
    public void move() {
        switch (movDirection) {
            case MOVE_LEFT:
                if(isAnimPaused())
                    pausePlay();
                this.moveLeft();
                break;
            case MOVE_RIGHT:
                if(isAnimPaused())
                    pausePlay();
                this.moveRight();
                break;
            case MOVE_UP:
                if(isAnimPaused())
                    pausePlay();
                this.moveUp();
                break;
            case MOVE_DOWN:
                if(isAnimPaused())
                    pausePlay();
                this.moveDown();
                break;
            case STOP:
                if(!isAnimPaused())
                    pausePlay();
                break;
            default:
                break;
        }
    }
    
    public void refreshAnim(){
        if(prevMove != lastMove){
            if(prevMove!=STOP){
                switch (lastMove){
                    case MOVE_UP:
                        if((prevMove == MOVE_RIGHT && !alternative) || ((prevMove == MOVE_LEFT || prevMove == MOVE_DOWN) && alternative)){
                            alternative = false;
                            changeAnimation(getFrame(), "pacmanU1.png","pacmanU2.png","pacmanU3.png","pacmanU2.png");
                        }else{
                            alternative = true;
                            changeAnimation(getFrame(), "pacmanU1a.png","pacmanU2a.png","pacmanU3a.png","pacmanU2a.png");
                        }
                        break;

                    case MOVE_DOWN:
                        if((prevMove == MOVE_RIGHT && !alternative) || ((prevMove == MOVE_UP || prevMove == MOVE_LEFT) && alternative)){
                            alternative = false;
                            changeAnimation(getFrame(), "pacmanD1.png","pacmanD2.png","pacmanD3.png","pacmanD2.png");
                        }else{
                            alternative = true;
                            changeAnimation(getFrame(), "pacmanD1a.png","pacmanD2a.png","pacmanD3a.png","pacmanD2a.png");
                        }
                        break;

                    case MOVE_LEFT:
                        if((prevMove == MOVE_RIGHT && !alternative) || ((prevMove == MOVE_UP || prevMove == MOVE_DOWN) && alternative)){
                            alternative = false;
                            changeAnimation(getFrame(), "pacmanL1.png","pacmanL2.png","pacmanL3.png","pacmanL2.png");
                        }else{
                            alternative = true;
                            changeAnimation(getFrame(), "pacmanL1a.png","pacmanL2a.png","pacmanL3a.png","pacmanL2a.png");
                        }
                        break;

                    case MOVE_RIGHT:
                        if((prevMove == MOVE_LEFT || prevMove == MOVE_DOWN || prevMove == MOVE_UP) && !alternative){
                            alternative = false;
                            changeAnimation(getFrame(), "pacmanR1.png","pacmanR2.png","pacmanR3.png","pacmanR2.png");
                        }else{
                            alternative = true;
                            changeAnimation(getFrame(), "pacmanR1a.png","pacmanR2a.png","pacmanR3a.png","pacmanR2a.png");
                        }
                        break;
                }
            }else{
                switch (lastMove){
                    case MOVE_UP:
                        changeAnimation(getFrame(), "pacmanU1.png","pacmanU2.png","pacmanU3.png","pacmanU2.png");
                        break;

                    case MOVE_DOWN:
                        changeAnimation(getFrame(), "pacmanD1.png","pacmanD2.png","pacmanD3.png","pacmanD2.png");
                        break;

                    case MOVE_LEFT:
                        changeAnimation(getFrame(), "pacmanL1.png","pacmanL2.png","pacmanL3.png","pacmanL2.png");
                        break;

                    case MOVE_RIGHT:
                        changeAnimation(getFrame(), "pacmanR1.png","pacmanR2.png","pacmanR3.png","pacmanR2.png");
                        break;
                }
            }
        }
    }
    
    public void TryToMove(ArrayList<Element> e, GameController gm){
        prevMove = lastMove;
        if (tryMove != 0){
            if((tryMove > 2 && pos.getY() > 0 && pos.getY() < utils.Consts.NUM_CELLS[0]-1) || (tryMove <= 2 && pos.getX() > 0 && pos.getX() < utils.Consts.NUM_CELLS[1]-3)){
                setMovDirection(tryMove);
                move();
                pos.complete(lastMove);
                if (gm.isValidPosition(e, this)) {
                    currentMove = tryMove;
                    lastMove = tryMove;
                    tryMove = 0;
                }else{
                    backToLastPosition();
                    if(switchMove != tryMove && switchMove != 0){
                        currentMove = switchMove;
                        setMovDirection(currentMove);
                        move();
                        if (gm.isValidPosition(e, this))
                            tryMove = 0;
                        else{
                            tryMove = switchMove;
                            currentMove = lastMove;
                        }
                        switchMove = 0;
                        backToLastPosition();
                    }
                    lastMove = currentMove;
                    setMovDirection(currentMove);
                    move();
                    if (!gm.isValidPosition(e, this)) {
                        tryMove = 0;
                        backToLastPosition();
                        setMovDirection(STOP);
                        pos.complete(lastMove);
                    }
                }
            }else{
                setMovDirection(lastMove);
                currentMove = lastMove;
                move();
            }
        }else{
            move();
            if (!gm.isValidPosition(e, this)) {
                tryMove = currentMove;
                backToLastPosition();
                if(tryMove != lastMove){
                    setMovDirection(lastMove);
                    move();
                    if (!gm.isValidPosition(e, this)) {
                        tryMove = 0;
                        backToLastPosition();
                        setMovDirection(STOP);
                        pos.complete(lastMove);
                    }
                }else{
                    setMovDirection(STOP);
                    pos.complete(lastMove);
                }
                currentMove = lastMove;
            }else{
                if(tryMove == 0)
                    lastMove = currentMove;
            }
        }
        refreshAnim();
    }
}
