/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import java.awt.Graphics;
import utils.Drawing;

/**
 *
 * @author wln
 */
public class Strawberry extends Element{
    private double timeStart, time, timeLimit = 15.0;
    
    
    public Strawberry() {
        super("morango.png");
        timeStart = System.nanoTime() / 1000000000.0;
        time = System.nanoTime() / 1000000000.0;
    }

    public boolean timeOut(){
        time = System.nanoTime() / 1000000000.0;
        return time - timeStart >= timeLimit;
    }
    
    @Override
    public void autoDraw(Graphics g) {
        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
    }
    
}
