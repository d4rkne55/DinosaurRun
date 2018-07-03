package dinosaurGame.helper;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import javax.imageio.ImageIO;

public class ResourceLoader
{
    private HashMap<String, Object> resources = new HashMap<String, Object>();
    
    /**
     * Loads and stores an image to the cache
     * 
     * @param name
     * @param imgPath
     * @return {@code true} when loaded and stored, {@code false} when the requested image couldn't be loaded
     */
    public boolean loadImage(String name, String imgPath) {
        BufferedImage img = loadImage(imgPath);
        
        if (img != null) {
            this.resources.put(name, img);
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Gets stored image from cache
     * 
     * @param name
     */
    public BufferedImage getImage(String name) {
        return (BufferedImage) this.resources.get(name);
    }
    
    /**
     * Returns a Set with all the names/keys of stored resources
     */
    public Set<String> getNames() {
        return this.resources.keySet();
    }
    
    /**
     * Static function for just loading and returning the requested image. No use of cache.
     * 
     * @param imgPath
     */
    public static BufferedImage loadImage(String imgPath) {
        try {
            return ImageIO.read(new File(imgPath));
        } catch (IOException e) {
            System.out.print("Image from '" + imgPath + "' couldn't be loaded.");
            return null;
        }
    }
    
    /**
     * Static function for loading and registering a custom font
     * 
     * @param filePath
     */
    public static void registerFont(String filePath) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        
        try {
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(filePath)));
        } catch (IOException|FontFormatException e) {
            System.out.print("Custom font couldn't be loaded.");
        }
    }
}
