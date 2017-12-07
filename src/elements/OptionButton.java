/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import control.GameScreen;
import java.awt.Graphics;
import java.util.ArrayList;
import utils.Drawing;

/**
 *
 * @author wln
 */
public abstract class OptionButton extends Button{
    protected ArrayList<String> options;
    private int op;
    private Text text;
    
    public OptionButton(String imageName, String text, String startOp) {
        super(imageName, text);
        options = new ArrayList<String>();
        op = 0;
        
        this.text = new Text("char_.png","<" + startOp + ">");
        this.text.setPosition(pos.getX(), pos.getY() + tiles.size() + 1);
    }
    
    public void addOption(String op){
        options.add(op);
    }
    
    @Override
    public void autoDraw(Graphics g){
        super.autoDraw(g);
        text.autoDraw(g);
    }
    
    @Override
    public boolean setPosition(double x, double y) {
        text.setPosition(x, y + 1+ tiles.size());
        return super.setPosition(x, y); 
    }
    
    public int getOption(){
        return op;
    }
    
    public void changeOption(int dir){
        if(dir == -1){
            if(op > 0)
                op--;
            else
                op = options.size() - 1;
        }else if(dir == 1){
            if(op < options.size() - 1)
                op++;
            else
                op = 0;
        }
        
        text.changeText("<" + options.get(op) + ">", false);
    }

    @Override
    public void active(GameScreen gs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
