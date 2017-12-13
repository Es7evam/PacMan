/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buttons;

import control.GameScreen;
import control.LevelManager;
import elements.Element;
import java.util.ArrayList;
import stages.Stage;
import utils.Consts;

/**
 *
 * @author wln
 */
public class SaveButton extends Button{

    public SaveButton(String imageName, String text) {
        super(imageName, text);
    }

    @Override
    public void active(GameScreen gs) {
        Stage stage = gs.getStage();
        ArrayList<Element> aux = new ArrayList<Element>();
        for (int i=0; i<stage.getCount();i++){
            aux.add(stage.getElement(i));
        }
        
        gs.getLevelManager().saveMap(aux);
        
        Text t = new Text("char_", "level saved");
        t.setPosition(Consts.NUM_CELLS[1] - 1, 0);
        gs.addElement(t);
    }
    
}
