/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buttons;

import control.GameScreen;
import stages.MenuStage;
import stages.SettingStage;

/**
 *
 * @author wln
 */
public class ConfirmButton extends Button{

    public ConfirmButton(String imageName, String text) {
        super(imageName, text);
    }

    @Override
    public void active(GameScreen gs) {
        if(gs.getStage() instanceof SettingStage){
            SettingStage stage = (SettingStage)gs.getStage();
            gs.setConfig(stage.getConfig());
            MenuStage menu = new MenuStage("Menu");
            gs.setStage(menu);
        }
    }
    
}
