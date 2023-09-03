package String;

import java.util.*;
import java.io.*;


public class Solution13{  // 근무 시간 
    static int answer=0;
    public static void main(String args[]) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        for(int i = 0; i<5; i++){
            st = new StringTokenizer(br.readLine());
            String start = st.nextToken();
            String end = st.nextToken();
            int start_hour = Integer.parseInt(start.substring(0, 2));
            int start_min = Integer.parseInt(start.substring(3, 5));
            int end_hour = Integer.parseInt(end.substring(0, 2));
            int end_min = Integer.parseInt(end.substring(3, 5));
            answer += (end_hour - start_hour)*60 + (end_min-start_min);
        }
        System.out.println(answer);
        
    }
}