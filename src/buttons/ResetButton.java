/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buttons;

import control.GameScreen;
import stages.SettingStage;

/**
 *
 * @author wln
 */
public class ResetButton extends Button{

    public ResetButton(String imageName, String text) {
        super(imageName, text);
    }

    @Override
    public void active(GameScreen gs) {
        if(gs.getStage() instanceof SettingStage){
            SettingStage stage = (SettingStage)gs.getStage();
            stage.reset();
        }
    }
    
}
