package dinosaurGame.entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import dinosaurGame.Game;
import dinosaurGame.helper.RandomNumber;

public class World
{
    public static int offset = 0;
    public static int speed = 9;
    
    public Terrain terrain;
    public List<Entity> obstacles;
    
    private long obstacleDelay;
    private long lastObstacleCreation;
    private boolean multiple = false;
    private boolean clearRequested = false;
    
    public World() {
        this.terrain = new Terrain();
        this.obstacles = new ArrayList<Entity>();
    }
    
    public void draw(Graphics g) {
        offset -= speed;
        
        this.terrain.draw(g);
        
        if (this.clearRequested) {
            this.obstacles.clear();
            this.clearRequested = false;
        }
        
        generateObstacles();
        
        for (Entity obstacle: this.obstacles) {
            obstacle.draw(g);
            
            // collision detection
            if (Game.player.hasCollision && ((Cactus) obstacle).hasCollision) {
                if (Game.player.getHitbox().intersects(obstacle.getHitbox())) {
                    Game.gameOver();
                }
            }
        }
    }
    
    private void generateObstacles() {
        int maxCount = 5;
        
        // if an obstacle is out of view, remove it
        if (!this.obstacles.isEmpty() && !this.obstacles.get(0).isVisible()) {
            this.obstacles.remove(0);
        }
        
        if (this.obstacles.size() < maxCount) {
            // manual time check and not Java's Timer class as the latter results in
            // concurrent modification of the obstacles list with the foreach in draw()
            if (this.multiple || System.currentTimeMillis() - this.lastObstacleCreation >= this.obstacleDelay) {
                int posX = Game.window.getWidth();
                
                if (this.multiple && !this.obstacles.isEmpty()) {
                    Entity lastObstacle = this.obstacles.get(this.obstacles.size() - 1);
                    posX += lastObstacle.getHitbox().width;
                }
                
                Entity newObstacle = new Cactus(posX, -50, RandomNumber.getFloatRange(0.75f, 1.0f));
                obstacles.add(newObstacle);
                
                this.lastObstacleCreation = System.currentTimeMillis();
                // random delay after which an obstacle should be added
                this.obstacleDelay = (long) RandomNumber.getIntRange(750, 2500);
                
                // if enough space and this obstacle is not already placed next to one (max two together)
                // decide randomly whether the next obstacle should be placed next to this one
                if (this.obstacles.size() < (maxCount - 1) && !this.multiple) {
                    this.multiple = (RandomNumber.getIntRange(0, 1) == 1);
                } else {
                    this.multiple = false;
                }
            }
        }
    }
    
    public void requestClear() {
        this.clearRequested = true;
    }
    
    public void reset() {
        // don't clear the list directly, as that can potentially
        // throw a ConcurrentModificationException (because of the draw for-loop)
        this.requestClear();
        
        this.multiple = false;
        World.offset = 0;
        World.speed = 9;
    }
}
