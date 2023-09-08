import java.util.*;
import java.io.*;

public class Solution_업무처리 { // softeer 업무처리

	static int H; // 조직도의 높이
	static int K; // 말단에 대기하는 업무의 개수
	static int R; // 업무가 진행되는 날짜 수
	static Worker[] tree; 
	static int answer;
	public static void main(String args[]) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		StringTokenizer st = new StringTokenizer(br.readLine()," ");
		H = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());

		tree = new Worker[((int)Math.pow(2,H))*2];
		init(1,0);
		for(int i=(int)Math.pow(2,H); i<(int)Math.pow(2,H+1); i++){
			st = new StringTokenizer(br.readLine()," ");
			for(int k=0;k<K;k++){
				tree[i].job.offer(Integer.parseInt(st.nextToken()));
			}
		}

		answer = 0;
		for(int r=1;r<=R;r++){
			workProcess(1, r, 0);
		}

		bw.write(String.valueOf(answer));
		bw.flush();
		bw.close();
	}

	static void workProcess(int idx, int r, int depth){
		//말단 노드의 depth는 H이기 때문에 H를 넘어가면 재귀를 종료
		if(depth > H) return;
		
		//트리는 루트를 idx=1로 선언하고 왼쪽 노드는 idx*2, 오른쪽 노드는 idx*2+1로 선언
		Worker worker = tree[idx];
		
		//말단 노드이고 작업이 존재하는 경우
		if(depth == H && !worker.job.isEmpty()){
			int job = worker.job.poll();
			//상사 노드의 왼쪽에 존재하는 말단 노드는 상사 노드의 왼쪽job에 작업을 올림
			if(idx%2==0) tree[idx/2].leftJob.offer(job);
			//오른쪽에 존재한다면 오른쪽job에 작업을 올림
			else tree[idx/2].rightJob.offer(job);
		}
		//상사 노드이고 짝수 날짜인 경우
       	//짝수 날짜인데 오른쪽 작업이 존재하지 않는다면 해당 상사는 작업을 하지 않음
		else if(depth < H && r%2 == 0 && !worker.rightJob.isEmpty()){
			int job = worker.rightJob.poll();
			//상사 노드가 루트라면 해당 작업의 합을 갱신
			if(idx==1) answer += job;
			//루트가 아닌 상사 노드인 경우
			else{
				//해당 노드가 상사의 왼쪽 노드라면 상사의왼쪽job에, 오른쪽 노드라면 상사의 오른쪽job에 작업을 올림
				if(idx%2==0) tree[idx/2].leftJob.offer(job);
				else tree[idx/2].rightJob.offer(job);
			}	
		}
		//상사 노드이고 홀수 날짜인 경우
        //오른쪽 작업이 존재하지 않는다면 작업하지 않음
		else if(depth < H && r%2 == 1 && !worker.leftJob.isEmpty()){
			int job = worker.leftJob.poll();
			 //해당 노드가 루트라면 작업의 합을 갱신
			if(idx==1) answer += job;
			//루트가 아닌 상사 노드인 경우 상사의 상사 노드에 날짜에 따라 작업을 올려준다
			else{
				if(idx%2==0) tree[idx/2].leftJob.offer(job);
				else tree[idx/2].rightJob.offer(job);
			}
		}
		
		//이전에 해당 노드로 올라온 작업을 처리했다면 부하 직원을 탐색하여 반복
		workProcess(idx*2, r, depth+1);
		workProcess(idx*2+1, r, depth+1);
	}

	//상사노드라면 왼쪽, 오른쪽 부하 직원에서 올라온 작업을 저장할 큐를 정의
    //말단노드라면 가지고있는 작업을 저장할 큐를 정의
	static void init(int idx, int depth){
		if(depth > H) return;

		Worker newWorker = new Worker(depth);
		tree[idx] = newWorker;

		init(idx*2, depth+1);
		init(idx*2+1, depth+1);
	}

	static class Worker{
		int depth;
		Queue<Integer> leftJob;
		Queue<Integer> rightJob;
		Queue<Integer> job;

		public Worker(int depth){
			this.depth = depth;
			initJob();
		}
		
		//상사노드라면 왼쪽, 오른쪽 부하 직원에서 올라온 작업을 저장할 큐를 정의
        //말단노드라면 가지고있는 작업을 저장할 큐를 정의
		public void initJob(){
			if(depth == H){
				job = new LinkedList<>();
			}else{
				leftJob = new LinkedList<>();
				rightJob = new LinkedList<>();
			}
		}
	}
}