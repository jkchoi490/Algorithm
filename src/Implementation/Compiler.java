package Implementation;

import java.util.*;
import java.io.*;

// Kattis - Compiler
public class Compiler {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        long n = Long.parseLong(br.readLine());

        if (n == 0) {
            System.out.println("ZE A");
            System.out.println("DI A");
            return;
        }

        StringBuilder sb = new StringBuilder();

        sb.append("ST X\n");

        String binary = Long.toBinaryString(n);

        sb.append("PH X\n");


        for (int i = 1; i < binary.length(); i++) {
            sb.append("PL A\n");
            sb.append("PH A\n");
            sb.append("PH A\n");
            sb.append("AD\n");

            if (binary.charAt(i) == '1') {
                sb.append("PH X\n");
                sb.append("AD\n");
            }
        }

        sb.append("PL A\n");
        sb.append("DI A\n");

        System.out.print(sb.toString());
    }
}