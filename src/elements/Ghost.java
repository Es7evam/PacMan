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

/**
 *
 * @author wln
 */
public class Ghost extends AnimatedElement {

    protected String[][] animLeft;
    protected String[][] animRight;
    protected String[][] animUp;
    protected String[][] animDown;
    protected int state;

    public Ghost(String... images) {
        super(12, images);
        speed = 0.066;
        state = 0;
        animLeft = new String[2][4];
        animRight = new String[3][4];
        animUp = new String[3][4];
        animDown = new String[3][4];
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
        refreshAnim();
    }

    public void refreshAnim() {
        if (prevMove != lastMove) {
            switch (lastMove) {
                case MOVE_UP:
                    changeAnimation(getFrame(), animUp[0][0], animUp[0][1], animUp[0][2], animUp[0][3]);
                    break;

                case MOVE_DOWN:
                    changeAnimation(getFrame(), animDown[0][0], animDown[0][1], animDown[0][2], animDown[0][3]);
                    break;

                case MOVE_LEFT:
                    changeAnimation(getFrame(), animLeft[0][0], animLeft[0][1], animLeft[0][2], animLeft[0][3]);
                    break;

                case MOVE_RIGHT:
                    changeAnimation(getFrame(), animRight[0][0], animRight[0][1], animRight[0][2], animRight[0][3]);
                    break;
            }
        }
    }

    public void playBehavior(Pacman pacman, Graph map, ArrayList<Element> e, GameController gm) {
        setMovDirection(moveToTarget(pacman, map));
        tryMove(e, gm);
    }

    public int moveToTarget(Element target, Graph g) {
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
                    myVert = -(int) Math.round(getPosition().getX()) * Consts.NUM_CELLS[0];
                }
                break;
            default:
                myVert = (int) getPosition().getX() * Consts.NUM_CELLS[0] + (int) getPosition().getY();
                break;
        }

        int tVert = (int) target.getPosition().getX() * Consts.NUM_CELLS[0] + (int) target.getPosition().getY();
        if (target.getPosition().getX() < 0 || target.getPosition().getX() > Consts.NUM_CELLS[1] - 3) {
            tVert = -(int) target.getPosition().getY();
        } else if (target.getPosition().getY() < 0 || target.getPosition().getY() > Consts.NUM_CELLS[0] - 1) {
            tVert = -(int) target.getPosition().getX() * Consts.NUM_CELLS[0];
        }

        int[] link = {myVert, myVert};
        if (movDirection == Pacman.MOVE_UP) {
            link[1] += Consts.NUM_CELLS[0];
        } else if (movDirection == Pacman.MOVE_DOWN) {
            link[1] -= Consts.NUM_CELLS[0];
        } else if (movDirection == Pacman.MOVE_LEFT) {
            link[1] += 1;
        } else if (movDirection == Pacman.MOVE_RIGHT) {
            link[1] -= 1;
        }

        String way = g.minWay(myVert, tVert, link);

        if (!way.isEmpty()) {
            return Character.getNumericValue(way.charAt(0));
        } else {
            return 0;
        }
    }
}
