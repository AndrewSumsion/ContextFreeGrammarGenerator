package comprehensive;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Implements the XoRoShiRo128++ algorithm defined here: https://prng.di.unimi.it (Public Domain)
 *
 * @author Andrew Sumsion and Dillon Otto
 */
public class FastRandom extends Random {
    private long s0;
    private long s1;

    public FastRandom() {
        Random base = ThreadLocalRandom.current();
        s0 = base.nextLong();
        s1 = base.nextLong();
    }

    @Override
    public int nextInt(int bound) {
        int result = nextInt() % bound;
        if(result < 0) {
            result += bound;
        }
        return result;
    }

    @Override
    public int nextInt() {
        return (int) nextLong();
    }

    @Override
    public long nextLong() {
        long a = s0;
        long b = s1;
        long out = Long.rotateLeft(a + b, 17) + a;
        b ^= a;
        s0 = Long.rotateLeft(a, 49) ^ b ^ (b << 21);
        s1 = Long.rotateLeft(b, 28);

        return out;
    }
}
