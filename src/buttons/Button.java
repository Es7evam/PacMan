/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buttons;

import control.GameScreen;
import elements.Element;
import buttons.Tile;
import utils.Drawing;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author wln
 */
abstract public class Button extends Element implements Serializable{
    
    protected ArrayList<Tile> tiles;
    private boolean selected;
    
    public Button(String imageName, String text) {
        super(imageName);
        selected = false;
        isFree = true;
        tiles = new ArrayList<Tile>();
        
        for(int i = 0; i < text.length(); i++){
            Tile newTile;
            if (text.charAt(i) == ' ')
                newTile = new Tile("char_.png");
            else if (text.charAt(i) == '<')
                newTile = new Tile("char_low.png");
            else if (text.charAt(i) == '>')
                newTile = new Tile("char_high.png");
            else
                newTile = new Tile("char_" + text.charAt(i) + ".png");
            
            addTile(newTile, i + 1);
        }
    }
    
    public void addTile(Tile tile, int offset){
        tile.setPosition(this.pos.getX(), this.pos.getY() + offset);
        tiles.add(tile);
    }
    
    public void removeTile(Tile tile){
        tiles.remove(tile);
    }
    
    public void setEnable(boolean value){
        selected = value;
        if (value)
            changeImage("megadot.png");
        else
            changeImage("char_.png");
    }
    
    abstract public void active(GameScreen gs);
    
    @Override
    public void autoDraw(Graphics g){
        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());

        for(int i = 0; i<tiles.size(); i++)
            tiles.get(i).autoDraw(g);
    }
    
    @Override
    public boolean setPosition(double x, double y) {
        
        for (int i=0; i < tiles.size(); i++)
            tiles.get(i).setPosition(x,tiles.get(i).getPosition().getY() - this.pos.getY() + y);
        
        return pos.setPosition(x, y, true);
    }
}
