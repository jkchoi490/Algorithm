import java.util.*;
import java.io.*;

public class BAEKJOON19594 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int t = Integer.parseInt(br.readLine());
        for (int i = 0; i < t; i++) {
            int n = Integer.parseInt(br.readLine());
            int[] h = new int[n];
            int[] d = new int[n];

            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                h[j] = Integer.parseInt(st.nextToken());
            }

            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                d[j] = Integer.parseInt(st.nextToken());
            }

            System.out.println(solve(n, h, d));
        }
    }

    public static long solve(int n, int[] hours, int[] deadlines) {
        List<AbstractMap.SimpleEntry<Integer, Integer>> dhList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            dhList.add(new AbstractMap.SimpleEntry<>(deadlines[i], hours[i]));
        }
        
        dhList.sort(Comparator.comparingInt(AbstractMap.SimpleEntry::getKey));

        int s = 0;
        List<Integer> lateList = new ArrayList<>();
        for (AbstractMap.SimpleEntry<Integer, Integer> pair : dhList) {
            int d = pair.getKey();
            int h = pair.getValue();
            s += h;
            lateList.add(Math.max(0, s - d));
        }

        List<Integer> iMaxLateList = new ArrayList<>();
        int tempMaxLate = 0;
        for (int l : lateList) {
            tempMaxLate = Math.max(l, tempMaxLate);
            iMaxLateList.add(tempMaxLate);
        }

        long answer = Math.max(iMaxLateList.get(iMaxLateList.size() - 1) - (dhList.get(0).getValue() - 1), 0);
        for (int i = 1; i < n; i++) {
            long tmp = Math.max(iMaxLateList.get(i - 1), Math.max(iMaxLateList.get(iMaxLateList.size() - 1) - (dhList.get(i).getValue() - 1), 0));
            answer = Math.min(answer, tmp);
        }

        return answer;
    }
}