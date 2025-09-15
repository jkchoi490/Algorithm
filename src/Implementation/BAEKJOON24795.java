package Implementation;

import java.io.*;
import java.util.*;

//Saving Princess Peach
public class BAEKJOON24795 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int Y = Integer.parseInt(st.nextToken());

        List<Integer> list = new ArrayList<>();
        for(int i = 0; i<Y; i++){
            int k = Integer.parseInt(br.readLine());
            list.add(k);
        }
        StringBuilder sb = new StringBuilder();
        int cnt = 0;
        for(int j = 0; j<N; j++){
            if(list.contains(j)) {
                cnt++;
                continue;
            }
            sb.append(j).append("\n");
        }

        sb.append("Mario got ").append(cnt).append(" of the dangerous obstacles.");

        System.out.println(sb.toString());

    }
}
