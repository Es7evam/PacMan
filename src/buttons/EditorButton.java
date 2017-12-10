/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buttons;

import control.GameScreen;
import stages.EditorStage;

/**
 *
 * @author wln
 */
public class EditorButton extends Button{
    
    public EditorButton(String imageName, String text) {
        super(imageName, text);
    }
    
    @Override
    public void active(GameScreen gs){
        EditorStage level = new EditorStage("Editor");
        gs.setStage(level);
    }
}
