package Implementation;

import java.io.*;
import java.util.*;

//Escape
public class Escape {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());


        double vp = Double.parseDouble(st.nextToken());
        double vd = Double.parseDouble(st.nextToken());
        double t = Double.parseDouble(st.nextToken());
        double f = Double.parseDouble(st.nextToken());
        double c = Double.parseDouble(st.nextToken());

        if (vp >= vd) {
            System.out.println(0);
            return;
        }


        double princessPos = vp * t;
        int jewels = 0;


        while (true) {

            double timeToCatch = princessPos / (vd - vp);
            double catchPos = princessPos + vp * timeToCatch;

            if (catchPos >= c) break;
            jewels++;

            double timeBack = catchPos / vd + f;
            princessPos = catchPos + vp * timeBack;

            if (princessPos >= c) break;
        }

        System.out.println(jewels);
    }
}