import java.io.*;
import java.util.*;

public class BAEKJOON13414 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int K = Integer.parseInt(st.nextToken());
        int L = Integer.parseInt(st.nextToken());
        
       
        HashMap<String, Integer> studentMap = new HashMap<>();
        for (int i = 0; i < L; i++) {
            String student = br.readLine();
            studentMap.put(student, i);
        }
        
     
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(studentMap.entrySet());
        Collections.sort(entryList, Map.Entry.comparingByValue());
        
        int count = 0;
        for (Map.Entry<String, Integer> entry : entryList) {
            if (count >= K) break;
            System.out.println(entry.getKey());
            count++;
        }
    }
}
