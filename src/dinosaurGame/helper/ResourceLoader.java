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
    class ResourceCache<T> {
        private HashMap<String, T> cache = new HashMap<String, T>();
        
        public T get(String key) {
            return this.cache.get(key);
        }
        
        public void put(String key, T obj) {
            this.cache.put(key, obj);
        }
    }
    
    private ResourceCache<Object> resources = new ResourceCache<Object>();
    
    public boolean loadImage(String name, String imgPath) {
        BufferedImage img = loadImage(imgPath);
        
        if (img != null) {
            this.resources.put(name, img);
            return true;
        } else {
            return false;
        }
    }
    
    public BufferedImage getImage(String name) {
        return (BufferedImage) this.resources.get(name);
    }
    
    public Set<String> getNames() {
        return this.resources.cache.keySet();
    }
    
    public static BufferedImage loadImage(String imgPath) {
        try {
            return ImageIO.read(new File(imgPath));
        } catch (IOException e) {
            System.out.print("Image from '" + imgPath + "' couldn't be loaded.");
            return null;
        }
    }
    
    public static void registerFont(String filePath) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        
        try {
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(filePath)));
        } catch (IOException|FontFormatException e) {
            System.out.print("Custom font couldn't be loaded.");
        }
    }
}
