package String;

import java.io.*;
import java.util.*;

//CodeForces - Love Story
public class LoveStory {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String codeforces = "codeforces";

        int t = Integer.parseInt(br.readLine());

        for (int i = 0; i < t; i++) {
            String s = br.readLine();

            int count = 0;
            for (int j = 0; j < 10; j++) {
                if (s.charAt(j) != codeforces.charAt(j)) {
                    count++;
                }
            }

            System.out.println(count);
        }

        br.close();
    }
}