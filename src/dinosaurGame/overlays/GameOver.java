package dinosaurGame.overlays;

import java.awt.Font;
import java.awt.Graphics;

import dinosaurGame.helper.Position;

public class GameOver
{
    public static void draw(Graphics g) {
        Font font = new Font("ArcadeClassic", Font.PLAIN, 48);
        g.setFont(font);
        
        String text = "GAME OVER";
        int strW = text.length() * (font.getSize() / 2);
        int strH = font.getSize();
        g.drawString(text, Position.getAbsoluteX(0.5) - strW / 2, Position.getAbsoluteY(0.5) - strH / 2);
    }
}
