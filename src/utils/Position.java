package utils;

import java.io.Serializable;

/**
 * Projeto de POO 2017
 * 
 * @author Luiz Eduardo
 * Baseado em material do Prof. Jose Fernando Junior
 */
public class Position implements Serializable {
    /* Elements are positioned in a grid layout (integers).
       However, walking is implemented with float steps (continuous).
       This is why x and y are double types.
       x and y ranges from 0 to CELL_SIZE*NUM_CELLS.
       The real pixel positioning is converted by the Drawing class.
       As consequence, any element has size 1x1 (x and y). */
    private double x;
    private double y;
    private final double offset = 1.0;
    
    private double previousX;
    private double previousY;

    public Position(double x, double y){
        this.setPosition(x,y);
    }

    public final boolean setPosition(double x, double y){
        int factor = (int)Math.pow(10, Consts.WALK_STEP_DEC_PLACES+1);
        
        x = (double)Math.round(x * factor) / factor;
        y = (double)Math.round(y * factor) / factor;
        
        if(x < 0 || x > utils.Consts.NUM_CELLS[1]-3){
            if(x < 0 - offset)
                x = (double)Math.round((utils.Consts.NUM_CELLS[1]-3 + offset) * factor) / factor;
            else if(x > utils.Consts.NUM_CELLS[1]-3 + offset)
                x = (double)Math.round((-offset) * factor) / factor;
        }
        if(y < 0 || y > utils.Consts.NUM_CELLS[0]-1){
            if(y < 0 - offset)
                y = (double)Math.round((utils.Consts.NUM_CELLS[0]-1 + offset) * factor) / factor;
            else if(y > utils.Consts.NUM_CELLS[0]-1 + offset)
                y = (double)Math.round((-offset) * factor) / factor;
        }
        
        previousY = this.y;
        this.y = y;
            
        previousX = this.x;
        this.x = x;
        
        
        return true;
    }
    
    public double getX(){
        return x;
    }
   
    public double getY(){
        return y;
    }

    public boolean comeBack(){
        return this.setPosition(previousX,previousY);
    }
    
    public boolean moveUp(){
        return this.setPosition(this.getX()-Consts.WALK_STEP, this.getY());
    }
    public boolean moveDown(){
        return this.setPosition(this.getX()+Consts.WALK_STEP, this.getY());
    }
    public boolean moveRight(){
        return this.setPosition(this.getX(), this.getY()+Consts.WALK_STEP);
    }
    public boolean moveLeft(){
        return this.setPosition(this.getX(), this.getY()-Consts.WALK_STEP);        
    }
}
