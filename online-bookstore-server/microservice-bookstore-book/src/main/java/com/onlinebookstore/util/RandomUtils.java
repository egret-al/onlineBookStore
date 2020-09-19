package com.onlinebookstore.util;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author rkc
 * @date 2020/9/19 15:57
 * @version 1.0
 */
@Component
public class RandomUtils {

    private Random random = new Random();

    public int getInt() {
        return getInt(-1);
    }

    public int getInt(int bound) {
        return bound == -1 ? random.nextInt() : random.nextInt(bound);
    }
}
