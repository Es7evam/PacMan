package utils;

import java.io.File;

/**
 * Projeto de POO 2017
 * 
 * @author Luiz Eduardo
 * Baseado em material do Prof. Jose Fernando Junior
 */
public class Consts {
    public static final int CELL_SIZE = 30;
    public static final int[] NUM_CELLS = {19, 22};
    public static final int[] GHOST_AREA = {4,5};
    public static final int[] PAC_POS = {15,9};
    public static final int[] POWER_POS = {3,2};
    
    public static final int WALK_STEP_DEC_PLACES = 1;
    public static final double WALK_STEP = 0.075;
    
    public static final String PATH = File.separator+"imgs"+File.separator;
    
    public static final int DELAY = 10;
    public static final int TIMER_FOGO = 40;
}
