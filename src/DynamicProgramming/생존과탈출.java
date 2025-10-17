package DynamicProgramming;

import java.io.*;
import java.util.*;

//생존과 탈출
public class 생존과탈출 { //계속해서 풀기
    static class Box implements Comparable<Box> {
        int t, f, h; // t: 시간, f: 음식 HP, h: 쌓는 높이

        Box(int t, int f, int h) {
            this.t = t;
            this.f = f;
            this.h = h;
        }

        // 상자 도착 시간 t 순으로 정렬
        @Override
        public int compareTo(Box o) {
            return Integer.compare(this.t, o.t);
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int D = Integer.parseInt(st.nextToken());
        int G = Integer.parseInt(st.nextToken());

        Box[] boxes = new Box[G];
        for (int i = 0; i < G; i++) {
            st = new StringTokenizer(br.readLine());
            boxes[i] = new Box(
                    Integer.parseInt(st.nextToken()), // t
                    Integer.parseInt(st.nextToken()), // f
                    Integer.parseInt(st.nextToken())  // h
            );
        }

        // 1. 상자를 도착 시간 t 순으로 정렬
        Arrays.sort(boxes);

        // 2. DP 배열 및 초기값 설정
        // MAX_HP: 초기 10 + 모든 음식 HP (G*30) + 안전 마진
        int MAX_HP = 10 + 30 * G + D;

        // dp[i][hp]: i번째 상자까지 처리했을 때, HP가 hp일 때 도달 가능한 최대 높이
        int[][] dp = new int[G + 1][MAX_HP + 1];
        for (int i = 0; i <= G; i++) Arrays.fill(dp[i], -1);

        // 시작 상태: 0번째 상자 처리 전, HP 10, 높이 0
        dp[0][10] = 0;

        int prevTime = 0;
        int maxSurviveTime = 10;

        // 3. DP 전이 시작
        for (int i = 0; i < G; i++) {
            int dt = boxes[i].t - prevTime;

            for (int hp = 1; hp <= MAX_HP; hp++) {
                if (dp[i][hp] == -1) continue;
                int currentHP = hp - dt;

                if (currentHP <= 0) {

                    maxSurviveTime = Math.max(maxSurviveTime, prevTime + hp);
                    continue;
                }


                maxSurviveTime = Math.max(maxSurviveTime, boxes[i].t + currentHP);

                dp[i + 1][currentHP] = Math.max(dp[i + 1][currentHP], dp[i][hp]);


                int fillHp = Math.min(MAX_HP, currentHP + boxes[i].f);

                dp[i + 1][fillHp] = Math.max(dp[i + 1][fillHp], dp[i][hp]);


                int newHeight = dp[i][hp] + boxes[i].h;

                // 탈출 조건 만족 시 최소 시간 출력 후 종료
                if (newHeight >= D) {
                    System.out.println(boxes[i].t);
                    return;
                }

                dp[i + 1][currentHP] = Math.max(dp[i + 1][currentHP], newHeight);
            }
            prevTime = boxes[i].t;
        }


        int lastTime = (G == 0) ? 0 : boxes[G - 1].t;
        for (int hp = 1; hp <= MAX_HP; hp++) {
            if (dp[G][hp] >= 0) {

                maxSurviveTime = Math.max(maxSurviveTime, lastTime + hp);
            }
        }

        System.out.println(maxSurviveTime);
    }
}