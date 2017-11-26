/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import utils.Drawing;
import java.awt.Graphics;
import java.io.Serializable;

/**
 *
 * @author wln
 */
public class Dot extends Element implements Serializable{
    public Dot(String imageName) {
        super(imageName);
        colliderRay = 0.05;
    }

    @Override
    public void autoDraw(Graphics g) {
        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
    }
}
