/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import java.awt.Graphics;
import java.io.Serializable;
import utils.Drawing;

/**
 *
 * @author wln
 */
public class Tile extends Element implements Serializable{
    public Tile(String imageName) {
        super(imageName);
        isFree = true;
    }
    
    @Override
    public void autoDraw(Graphics g){
        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
    }
}
