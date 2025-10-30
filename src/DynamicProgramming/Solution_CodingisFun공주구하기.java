package DynamicProgramming;

import java.io.*;
import java.util.*;

// 공주 구하기
public class Solution_CodingisFun공주구하기 {
    /**
     * 섬의 정보를 담는 클래스.
     * dist: 시작점으로부터의 거리
     * jump: 최대로 점프할 수 있는 거리
     * canCarry: 공주를 업고 갈 수 있는지
     */
    static class Island {
        int dist, jump, canCarry;
        Island(int d, int j, int c) {
            dist = d; jump = j; canCarry = c;
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        Island[] islands = new Island[N];

        for (int i = 0; i < N; i++) {

            StringTokenizer st = new StringTokenizer(br.readLine());
            int dist = Integer.parseInt(st.nextToken());
            int jump = Integer.parseInt(st.nextToken());
            int canCarry = Integer.parseInt(st.nextToken());
            islands[i] = new Island(dist, jump, canCarry);
        }

        // 결과값을 1000으로 나눈 나머지로 유지하기 위한 MOD 값
        final int MOD = 1000;

        // dpForward[i]: 섬 0에서 섬 i까지 (공주 없이) 가는 경로의 수
        int[] dpForward = new int[N];
        // dpBackward[i]: 섬 N-1에서 섬 i까지 (공주 업고) 오는 경로의 수
        int[] dpBackward = new int[N];

        // 1️. 유시(Yushi)의 이동 (섬 0 → 섬 N-1, 공주 없음)
        // Dynamic Programming: 섬 0에서 시작하여 순차적으로 경로의 수를 계산
        // dpForward[i]는 i 이전의 모든 섬 j에서 i로 점프 가능한 경로의 수의 합
        dpForward[0] = 1; // 섬 0에서 섬 0으로 가는 경로의 수는 1 (시작점)
        for (int i = 0; i < N; i++) {
            // 현재 섬 i에서 다음 섬 j로 점프 가능한지 확인
            for (int j = i + 1; j < N; j++) {
                // 다음 섬 j까지의 거리가 현재 섬 i의 점프 가능 거리 이내인지 확인
                if (islands[j].dist - islands[i].dist <= islands[i].jump) {
                    // 섬 j에 도달하는 경로의 수에 섬 i를 경유하는 경로의 수를 더함
                    dpForward[j] = (dpForward[j] + dpForward[i]) % MOD;
                } else break; // 섬의 거리가 정렬되어 있으므로, 한 번 점프 불가능하면 이후 섬도 불가능
            }
        }

        // 2️. 후퍼(Hooper)의 이동 (섬 N-1 → 섬 0, 공주 업고)
        // Dynamic Programming: 섬 N-1에서 시작하여 역순으로 경로의 수를 계산
        // dpBackward[i]는 섬 i에서 N-1로 도착하는 것이 아니라, 섬 N-1에서 i까지 도착하는 경로의 수
        // 계산의 편의를 위해 N-1부터 0까지 역순으로 '출발'하며 경로를 갱신
        dpBackward[N - 1] = 1; // 섬 N-1에서 섬 N-1로 오는 경로의 수는 1 (시작점)
        for (int i = N - 1; i >= 0; i--) {
            // 후퍼가 공주를 업고 출발 섬 i를 밟을 수 없는 경우 (canCarry == 0)는 경로 계산에서 제외
            if (islands[i].canCarry == 0) continue;

            // 현재 섬 i에서 이전 섬 j로 (역순으로) 점프 가능한지 확인
            for (int j = i - 1; j >= 0; j--) {
                // 이전 섬 j까지의 거리가 현재 섬 i의 점프 가능 거리 이내인지 확인
                // '후퍼의 도착 섬 j'와 '후퍼의 출발 섬 i' 사이의 거리 비교
                if (islands[i].dist - islands[j].dist <= islands[i].jump) {
                    // 이전 섬 j가 후퍼가 공주를 업고 갈 수 있는 섬인지 (canCarry == 1) 확인
                    if (islands[j].canCarry == 1) {
                        // 섬 j에 도달하는 경로의 수에 섬 i를 경유하는 경로의 수를 더함
                        dpBackward[j] = (dpBackward[j] + dpBackward[i]) % MOD;
                    }
                } else break; // 섬의 거리가 정렬되어 있으므로, 한 번 점프 불가능하면 이후 섬도 불가능
            }
        }

        // 3️. 결과 계산: 두 경로가 만나는 모든 섬 i에서 유시 경로 수와 후퍼 경로 수의 곱을 합산
        int ans = 0;
        for (int i = 0; i < N; i++) {
            // 각 섬 i에서 유시가 도착하고 후퍼가 출발하는 경로의 곱을 전체 합에 추가
            // (a + b) % m = ((a % m) + (b % m)) % m 이므로, 중간 결과도 MOD 연산
            // 곱셈 결과도 MOD 연산: (dpForward[i] * dpBackward[i]) % MOD
            ans = (ans + (dpForward[i] * dpBackward[i]) % MOD) % MOD;
        }

        System.out.println(ans);
    }
}