package dinosaurGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import dinosaurGame.entities.Entity;
import dinosaurGame.entities.Player;
import dinosaurGame.entities.World;
import dinosaurGame.helper.InputHandler;
import dinosaurGame.overlays.GameOver;
import dinosaurGame.overlays.ScorePanel;

public class Game implements Runnable
{
    public static Window window;
    BufferStrategy buffer;
    InputHandler events;
    public static World world;
    public static Player player;
    public static ScorePanel score;
    
    // refresh rate in Hz
    private static final int REFRESH_RATE = 60;
    // frametime in ns (calculation with ms resulted in precision loss)
    private static final int FRAME_TIME = 1000000000 / REFRESH_RATE;
    // update rate in ms for the FPS counter
    private static final int FPS_COUNTER_UPDATE = 1000;
    
    public static void main(String[] args) {
        Game game = new Game();
        //game.run();
        new Thread(game).start();
        
        new Thread(game::runPhysics).start();
    }
    
    public Game() {
        window = new Window();
        
        this.buffer = window.getBufferStrategy();
        
        this.events = new InputHandler();
        window.addKeyListener(events);
        
        world = new World();
        
        player = new Player();
        
        score = new ScorePanel();
    }
    
    @Override
    public void run() {
        long timer = System.nanoTime();
        int framesDrawn = 0;
        
        while (true) {
            long execStartTime = System.nanoTime();
            
            Graphics g = buffer.getDrawGraphics();
            // clear previous frame
            g.clearRect(0, 0, window.getWidth(), window.getHeight());
            
            // game draw code
            render(g);
            
            buffer.show();
            g.dispose();
            
            framesDrawn++;
            
            long execTime = System.nanoTime() - execStartTime;
            
            if (execTime < FRAME_TIME) {
                try {
                    Thread.sleep((FRAME_TIME - execTime) / 1000 / 1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
            // if 1 second passed
            if ((System.nanoTime() - timer) / 1000 / 1000 >= FPS_COUNTER_UPDATE) {
                System.out.println("FPS: " + framesDrawn);
                
                timer = System.nanoTime();
                framesDrawn = 0;
            }
        }
    }
    
    public void render(Graphics g) {
        // set background
        g.setColor(new Color(247, 247, 247));
        g.fillRect(0, 0, window.getWidth(), window.getHeight());
        
        player.draw(g);
        
        world.draw(g);
        
        score.draw(g);
        
        if (!player.isAlive) {
            GameOver.draw(g);
        }
        
        // draw hitboxes for debug
//        for (Entity obstacle: world.obstacles) {
//            Rectangle rect = obstacle.getHitbox();
//            g.drawRect(rect.x, rect.y, rect.width, rect.height);
//        }
//        
//        Rectangle playerBox = player.getHitbox();
//        g.drawRect(playerBox.x, playerBox.y, playerBox.width, playerBox.height);
    }
    
    public void runPhysics() {
        double jumpHeight = (double) player.jumpHeight;
        double pixelPerFrame = player.jumpHeight / ((double) player.jumpDuration / 2 / 1000 * REFRESH_RATE);
        double pixelLeft = 0.0;
        
        while (true) {
            long execStartTime = System.nanoTime();
            
            if (events.getKeyState(KeyEvent.VK_SPACE)) {
                if (player.isAlive) {
                    player.isJumping = true;
                } else {
                    reset();
                }
            }
            
            // jump animation
            if (player.isAlive && player.isJumping) {
                if (jumpHeight != 0) {
                    int pixelToMove = (int) pixelPerFrame;
                    pixelLeft += pixelPerFrame - pixelToMove;
                    
                    // if pixelLeft is a round number, add it to pixelToMove
                    if ((double) Math.round(pixelLeft) == pixelLeft) {
                        pixelToMove += pixelLeft;
                        pixelLeft = 0.0;
                    }
                    
                    // when falling, invert pixelToMove
                    if (jumpHeight < 0) {
                        pixelToMove = -pixelToMove;
                    }
                    
                    // when jumpHeight is less than pixelToMove, move the remaining amount
                    if (jumpHeight > 0 && jumpHeight < pixelToMove || jumpHeight < 0 && jumpHeight > pixelToMove) {
                        pixelToMove = (int) jumpHeight;
                    }
                    
                    player.setY(player.getY() - pixelToMove);
                                        
                    if (jumpHeight > 0 && jumpHeight - pixelToMove == 0) {
                        jumpHeight = (double) -player.jumpHeight;
                    } else {
                        jumpHeight -= pixelToMove;
                    }
                } else {
                    jumpHeight = (double) player.jumpHeight;
                    player.isJumping = false;
                }
            }
            
            long execTime = System.nanoTime() - execStartTime;
            
            if (execTime < FRAME_TIME) {
                // following comment deprecated, but kept for reference:
                // some sleep so the thread doesn't terminate because of too low execution time when key not pressed
                try {
                    Thread.sleep((FRAME_TIME - execTime) / 1000 / 1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void gameOver() {
        player.setAlive(false);
        World.speed = 0;
        score.stop();
    }
    
    public static void reset() {
        player.setAlive(true);
        player.isJumping = false;
        // don't clear the list directly, as that can potentially
        // throw a ConcurrentModificationException (because of the draw for loop)
        world.requestClear();
        World.speed = 6;
        score.reset();
    }
}
