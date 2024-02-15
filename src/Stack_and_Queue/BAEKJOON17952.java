import java.io.*;
import java.util.*;

class Task{
	int score, time;
	public Task(int score, int time) {
		this.score = score;
		this.time = time;
	}
}
public class BAEKJOON17952 {

	public static void main(String[] args) throws IOException{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Stack<Task> stack = new Stack<>();
		int N = Integer.parseInt(br.readLine());
		int sum = 0;
		for(int i = 1; i<=N; i++) {
			String[] str = br.readLine().split(" ");
			if(Integer.parseInt(str[0])==1) {
				int A = Integer.parseInt(str[1]);
				int T = Integer.parseInt(str[2]);
				stack.push(new Task(A, T-1));
				if(stack.peek().time == 0 && !stack.isEmpty()) {
					Task finish = stack.pop();
					sum += finish.score;
				}
			}
			else if(Integer.parseInt(str[0])==0) {
				if(!stack.isEmpty()) {
				Task task = stack.pop();
				task.time -= 1;
				stack.push(new Task(task.score, task.time));
				if(stack.peek().time == 0 && !stack.isEmpty()) {
					Task finish = stack.pop();
					sum += finish.score;
					}
				}
			}
		
		}
		System.out.println(sum);
		

	}

}
