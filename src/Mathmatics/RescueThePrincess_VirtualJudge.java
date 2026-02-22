package Mathmatics;

import java.io.*;
import java.util.*;

// Virtual Judge - Rescue the Princess
public class RescueThePrincess_VirtualJudge {

    // 공주님을 구하고 돕기 위한 메서드를 구현합니다
    static double[] SaveAndHelpPrincess(double r, double c, double R, double C) {

        final double COS = 0.5;    // cos60도
        final double SIN = Math.sqrt(3.0) / 2.0; // sin60도

        double dr = R - r;
        double dc = C - c;

        double rotatedR = dr * COS - dc * SIN;
        double rotatedC = dr * SIN + dc * COS;

        double resultR = r + rotatedR;
        double resultC = c + rotatedC;
        if (Math.abs(resultR) < 0.0005) resultR = 0.0;
        if (Math.abs(resultC) < 0.0005) resultC = 0.0;
        return new double[]{resultR, resultC};
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine().trim());

        for (int i = 0; i < T; i++) {

            StringTokenizer st = new StringTokenizer(br.readLine());

            double r = Double.parseDouble(st.nextToken());
            double c = Double.parseDouble(st.nextToken());
            double R = Double.parseDouble(st.nextToken());
            double C = Double.parseDouble(st.nextToken());

            // 공주님을 구하고 돕기 위한 메서드를 실행합니다
            double[] result = SaveAndHelpPrincess(r, c, R, C);

            sb.append(String.format(Locale.US,
                    "(%.2f,%.2f)%n",
                    result[0],
                    result[1]));
        }

        System.out.print(sb.toString());
    }
}