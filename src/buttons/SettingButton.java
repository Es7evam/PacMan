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
public class SettingButton extends Button{

    public SettingButton(String imageName, String text) {
        super(imageName, text);
    }

    @Override
    public void active(GameScreen gs) {
        SettingStage setting = new SettingStage("Settings", gs);
        gs.setStage(setting);
    }
}
