/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import control.GameScreen;
import java.util.ArrayList;

/**
 *
 * @author wln
 */
public class ChooseLevelButton extends OptionButton{
    
    
    public ChooseLevelButton(String imageName, String text, String startOp) {
        super(imageName, text, startOp);  
    }

    @Override
    public void active(GameScreen gs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
