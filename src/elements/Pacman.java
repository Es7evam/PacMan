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
    private boolean paused, dead;
    public static boolean gameOver;
    private int score, cont, lives;
    private int combo = 0;
    private ArrayList<String>[] animDie;
    
    public Pacman(String... images) {
        super(5,images);
        paused = false;
        dead = false;
        gameOver = false;
        lives = 2;
        score = 0;
        cont = 0;
        alternative = false;
        speed = 0.052;
        
        animDie = new ArrayList[4];
        for (int i = 0; i<4; i++)
            animDie[i] = new ArrayList<>();
        
        for (int i=0; i<15; i++){
            if(i<3)
                animDie[0].add("pacmanRd1");
            else
                animDie[0].add("pacmanRd" + Integer.toString(i - 6));
        }
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
    
    public void incrementCombo(){
        combo++;
    }
    
    public void resetCombo(){
        combo = 0;
    }
    
    public int getLives(){
        return lives;
    }
    
    public int getCombo(){
        return combo;
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
    
    public void die(){
        if (!paused){
            dead = true;
            cont = 0;
            movDirection = 0;
            tryMove = 0;
            lastMove = 0;
            currentMove = 0;
            switchMove = 0;
            if(isAnimPaused())
                pausePlay();
            switch(prevMove){
                case MOVE_UP:
                    changeAnimation(0, false, "pacmanUd1.png", "pacmanUd2.png", "pacmanUd3.png", "pacmanUd4.png", "pacmanUd5.png", "pacmanUd6.png", "pacmanUd7.png", "pacmanUd8.png", "pacmanUd9.png", "pacmanE1.png", "pacmanE2.png", "pacmanE3.png", "pacmanE4.png", "char_.png");
                    break;
                case MOVE_DOWN:
                    changeAnimation(0, false, "pacmanDd1.png", "pacmanDd2.png", "pacmanDd3.png", "pacmanDd4.png", "pacmanDd5.png", "pacmanDd6.png", "pacmanDd7.png", "pacmanDd8.png", "pacmanDd9.png", "pacmanE1.png", "pacmanE2.png", "pacmanE3.png", "pacmanE4.png", "char_.png");
                    break;
                case MOVE_LEFT:
                    changeAnimation(0, false, "pacmanLd1.png", "pacmanLd2.png", "pacmanLd3.png", "pacmanLd4.png", "pacmanLd5.png", "pacmanLd6.png", "pacmanLd7.png", "pacmanLd8.png", "pacmanLd9.png", "pacmanE1.png", "pacmanE2.png", "pacmanE3.png", "pacmanE4.png", "char_.png");
                    break;
                case MOVE_RIGHT:
                    changeAnimation(0, false, "pacmanRd1.png", "pacmanRd2.png", "pacmanRd3.png", "pacmanRd4.png", "pacmanRd5.png", "pacmanRd6.png", "pacmanRd7.png", "pacmanRd8.png", "pacmanRd9.png", "pacmanE1.png", "pacmanE2.png", "pacmanE3.png", "pacmanE4.png", "char_.png");
                    break;
                default:
                    changeAnimation(0, false, "pacmanRd1.png", "pacmanRd2.png", "pacmanRd3.png", "pacmanRd4.png", "pacmanRd5.png", "pacmanRd6.png", "pacmanRd7.png", "pacmanRd8.png", "pacmanRd9.png", "pacmanE1.png", "pacmanE2.png", "pacmanE3.png", "pacmanE4.png", "char_.png");
                    break;
            }
            lives--;
        }
        paused = true;
    }
    
    public boolean isAlive(){
        return !dead;
    }
    
    public void behavior(ArrayList<Element> e, GameController gm){
        if(!paused)
            tryToMove(e, gm);
        if(dead){
            cont++;
            if(cont >= delay * 20){ System.out.println(lives);
                if(lives>=0){
                    paused = false;
                    dead = false;
                    setPosition(Consts.PAC_POS[0], Consts.PAC_POS[1]);
                    changeAnimation(0,true,"pacmanR1.png");
                }else{
                    gameOver = true;
                }
            }
        }
    }
    
    public void refreshAnim(){
        if(prevMove != lastMove){
            if(prevMove!=STOP){
                switch (lastMove){
                    case MOVE_UP:
                        if((prevMove == MOVE_RIGHT && !alternative) || ((prevMove == MOVE_LEFT || prevMove == MOVE_DOWN) && alternative)){
                            alternative = false;
                            changeAnimation(getFrame(), true, "pacmanU1.png","pacmanU2.png","pacmanU3.png","pacmanU2.png");
                        }else{
                            alternative = true;
                            changeAnimation(getFrame(), true, "pacmanU1a.png","pacmanU2a.png","pacmanU3a.png","pacmanU2a.png");
                        }
                        break;

                    case MOVE_DOWN:
                        if((prevMove == MOVE_RIGHT && !alternative) || ((prevMove == MOVE_UP || prevMove == MOVE_LEFT) && alternative)){
                            alternative = false;
                            changeAnimation(getFrame(), true, "pacmanD1.png","pacmanD2.png","pacmanD3.png","pacmanD2.png");
                        }else{
                            alternative = true;
                            changeAnimation(getFrame(), true, "pacmanD1a.png","pacmanD2a.png","pacmanD3a.png","pacmanD2a.png");
                        }
                        break;

                    case MOVE_LEFT:
                        if((prevMove == MOVE_RIGHT && !alternative) || ((prevMove == MOVE_UP || prevMove == MOVE_DOWN) && alternative)){
                            alternative = false;
                            changeAnimation(getFrame(), true, "pacmanL1.png","pacmanL2.png","pacmanL3.png","pacmanL2.png");
                        }else{
                            alternative = true;
                            changeAnimation(getFrame(), true, "pacmanL1a.png","pacmanL2a.png","pacmanL3a.png","pacmanL2a.png");
                        }
                        break;

                    case MOVE_RIGHT:
                        if((prevMove == MOVE_LEFT || prevMove == MOVE_DOWN || prevMove == MOVE_UP) && !alternative){
                            alternative = false;
                            changeAnimation(getFrame(), true, "pacmanR1.png","pacmanR2.png","pacmanR3.png","pacmanR2.png");
                        }else{
                            alternative = true;
                            changeAnimation(getFrame(), true, "pacmanR1a.png","pacmanR2a.png","pacmanR3a.png","pacmanR2a.png");
                        }
                        break;
                }
            }else{
                switch (lastMove){
                    case MOVE_UP:
                        changeAnimation(getFrame(), true, "pacmanU1.png","pacmanU2.png","pacmanU3.png","pacmanU2.png");
                        break;

                    case MOVE_DOWN:
                        changeAnimation(getFrame(), true, "pacmanD1.png","pacmanD2.png","pacmanD3.png","pacmanD2.png");
                        break;

                    case MOVE_LEFT:
                        changeAnimation(getFrame(), true, "pacmanL1.png","pacmanL2.png","pacmanL3.png","pacmanL2.png");
                        break;

                    case MOVE_RIGHT:
                        changeAnimation(getFrame(), true, "pacmanR1.png","pacmanR2.png","pacmanR3.png","pacmanR2.png");
                        break;
                }
            }
        }
    }
    
    public void tryToMove(ArrayList<Element> e, GameController gm){
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
