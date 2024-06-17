package io.github.evgenyjdev.ipaddrcounter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/*
A naive implementation of the algorithm, it uses Set<String> to determine that the address has already been added.
For performance comparison
 */
public class NaiveIpAddrCounter implements IpAddrCounter {

    @Override
    public long countUnique(String fileFullPath) throws IOException {

        Set<String> ipAddresses = new HashSet<>();
        BufferedReader reader = null;
        long counter = 0;

        try {
            reader = new BufferedReader(new FileReader(fileFullPath), READER_BUFFER_SIZE);
            String line;
            while ((line = reader.readLine()) != null) {
                if (!ipAddresses.contains(line)) {
                    ipAddresses.add(line);
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
