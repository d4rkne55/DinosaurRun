package dinosaurGame.entities;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import dinosaurGame.Game;

public class Cactus extends Entity
{
    private Dimension imgSize;
    
    public boolean hasCollision = true;
    public float sizeFactor = 1.0f;
    
    public Cactus(int x, int y) {
        this(x, y, 1.0f);
    }
    
    public Cactus(int x, int y, float sizeFactor) {
        super("Cactus", x, y, "assets/cactus.png");
        this.sizeFactor = sizeFactor;
        
        int imgW = (int) Math.round(this.image.getWidth() * this.sizeFactor);
        int imgH = (int) Math.round(this.image.getHeight() * this.sizeFactor);
        this.imgSize = new Dimension(imgW, imgH);
        
        // adjust position accordingly, so they are on the same baseline
        this.setY(y + (this.image.getHeight() - imgH));
    }
    
    public void draw(Graphics g) {
        g.drawImage(this.getImage(), this.getX(), this.getY(), this.imgSize.width, this.imgSize.height, null);
    }
    
    public int getY() {
        return Game.world.terrain.getY() + this.position.y;
    }
    
    public Rectangle getHitbox() {
        return new Rectangle(this.getX(), this.getY(), this.imgSize.width, this.imgSize.height);
    }
}
