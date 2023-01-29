package com.fishingbooker.util;

import java.util.concurrent.ThreadLocalRandom;

public class OrderIdGenerator {
    public static String generateOrderId() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        StringBuilder orderId = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            orderId.append(random.nextInt(10));
        }
        return orderId.toString();
    }
}
