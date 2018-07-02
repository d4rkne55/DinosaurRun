package dinosaurGame.helper;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener
{
    private boolean[] keyStates = new boolean[256];
    
    @Override
    public void keyPressed(KeyEvent e) {
        this.keyStates[e.getKeyCode()] = true;
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        this.keyStates[e.getKeyCode()] = false;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }
    
    public boolean getKeyState(int keyCode) {
        return this.keyStates[keyCode];
    }
}
