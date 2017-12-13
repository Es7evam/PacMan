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
public class PowerDot extends Element{
    private final int score = 50;
    
    public PowerDot(String imageName) {
        super(imageName);
    }
    
    public int getScore(){
        return score;
    }

    @Override
    public void autoDraw(Graphics g) {
        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
    }
    
}
