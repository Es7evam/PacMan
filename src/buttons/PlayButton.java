/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buttons;

import control.GameScreen;
import control.LevelManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import stages.LevelStage;
import stages.MenuStage;

/**
 *
 * @author wln
 */
public class PlayButton extends Button{
    
    public PlayButton(String imageName, String text) {
        super(imageName, text);
    }
    
    @Override
    public void active(GameScreen gs){
        NewGameButton b1 = new NewGameButton("megadot.png", "new game");
        b1.setPosition(12,2);
        
        try {
            FileInputStream fileIn = new FileInputStream(new java.io.File(".").getCanonicalPath() + "/save/save.txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            LevelStage stage = (LevelStage) in.readObject();
            in.close();
            fileIn.close();
            LoadGameButton b2 = new LoadGameButton("char_.png", "load game", stage);
            b2.setPosition(14,2);
        
        ((MenuStage)gs.getStage()).changeTab(gs, b1, b2);
        } catch (IOException i) {
            System.out.println("save nao encontrado");
            ((MenuStage)gs.getStage()).changeTab(gs, b1);
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("deu ruim");
            ((MenuStage)gs.getStage()).changeTab(gs, b1);
            return;
         }
    }
}
