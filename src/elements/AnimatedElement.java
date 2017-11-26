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

    protected int delay;
    private int count;
    private int index;
    private ArrayList<String> sprites;
    private boolean pause;
    
    public AnimatedElement(int delay, String... images) {
        super(images[0]);
        count = 0;
        index = 0;
        this.delay = delay;
        sprites = new ArrayList<String>();
        
        for (int i=0; i<images.length; i++)
            sprites.add(images[i]);
    }

    @Override
    public void autoDraw(Graphics g) {
        if(!pause){
            if(count >= delay){
                index++;
                if(index == sprites.size())
                    index = 0;

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
    
    public void changeAnimation(int offset, String ... images){
        sprites.clear();
        index = offset;
        for (int i=0; i<images.length; i++)
            sprites.add(images[i]);
    }
    
    public int getFrame(){
        return index;
    }
    
    public boolean isAnimPaused(){
            return pause;
    }
}
