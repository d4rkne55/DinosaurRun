package dinosaurGame.entities;

import java.awt.Color;
import java.awt.Graphics;

import dinosaurGame.Game;
import dinosaurGame.helper.Position;

public class Terrain
{
    private Position position;
    private static int height = 80;
    
    public Terrain() {
        this.position = new Position(0, Game.window.getHeight() - height);
    }
    
    public void draw(Graphics g) {
        g.setColor(new Color(83, 83, 83));
        g.drawRect(this.position.x, this.position.y, Game.window.getWidth(), height);
    }
    
    public int getX() {
        return this.position.x;
    }
    
    public int getY() {
        return this.position.y;
    }
}
