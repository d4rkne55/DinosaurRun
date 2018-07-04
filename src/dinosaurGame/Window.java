package dinosaurGame;

import java.awt.Insets;

import javax.swing.JFrame;

public class Window extends JFrame
{
    protected Insets borders;
    
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
        
        this.borders = getInsets();
        
        createBufferStrategy(3);
    }
    
    protected void setFullscreen() {
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    public int getContentWidth() {
        return this.getContentPane().getWidth();
    }
    
    public int getContentHeight() {
        return this.getContentPane().getHeight();
    }
    
    public Insets getBorders() {
        return this.borders;
    }
}
