package Mathmatics;

import java.util.*;
import java.io.*;

// HCPC - Rescue The Princess
public class RescueThePrincess_HCPC {

    // 공주님을 구하고 돕기 위한 메서드를 구현합니다
    public static String SaveAndHelpPrincess(double r, double c, double R, double C) {
        double dr = R - r;
        double dc = C - c;

        double ResultR = (r + R) / 2.0 - (Math.sqrt(3) / 2.0) * dc; // 문제에서 주어진 값 등을 사용합니다
        double ResultC = (c + C) / 2.0 + (Math.sqrt(3) / 2.0) * dr;

        return String.format("(%.2f,%.2f)", ResultR == 0.0 ? 0.0 : ResultR, ResultC == 0.0 ? 0.0 : ResultC);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine().trim());

        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            double r = Double.parseDouble(st.nextToken());
            double c = Double.parseDouble(st.nextToken());
            double R = Double.parseDouble(st.nextToken());
            double C = Double.parseDouble(st.nextToken());

            // 공주님을 구하고 돕기 위한 메서드를 실행합니다
            sb.append(SaveAndHelpPrincess(r, c, R, C)).append("\n");
        }

        System.out.print(sb.toString());
    }
}