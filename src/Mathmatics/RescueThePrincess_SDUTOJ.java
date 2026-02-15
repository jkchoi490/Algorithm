package Mathmatics;

import java.io.*;
import java.util.*;

// SDUT OJ - Rescue The Princess
public class RescueThePrincess_SDUTOJ {

    // 공주님을 구하고 돕기 위한 메서드를 구현합니다
    static double[] SaveAndHelpPrincess(double r, double c, double R, double C) {

        final double COS = 0.5;
        final double SIN = Math.sqrt(3.0) / 2.0;

        double dr = R - r;
        double dc = C - c;

        double DR = dr * COS - dc * SIN;
        double DC = dr * SIN + dc * COS;

        double DirectionR = r + DR;
        double DirectionC = c + DC;

        return new double[]{DirectionR, DirectionC};
    }

    public static void main(String[] args) throws Exception {

        Locale.setDefault(Locale.US);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine().trim());

        for (int tc = 0; tc < T; tc++) {

            StringTokenizer st = new StringTokenizer(br.readLine());

            double r = Double.parseDouble(st.nextToken());
            double c = Double.parseDouble(st.nextToken());
            double R = Double.parseDouble(st.nextToken());
            double C = Double.parseDouble(st.nextToken());

            // 공주님을 구하고 돕기 위한 메서드를 실행합니다
            double[] answer = SaveAndHelpPrincess(r, c, R, C);

            sb.append(String.format(Locale.US, "(%.2f,%.2f)", answer[0], answer[1]));

            if (tc + 1 < T) sb.append('\n');
        }

        System.out.print(sb.toString());
    }
}
