/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import control.GameScreen;

/**
 *
 * @author wln
 */
public class Text extends Button{

    private String text;
    
    public Text(String imageName, String text) {
        super(imageName, text);
        this.text = text;
        isFree = true;
    }
    
    public void changeText(String text, boolean isNumber){
        if(isNumber){
            for(int i = text.length() - 1; i >= 0; i--){
                if(text.charAt(i) != this.text.charAt(i + this.text.length() - text.length())){
                    if (text.charAt(i) == ' ')
                        tiles.get(i + this.text.length() - text.length()).changeImage("char_.png");
                    else
                        tiles.get(i + this.text.length() - text.length()).changeImage("char_" + text.charAt(i) + ".png");
                }    
            }
        }else{
            tiles.clear();
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
    }

    @Override
    public void active(GameScreen gs) {
        
    }
    
}
