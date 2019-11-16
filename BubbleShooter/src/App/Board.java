package App;

import Menus.StartMenu;
import java.awt.BorderLayout;
import Menus.EndGame;
import Sprites.Bubble;
import Sprites.Cannon;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Window;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Board extends JPanel implements Runnable, MouseMotionListener, MouseListener {

    private final int BUBBLE_WIDTH = 30;
    private final int BUBBLE_HEIGHT = BUBBLE_WIDTH;
    private final int ROW_LENGTH = 39; 
    private final int COL_LENGTH = 10;
    private final int CANNON_X = 580;
    private final int CANNON_Y = 500;
    private final int CANNON_WIDTH = 64;
    private final int CANNON_HEIGHT = 64;
    private final int CANNONCENTER_X = CANNON_X + CANNON_WIDTH / 2;
    private final int CANNONCENTER_Y = CANNON_Y + CANNON_HEIGHT / 2;
    private final double TUNER = 0.89; //fine tuning shooting angle

    private int mouseX, mouseY, cyclesLeft, angle;
    public int score, limit;
    private Thread animator;
    private JLabel jlabelScore, jlabelTime;
    private List<List<Bubble>> grid = new ArrayList<>();
    private Cannon can;

  public Board(int difficulty) {
        initBoard(difficulty);
    }

    private void initBoard(int difficulty) {

        addMouseMotionListener(this);
        addMouseListener(this);
        setBackground(Color.BLACK);
        initBubble();
        initLabels();
        limit = difficulty * 20;
        cyclesLeft = limit * 40; //Convert seconds to cycles
        try {
            initCannon();
        } catch (Exception e) {
            System.out.println("No Spin");
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
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
        updateCannon(g);
        updateLabels();
    }
    
    private void initLabels(){
        score = 0;
        jlabelScore = new JLabel("Score: ");
        jlabelScore.setFont(new Font("Verdana",1,20));
        jlabelScore.setForeground(Color.WHITE);
        jlabelScore.setAlignmentX(LEFT_ALIGNMENT);
        jlabelScore.setVerticalAlignment(JLabel.BOTTOM);
        
        jlabelTime = new JLabel("");
        jlabelTime.setFont(new Font("Verdana",1,20));
        jlabelTime.setForeground(Color.WHITE);
        jlabelTime.setHorizontalAlignment(JLabel.LEFT);
        jlabelTime.setVerticalAlignment(JLabel.BOTTOM);
         
        this.add(jlabelScore);
        this.add(jlabelTime);
        System.out.println(this.getWidth());
        System.out.println(this.getHeight());
    }

    private void initCannon() throws Exception {
        can = new Cannon(CANNON_X, CANNON_Y);
    }

    private void initBubble() {
        int offset;
        for (int j = 0; j < COL_LENGTH; j++) {
            grid.add(new ArrayList<Bubble>());
            if (j % 2 == 0) {
                offset = 0;
            } else {
                offset = 15;
            }
            for (int i = 0; i < ROW_LENGTH; i++) {
                int colour = (int) (Math.random() * 5 + 1);
                int x = i * BUBBLE_WIDTH + offset;
                int y = j * BUBBLE_HEIGHT;
                grid.get(j).add(new Bubble(x, y, colour));
            }
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void updateCannon(Graphics g) {
        try {
            angle = (int) Math.toDegrees(Math.atan2((mouseY - CANNONCENTER_Y), (mouseX - CANNONCENTER_X)));
        } catch (Exception e) {
            angle = 0;
        }
        can.update(g, angle);
    }

    private void updateBubble(Graphics g) {
        for (List<Bubble> j : grid) {
            for (Bubble i : j) {
                i.update(g);
            }
        }
    }
    
    private void updateLabels(){
        jlabelScore.setText("Bubbles: " + Integer.toString(score));
        jlabelTime.setText("Time Left: " + Integer.toString(limit));
    }

    private void cycle() {
        cyclesLeft--;
        if (cyclesLeft % 40 == 0){
            limit--;
        }
    }

    @Override
    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (cyclesLeft > 0) {

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
        new EndGame(score).setVisible(true);
        Window win = SwingUtilities.getWindowAncestor(this);
        win.dispose();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // calculate the bubble that is shot
        shotBubble(e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private void shotBubble(int xm, int ym) {
        // cannon position is (580,500), pic is 64X64, so the center is (612, 532)
        int xc = CANNONCENTER_X; //612;
        int yc = CANNONCENTER_Y; //532;
        //int shootAngle = Utils.calculateBulletAngle(xc, yc, xm, ym);

        // calculate from the bottom row
        for (int row = COL_LENGTH - 1; row >= 0; row--) {
            List<Bubble> rowBubbleList = grid.get(row);
            int yb = rowBubbleList.get(0).getY();
            double ratio = TUNER * (double) (xm - xc) / (double) (ym - yc);
            int xb = xc + (int) (ratio * (yb - yc));
            //System.out.println("X calculated is (x,y): " + xb + ", " + yb);
            for (Bubble bub : rowBubbleList) {
                // Bubble dimension is 32X32
                if (bub.isVisible()) {
                    if (xb >= bub.getX() && xb < (bub.getX() + this.BUBBLE_WIDTH)) {
                        bub.setVisible(false);
                        //System.out.println("hit bubble: " + bub.getX() + ", " + bub.getY());
                        score++;
                        // only hit the most bottom row
                        return;
                    }
                }

            }

        }

    }


}
