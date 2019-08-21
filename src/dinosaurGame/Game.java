package dinosaurGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import dinosaurGame.entities.Entity;
import dinosaurGame.entities.Player;
import dinosaurGame.entities.World;
import dinosaurGame.helper.Animation;
import dinosaurGame.helper.InputHandler;
import dinosaurGame.helper.ResourceLoader;
import dinosaurGame.overlays.GameOver;
import dinosaurGame.overlays.ScorePanel;

public class Game implements Runnable
{
    public static Window window;
    BufferStrategy buffer;
    InputHandler events;
    public static ResourceLoader resources;
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
        
        resources = new ResourceLoader();
        
        world = new World();
        
        player = new Player();
        
        // register the custom font so it can be used in Font constructor
        ResourceLoader.registerFont("assets/ARCADECLASSIC.TTF");
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
        final int playerYPos = player.getY();
        Animation anim = new Animation((double) player.jumpHeight, (int) (player.jumpDuration / 2), Animation.EASING_SINE);
        
        final int ANIM_NONE = 0;
        final int ANIM_UP = 1;
        final int ANIM_DOWN = 2;
        int animMode = ANIM_NONE;
        
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
                if (animMode == ANIM_NONE) {
                    animMode = ANIM_UP;
                }
                
                anim.update();
                
                player.setY(playerYPos - (int) anim.getState());
                
                if (anim.isFinished()) {
                    if (animMode == ANIM_UP) {
                        animMode = ANIM_DOWN;
                        anim.invert();
                    } else if (animMode == ANIM_DOWN) {
                        animMode = ANIM_NONE;
                        anim.invert(false);
                        player.isJumping = false;
                    }
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
        world.reset();
        score.updateHighscore();
        score.reset();
    }
}
