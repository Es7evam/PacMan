/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import elements.Dot;
import elements.Element;
import elements.Wall;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import utils.Consts;

/**
 *
 * @author wln
 */
public class LevelManager {
    private Path levelFile;
    private ArrayList<String> levelNames;
    private ArrayList<String> levelCodes;
    private int cont;
    
    public LevelManager(){
        try {
            levelFile = Paths.get(new java.io.File(".").getCanonicalFile() + File.separator + "src" + File.separator + "levels.txt");
            List<String> aux = Files.readAllLines(levelFile);
            levelNames = new ArrayList<String>();
            levelCodes = new ArrayList<String>();
            cont = Integer.parseInt(aux.get(0));
         
            for (int i=1; i<=cont; i++){
                levelNames.add(aux.get(2*i - 1));
                levelCodes.add(aux.get(2*i));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void saveMap(ArrayList<Element> e){
        int[] limits = {(int)Math.floor((Consts.NUM_CELLS[1]-1)/2),(int)Math.floor((Consts.NUM_CELLS[0] + 1)/2)};
        char[] code = new char[(limits[0]-Consts.GHOST_AREA[0]/2)*limits[1] + 2*(limits[1]-Consts.GHOST_AREA[1]/2)];
        for (int i = 0; i<e.size(); i++){
            int[] pos = {(int)e.get(i).getPosition().getX(),(int)e.get(i).getPosition().getY()};
            
            if(pos[0] < limits[0] - Consts.GHOST_AREA[0]/2){
                if(pos[1] < limits[1]){
                    if(e.get(i) instanceof Wall)
                        code[pos[0] * limits[0] + pos[1]] = '1';
                    else if(e.get(i) instanceof Dot)
                        code[pos[0] * limits[0] + pos[1]] = '0';
                }
            }else if(pos[0] < limits[0]){
                if(pos[1] < limits[1]){
                    if(e.get(i) instanceof Wall)
                        code[(limits[0]-Consts.GHOST_AREA[0]/2 - 1)*limits[0]+(pos[0]-limits[0]+Consts.GHOST_AREA[0]/2)*(pos[1]-Consts.GHOST_AREA[1])] = '1';
                    else if(e.get(i) instanceof Dot)
                        code[(limits[0]-Consts.GHOST_AREA[0]/2 - 1)*limits[0]+(pos[0]-limits[0]+Consts.GHOST_AREA[0]/2)*(pos[1]-Consts.GHOST_AREA[1])] = '0';
                }
            }
        }
        ArrayList<String> aux = new ArrayList<String>();
        cont++;
        levelNames.add("Level" + cont);
        levelCodes.add(new String(code));
        aux.add(Integer.toString(cont));
        for(int i=0; i<cont; i++){
            aux.add(levelNames.get(i));
            aux.add(levelCodes.get(i));
        }
        
        try{
            Files.write(levelFile, aux, Charset.forName("UTF-8"));
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        System.out.println(new String(code));
    }
}
