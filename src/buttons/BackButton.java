/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buttons;

import control.GameScreen;
import stages.MenuStage;

/**
 *
 * @author wln
 */
public class BackButton extends Button{

    public BackButton(String imageName, String text) {
        super(imageName, text);
    }

    @Override
    public void active(GameScreen gs) {
        MenuStage menu = new MenuStage("Menu");
        gs.setStage(menu);
    }
    
}
