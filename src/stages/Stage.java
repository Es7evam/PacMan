/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stages;

import control.GameScreen;
import elements.Element;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author wln
 */
public abstract class Stage implements Serializable{
    protected String name;
    protected ArrayList<Element> elemArray;
    protected boolean jogable;
    
    public Stage(String name){
        this.name = name;
        jogable = true;
        elemArray = new ArrayList<Element>();
    }
    
    public void addElement(Element e){
        elemArray.add(e);
    }
    
    public Element getElement(int i){
        return elemArray.get(i);
    }
    
    public int getCount(){
        return elemArray.size();
    }
            
    
    public void removeElement(Element e){
        elemArray.remove(e);
    }
    
    public String getName(){
        return name;
    }
    
    public boolean isJogable(){
        return jogable;
    }
    
    abstract public void getKey(KeyEvent key, GameScreen gs);
    abstract public void getClick(MouseEvent me, GameScreen gs);
}
