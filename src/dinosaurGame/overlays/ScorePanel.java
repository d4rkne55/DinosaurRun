package dinosaurGame.overlays;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;

import dinosaurGame.Game;

public class ScorePanel
{
    public static int highScore;
    private int currentScore = 0;
    
    private boolean isRunning = true;
    
    public ScorePanel() {
        new Thread(this::update).start();
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
        
        String highScoreText = "HI  " + highScore;
        String scoreText = String.format("%03d", this.currentScore);
        
        int spacing = 20;
        FontMetrics metrics = g.getFontMetrics();
        int str1W = metrics.stringWidth(highScoreText);
        int str2W = metrics.stringWidth(scoreText);
        int strW = str1W + spacing + str2W;
        int chrW = metrics.charWidth('0');
        // why is strH double the actual height? retina display stuff?
        float strH = (float) metrics.getHeight() / 2;
        Insets windowBorders = Game.window.getBorders();
        
        if (highScore > 0) {
            g.setColor(new Color(115, 115, 115));
            g.drawString(highScoreText, Game.window.getWidth() - strW - chrW, windowBorders.top + (int) (strH * 2));
        }
        
        g.setColor(new Color(83, 83, 83));
        g.drawString(scoreText, Game.window.getWidth() - str2W - chrW, windowBorders.top + (int) (strH * 2));
        
        // draw box for debug
//        g.drawRect(Game.window.getWidth() - strW - metrics.charWidth('0'), windowBorders.top + (int) strH, strW, (int) strH);
    }
    
    public void updateHighscore() {
        if (this.currentScore > highScore) {
            highScore = this.currentScore;
        }
    }
    
    public int getScore() {
        return this.currentScore;
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
