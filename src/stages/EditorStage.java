/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stages;

import control.GameScreen;
import elements.BackButton;
import elements.Button;
import elements.Dot;
import elements.Element;
import elements.Pacman;
import elements.SaveButton;
import elements.Wall;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import utils.Consts;

/**
 *
 * @author wln
 */
public class EditorStage extends Stage{

    private Element[][] matrizElem;
    private final int[][] ghostArea = {{8,11} , {7,11}};
    private Pacman pacman;
    private final int[] pacPos = {15, 9};
    private int selectedButton = 0, buttonCont = 0;
    
    
    public EditorStage(String name) {
        super(name);
        jogable = false;
        matrizElem = new Element[Consts.NUM_CELLS[1] - 2][Consts.NUM_CELLS[0]];
        
        SaveButton b1 = new SaveButton("megadot.png", "save");
        b1.setPosition(21, 0);
        b1.setEnable(true);
        addElement(b1);
        buttonCont++;
        
        BackButton b2 = new BackButton("megadot.png", "back");
        b2.setPosition(21, 9);
        b2.setEnable(false);
        addElement(b2);
        buttonCont++;
        
        pacman = new Pacman ("pacmanL1.png");
        pacman.setPosition(pacPos[0], pacPos[1]);
        addElement(pacman);
        matrizElem[pacPos[0]][pacPos[1]] = pacman;
        
        for (int i=ghostArea[0][0];i<=ghostArea[0][1]; i++){
            createWall(i, ghostArea[1][0], null);
            createWall(i, ghostArea[1][1], null);
        }
        
        for (int i=ghostArea[1][0] + 1 ;i<ghostArea[1][1]; i++){
            createWall(ghostArea[0][0], i, null);
            createWall(ghostArea[0][1], i, null);
        }
        
        for (int i = 0; i< Consts.NUM_CELLS[1] - 2; i++){
            for (int j = 0; j< Consts.NUM_CELLS[0]; j++){
                if((i<ghostArea[0][0] || i>ghostArea[0][1] || j<ghostArea[1][0] || j>ghostArea[1][1]) && matrizElem[i][j] == null){
                    Dot dot = new Dot("dot.png");
                    dot.setPosition(i, j);
                    addElement(dot);
                    matrizElem[i][j] = dot;
                }
            }
        }
    }

    @Override
    public void getKey(KeyEvent e, GameScreen gs){
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
             Button b = (Button)elemArray.get(selectedButton);
            b.setEnable(false);
            if(selectedButton > 0)
                selectedButton--;
            
            b = (Button)elemArray.get(selectedButton);
            b.setEnable(true);
        }if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
             Button b = (Button)elemArray.get(selectedButton);
            b.setEnable(false);
            if(selectedButton < buttonCont)
                selectedButton++;
            
