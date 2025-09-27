package Implementation;

import java.io.*;
import java.util.*;

//Codewars - Help Your Granny!
public class HelpYourGranny {
    /**
     * friends: 방문 순서대로 된 친구 이름 배열
     * friendTowns: 친구-마을 쌍 배열, 예: { {"A1","X1"}, {"A2","X2"}, ... }
     * distances: 마을 이름 -> 거리 (X0은 보통 0)
     *
     * 반환: 총 이동 거리 (반올림하여 int로 반환)
     */
    public static int solution(String[] friends, String[][] friendTowns, Map<String, Double> distances) {
        if (friends == null || friendTowns == null || distances == null) return 0;

        // 친구 -> 마을 맵 생성
        Map<String, String> friendToTown = new HashMap<>();
        for (String[] pair : friendTowns) {
            if (pair == null || pair.length < 2) continue;
            String friend = pair[0];
            String town = pair[1];
            friendToTown.put(friend, town);
        }

        // 방문 가능한 친구의 마을에 대한 거리 리스트(go)에 거리값 저장
        List<Double> go = new ArrayList<>();
        for (String friend : friends) {
            if (friend == null) continue;
            String town = friendToTown.get(friend);
            if (town == null) continue; // 모르면 skip
            Double dist = distances.get(town);
            if (dist == null) continue; // 거리가 없으면 skip
            go.add(dist);
        }

        if (go.isEmpty()) return 0;

        double res = 0.0;

        res += go.get(0);
        res += go.get(go.size() - 1);

        for (int i = 0; i < go.size() - 1; i++) {
            double a = go.get(i);
            double b = go.get(i + 1);
            if (b < a) {
                double tmp = a; a = b; b = tmp;
            }

            double diff = b * b - a * a;
            if (diff < 0 && diff > -1e-12) diff = 0;
            if (diff < 0) {
                continue;
            }
            res += Math.sqrt(diff);
        }

        return (int)Math.round(res);
    }

    // 테스트 예시
    public static void main(String[] args) {
        String[] friends = {"A2", "A1", "A3"};
        String[][] fTowns = { {"A1","X1"}, {"A2","X2"}, {"A3","X3"} };
        Map<String, Double> dist = new HashMap<>();
        dist.put("X0", 0.0);
        dist.put("X1", 2.0);
        dist.put("X2", 1.0);
        dist.put("X3", 3.0);

        int ans = solution(friends, fTowns, dist);
        System.out.println(ans); // dev.to 예시와 비슷한 방식으로 계산하면 8 출력(예시 입력/거리 설정에 따라 달라짐)
    }
}
