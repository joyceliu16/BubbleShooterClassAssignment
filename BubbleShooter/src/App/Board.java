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
public class Board extends JPanel{
    
    private Image blueBubble;
    
    public Board() {
        initBoard();
    }
    private void initBoard(){
        
        loadImage();
        
        int w = blueBubble.getWidth(this);
        int h =  blueBubble.getHeight(this);
        setPreferredSize(new Dimension(w, h));         
    }
    
    private void loadImage(){
        
        ImageIcon ii = new ImageIcon("src/resources/blue_bubble.png");
        blueBubble = ii.getImage();  
    }
    @Override
    public void paintComponent(Graphics g) {

        g.drawImage(blueBubble, 0, 0, null);
    }
}
