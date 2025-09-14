package Array;

import java.io.*;
import java.util.*;

//Wrong Directions
public class BAEKJOON5895 {
    static class State {
        long x, y;
        int dir;
        State(long x, long y, int dir) {
            this.x = x; this.y = y; this.dir = dir;
        }
    }
    static class Delta {
        long dx, dy;
        int ddir;
        Delta(long dx, long dy, int ddir) {
            this.dx = dx; this.dy = dy; this.ddir = ddir;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String S = br.readLine().trim();
        int N = S.length();

        State[] prefix = new State[N+1];
        prefix[0] = new State(0, 0, 0);
        for (int i = 1; i <= N; i++) {
            prefix[i] = apply(prefix[i-1], S.charAt(i-1));
        }

        Delta[] suffix = new Delta[N+2];
        suffix[N+1] = new Delta(0, 0, 0);
        for (int i = N; i >= 1; i--) {
            Delta next = suffix[i+1];
            Delta cur = simulate(S.charAt(i-1));
            suffix[i] = combine(cur, next);
        }

        HashSet<String> set = new HashSet<>();

        for (int i = 1; i <= N; i++) {
            char orig = S.charAt(i-1);
            for (char alt : new char[]{'F','L','R'}) {
                if (alt == orig) continue;

                State pre = prefix[i-1];
                State mid = apply(pre, alt);
                Delta suf = suffix[i+1];

                State fin = attach(mid, suf);
                set.add(fin.x + "," + fin.y);
            }
        }

        System.out.println(set.size());
    }

    static State apply(State st, char c) {
        long x = st.x, y = st.y;
        int dir = st.dir;
        if (c == 'F') {
            switch(dir) {
                case 0: y++; break;
                case 1: x++; break;
                case 2: y--; break;
                case 3: x--; break;
            }
        } else if (c == 'L') {
            dir = (dir + 3) % 4;
        } else if (c == 'R') {
            dir = (dir + 1) % 4;
        }
        return new State(x, y, dir);
    }

    static Delta simulate(char c) {
        if (c == 'F') return new Delta(0, 1, 0);
        if (c == 'L') return new Delta(0, 0, 3); // -90도
        return new Delta(0, 0, 1);               // +90도
    }

    static Delta combine(Delta cur, Delta next) {

        long bx = next.dx, by = next.dy;
        long nx=0, ny=0;
        switch(cur.ddir) {
            case 0: nx = bx; ny = by; break;
            case 1: nx = by; ny = -bx; break;
            case 2: nx = -bx; ny = -by; break;
            case 3: nx = -by; ny = bx; break;
        }
        return new Delta(cur.dx + nx, cur.dy + ny, (cur.ddir + next.ddir) % 4);
    }

    static State attach(State mid, Delta suf) {
        long bx = suf.dx, by = suf.dy;
        long nx=0, ny=0;
        switch(mid.dir) {
            case 0: nx = bx; ny = by; break;
            case 1: nx = by; ny = -bx; break;
            case 2: nx = -bx; ny = -by; break;
            case 3: nx = -by; ny = bx; break;
        }
        return new State(mid.x + nx, mid.y + ny, (mid.dir + suf.ddir) % 4);
    }
}
