package dinosaurGame.helper;

import java.awt.Rectangle;

import dinosaurGame.entities.Entity;

/**
 * @deprecated
 */
public class Hitbox extends Rectangle
{
    public Hitbox(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
    
    public static boolean isColliding(Entity entity1, Entity entity2) {
        if (!entity1.hasCollision || !entity2.hasCollision) {
            return false;
        }
        
        return entity1.getHitbox().intersects(entity2.getHitbox());
    }
}
