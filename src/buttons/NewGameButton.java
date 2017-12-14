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
public class NewGameButton extends Button{

    public NewGameButton(String imageName, String text) {
        super(imageName, text);
    }

    @Override
    public void active(GameScreen gs) {
        LevelStage level = new LevelStage("Level1", gs.getLevelManager().loadLevel(gs.getConfig().getLevelOption()));
        gs.setStage(level);
    }
    
}
