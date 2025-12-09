package String;

import java.io.*;
import java.util.*;

//Sphere Online Judge - THANKS
public class THANKS {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int T = Integer.parseInt(st.nextToken());
        int myGender = Integer.parseInt(st.nextToken());

        StringBuilder sb = new StringBuilder();

        while (T-- > 0) {
            st = new StringTokenizer(br.readLine());
            String name = st.nextToken();
            int gender = Integer.parseInt(st.nextToken());

            if (gender != myGender) {
                sb.append("Welcome ").append(name).append(", ;)\n");
            } else {
                sb.append("Welcome ").append(name).append("\n");
            }
        }

        System.out.print(sb.toString());
    }
}
