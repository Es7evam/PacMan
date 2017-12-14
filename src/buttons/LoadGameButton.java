/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buttons;

import control.GameScreen;
import stages.LevelStage;

/**
 *
 * @author wln
 */
public class LoadGameButton extends Button{
    private LevelStage stage;
    
    public LoadGameButton(String imageName, String text, LevelStage stage) {
        super(imageName, text);
        this.stage = stage;
    }

    @Override
    public void active(GameScreen gs) {
        gs.setStage(stage);
    }
    
}
