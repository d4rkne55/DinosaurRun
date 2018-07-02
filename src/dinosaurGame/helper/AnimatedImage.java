package dinosaurGame.helper;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class AnimatedImage
{
    public int animationTime;
    private List<BufferedImage> frames;
    private int currentFrame = 0;
    private long timer;
    
    public AnimatedImage(int animationTime) {
        this.animationTime = animationTime;
        this.frames = new ArrayList<BufferedImage>();
    }
    
    public void addFrame(BufferedImage img) {
        this.frames.add(img);
    }
    
    public BufferedImage nextFrame() {
        if (this.timer == 0) {
            this.timer = System.currentTimeMillis();
        }
        
        if (System.currentTimeMillis() - this.timer >= this.animationTime / 2) {
            this.currentFrame++;
            if (this.currentFrame == this.frames.size()) {
                this.currentFrame = 0;
            }
            
            this.timer = System.currentTimeMillis();
        }
        
        return this.frames.get(this.currentFrame);
    }
}
