package dinosaurGame.helper;

import dinosaurGame.Game;

public class Position
{
    public int x;
    public int y;
    
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Calculates the horizontal position in pixel by the given percentage
     * 
     * @param relativeX  the percentage
     */
    public static int getAbsoluteX(double relativeX) {
        return Game.window.getBorders().left + (int) (Game.window.getContentWidth() * relativeX);
    }
    
    /**
     * Calculates the vertical position in pixel by the given percentage
     * 
     * @param relativeY  the percentage
     */
    public static int getAbsoluteY(double relativeY) {
        return Game.window.getBorders().top + (int) (Game.window.getContentHeight() * relativeY);
    }
}
