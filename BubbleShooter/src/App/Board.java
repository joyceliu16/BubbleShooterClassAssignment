/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import Sprites.Bubble;
/**
 *
 * @author S346795925
 */
public class Board extends JPanel implements Runnable{
    
    private final int BUBBLE_WIDTH = 15;
    private final int BUBBLE_HEIGHT = 15;
    private final int ROW_LENGTH = 81;
    private final int COL_LENGTH = 10;
    private Thread animator;
    private java.util.List<Bubble> row = new ArrayList<>();
    
    public Board() {
        initBoard();
    }
    private void initBoard(){
        
        setBackground(Color.BLACK);
        initBubble();
    }
    
    @Override
    public void addNotify() {
        super.addNotify();

        animator = new Thread(this);
        animator.start();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateBubble(g);
    }
    
    private void initBubble() {
        int offset;
        for(int j = 0; j < COL_LENGTH; j++){
            if(j%2 == 0){
                offset = 5;
            }else{
                offset = 0;
            }
            for(int i = 0; i < ROW_LENGTH; i++){
                int colour = (int)(Math.random()*5+1);
                int x = i*BUBBLE_WIDTH+offset;
                int y = j*BUBBLE_HEIGHT;
                switch (colour){
                    case 1:
                        row.add(new Bubble(x,y,"src/images/blue_bubble.png"));
                        break;
                    case 2:
                        row.add(new Bubble(x,y,"src/images/green_bubble.png"));
                        break;
                    case 3:
                        row.add(new Bubble(x,y,"src/images/orange_bubble.png"));
                        break;
                    case 4:
                        row.add(new Bubble(x,y,"src/images/purple_bubble.png"));
                        break;
                    case 5:
                        row.add(new Bubble(x,y,"src/images/red_bubble.png"));
                        break;
                    default:
                        row.add(new Bubble(i*BUBBLE_WIDTH,y,"src/images/blue_bubble.png"));
                        break;
                }
            }
        }
        Toolkit.getDefaultToolkit().sync();
    }
    
    private void updateBubble(Graphics g){
        for (int i = 0; i<row.size(); i++) {
            row.get(i).update(g);
                        
        }
    }
    
    private void cycle() {
        
    }
    
    @Override
    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (true) {

            cycle();
            repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = 25 - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                
                String msg = String.format("Thread interrupted: %s", e.getMessage());
                
                JOptionPane.showMessageDialog(this, msg, "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }

            beforeTime = System.currentTimeMillis();
        }
    }
}
