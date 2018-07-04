package dinosaurGame.overlays;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import dinosaurGame.helper.Position;

public class GameOver
{
    public static void draw(Graphics g) {
        Font font = new Font("ArcadeClassic", Font.PLAIN, 48);
        g.setFont(font);
        
        String text = "GAME OVER";
        FontMetrics metrics = g.getFontMetrics();
        int strW = metrics.stringWidth(text);
        float strH = (float) metrics.getHeight() / 2;
        
        g.drawString(text, Position.getAbsoluteX(0.5) - strW / 2, Position.getAbsoluteY(0.425) + (int) (strH / 2));
    }
}
