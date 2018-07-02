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
        int maxCount = 3;
        //int minDistance = 100;
        
        // if an obstacle is out of view, remove it
        if (!this.obstacles.isEmpty() && !this.obstacles.get(0).isVisible()) {
            this.obstacles.remove(0);
        }
        
        if (this.obstacles.size() < maxCount) {
            // generate random delay after which an obstacle should be added
            long randDelay = (long) RandomNumber.getIntRange(600, 5000);
            
            // manual time check and not Java's Timer class as the latter results in
            // concurrent modification of the obstacles list with the foreach in draw()
            if (this.multiple || System.currentTimeMillis() - this.lastObstacleCreation >= randDelay) {
                int posX = Game.window.getWidth();
                if (this.multiple && !this.obstacles.isEmpty()) {
                    posX += this.obstacles.get(this.obstacles.size() - 1).getImage().getWidth();
                }
                
                obstacles.add(new Cactus(posX, -45, RandomNumber.getFloatRange(0.75f, 1.0f)));
                
                this.lastObstacleCreation = System.currentTimeMillis();
                // random choice whether the next obstacle should be placed next to this one
                // TODO: Doesn't really work as expected..
                this.multiple = RandomNumber.getIntRange(0, 1) == 1;
            }
        }
    }
    
    public void requestClear() {
        this.clearRequested = true;
    }
}
