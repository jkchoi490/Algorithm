import java.io.*;
import java.util.*;

public class BAEKJOON1235 {
    
    static int n;
    static String[] students;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        students = new String[n];
        for(int i = 0; i < n; i++) {
            String str = br.readLine();
            students[i] = str;
        }
        solve();
    }

    public static void solve() {
        int cnt = 0;
        while (true) {
            HashSet<String> set = new HashSet<>();
            boolean isUnique = true;

            for (int i = 0; i < n; i++) {
                String num = students[i].substring(students[i].length() - 1 - cnt);
                if (set.contains(num)) {
                    isUnique = false;
                    break;
                }
                set.add(num);
            }

            if (isUnique) {
                System.out.println(cnt + 1);
                return;
            }
            cnt++;
        }
    }
}
