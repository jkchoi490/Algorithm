package Mathmatics;

import java.io.*;
import java.util.*;

//CSDN - Rescue the Princess
public class RescueThePrincess_CSDN {

    //공주님을 구하고 돕기 위한 메서드 작성
    static void SaveAndHelpPrincess() throws Exception {
        FastReader fr = new FastReader();
        StringBuilder sb = new StringBuilder();

        int T = fr.nextInt();

        // 문제에서 주어진 값
        final double COS = 0.5;
        final double SIN = Math.sqrt(3.0) / 2.0;

        for (int tc = 0; tc < T; tc++) {
            double value_r = fr.nextDouble();
            double value_c = fr.nextDouble();
            double value_R = fr.nextDouble();
            double value_C = fr.nextDouble();

            double vectorR = value_R - value_r;
            double vectorC = value_C - value_c;


            double R = vectorR * COS - vectorC * SIN;
            double C = vectorR * SIN + vectorC * SIN;

            double valueR = value_r + R;
            double valueC = value_c + C;

            sb.append(String.format(Locale.US, "(%.2f,%.2f)%n", valueR, valueC));
        }

        System.out.print(sb.toString());
    }

    static class FastReader {
        private final BufferedReader br;
        private StringTokenizer st;

        FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) {
                String line = br.readLine();
                if (line == null) return null;
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }
    }

    public static void main(String[] args) throws Exception {
        //공주님을 구하고 돕기 위한 메서드 구현
        SaveAndHelpPrincess();
    }
}
