/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import java.awt.Graphics;
import java.util.ArrayList;
import utils.Drawing;

/**
 *
 * @author wln
 */
public class AnimatedElement extends Element{
    public static final int STOP = 0;
    public static final int MOVE_LEFT = 1;
    public static final int MOVE_RIGHT = 2;
    public static final int MOVE_UP = 3;
    public static final int MOVE_DOWN = 4;
    
    protected int delay;
    private int count;
    private int index;
    private ArrayList<String> sprites;
    private boolean pause;
    protected double speed;
    protected int movDirection = STOP;
    protected int tryMove = 0, lastMove = 0, prevMove = 0;
    private boolean invoking;
    private int invokeCont, invokeTime;
    ArrayList<String> invokeAnim;
    protected boolean loop;
    
    
    public AnimatedElement(int delay, String... images) {
        super(images[0]);
        count = 0;
        invokeCont = 0;
        invoking = false;
        index = 0;
        loop = true;
        this.delay = delay;
        sprites = new ArrayList<String>();
        invokeAnim = new ArrayList<>();
        
        for (int i=0; i<images.length; i++)
            sprites.add(images[i]);
    }
    
    public void backToLastPosition(){
        if(movDirection == 0)
            this.pos.comeBack(3);
        else if(movDirection < 3)
            this.pos.comeBack(2);
        else
            this.pos.comeBack(1);
        
    }

    @Override
    public void autoDraw(Graphics g) {
        if (invoking){
                    invokeCont++;
                    if(invokeCont>=invokeTime){
                        invokeCont = 0;
                        invoking = false;
                        loop = false;
                        sprites = invokeAnim;
                        index = 0;
                        count = 0;
                        pause = false;
                    }
                }
        
        if(!pause){
            if(count >= delay){
                
                index++;
                if(index == sprites.size()){
                    if(loop)
                        index = 0;
                    else
                        index = sprites.size() - 1;
                }

                changeImage(sprites.get(index));
                count = 0;
            }
            count++;
        }
        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
    }
    
    public void pausePlay(){
        if(pause)
            pause = false;
        else{
            count = 0;
            index = 0;
            changeImage(sprites.get(0));
            pause = true;
        }
    }
    public void setDelay(int value){
        delay = value;
    }
    
    public void invokeAnimation(int delay, boolean loop, String... images){
        invoking = true;
        invokeTime = delay;
        this.loop = loop;
        invokeCont = 0;
        invokeAnim.clear();
        for (int i=0; i<images.length; i++)
            invokeAnim.add(images[i]);
    }
    
    public void changeAnimation(int offset, boolean loop, String ... images){
        sprites.clear();
        index = offset;
        this.loop = loop;
        for (int i=0; i<images.length; i++)
            sprites.add(images[i]);
    }
    
    public int getFrame(){
        return index;
    }
    
    public boolean isAnimPaused(){
            return pause;
    }
    
    public boolean moveUp() {
        return this.pos.moveUp(speed);
    }

    public boolean moveDown() {
        return this.pos.moveDown(speed);
    }

    public boolean moveRight() {
        return this.pos.moveRight(speed);
    }

    public boolean moveLeft() {
        return this.pos.moveLeft(speed);
    }
}
