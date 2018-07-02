package dinosaurGame.entities;

import java.awt.image.BufferedImage;

import dinosaurGame.Game;
import dinosaurGame.helper.AnimatedImage;
import dinosaurGame.helper.ResourceLoader;

public class Player extends Entity
{
    private AnimatedImage imgAnim;
    
    public boolean hasCollision = true;
    public boolean isAlive = true;
    public boolean isJumping = false;
    public int jumpHeight = 180;
    public int jumpDuration = 750;
    
    public Player() {
        super("Player", 0, 0, "assets/dino.png");
        
        // set at least x position here instead of in the Entity constructor
        // we don't want it to be handled relative to the world offset..
        this.position.x = 150;
        this.position.y = Game.world.terrain.getY() - 46;
        
        this.imgAnim = new AnimatedImage(200);
        this.imgAnim.addFrame(ResourceLoader.loadImage("assets/dino_run1.png"));
        this.imgAnim.addFrame(ResourceLoader.loadImage("assets/dino_run2.png"));
        
        // (pre-)load the dead player picture
        Game.resources.loadImage("player_dead", "assets/dino_crashed.png");
    }
    
    public BufferedImage getImage() {
        if (this.isAlive && !this.isJumping) {
            return this.imgAnim.nextFrame();
        }
        
        return this.image;
    }
    
    public int getX() {
        return this.position.x;
    }
    
    public void setX(int posX) {
        this.position.x = posX;
    }
    
    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
        String img = (isAlive) ? "player" : "player_dead";
        
        this.image = Game.resources.getImage(img);
    }
}
