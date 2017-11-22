/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stages;

import control.GameScreen;
import elements.Button;
import elements.EditorButton;
import elements.PlayButton;
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

    @Override
    public void getClick(MouseEvent me, GameScreen gs) {
    }
}
