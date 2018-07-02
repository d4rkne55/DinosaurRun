package dinosaurGame.overlays;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

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
        
        String scoreText = (highScore > 0) ? "HI  " + highScore + "    " + currentScore : String.valueOf(this.currentScore);
        FontMetrics metrics = g.getFontMetrics();
        int strW = metrics.stringWidth(scoreText);
        int strH = metrics.getHeight();
        
        // why is strH double the actual height? retina display stuff?
        g.drawString(scoreText, Game.window.getWidth() - strW - metrics.charWidth('0'), strH);
        
        // draw box for debug
//        g.drawRect(Game.window.getWidth() - strW - metrics.charWidth('0'), strH / 2, strW, strH / 2);
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