            b = (Button)elemArray.get(selectedButton);
            b.setEnable(true);
        }else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            Button b = (Button)elemArray.get(selectedButton);
            b.active(gs);
        }
    }
   
    @Override
    public void getClick(MouseEvent me, GameScreen gs) {
        int[] clickPos = {(int)Math.floor((me.getY() - 25)/Consts.CELL_SIZE), (int)Math.floor((me.getX() - 3)/Consts.CELL_SIZE)};
        if(SwingUtilities.isLeftMouseButton(me)){
            if(isValidPosition(clickPos) && matrizElem[clickPos[0]][clickPos[1]] instanceof Dot){
                createWall(clickPos[0], clickPos[1], gs);
                createWall(Consts.NUM_CELLS[1] - 3 - clickPos[0], clickPos[1], gs);
                if((Consts.NUM_CELLS[0] % 2 != 0 && clickPos[1] != (Consts.NUM_CELLS[0]-1)/2) || Consts.NUM_CELLS[0] % 2 == 0){
                    createWall(clickPos[0], Consts.NUM_CELLS[0] - 1 - clickPos[1], gs);
                    createWall(Consts.NUM_CELLS[1] - 3 - clickPos[0], Consts.NUM_CELLS[0] - 1 - clickPos[1], gs);
                }
            }
        }else if (SwingUtilities.isRightMouseButton(me)){
            if(isValidPosition(clickPos) && matrizElem[clickPos[0]][clickPos[1]] instanceof Wall){
                removeWall(clickPos[0], clickPos[1], gs);
                removeWall(Consts.NUM_CELLS[1] - 3 - clickPos[0], clickPos[1], gs);
                removeWall(clickPos[0], Consts.NUM_CELLS[0] - 1 - clickPos[1], gs);
                removeWall(Consts.NUM_CELLS[1] - 3 - clickPos[0], Consts.NUM_CELLS[0] - 1 - clickPos[1], gs);
            }
        }
    }
    
    public void createWall(int x, int y, GameScreen gs){
        removeElement(matrizElem[x][y]);
        if (gs != null)
            gs.removeElement(matrizElem[x][y]);
        
        Wall wall = new Wall("wall.png");
        wall.setPosition(x, y);
        addElement(wall);
        matrizElem[x][y] = wall;
        if (gs != null)
            gs.addElement(wall);
        
        refreshImage(x, y, true);
        refreshImage(x-1, y, false);
        refreshImage(x, y+1, false);
        refreshImage(x+1, y, false);
        refreshImage(x, y-1, false);
    }
    
    public void removeWall(int x, int y, GameScreen gs){
        removeElement(matrizElem[x][y]);
        gs.removeElement(matrizElem[x][y]);
        
        Dot dot = new Dot("dot.png");
        dot.setPosition(x, y);
        addElement(dot);
        gs.addElement(dot);
        matrizElem[x][y] = dot;
        
        refreshImage(x-1, y, false);
        refreshImage(x, y+1, false);
        refreshImage(x+1, y, false);
        refreshImage(x, y-1, false);
        refreshImage(x-1, y-1, false);
        refreshImage(x-1, y+1, false);
        refreshImage(x+1, y+1, false);
        refreshImage(x+1, y-1, false);
    }
    
    public void refreshImage(int x, int y, boolean control){
        if (x >= 0 && x < Consts.NUM_CELLS[1] - 2 && y>=0 && y < Consts.NUM_CELLS[0] && matrizElem[x][y] instanceof Wall){
            String code = "wall";
            int[] dir = {0,0};
            int cont = 0;
            if (x > 0 && matrizElem[x-1][y] instanceof Wall){
                code += "N";
                dir[0]--;
                cont++;
            }

            if (x < Consts.NUM_CELLS[1] - 3 && matrizElem[x+1][y] instanceof Wall){
                code += "S";
                dir[0]++;
                cont++;
            }

            if (y < Consts.NUM_CELLS[0] - 1 && matrizElem[x][y+1] instanceof Wall){
                code += "E";
                dir[1]++;
                cont++;
            }

            if (y > 0 && matrizElem[x][y-1] instanceof Wall){
                code += "W";
                dir[1]--;
                cont++;
            }

            if(cont == 2 && dir[0] != 0){
                if((x>0 || dir[0] != -1) && (x<Consts.NUM_CELLS[1] - 3 || dir[0] != 1)){
                    if((y>0 || dir[1] != -1) && (y<Consts.NUM_CELLS[0] - 1 || dir[1] != 1)){
                        if(matrizElem[x+dir[0]][y+dir[1]] instanceof Wall){
                            code += "a";
                            if(control)
                                refreshImage(x+dir[0],y+dir[1], false);
                        }
                    }
                }
            }else if (cont == 3){
                if(dir[0] == 0){
                    if(matrizElem[x - 1][y+dir[1]] instanceof Wall){
                        code += "n";
                        if(control)
                            refreshImage(x-1,y+dir[1], false);
                    }
                    if(matrizElem[x + 1][y+dir[1]] instanceof Wall){
                        code += "p";
                        if(control)
                            refreshImage(x+1,y+dir[1], false);
                    }
                }
                if(dir[1] == 0){
                    if(matrizElem[x + dir[0]][y - 1] instanceof Wall){
                        code += "n";
                        if(control)
                            refreshImage(x+dir[0],y-1, false);
                    }
                    if(matrizElem[x + dir[0]][y + 1] instanceof Wall){
                        code += "p";
                        if(control)
                            refreshImage(x+dir[0],y+1, false);
                    }
                }
            }else if (cont == 4){
                if(matrizElem[x - 1][y - 1] instanceof Wall){
                    code += "ul";
                    if(control)
                        refreshImage(x-1,y-1, false);
                }
                if(matrizElem[x + 1][y - 1] instanceof Wall){
                    code += "dl";
                    if(control)
                        refreshImage(x+1,y-1, false);
                }
                if(matrizElem[x - 1][y + 1] instanceof Wall){
                    code += "ur";
                    if(control)
                        refreshImage(x-1,y+1, false);
                }
                if(matrizElem[x + 1][y + 1] instanceof Wall){
                    code += "dr";
                    if(control)
                        refreshImage(x+1,y+1, false);
                }
            }
            
            matrizElem[x][y].changeImage(code + ".png");
        }
    }
    
    public boolean isValidPosition (int[] position){
        if((position[0]<ghostArea[0][0] || position[0]>ghostArea[0][1] || position[1]<ghostArea[1][0] || position[1]>ghostArea[1][1]) && position[1]<Consts.NUM_CELLS[0] && position[0]<Consts.NUM_CELLS[1] - 2 && (position[0]!=Consts.NUM_CELLS[1] - 3 - pacPos[0] || position[1] != pacPos[1]))
            return true;      
        
        System.out.println("invalid position");
        return false;
    }
}
