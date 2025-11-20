package String;

import java.io.*;
import java.util.*;

//Kattis - Cutting Strings
public class CuttingStrings  {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder out = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {

            StringTokenizer st = new StringTokenizer(br.readLine());
            int K = Integer.parseInt(st.nextToken());
            String S = st.nextToken();

            int n = S.length();

            // 각 문자별 등장 위치 저장
            List<Integer>[] pos = new ArrayList[26];
            for (int i = 0; i < 26; i++) pos[i] = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                pos[S.charAt(i) - 'a'].add(i);
            }

            StringBuilder ans = new StringBuilder();

            int last = -1;  // 마지막으로 선택한 문자 위치
            int used = 0;   // 사용한 삭제 구간 수

            while (true) {
                boolean picked = false;

                // Z부터 A까지 탐색
                for (int c = 25; c >= 0; c--) {
                    List<Integer> list = pos[c];
                    int idx = upperBound(list, last);

                    while (idx < list.size()) {
                        int i = list.get(idx);

                        int needed = used;
                        if (i > last + 1) needed++;
                        if (needed <= K) {
                            // 선택 가능
                            ans.append((char)(c + 'a'));
                            used = needed;
                            last = i;
                            picked = true;
                            break;
                        }
                        idx++;
                    }

                    if (picked) break;
                }

                if (!picked) break;
            }

            out.append(ans).append("\n");
        }

        System.out.print(out);
    }

    // upperBound: list에서 last보다 큰 첫 번째 위치
    static int upperBound(List<Integer> list, int last) {
        int l = 0, r = list.size();
        while (l < r) {
            int m = (l + r) / 2;
            if (list.get(m) <= last) l = m + 1;
            else r = m;
        }
        return l;
    }
}
