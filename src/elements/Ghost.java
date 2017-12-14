/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import control.GameController;
import utils.Graph;
import java.awt.Graphics;
import java.util.ArrayList;
import utils.Consts;
import utils.Position;

/**
 *
 * @author wln
 */
public class Ghost extends AnimatedElement {

    protected String[][] animLeft;
    protected String[][] animRight;
    protected String[][] animUp;
    protected String[][] animDown;
    protected String[] animInOut;
    protected int state;
    protected boolean weak, dead;
    protected Position corner;
    protected double time, timeStart, timeChase, timeScat, timeTrans, timeStartWeak, timeWeak, timeDeath;

    public Ghost(Graph map, String... images) {
        super(12, images);
        timeChase = 0.0;
        timeScat = 0.0;
        timeTrans = 0.0;
        timeStart = System.nanoTime() / 1000000000.0;
        timeWeak = 10.0;
        timeDeath = 1.0;
        time = System.nanoTime() / 1000000000.0;

        weak = false;
        dead = false;
        speed = 0.05;
        state = 1;
        animLeft = new String[2][4];
        animRight = new String[3][4];
        animUp = new String[3][4];
        animDown = new String[3][4];
        animInOut = new String[8];

        int i = 1, j = 1;
        while (!map.existNode(i * Consts.NUM_CELLS[0] + j)) {
            j++;
            if (j >= Consts.NUM_CELLS[0] / 2) {
                i++;
                j = 1;
            }
        }
        corner = new Position(i, j, false);
    }

    @Override
    public void autoDraw(Graphics g) {
        super.autoDraw(g);
    }

    public void setMovDirection(int direction) {
        movDirection = direction;
    }

    public void move() {
        switch (movDirection) {
            case MOVE_LEFT:
                if (isAnimPaused()) {
                    pausePlay();
                }
                this.moveLeft();
                break;
            case MOVE_RIGHT:
                if (isAnimPaused()) {
                    pausePlay();
                }
                this.moveRight();
                break;
            case MOVE_UP:
                if (isAnimPaused()) {
                    pausePlay();
                }
                this.moveUp();
                break;
            case MOVE_DOWN:
                if (isAnimPaused()) {
                    pausePlay();
                }
                this.moveDown();
                break;
            case STOP:
                if (!isAnimPaused()) {
                    pausePlay();
                }
                break;
            default:
                break;
        }
    }

    public void tryMove(ArrayList<Element> e, GameController gm) {
        prevMove = lastMove;
        if (movDirection == 0) {
            movDirection = lastMove;
        }

        if (tryMove != 0) {
            if(pos.getX() > 0 && pos.getX() < Consts.NUM_CELLS[1] - 2 && pos.getY() > 0 && pos.getY() < Consts.NUM_CELLS[0]){
               setMovDirection(tryMove);
                move();
                pos.complete(lastMove);
                if (gm.isValidPosition(e, this)) {
                    lastMove = tryMove;
                    tryMove = 0;
                } else {
                    backToLastPosition();
                    setMovDirection(lastMove);
                    move();
                    if (!gm.isValidPosition(e, this)) {
                        tryMove = 0;
                        backToLastPosition();
                        setMovDirection(STOP);
                        lastMove = 0;
                    }
                }
            }else{
                setMovDirection(lastMove);
                move();
            }
                
            
        } else {
            move();
            if (!gm.isValidPosition(e, this)) {
                tryMove = movDirection;
                backToLastPosition();
                if (tryMove != lastMove) {
                    setMovDirection(lastMove);
                    move();
                    if (!gm.isValidPosition(e, this)) {
                        tryMove = 0;
                        backToLastPosition();
                        setMovDirection(STOP);
                    }
                } else {
                    setMovDirection(STOP);
                }
                movDirection = lastMove;
            } else {
                if (tryMove == 0 && movDirection != 0) {
                    lastMove = movDirection;
                }
            }
        }
        refreshAnim(false);
    }

    public void refreshAnim(boolean now) {
        if (prevMove != lastMove || now) {
            switch (lastMove) {
                case MOVE_UP:
                    if(!weak)
                        changeAnimation(getFrame(), true, animUp[0][0], animUp[0][1], animUp[0][2], animUp[0][3]);
                    else
                        changeAnimation(getFrame(), true, animUp[1][0], animUp[1][1], animUp[1][2], animUp[1][3]);
                    break;

                case MOVE_DOWN:
                    if(!weak)
                        changeAnimation(getFrame(), true, animDown[0][0], animDown[0][1], animDown[0][2], animDown[0][3]);
                    else
                        changeAnimation(getFrame(), true, animDown[1][0], animDown[1][1], animDown[1][2], animDown[1][3]);
                    break;

                case MOVE_LEFT:
                    if(!weak)
                        changeAnimation(getFrame(), true, animLeft[0][0], animLeft[0][1], animLeft[0][2], animLeft[0][3]);
                    else
                        changeAnimation(getFrame(), true, animLeft[1][0], animLeft[1][1], animLeft[1][2], animLeft[1][3]);
                    break;

                case MOVE_RIGHT:
                    if(!weak)
                        changeAnimation(getFrame(), true, animRight[0][0], animRight[0][1], animRight[0][2], animRight[0][3]);
                    else
                        changeAnimation(getFrame(), true, animRight[1][0], animRight[1][1], animRight[1][2], animRight[1][3]);
                    break;
            }
        }
    }

