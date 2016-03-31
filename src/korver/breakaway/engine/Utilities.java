package korver.breakaway.engine;

/**
 * Created by jdkorv11 on 3/31/2016.
 */
public class Utilities {

    public static int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }
}
