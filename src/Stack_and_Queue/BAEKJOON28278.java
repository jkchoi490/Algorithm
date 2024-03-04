import java.io.*;
import java.util.*;

public class BAEKJOON28278 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Stack<Integer> stack = new Stack<>();
		int n = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i<n; i++) {
			String[] s = br.readLine().split(" ");
			
			String command = s[0];
			if(command.equals("1")) stack.push(Integer.parseInt(s[1]));
			else if(command.equals("2")) {
					if(!stack.isEmpty()) {
						sb.append(stack.pop()).append("\n");
					}
					else sb.append(-1).append("\n");
			} 
			else if(command.equals("3")) sb.append(stack.size()).append("\n");
			else if(command.equals("4")) {
					if(stack.isEmpty()) sb.append(1).append("\n");
					else sb.append(0).append("\n");
			} 
			else if(command.equals("5")) {
					if(!stack.isEmpty()) sb.append(stack.peek()).append("\n");
					else sb.append(-1).append("\n");
				}
				
			}
			System.out.println(sb);
			
		}
	}