    public int refreshState(Pacman pacman) {
        time = System.nanoTime() / 1000000000.0;
        if(!dead){
            if(pacman.isAlive() && state == -1)
                reset();
            
            if (state == 0 && ((!weak && time - timeStart >= timeChase) || (weak && time - timeStart >= timeChase*0.6))) {
                if (time - timeStart < timeChase + timeTrans) {
                    double d = Math.random();
                    if (d >= 0.5) {
                        state = 1;
                        timeStart = time;
                    }
                } else {
                    state = 1;
                    timeStart = time;
                }
            } else if (state == 1 && ((!weak && time - timeStart >= timeScat) || (weak && time - timeStart >= timeScat*1.6))) {
                if (timeStart - time < timeScat + timeTrans) {
                    double d = Math.random();
                    if (d >= 0.5) {
                        state = 0;
                        timeStart = time;
                    }
                } else {
                    state = 0;
                    timeStart = time;
                }
            }
        }else{
            if(time - timeStart >= timeDeath)
                reset();
            
        }
        return state;
    }
    
    public void reset(){
        dead = false;
        weak = false;
        changeAnimation(0, true, animRight[0][0], animRight[0][1], animRight[0][2], animRight[0][3]);
        setPosition(4, 9);
        setDelay(12);
        state = 1;
        timeStart = System.nanoTime() / 1000000000.0;
    }
    
    public void exit(){
        if(state!=-1){
            setDelay(5);
            state = -1;
            movDirection = 0;
            lastMove = 0;
            tryMove = 0;
            if(!isAnimPaused())
                pausePlay();
            invokeAnimation(40, false, animInOut[7], animInOut[6], animInOut[5], animInOut[4], animInOut[3], animInOut[2], animInOut[1], animInOut[0], "char_.png");
        }
    }
    
    public void kill(){
        if(!dead){
            dead = true;
            changeAnimation(0, false, "char_.png");
            timeStart = System.nanoTime() / 1000000000.0;
            speed = speed/0.7;
        }
    }
    
    public boolean isAlive(){
        return !dead;
    }
    
    public void setWeak(boolean value){
        if(value){
            if (!weak){
                weak = true;
                timeStartWeak = System.nanoTime() / 1000000000.0;
                speed = speed*0.7;
            }
        }else{
            if(weak){
                weak = false;
                speed = speed/0.7;
            }
        }
        refreshAnim(true);
    }
    
    public boolean getWeak(){
        return weak;
    }

    

