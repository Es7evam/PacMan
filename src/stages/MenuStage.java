/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stages;

import control.GameScreen;
import buttons.Button;
import buttons.EditorButton;
import buttons.PlayButton;
import buttons.SettingButton;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author wln
 */
public class MenuStage extends Stage{
    private int selectedButton, buttonCont;
    
    public MenuStage(String name){
        super(name);
        jogable = false;
        selectedButton = 0;
        buttonCont = 0;
        
        /*Cria e adiciona elementos*/
        PlayButton b1 = new PlayButton("megadot.png", "play");
        b1.setPosition(12, 2);
        this.addElement(b1);
        buttonCont++;
        
        EditorButton b2 = new EditorButton("char_.png", "level editor");
        b2.setPosition(14, 2);
        this.addElement(b2);
        buttonCont++;
        
        SettingButton b3 = new SettingButton("char_.png", "settings");
        b3.setPosition(16, 2);
        this.addElement(b3);
        buttonCont++;
    }
    
    @Override
    public void getKey(KeyEvent e, GameScreen gs){
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            Button b = (Button)elemArray.get(selectedButton);
            b.setEnable(false);
            if(selectedButton > 0)
                selectedButton--;
            
            b = (Button)elemArray.get(selectedButton);
            b.setEnable(true);
            
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            Button b = (Button)elemArray.get(selectedButton);
            b.setEnable(false);
            
            if(selectedButton < buttonCont - 1)
                selectedButton++;
            
            b = (Button)elemArray.get(selectedButton);
            b.setEnable(true);
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            Button b = (Button)elemArray.get(selectedButton);
            b.active(gs);
        }
    }
    
    public void changeTab(GameScreen gs, Button... b){
        elemArray.clear();
        buttonCont = b.length;
        selectedButton = 0;
        for (int i=0; i<b.length; i++){
            addElement(b[i]);
        }
        gs.refreshElements();
    }

    @Override
    public void getClick(MouseEvent me, GameScreen gs) {
        
    }
}
