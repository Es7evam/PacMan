/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

/**
 *
 * @author wln
 */
public class Blinky extends Ghost{
    public Blinky(String... images){
        super(images);
        for (int i=1; i<=4; i++){
            animLeft[0][i-1] = "blinkyL" + i + ".png";
            animRight[0][i-1] = "blinkyR" + i + ".png";
            animUp[0][i-1] = "blinkyU" + i + ".png";
            animDown[0][i-1] = "blinkyD" + i + ".png";
            
        }
    }
}
