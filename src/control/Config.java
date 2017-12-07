/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wln
 */
public class Config implements Cloneable{
    private int levelOption;
    
    public Config(){
        levelOption = 0;
    }
    
    
    public void setLevelOption(int value){
        levelOption = value;
    }
    
    public int getLevelOption(){
        return levelOption;
    }
    
    public Config getCopy(){
        try {
            return (Config)this.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
