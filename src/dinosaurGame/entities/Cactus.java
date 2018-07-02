package dinosaurGame.entities;

import dinosaurGame.Game;

public class Cactus extends Entity
{
    public boolean hasCollision = true;
    
    public Cactus(int x, int y) {
        super("Cactus", x, y, "assets/cactus.png");
    }
    
    public int getY() {
        return Game.world.terrain.getY() + this.position.y;
    }
}
