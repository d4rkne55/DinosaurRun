package dinosaurGame.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import dinosaurGame.Game;
import dinosaurGame.helper.Position;

public abstract class Entity
{
    public String name;
    protected Position position;
    protected BufferedImage image;
    
    public boolean hasCollision = false;
    
    public Entity(String name, int x, int y, String imgPath) {
        this.name = name;
        this.position = new Position(x - World.offset, y);
        
        loadImage(imgPath);
    }
    
    public void draw(Graphics g) {
        g.drawImage(this.image, this.getX(), this.getY(), null);
    }
    
    public boolean loadImage(String imgPath) {
        try {
            this.image = ImageIO.read(new File(imgPath));
            return true;
        } catch (IOException e) {
            System.out.print(this.name + " image couldn't be loaded.");
            return false;
        }
    }
    
    public BufferedImage getImage() {
        return this.image;
    }
    
    public int getX() {
        return World.offset + this.position.x;
    }
    
    public int getY() {
        return this.position.y;
    }
    
    public void setX(int posX) {
        this.position.x = posX;
    }
    
    public void setY(int posY) {
        this.position.y = posY;
    }
    
    public Rectangle getHitbox() {
        return new Rectangle(this.getX(), this.getY(), this.image.getWidth(), this.image.getHeight());
    }
    
    public boolean isVisible() {
        return this.getX() > -this.image.getWidth() && this.getX() < Game.window.getWidth();
    }
}
