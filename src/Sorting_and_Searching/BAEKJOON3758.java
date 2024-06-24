import java.io.*;
import java.util.*;

class Team{
    int id;
    HashMap<Integer, Integer> map;
    int submitTime;
    int totalScore;
    int submitCnt;

    public Team(int id) {
        this.id = id;
        this.map = new HashMap<>();
        this.submitTime = 0;
        this.totalScore = 0;
        this.submitCnt = 0;
    }
}

public class BAEKJOON3758 {

    static int T;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            Map<Integer, Team> map = new HashMap<>();
            
            for (int l = 0; l < m; l++) {
                st = new StringTokenizer(br.readLine());
                int i = Integer.parseInt(st.nextToken()); 
                int j = Integer.parseInt(st.nextToken()); 
                int s = Integer.parseInt(st.nextToken()); 

                map.putIfAbsent(i, new Team(i));
                Team team = map.get(i);

           
                team.map.put(j, Math.max(team.map.getOrDefault(j, 0), s));
                team.submitTime = l + 1;
                team.submitCnt++;

                
                team.totalScore = 0;
                for (int score : team.map.values()) {
                    team.totalScore += score;
                }
            }

            ArrayList<Team> list = new ArrayList<>(map.values());

            Collections.sort(list, new Comparator<Team>() {
                @Override
                public int compare(Team o1, Team o2) {
                    if (o2.totalScore == o1.totalScore) {
                        if (o1.submitCnt == o2.submitCnt) {
                            return o1.submitTime - o2.submitTime;
                        }
                        return o1.submitCnt - o2.submitCnt;
                    }
                    return o2.totalScore - o1.totalScore;
                }
            });

            for (int idx = 0; idx < list.size(); idx++) {
                if (list.get(idx).id == t) {
                    System.out.println(idx + 1);
                    break;
                }
            }
        }
    }
}
