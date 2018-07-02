package dinosaurGame.entities;

import dinosaurGame.Game;

public class Player extends Entity
{
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
        
        // (pre-)load the dead player picture
        Game.resources.loadImage("player_dead", "assets/dino_crashed.png");
    }
    
    public int getX() {
        return this.position.x;
    }
    
    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
        String img = (isAlive) ? "player" : "player_dead";
        
        this.image = Game.resources.getImage(img);
    }
}
