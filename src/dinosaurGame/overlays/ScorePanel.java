package dinosaurGame.overlays;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import dinosaurGame.Game;

public class ScorePanel
{
    public static int highScore;
    private int currentScore = 0;
    
    private boolean isRunning = true;
    
    public ScorePanel() {
        new Thread(this::update).start();
        
        // register the custom font so it can be used in Font constructor
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("assets/ARCADECLASSIC.TTF")));
        } catch (IOException|FontFormatException e) {
            System.out.print("ScorePanel font couldn't be loaded.");
        }
    }
    
    public void update() {
        while (this.isRunning) {
            this.currentScore++;
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    public void draw(Graphics g) {
        Font font = new Font("ArcadeClassic", Font.PLAIN, 24);
        g.setFont(font);
        
        String scoreText = String.valueOf(this.currentScore);
        int chrW = font.getSize() / 2;
        int chrH = font.getSize();
        g.drawString(scoreText, Game.window.getWidth() - (scoreText.length() + 1) * chrW, chrH);
    }
    
    public void updateHighscore() {
        if (this.currentScore > highScore) {
            highScore = this.currentScore;
        }
    }
    
    public void stop() {
        this.isRunning = false;
    }
    
    public void reset() {
        this.currentScore = 0;
        
        this.isRunning = true;
        new Thread(this::update).start();
    }
}
