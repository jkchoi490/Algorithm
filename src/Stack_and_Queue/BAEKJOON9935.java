import java.io.*;
import java.util.*;

public class BAEKJOON9935 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        String bomb = br.readLine();
        int bombLength = bomb.length();
        
        Stack<Character> stack = new Stack<>();
        
        for (char c : input.toCharArray()) {
            stack.push(c);
            if (stack.size() >= bombLength) {
                boolean isBomb = true;
                for (int i = 0; i < bombLength; i++) {
                    if (stack.get(stack.size() - bombLength + i) != bomb.charAt(i)) {
                        isBomb = false;
                        break;
                    }
                }
                if (isBomb) {
                    for (int i = 0; i < bombLength; i++) {
                        stack.pop();
                    }
                }
            }
        }
        
        StringBuilder result = new StringBuilder();
        for (char c : stack) {
            result.append(c);
        }
        
        if (result.length() == 0) {
            System.out.println("FRULA");
        } else {
            System.out.println(result.toString());
        }
    }
}
