package com.code10.isa.util;

public class TransactionsUtil {

    private static final boolean TESTING_TRANSACTIONS = false;

    public static void sleepIfTesting() {
        if (TESTING_TRANSACTIONS) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
