package BFS;

import java.util.*;
import java.io.*;

//AtCoder - Reflection
public class Reflection {


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            String[] parts = br.readLine().split(" ");
            long a = Long.parseLong(parts[0]);
            long b = Long.parseLong(parts[1]);
            long c = Long.parseLong(parts[2]);

            System.out.println(solve(a, b, c));
        }
    }

    static long solve(long a, long b, long c) {
        // Special case: all three stones at the same position
        if (a == b && b == c) {
            return 1;
        }

        // Calculate GCD of distances
        long d1 = b - a;
        long d2 = c - b;
        long g = gcd(d1, d2);

        if (g == 0) {
            // Two stones at same position
            return 2;
        }

        long p1 = 0;
        long p2 = d1 / g;
        long p3 = (d1 + d2) / g;

        Set<State> visited = new HashSet<>();
        Queue<State> queue = new LinkedList<>();

        State start = new State(p1, p2, p3);
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            State curr = queue.poll();
            long[] pos = curr.toArray();
            Arrays.sort(pos);

            long newA = 2 * pos[1] - pos[0];
            State next1 = new State(newA, pos[1], pos[2]);
            if (visited.add(next1) && isValid(next1)) {
                queue.add(next1);
            }

            long newC = 2 * pos[1] - pos[2];
            State next2 = new State(pos[0], pos[1], newC);
            if (visited.add(next2) && isValid(next2)) {
                queue.add(next2);
            }
        }

        return visited.size();
    }

    static boolean isValid(State s) {
        long[] pos = s.toArray();
        for (long p : pos) {
            if (Math.abs(p) > 2000000) {
                return false;
            }
        }
        return true;
    }

    static long gcd(long a, long b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    static class State {
        long a, b, c;

        State(long a, long b, long c) {
            long[] arr = {a, b, c};
            Arrays.sort(arr);
            this.a = arr[0];
            this.b = arr[1];
            this.c = arr[2];
        }

        long[] toArray() {
            return new long[]{a, b, c};
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof State)) return false;
            State s = (State) o;
            return a == s.a && b == s.b && c == s.c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b, c);
        }
    }
}