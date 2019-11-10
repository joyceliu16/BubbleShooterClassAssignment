
package Sprites;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;


public class Cannon extends Sprite {
    private final String imageName = "src/images/cannon.png"; 
    private final BufferedImage bi;
    private AffineTransform transform;
    private AffineTransformOp op;
    
    public Cannon(int x, int y) throws Exception{      
        super(x,y);    
        super.loadImage(imageName);
        transform = new AffineTransform();
        bi = ImageIO.read(new File(imageName));
    }
    
    public void update(Graphics g, int angle){
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform rotation = transform.getRotateInstance(Math.toRadians(angle+90), this.width/2, this.height/2);
        op = new AffineTransformOp(rotation, AffineTransformOp.TYPE_BILINEAR);
        g2.drawImage(op.filter(bi, null), this.x, this.y, null);
    }
}
