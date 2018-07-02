package dinosaurGame;

import javax.swing.JFrame;

public class Window extends JFrame
{
    public Window() {
        this(0, 0);
    }
    
    public Window(int width, int height) {
        if (width > 0) {
            setSize(width, height);
        } else {
            setFullscreen();
        }
        
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        createBufferStrategy(3);
    }
    
    protected void setFullscreen() {
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
}
