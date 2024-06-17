package io.github.evgenyjdev.ipaddrcounter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;

/*
Implementation using java.util.BitSet to determine if a given IP address has already been added
 */
public class BitSetIpAddrCounter implements IpAddrCounter {

    @Override
    public long countUnique(String fileFullPath) throws IOException {

        BufferedReader reader = null;
        BitSet positiveIntSet = new BitSet(Integer.MAX_VALUE);
        BitSet negativeIntSet = new BitSet(Integer.MAX_VALUE);

        try {
            reader = new BufferedReader(new FileReader(fileFullPath), READER_BUFFER_SIZE);

            String line;
            while ((line = reader.readLine()) != null) {
                int ipIntValue = IpAddrParseUtil.parseAsInt(line);
                if (ipIntValue >= 0) {
                    positiveIntSet.set(ipIntValue);
                } else {
                    negativeIntSet.set(~ipIntValue);
                }
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        return positiveIntSet.cardinality() + negativeIntSet.cardinality();
    }
}
