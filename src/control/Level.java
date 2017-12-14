/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import utils.Graph;
import elements.Dot;
import elements.Element;
import elements.PowerDot;
import elements.Wall;
import java.io.Serializable;
import java.util.ArrayList;
import utils.Consts;

/**
 *
 * @author wln
 */
public class Level implements Serializable{
    private String name;
    private ArrayList<Element> elemArray;
    private Element[][] matrizElem;
    private Graph g;
    
    public Level(String code){
        matrizElem = new Element[Consts.NUM_CELLS[1] - 2][Consts.NUM_CELLS[0]];
        elemArray = new ArrayList<Element>();
        int[] limits = {(int)Math.floor((Consts.NUM_CELLS[1]-1)/2),(int)Math.floor((Consts.NUM_CELLS[0] + 1)/2)};
        for(int i=0; i<code.length(); i++){
            if(code.charAt(i) == '1'){
                int[] pos = {(int)Math.floor(i/limits[1]),i % limits[1]};
                createWall(pos[0],pos[1]);
                createWall(Consts.NUM_CELLS[1] - 3 - pos[0], pos[1]);
                if((Consts.NUM_CELLS[0] % 2 != 0 && pos[1] != (Consts.NUM_CELLS[0]-1)/2) || Consts.NUM_CELLS[0] % 2 == 0){
                    createWall(pos[0], Consts.NUM_CELLS[0] - 1 - pos[1]);
                    createWall(Consts.NUM_CELLS[1] - 3 - pos[0], Consts.NUM_CELLS[0] - 1 - pos[1]);
                }
            }
            if(code.charAt(i) == '0'){
                int[] pos = {(int)Math.floor(i/limits[1]),i % limits[1]};
                createDot(pos[0],pos[1]);
                createDot(Consts.NUM_CELLS[1] - 3 - pos[0], pos[1]);
                if((Consts.NUM_CELLS[0] % 2 != 0 && pos[1] != (Consts.NUM_CELLS[0]-1)/2) || Consts.NUM_CELLS[0] % 2 == 0){
                    createDot(pos[0], Consts.NUM_CELLS[0] - 1 - pos[1]);
                    createDot(Consts.NUM_CELLS[1] - 3 - pos[0], Consts.NUM_CELLS[0] - 1 - pos[1]);
                }
            }
            if(code.charAt(i) == 'p'){
                int[] pos = {(int)Math.floor(i/limits[1]),i % limits[1]};
                createPowerDot(pos[0],pos[1]);
                createPowerDot(Consts.NUM_CELLS[1] - 3 - pos[0], pos[1]);
                if((Consts.NUM_CELLS[0] % 2 != 0 && pos[1] != (Consts.NUM_CELLS[0]-1)/2) || Consts.NUM_CELLS[0] % 2 == 0){
                    createPowerDot(pos[0], Consts.NUM_CELLS[0] - 1 - pos[1]);
                    createPowerDot(Consts.NUM_CELLS[1] - 3 - pos[0], Consts.NUM_CELLS[0] - 1 - pos[1]);
                }
            }
        }
        
        g = new Graph();
        for (int i=0; i<Consts.NUM_CELLS[1] - 2; i++){
            for (int j=0; j<Consts.NUM_CELLS[0]; j++){
                if(!(matrizElem[i][j] instanceof Wall)){
                    g.addVertex(i * Consts.NUM_CELLS[0] + j);
                    if(j<Consts.NUM_CELLS[0]-1 && !(matrizElem[i][j+1] instanceof Wall))
                        g.addLink(i * Consts.NUM_CELLS[0] + j, i * Consts.NUM_CELLS[0] + (j+1));
                        
                    if(i<Consts.NUM_CELLS[1]-3 && !(matrizElem[i+1][j] instanceof Wall))
                        g.addLink(i * Consts.NUM_CELLS[0] + j, (i+1) * Consts.NUM_CELLS[0] + j);
                }
            }
        }
    }
    
    public Graph getMap(){
        return g;
    }
    
    public void addElement(Element elem){
        elemArray.add(elem);
    }
    
    public Element getElement(int i){
        return elemArray.get(i);
    }
    
    public int getSize(){
        return elemArray.size();
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void createWall(int x, int y){
        Wall wall = new Wall("wall.png");
        wall.setPosition(x, y);
        addElement(wall);
        matrizElem[x][y] = wall;
        
        refreshImage(x, y, true);
        refreshImage(x-1, y, false);
        refreshImage(x, y+1, false);
        refreshImage(x+1, y, false);
        refreshImage(x, y-1, false);
    }
    public void createDot(int x, int y){
        if(x!=Consts.PAC_POS[0] || y!=Consts.PAC_POS[1]){
            Dot dot = new Dot("dot.png");
            dot.setPosition(x, y);
            addElement(dot);
            matrizElem[x][y] = dot;
        }
    }
    
    public void createPowerDot(int x, int y){
        if(x!=Consts.PAC_POS[0] || y!=Consts.PAC_POS[1]){
            PowerDot pdot = new PowerDot("powerdot1.png");
            pdot.setPosition(x, y);
            addElement(pdot);
            matrizElem[x][y] = pdot;
        }
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
}