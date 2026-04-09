package Implementation;

import java.io.*;
import java.util.*;

// Virtual Judge - Heal
public class Heal {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //문제에서 주어진 입력값들
        StringTokenizer st = new StringTokenizer(br.readLine());
        long h = Long.parseLong(st.nextToken());
        long d = Long.parseLong(st.nextToken());
        long t = Long.parseLong(st.nextToken());


        st = new StringTokenizer(br.readLine());
        long H = Long.parseLong(st.nextToken());
        long D = Long.parseLong(st.nextToken());
        long T = Long.parseLong(st.nextToken());


        st = new StringTokenizer(br.readLine());
        long hp = Long.parseLong(st.nextToken());
        long tp = Long.parseLong(st.nextToken());
        long time = 0;

        while (true) {

            if ((time - 1) % t == 0) {
                H -= d;
            }

            if (H <= 0) {
                break;
            }

            if ((time - 1) % T == 0) {
                h -= D;
            }

            if (h <= 0) {
                break;
            }

            if ((time - 1) % tp == 0) {
                h += hp;
            }

            time++;


            if (time > 630) {
                System.out.println("Heal");
                break;
            }
        }
    }
}