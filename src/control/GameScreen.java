package control;

import elements.BackgroundElement;
import elements.Dot;
import elements.Element;
import elements.Pacman;
import elements.PowerDot;
import utils.Consts;
import utils.Drawing;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import stages.LevelStage;
import stages.MenuStage;
import stages.Stage;

/**
 * Projeto de POO 2017
 * 
 * @author Luiz Eduardo
 * Baseado em material do Prof. Jose Fernando Junior
 */
public class GameScreen extends javax.swing.JFrame implements KeyListener, MouseListener, MouseMotionListener {
    
    protected ArrayList<Element> elemArray, bgArray;
    private final GameController controller = new GameController();
    private Stage stage;
    private LevelManager lm;
    private Config config;
    private int dotCont = 0;

    public GameScreen(Stage startStage) {
        Drawing.setGameScreen(this);
        initComponents();
        stage = startStage;
        lm = new LevelManager();
        config = new Config();
        
        this.addKeyListener(this);   /*teclado*/
        this.addMouseListener(this); /*mouse*/
        this.addMouseMotionListener(this);
        
        /*Cria a janela do tamanho do tabuleiro + insets (bordas) da janela*/
        this.setSize(Consts.NUM_CELLS[0] * Consts.CELL_SIZE + getInsets().left + getInsets().right,
                     Consts.NUM_CELLS[1] * Consts.CELL_SIZE + getInsets().top + getInsets().bottom);

        elemArray = new ArrayList<Element>();
        bgArray = new ArrayList<>();

        /*Cria e adiciona elementos*/
        for (int i = 0; i < stage.getCount(); i++){
            addElement(stage.getElement(i));
        }
        
        if(stage instanceof LevelStage){
            for (int i = 0; i < Consts.NUM_CELLS[0]; i++){
                BackgroundElement b = new BackgroundElement();
                b.setPosition(Consts.NUM_CELLS[1] - 2, i);
                bgArray.add(b);
            }
        }
    }
    public void refreshElements(){
        elemArray.clear();
        dotCont = 0;
        for (int i = 0; i < stage.getCount(); i++){
            if (stage.getElement(i) instanceof Dot || stage.getElement(i) instanceof PowerDot)
                dotCont++;
            addElement(stage.getElement(i));
         }
    }
    
    public void decrementDotCont(){
        dotCont--;
        if(dotCont == 0)
                setStage(new LevelStage("level", lm.loadLevel(0)));
    }
    
    public Config getConfig(){
        return config;
    }
    
    public void setConfig(Config config){
        this.config = config;
    }
    
    public final void addElement(Element elem) {
        elemArray.add(elem);
    }
    
    public boolean haveElement(Element elem){
        return elemArray.contains(elem);
    }
    
    public void removeElement(Element elem) {
        elemArray.remove(elem);
    }
    
    @Override
    public void paint(Graphics gOld) {
        Graphics g = getBufferStrategy().getDrawGraphics();
        
        /*Criamos um contexto grafico*/
        Graphics g2 = g.create(getInsets().right, getInsets().top, getWidth() - getInsets().left, getHeight() - getInsets().bottom);
        
        /* DESENHA CENARIO
           Trocar essa parte por uma estrutura mais bem organizada
           Utilizando a classe Stage
        */
        for (int i = 0; i < Consts.NUM_CELLS[1]; i++) {
            for (int j = 0; j < Consts.NUM_CELLS[0]; j++) {
                try {
                    Image newImage = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Consts.PATH + "bricks.png");
                    g2.drawImage(newImage,
                            j * Consts.CELL_SIZE, i * Consts.CELL_SIZE, Consts.CELL_SIZE, Consts.CELL_SIZE, null);
                            
                } catch (IOException ex) {
                    Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        if(stage instanceof LevelStage){
            this.controller.drawAllElements(elemArray, bgArray, 3, g2);
            LevelStage aux = (LevelStage)stage;
            if(Pacman.gameOver){
                MenuStage menu = new MenuStage("Menu");
                setStage(menu);
            }else{
                this.controller.processAllElements(elemArray, this, aux.getLevel().getMap());
            }
        }else{
            this.controller.drawAllElements(elemArray, bgArray, 0, g2);
        }
        this.setTitle("-> " + stage.getName());
        
        g.dispose();
        g2.dispose();
        if (!getBufferStrategy().contentsLost()) {
            getBufferStrategy().show();
        }
        
    }
    
    public void go() {
        TimerTask task = new TimerTask() {
            
            public void run() {
                repaint();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, Consts.DELAY);
    }
    
    public void setStage(Stage s){
        elemArray.clear();
        dotCont = 0;
        this.stage = s;
         for (int i = 0; i < s.getCount(); i++){
            if (stage.getElement(i) instanceof Dot || stage.getElement(i) instanceof PowerDot)
                dotCont++;
            addElement(s.getElement(i));
         }
    }
    
    public Stage getStage(){
        return stage;
    }
    
    public LevelManager getLevelManager(){
        return lm;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        stage.getKey(e, this);
        //repaint(); /*invoca o paint imediatamente, sem aguardar o refresh*/
    }
    
    @Override
    public void mousePressed(MouseEvent me){
        stage.getClick(me, this);
    }
    
    @Override
    public void mouseDragged(MouseEvent me) {
        stage.getClick(me, this);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SCC0604 - Pacman");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocation(new java.awt.Point(20, 20));
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        if (stage instanceof LevelStage){
            LevelStage level = (LevelStage)stage;
            level.releaseKey(e);
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
    
    @Override
    public void mouseMoved(MouseEvent me) {
    }

}
