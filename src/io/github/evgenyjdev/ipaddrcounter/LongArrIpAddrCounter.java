package io.github.evgenyjdev.ipaddrcounter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
Implementation using arrays of long primitive ("bucket" of bits) to determine if a given IP address has already been added
 */
public class LongArrIpAddrCounter implements IpAddrCounter {

    private long setBitTrue(long number, int bitPosition) {
        long mask = 1L << bitPosition; // Create a mask with a bit set at a given position
        number |= mask; // Setting bit to true
        return number;
    }

    private boolean getBit(long number, int bitIndex) {
        long mask = 1L << bitIndex;
        return (number & mask) != 0;
    }

    @Override
    public long countUnique(String fileFullPath) throws IOException {
        final int IP_ADDR_SET_SIZE = 33554432; // == MAXINT (==2^31-1) / 64 (== number of bits in long type)

        long[] ipAddressesSetPositive = new long[IP_ADDR_SET_SIZE];
        long[] ipAddressesSetNegative = new long[IP_ADDR_SET_SIZE];

        BufferedReader reader = null;
        int counter = 0;

        try {
            reader = new BufferedReader(new FileReader(fileFullPath), READER_BUFFER_SIZE);

            String line;
            while ((line = reader.readLine()) != null) {
                int index = IpAddrParseUtil.parseAsInt(line);
                long[] actualSet;
                if (index >= 0) {
                    actualSet = ipAddressesSetPositive;
                } else {
                    actualSet = ipAddressesSetNegative;
                    index = ~index;
                }
                int indexCart = index / 64; // number of "cart" (long element)
                int indexBit = index % 64; // number of bit in "cart"

                long cartValue = actualSet[indexCart];
                boolean result = getBit(cartValue, indexBit);
                if (!result) {
                    long newCartValue = setBitTrue(cartValue, indexBit);
                    actualSet[indexCart] = newCartValue;
                    counter++;
                }
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        return counter;
    }
}
