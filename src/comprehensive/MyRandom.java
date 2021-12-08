package comprehensive;

import java.util.Random;

public class MyRandom extends Random {
    private static long elapsed;

    @Override
    public int nextInt(int i) {
        long startTime = System.nanoTime();
        int out = super.nextInt(i);
        long endTime = System.nanoTime();
        elapsed += endTime - startTime;
        return out;
    }

    public static long getElapsed() {
        return elapsed;
    }
}
