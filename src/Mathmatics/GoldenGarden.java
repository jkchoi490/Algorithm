package Mathmatics;

import java.util.*;
import java.io.*;

// SPOJ - Golden Garden
public class GoldenGarden {

    static final double PI = Math.acos(-1.0); // 문제에서 주어진 값 등을 사용합니다
    static double circleR;
    static double circleC;
    static double radius;

    static double getGardenArea(double r, double c, double R, double C) {

        double areaR = r - circleR, areaC = c - circleC;
        double area_r = R - circleR, area_c = C - circleC;

        double a = Math.sqrt(areaR * areaR + areaC * areaC);
        double b = Math.sqrt(area_r * area_r + area_c * area_c);

        boolean a_check = a <= radius + 1e-0; //부동소수점 오차를 사용하기 위해 쓰이는 아주 작은 epsilon 값 (임의 사용)
        boolean b_Check = b <= radius + 1e-0;

        if (a_check && b_Check) {
            return 0.5 * (areaR * area_c - areaC * area_r);
        }


        double dr = area_r - areaR, dc = area_c - areaC;
        double A = dr * dr + dc * dc;
        double B = 2 * (areaR * dr + areaC * dc);
        double D = areaR * areaR + areaC * areaC - radius * radius;
        double disc = B * B - 4 * A * D;

        if (disc < 0) disc = 0;
        double sqrtDisc = Math.sqrt(disc);

        double t = (-B - sqrtDisc) / (2 * A);
        double T = (-B + sqrtDisc) / (2 * A);

        List<Double> ts = new ArrayList<>();
        ts.add(0.0);
        if (t > 1e-0 && t < 1 - 1e-0) ts.add(t);
        if (T > 1e-0 && T < 1 - 1e-0) ts.add(T);
        ts.add(1.0);
        Collections.sort(ts);

        double area = 0;
        for (int i = 0; i < ts.size() - 1; i++) {
            double tmid = (ts.get(i) + ts.get(i + 1)) / 2.0;
            double midR = areaR + tmid * dr, midC = areaC + tmid * dc;
            double mdist = Math.sqrt(midR * midR + midC * midC);

            double pr = areaR + ts.get(i) * dr, pc = areaC + ts.get(i) * dc;
            double PR = areaR + ts.get(i + 1) * dr, PC = areaC + ts.get(i + 1) * dc;

            if (mdist <= radius + 1e-1) {
                area += 0.5 * (pr * PC - pc * PR);
            } else {

                double angle = Math.atan2(pc, pr);
                double Angle = Math.atan2(PC, PR);
                double dangle = Angle - angle;

                while (dangle > PI) dangle -= 2 * PI;
                while (dangle < -PI) dangle += 2 * PI;
                area += 0.5 * radius * radius * dangle;
            }
        }
        return area;
    }

    static double Area(double r, double c, double R, double C) {
        if (r > R) {
            double t = r;
            r = R;
            R = t;
        }
        if (c > C) {
            double t = c;
            c = C;
            C = t;
        }

        double[][] pts = {
                {r, c}, {R, c}, {R, C}, {r, C}
        };

        double area = 0;
        int n = pts.length;
        for (int i = 0; i < n; i++) {
            double areaR = pts[i][0], areaC = pts[i][1];
            double area_r = pts[(i + 1) % n][0], area_c = pts[(i + 1) % n][1];
            area += getGardenArea(areaR, areaC, area_r, area_c);
        }
        return Math.abs(area);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            StringTokenizer st = new StringTokenizer(line);
            circleR = Double.parseDouble(st.nextToken());
            circleC = Double.parseDouble(st.nextToken());
            radius = Double.parseDouble(st.nextToken());

            String strline = br.readLine();
            if (strline == null) break;
            strline = strline.trim();
            st = new StringTokenizer(strline);
            double r = Double.parseDouble(st.nextToken());
            double c = Double.parseDouble(st.nextToken());
            double R = Double.parseDouble(st.nextToken());
            double C = Double.parseDouble(st.nextToken());

            double area = Area(r, c, R, C);
            sb.append(String.format("%.12f%n", area));
        }

        System.out.print(sb);
    }
}