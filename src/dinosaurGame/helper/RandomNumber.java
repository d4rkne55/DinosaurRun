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
    
    public static int getInt() {
        return rand.nextInt();
    }
    
    public static int getIntRange(int min, int max) {
        return min + rand.nextInt(max + 1 - min);
    }
}
