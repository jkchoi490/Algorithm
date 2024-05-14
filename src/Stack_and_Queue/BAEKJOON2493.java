import java.io.*;
import java.util.*;

public class BAEKJOON2493 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        Stack<Integer> stack = new Stack<>();
        Stack<Integer> indexStack = new Stack<>();
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) arr[i] = Integer.parseInt(st.nextToken());

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty()) {
                if (stack.peek() >= arr[i]) {
                    System.out.print(indexStack.peek() + " ");
                    break;
                }
                stack.pop();
                indexStack.pop();
            }
            if (stack.isEmpty()) System.out.print("0 ");
            stack.push(arr[i]);
            indexStack.push(i + 1);
        }
    }
}