    public int moveToTarget(Position target, Graph g, boolean invert) {
        int myVert;

        switch (lastMove) {
            case MOVE_UP:
                myVert = (int) Math.floor(getPosition().getX()) * Consts.NUM_CELLS[0] + (int) Math.round(getPosition().getY());
                if (getPosition().getX() < 0 || getPosition().getX() > Consts.NUM_CELLS[1] - 3) {
                    myVert = -(int) Math.round(getPosition().getY());
                } else if (getPosition().getY() < 0 || getPosition().getY() > Consts.NUM_CELLS[0] - 1) {
                    myVert = -(int) Math.floor(getPosition().getX()) * Consts.NUM_CELLS[0];
                }
                break;
            case MOVE_DOWN:
                myVert = (int) Math.ceil(getPosition().getX()) * Consts.NUM_CELLS[0] + (int) Math.round(getPosition().getY());
                if (getPosition().getX() < 0 || getPosition().getX() > Consts.NUM_CELLS[1] - 3) {
                    myVert = -(int) Math.round(getPosition().getY());
                } else if (getPosition().getY() < 0 || getPosition().getY() > Consts.NUM_CELLS[0] - 1) {
                    myVert = -(int) Math.ceil(getPosition().getX()) * Consts.NUM_CELLS[0];
                }
                break;
            case MOVE_LEFT:
                myVert = (int) Math.round(getPosition().getX()) * Consts.NUM_CELLS[0] + (int) Math.floor(getPosition().getY());
                if (getPosition().getX() < 0 || getPosition().getX() > Consts.NUM_CELLS[1] - 3) {
                    myVert = -(int) Math.floor(getPosition().getY());
                } else if (getPosition().getY() < 0 || getPosition().getY() > Consts.NUM_CELLS[0] - 1) {
                    myVert = -(int) Math.round(getPosition().getX()) * Consts.NUM_CELLS[0];
                }
                break;
            case MOVE_RIGHT:
                myVert = (int) Math.round(getPosition().getX()) * Consts.NUM_CELLS[0] + (int) Math.ceil(getPosition().getY());
                if (getPosition().getX() < 0 || getPosition().getX() > Consts.NUM_CELLS[1] - 3) {
                    myVert = -(int) Math.ceil(getPosition().getY());
                } else if (getPosition().getY() < 0 || getPosition().getY() > Consts.NUM_CELLS[0] - 1) {
                    myVert = - (int) Math.ceil(getPosition().getX()) * Consts.NUM_CELLS[0];
                }
                break;
            default:
                myVert = (int) getPosition().getX() * Consts.NUM_CELLS[0] + (int) getPosition().getY();
                break;
               
        }
        
        int tVert = ((int) target.getX()) * Consts.NUM_CELLS[0] + (int) target.getY();
        if (target.getX() < 0 || target.getX() > Consts.NUM_CELLS[1] - 3) {
            tVert = -(int) target.getY();
        } else if (target.getY() < 0 || target.getY() > Consts.NUM_CELLS[0] - 1) {
            tVert = -((int) target.getX()) * Consts.NUM_CELLS[0];
        }
        
        if(invert && tVert >= 0)
            tVert = (Consts.NUM_CELLS[1] - 3) * Consts.NUM_CELLS[0] + (Consts.NUM_CELLS[0] - 1) - tVert;

        int[] link = {myVert, myVert};
        if (movDirection == Pacman.MOVE_UP)
                link[1] += Consts.NUM_CELLS[0];
        else if (movDirection == Pacman.MOVE_DOWN)
                link[1] -= Consts.NUM_CELLS[0];
        else if (movDirection == Pacman.MOVE_LEFT)
                link[1] += 1;
        else if (movDirection == Pacman.MOVE_RIGHT)
                link[1] -= 1;
        

        String way = g.minWay(myVert, tVert, link);

        if (!way.isEmpty()) {
            return Character.getNumericValue(way.charAt(0));
        } else {
            switch (lastMove) {
                case MOVE_UP:
                    if (g.existNode((int) Math.floor(pos.getX() - 1) * Consts.NUM_CELLS[0] + (int) Math.round(pos.getY()))) {
                        return MOVE_UP;
                    } else if (g.existNode((int) Math.floor(pos.getX()) * Consts.NUM_CELLS[0] + (int) Math.round(pos.getY() + 1))) {
                        return MOVE_RIGHT;
                    } else if (g.existNode((int) Math.floor(pos.getX()) * Consts.NUM_CELLS[0] + (int) Math.round(pos.getY() - 1))) {
                        return MOVE_LEFT;
                    } else {
                        return 0;
                    }
                case MOVE_DOWN:
                    if (g.existNode((int) Math.ceil(pos.getX() + 1) * Consts.NUM_CELLS[0] + (int) Math.round(pos.getY()))) {
                        return MOVE_DOWN;
                    } else if (g.existNode((int) Math.ceil(pos.getX()) * Consts.NUM_CELLS[0] + (int) Math.round(pos.getY() + 1))) {
                        return MOVE_RIGHT;
                    } else if (g.existNode((int) Math.ceil(pos.getX()) * Consts.NUM_CELLS[0] + (int) Math.round(pos.getY() - 1))) {
                        return MOVE_LEFT;
                    } else {
                        return 0;
                    }
                case MOVE_LEFT:
                    if (g.existNode((int) Math.round(pos.getX()) * Consts.NUM_CELLS[0] + (int) Math.floor(pos.getY() - 1))) {
                        return MOVE_LEFT;
                    } else if (g.existNode((int) Math.round(pos.getX() - 1) * Consts.NUM_CELLS[0] + (int) Math.floor(pos.getY()))) {
                        return MOVE_UP;
                    } else if (g.existNode((int) Math.round((pos.getX() + 1)) * Consts.NUM_CELLS[0] + (int) Math.floor(pos.getY()))) {
                        return MOVE_DOWN;
                    } else {
                        return 0;
                    }
                case MOVE_RIGHT:
                    if (g.existNode((int) Math.round(pos.getX()) * Consts.NUM_CELLS[0] + (int) Math.ceil(pos.getY() + 1))) {
                        return MOVE_RIGHT;
                    }else if (g.existNode((int) Math.round(pos.getX() - 1) * Consts.NUM_CELLS[0] + (int) Math.ceil(pos.getY()))) {
                        return MOVE_UP;
                    } else if (g.existNode((int) Math.round((pos.getX() + 1)) * Consts.NUM_CELLS[0] + (int) Math.ceil(pos.getY()))) {
                        return MOVE_DOWN;
                    } else {
                        return 0;
                    }
                default:
                    return 0;
            }
        }
    }
}
