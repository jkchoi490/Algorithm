package String;

import java.io.*;
import java.util.*;

// Dovelet - 노력
public class 노력 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        List<String> numbers = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            int len = line.length();

            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < len; j++) {
                char c = line.charAt(j);

                if (Character.isDigit(c)) {
                    sb.append(c);
                } else {
                    if (sb.length() > 0) {
                        numbers.add(normalize(sb.toString()));
                        sb.setLength(0);
                    }
                }
            }

            if (sb.length() > 0) {
                numbers.add(normalize(sb.toString()));
            }
        }

        Collections.sort(numbers, (a, b) -> {
            if (a.length() != b.length()) return a.length() - b.length();
            return a.compareTo(b);
        });

        StringBuilder out = new StringBuilder();
        for (String num : numbers) {
            out.append(num).append("\n");
        }
        System.out.print(out.toString());
    }

    static String normalize(String num) {
        int i = 0;
        int n = num.length();
        while (i < n && num.charAt(i) == '0') {
            i++;
        }
        if (i == n) return "0"; // "0", "00", "000" 등
        return num.substring(i);
    }
}
