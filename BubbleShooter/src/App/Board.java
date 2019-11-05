/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import java.awt.*;
import javax.swing.*;
/**
 *
 * @author S346795925
 */
public class Board extends JPanel implements Runnable{
    
    private final int INITIAL_X = 0;
    private final int INITIAL_Y = 0;
    
    private Image blueBubble;
    private Thread animator;
    private int x,y;
    
    public Board() {
        initBoard();
    }
    private void loadImage(){
        ImageIcon ii = new ImageIcon("src/images/blue_bubble.png");
        blueBubble = ii.getImage();  
    }
    private void initBoard(){
        
        setBackground(Color.BLACK);
        
        loadImage();
        
        int w = blueBubble.getWidth(this);
        int h =  blueBubble.getHeight(this);
        setPreferredSize(new Dimension(w, h));    
        
        x = INITIAL_X;
        y = INITIAL_Y;
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
        drawBubble(g);
    }
    
    private void drawBubble(Graphics g) {

        g.drawImage(blueBubble, x, y, this);
        Toolkit.getDefaultToolkit().sync();
    }
    
    private void cycle() {

        x += 1;
        y += 1;

        if (y > 720) {

            y = INITIAL_Y;
            x = INITIAL_X;
        }
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
