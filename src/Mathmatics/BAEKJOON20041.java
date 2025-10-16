package Mathmatics;

import java.io.*;
import java.util.*;

//Escaping
public class BAEKJOON20041 {
    static class Pair {
        long x, y;
        Pair(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        final long MAX = 0x7FFFFFFFL;
        final long MIN = 0x80000000L;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        List<Pair> police = new ArrayList<>();

        long[] vmin = {MAX, MAX};
        long[] vmax = {MIN, MIN};

        // 경찰 좌표 입력
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            long x = Long.parseLong(st.nextToken());
            long y = Long.parseLong(st.nextToken());
            police.add(new Pair(x, y));

            vmin[0] = Math.min(vmin[0], x);
            vmin[1] = Math.min(vmin[1], y);
            vmax[0] = Math.max(vmax[0], x);
            vmax[1] = Math.max(vmax[1], y);
        }

        // 도둑 좌표 입력
        st = new StringTokenizer(br.readLine());
        long Sx = Long.parseLong(st.nextToken());
        long Sy = Long.parseLong(st.nextToken());

        int block = 0;

        // 도둑이 이미 경찰의 최소/최대 범위 밖에 있는 경우
        if (Sx <= vmin[0] || Sx >= vmax[0] || Sy <= vmin[1] || Sy >= vmax[1]) {
            block = 0;
        } else {
            List<Pair> dst = new ArrayList<>();

            // 도둑이 도망칠 수 있는 4방향 목표 지점
            dst.add(new Pair(vmin[0], Sy)); // 왼쪽
            dst.add(new Pair(vmax[0], Sy)); // 오른쪽
            dst.add(new Pair(Sx, vmin[1])); // 아래쪽
            dst.add(new Pair(Sx, vmax[1])); // 위쪽

            // 각 목표지점에 대해 경찰이 더 빨리 도달 가능한지 확인
            for (Pair dpos : dst) {
                long dx = dpos.x;
                long dy = dpos.y;
                long Sd = Math.abs(Sx - dx) + Math.abs(Sy - dy); // 도둑 거리

                boolean blocked = false;
                for (Pair p : police) {
                    long d = Math.abs(p.x - dx) + Math.abs(p.y - dy);
                    if (d <= Sd) {
                        blocked = true;
                        break;
                    }
                }

                if (blocked) block++;
            }
        }

        // 출력
        if (block < 4)
            System.out.println("YES");
        else
            System.out.println("NO");
    }
}
