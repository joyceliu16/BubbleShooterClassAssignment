/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import Sprites.Bubble;

/**
 *
 * @author S346795925
 */
public class Board extends JPanel implements Runnable, MouseMotionListener {
    private final int BUBBLE_WIDTH = 15;
    private final int BUBBLE_HEIGHT = 15;
    private final int ROW_LENGTH = 5;
    private final int COL_LENGTH = 10;
    private Thread animator;
    private java.util.List<Bubble> row = new ArrayList<>();
    private java.util.List<java.util.List> col = new ArrayList<>();

    public Board() {
        initBoard();
    }

    private void initBoard() {

        addMouseMotionListener(this);
        setBackground(Color.BLACK);
        initBubble();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {

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
        for (int j = 0; j < COL_LENGTH; j++) {
            row.clear();
            if (j % 2 == 0) {
                offset = 5;
            } else {
                offset = 0;
            }
            for (int i = 0; i < ROW_LENGTH; i++) {
                int colour = (int) (Math.random() * 5 + 1);
                int x = i * BUBBLE_WIDTH + offset;
                int y = j * BUBBLE_HEIGHT;
                row.add(new Bubble(x, y, colour));
            }
            col.add(row);
            System.out.println(col);

        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void updateBubble(Graphics g) {
        for (java.util.List<Bubble> j : col) {
            for (Bubble i : j) {
                i.update(g);
            }
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
