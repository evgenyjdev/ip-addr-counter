package io.github.evgenyjdev.ipaddrcounter;

public class IpAddrParseUtil {

    public static int parseAsInt(String source) {
        int[] substr = split(source);
        return substr[3] + substr[2] * 256 + substr[1] * 65536 + substr [0] * 16777216;
    }

    // parse ipv4 address as four numbers
    public static int[] split(String source) {
        int index = 3;
        int digitNumber = 0;
        int[] result = new int[4];
        byte[] byteArr = source.getBytes();
        for (int i = byteArr.length - 1; i >= 0; i--) {
            byte currentByte = byteArr[i];
            if (currentByte == 46) { // dot character
                index--;
                digitNumber = 0;
            } else {
                int digit = (currentByte - 48);
                switch (digitNumber) {
                    case 0 -> result[index] = result[index] + digit;
                    case 1 -> result[index] = result[index] + 10 * digit;
                    case 2 -> result[index] = result[index] + 100 * digit;
                    default -> {
                        throw new RuntimeException("Wrong IPv4 address string: " + source);
                    }
                }
                digitNumber++;
            }
        }
        return result;
    }
}
