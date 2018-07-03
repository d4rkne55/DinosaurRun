package dinosaurGame.helper;

import java.util.Random;

public final class RandomNumber
{
    /*
     * The Random class shouldn't be instantiated for each call but rather once.
     * Alternatively ThreadLocalRandom.current() can be used.
     * 
     * @see https://stackoverflow.com/a/363692/3439786
     */
    private static Random rand = new Random();
    
    /**
     * Generates a random full-range integer, from Integer.MIN_VALUE to Integer.MAX_VALUE
     */
    public static int getInt() {
        return rand.nextInt();
    }
    
    /**
     * Generates a random integer between given min (inclusive) and max (inclusive)
     * 
     * @param min
     * @param max
     */
    public static int getIntRange(int min, int max) {
        return min + rand.nextInt(max + 1 - min);
    }
    
    /**
     * Generates a random float from 0.0 (inclusive) to 1.0 (exclusive)
     */
    public static float getFloat() {
        return rand.nextFloat();
    }
    
    /**
     * Generates a random float between given min (inclusive) and max (exclusive)
     * TODO: try to get max inclusive
     * 
     * @param min
     * @param max
     */
    public static float getFloatRange(float min, float max) {
        return min + rand.nextFloat() * (max - min);
    }
}
