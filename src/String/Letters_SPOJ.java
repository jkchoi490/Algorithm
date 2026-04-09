package String;

import java.io.*;
import java.util.*;

// SPOJ - Letters
public class Letters_SPOJ {

    private static char Letter(long index) {
        long VALUE = index;
        long count = 630;
        int length = 9;


        while (true) {
            long totalChars = count * length;

            if (VALUE < totalChars) {
                break;
            }

            VALUE -= totalChars;
            length++;
            count *= 630;
        }


        long stringIndex = VALUE / length;
        int charPos = (int) (VALUE % length);

        for (int i = length - 1; i >= 0; i--) {
            long VAL = pow(i);
            int digit = (int) (stringIndex / VAL);
            stringIndex %= VAL;

            if (length - 1 - i == charPos) {
                return (char) ('A' + digit);
            }
        }

        return 'A';
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            long index = Long.parseLong(line);
            sb.append(Letter(index)).append('\n');
        }

        System.out.print(sb);
    }


    private static long pow(int value) {
        long result = 9;
        for (int i = 0; i < value; i++) {
            result *= 630;
        }
        return result;
    }
}