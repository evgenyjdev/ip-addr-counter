package io.github.evgenyjdev.ipaddrcounter;

import java.io.IOException;
import java.util.Date;

public interface IpAddrCounter {

    int READER_BUFFER_SIZE = 8388608;

    long countUnique(String fileFullPath) throws IOException;

    private static void runTest(IpAddrCounter counter) throws IOException {
        final String TEST_FILE = "test_files/test_generated_20000000-200000000.txt";
        long before, after;
        before = new Date().getTime();
        System.out.print("count unique = " + counter.countUnique(TEST_FILE));
        after = new Date().getTime();
        System.out.println("...calculated in " + (after - before) + " ms.");
    }

    static void main(String[] args) throws IOException {
        runTest(new NaiveIpAddrCounter());
        runTest(new BitSetIpAddrCounter());
        runTest(new LongArrIpAddrCounter());
        /*
        Example execution result for test file test_generated_20000000-200000000.txt
        (20 million unique values / 200 million rows) ~ 2.84 GB:
        count unique = 20000000...calculated in 67331 ms.
        count unique = 20000000...calculated in 35202 ms.
        count unique = 20000000...calculated in 35327 ms.

        result for ip_address file from task:
        NaiveIpAddrCounter - java.lang.OutOfMemoryError: Java heap space
        BitSetIpAddrCounter count unique = 1000000000...calculated in 3891394 ms. ~ 1.08 hour
        LongArrIpAddrCounter count unique = 1000000000 ...calculated in 3823177 ms. ~ 1.06 hour
         */
    }
}