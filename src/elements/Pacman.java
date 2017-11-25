package elements;

import utils.Drawing;
import control.GameController;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Projeto de POO 2017
 * 
 * @author Luiz Eduardo
 * Baseado em material do Prof. Jose Fernando Junior
 */
public class Pacman extends Element  implements Serializable{
    
    public static final int STOP = 0;
    public static final int MOVE_LEFT = 1;
    public static final int MOVE_RIGHT = 2;
    public static final int MOVE_UP = 3;
    public static final int MOVE_DOWN = 4;
    
    private int movDirection = STOP;
    private int lastMove = 0;
    private int currentMove = 0;
    private int tryMove = 0;
    private int switchMove = 0;
    
    private int score;
    
    public Pacman(String imageName) {
        super(imageName);
        score = 0;
    }
    
    @Override
    public void autoDraw(Graphics g){
        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
    }

    public void backToLastPosition(){
        this.pos.comeBack();
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
                this.moveLeft();
                break;
            case MOVE_RIGHT:
                this.moveRight();
                break;
            case MOVE_UP:
                this.moveUp();
                break;
            case MOVE_DOWN:
                this.moveDown();
                break;
            default:
                break;
        }
    }
    
    public void TryToMove(ArrayList<Element> e, GameController gm){
        if (tryMove != 0){
            if((tryMove > 2 && pos.getY() > 0 && pos.getY() < utils.Consts.NUM_CELLS[0]-1) || (tryMove <= 2 && pos.getX() > 0 && pos.getX() < utils.Consts.NUM_CELLS[1]-3)){
                setMovDirection(tryMove);
                move();
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
                    }
                }else{
                    setMovDirection(STOP);
                }
                currentMove = lastMove;
            }else{
                if(tryMove == 0)
                    lastMove = currentMove;
            }
        }
    }
}