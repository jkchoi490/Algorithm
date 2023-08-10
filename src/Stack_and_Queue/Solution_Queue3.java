package Stack_and_Queue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Person{
	int id;
	int priority;
	public Person(int id, int priority) {
		this.id=id;
		this.priority=priority;
	}
}

public class Solution_Queue3 { //응급실

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		int[] arr = new int[N];
		for(int i = 0; i<N; i++) arr[i] = Integer.parseInt(st.nextToken());
		System.out.println(solution(N,M,arr));
	}

	public static int solution(int n, int m, int[] arr){
		int answer=0;
		Queue<Person> q =new LinkedList<>();
		for(int i=0; i<n; i++) q.offer(new Person(i, arr[i]));
		
		while(!q.isEmpty()){
			Person person =q.poll(); //큐에서 꺼냄
			
			for(Person x : q){
				if(x.priority > person.priority){ //위험도가 더 높은 사람이 존재하면
					q.offer(person); //큐에 꺼낸 person 객체를 다시 add
					person=null; //person 객체를 null처리 하고 멈춤
					break;
				}
			}
			if(person!=null){ // 꺼낸 사람이 위험도가 제일 높으면
				answer++; // 진료받음
				if(person.id==m) return answer; //m번째 사람이 진료받는 순서 리턴
			}
		}
		return answer;
	}

}
