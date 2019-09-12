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
    public static int level = 1;
    
    public Terrain terrain;
    public List<Entity> obstacles;
    public int maxCount = 5;
    public int maxMultipleCount = 2;
    
    private int obstacleDistance;
    private boolean multiple = false;
    private int multipleCount = 0;
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
            if (Game.player.isAlive) {
                if (Game.player.hasCollision && ((Cactus) obstacle).hasCollision) {
                    if (Game.player.getHitbox().intersects(obstacle.getHitbox())) {
                        Game.gameOver();
                    }
                }
            }
        }
    }
    
    private void generateObstacles() {
        // if an obstacle is out of view, remove it
        if (!this.obstacles.isEmpty() && !this.obstacles.get(0).isVisible()) {
            this.obstacles.remove(0);
        }
        
        if (this.obstacles.size() < this.maxCount) {
            Entity lastObstacle = null;
            int lastObstacleDistance = 0;
            
            if (!this.obstacles.isEmpty()) {
                lastObstacle = this.obstacles.get(this.obstacles.size() - 1);
                lastObstacleDistance = Game.window.getWidth() - lastObstacle.getX() + lastObstacle.getHitbox().width;
            }
            
            if (this.multiple || lastObstacleDistance >= this.obstacleDistance) {
                int posX = Game.window.getWidth();
                
                if (this.multiple && !this.obstacles.isEmpty()) {
                    posX = lastObstacle.getX() + lastObstacle.getHitbox().width + 10;
                    this.multipleCount = (this.multipleCount == 0) ? 2 : this.multipleCount + 1;
                } else {
                    this.multipleCount = 0;
                }
                
                Entity newObstacle = new Cactus(posX, -50, RandomNumber.getFloatRange(0.75f, 1.0f));
                obstacles.add(newObstacle);
                
                // random distance after which an obstacle should be added
                this.obstacleDistance = RandomNumber.getIntRange(World.speed * 39, Game.window.getWidth());
                
                // if enough space and there are not already two obstacles together now
                // decide randomly whether the next obstacle should be placed next to this one
                if (this.obstacles.size() < (this.maxCount - 1) && this.multipleCount < this.maxMultipleCount) {
                    this.multiple = (RandomNumber.getIntRange(0, 1) == 1);
                } else {
                    this.multiple = false;
                }
            }
        }
    }
    
    public void setLevel(int level) {
        if (level != World.level) {
            World.level = level;
            
            switch (level) {
                case 2:
                    this.maxMultipleCount = 3;
                default:
                    World.speed += 1;
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
        
        this.obstacleDistance = 0;
        this.multiple = false;
        this.maxMultipleCount = 2;
        World.offset = 0;
        World.speed = 9;
    }
}
