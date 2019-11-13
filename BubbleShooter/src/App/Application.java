
package App;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Application extends JFrame {
    
    public Application(int difficulty) {

        initUI(difficulty);
    }

    private void initUI(int difficulty) {

        add(new Board(difficulty));

        setSize(1250, 720);

        setTitle("Bubble Vacuum");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }    
    
    /*public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            Application ex = new Application(difficulty);
            ex.setVisible(true);
        });
    }*/
}
