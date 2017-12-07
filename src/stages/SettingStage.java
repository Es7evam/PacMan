/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stages;

import control.Config;
import control.GameScreen;
import elements.BackButton;
import elements.Button;
import elements.ChooseLevelButton;
import elements.ConfirmButton;
import elements.OptionButton;
import elements.ResetButton;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author wln
 */
public class SettingStage extends Stage{

    private int buttonCont, selectedButton;
    private Config oldConfig, config;
    
    public SettingStage(String name, GameScreen gs) {
        super(name);
        oldConfig = gs.getConfig();
        config = new Config();
        buttonCont = 0;
        selectedButton = 0;
        jogable = false;
        
        ChooseLevelButton b1 = new ChooseLevelButton("megadot.png", "level", "random");
        b1.addOption("random");
        for(int i=1; i<=gs.getLevelManager().getCont(); i++)
            b1.addOption("level" + i);
        for(int i=1; i<=config.getLevelOption(); i++)
            b1.changeOption(1);
        b1.setPosition(7, 2);
        this.addElement(b1);
        buttonCont++;
        
        ConfirmButton b2 = new ConfirmButton("char_.png", "confirm");
        b2.setPosition(18, 1);
        this.addElement(b2);
        buttonCont++;
        
        ResetButton b3 = new ResetButton("char_.png", "reset");
        b3.setPosition(19, 1);
        this.addElement(b3);
        buttonCont++;
        
        BackButton b4 = new BackButton("char_.png", "back");
        b4.setPosition(20, 1);
        this.addElement(b4);
        buttonCont++;
        
        reset();
    }

    @Override
    public void getKey(KeyEvent key, GameScreen gs) {
        if (key.getKeyCode() == KeyEvent.VK_RIGHT) {
             if(elemArray.get(selectedButton) instanceof OptionButton){
                 OptionButton b = (OptionButton)elemArray.get(selectedButton);
                 b.changeOption(1);
                 config.setLevelOption(b.getOption());
             }
        }if (key.getKeyCode() == KeyEvent.VK_LEFT) {
             if(elemArray.get(selectedButton) instanceof OptionButton){
                 OptionButton b = (OptionButton)elemArray.get(selectedButton);
                 b.changeOption(-1);
                 config.setLevelOption(b.getOption());
             }
        }else if (key.getKeyCode() == KeyEvent.VK_UP) {
            Button b = (Button)elemArray.get(selectedButton);
            b.setEnable(false);
            if(selectedButton > 0)
                selectedButton--;
            
            b = (Button)elemArray.get(selectedButton);
            b.setEnable(true);
            
        } else if (key.getKeyCode() == KeyEvent.VK_DOWN) {
            Button b = (Button)elemArray.get(selectedButton);
            b.setEnable(false);
            
            if(selectedButton < buttonCont - 1)
                selectedButton++;
            
            b = (Button)elemArray.get(selectedButton);
            b.setEnable(true);
        }else if (key.getKeyCode() == KeyEvent.VK_ENTER) {
            Button b = (Button)elemArray.get(selectedButton);
            b.active(gs);
        }else if (key.getKeyCode() == KeyEvent.VK_ESCAPE) {
             MenuStage menu = new MenuStage("Menu");
             gs.setStage(menu);
        }
    }
    
    public Config getConfig(){
        return config;
    }
    
    public void reset(){
        
        if(elemArray.get(0) instanceof OptionButton){
            OptionButton b = (OptionButton)elemArray.get(0);
            if(oldConfig.getLevelOption() > config.getLevelOption()){
                for(int i=config.getLevelOption(); i< oldConfig.getLevelOption(); i++)
                    b.changeOption(1);
            }else{
                for(int i=config.getLevelOption(); i> oldConfig.getLevelOption(); i--)
                    b.changeOption(-1);
            }
            config.setLevelOption(b.getOption());
        }
    }

    @Override
    public void getClick(MouseEvent me, GameScreen gs) {
    }
    
}
