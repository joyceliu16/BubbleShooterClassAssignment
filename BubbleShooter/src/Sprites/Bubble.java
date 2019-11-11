
package Sprites;


public class Bubble extends Sprite{
    
    private String imageName; 
    
    public Bubble(int x, int y, int colour){      
        super(x,y);    
        setColour(colour);
        super.loadImage(imageName);
        
    }
    
    private void setColour(int colour){
        switch (colour){
            case 1:
               imageName = "src/images/blue_bubble.png";
                break;
            case 2:
                imageName = "src/images/green_bubble.png";
                break;
            case 3:
                imageName = "src/images/orange_bubble.png";
                break;
            case 4:
                imageName = "src/images/purple_bubble.png";
                break;
            case 5:
                imageName = "src/images/red_bubble.png";
                break;
            default:
                imageName = "src/images/blue_bubble.png";
                break;
        }
    }
    
    
}
