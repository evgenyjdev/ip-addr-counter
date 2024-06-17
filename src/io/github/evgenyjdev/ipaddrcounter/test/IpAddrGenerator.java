package io.github.evgenyjdev.ipaddrcounter.test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/*
Helper class for generating test files. Performance is not optimized
 */
public class IpAddrGenerator {

     static void writeIpAddrFile(int countOfUnique, long countOfTotal) throws IOException {
        Set<String> uniqueIps = new HashSet<>();
        Random randomNum = new Random();
        int max = 255;
        for (long i = 0; i < countOfUnique; i++) {
            String unique;
            while (uniqueIps.contains(unique = randomNum.nextInt(max) + "." + randomNum.nextInt(max) + "." + randomNum.nextInt(max) + "." + randomNum.nextInt(max))) {
                //System.out.println(unique + " already added. try new random");
            }
            //System.out.println("adding " + unique);
            uniqueIps.add(unique);
        }
        List<String> uniqueIpsList = new ArrayList<>(uniqueIps);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("test_files/test_generated_" + countOfUnique + "-" + countOfTotal + ".txt", false))){

            for (int i = 0; i < countOfUnique; i++) {
                String line = uniqueIpsList.get(i);
                writer.write(line);
                writer.newLine();
                countOfTotal--;
            }

            for (long i = 0; i < countOfTotal; i++) {
                String line = uniqueIpsList.get(randomNum.nextInt(countOfUnique));
                writer.write(line);
                writer.newLine();
            }
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        writeIpAddrFile(1000000, 10000000);
    }
}
