package LinkedList;

import java.io.*;
import java.util.*;

//에디터
public class BAEKJOON1406 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String initial = br.readLine();
        int m = Integer.parseInt(br.readLine());

        LinkedList<Character> list = new LinkedList<>();
        for (char c : initial.toCharArray()) {
            list.add(c);
        }

        ListIterator<Character> iter = list.listIterator(list.size());

        for (int i = 0; i < m; i++) {
            String command = br.readLine();
            switch (command.charAt(0)) {
                case 'L':
                    if (iter.hasPrevious()) iter.previous();
                    break;
                case 'D':
                    if (iter.hasNext()) iter.next();
                    break;
                case 'B':
                    if (iter.hasPrevious()) {
                        iter.previous();
                        iter.remove();
                    }
                    break;
                case 'P':
                    char x = command.charAt(2);
                    iter.add(x);
                    break;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (char c : list) sb.append(c);

        bw.write(sb.toString());
        bw.flush();
    }
}
