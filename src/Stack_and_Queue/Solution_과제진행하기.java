package Stack_and_Queue;

import java.util.*;

class Solution_과제진행하기 { //프로그래머스 - 과제 진행하기
/*
 문제 풀이 
- 과제 리스트 생성 - 과제 시작 시간을 분으로 변환하고 시작시간순으로 오름차순 정렬한다

- 과제 관리를 위한 스택 생성 - 스택에 진행 중인 과제 리스트들을 담고 새 작업을 순회하면서 
현재 과제를 종료할지, 새 과제를 진행할지 비교한다

- 과제 리스트를 순회하면서 현재 작업 중인 과제와 새 과제를 비교
 * 스택이 비어있는 경우 : 새 과제 진행을 위해 스택에 push
 * 스택이 비어있지 않은 경우 : 
      현재 시간 (stack에서 peek 한 값의 시작 시간 + 과제 진행 시간)이 새 과제 시간보다 같거나 빠른 경우
  	-> 현재 과제 종료 및 남아 있는 작업이 있다면 재귀를 사용해서 pop 
      현재 시간이 새 과제 시간보다 늦은 경우 
    ->새 작업 진행을 위해 스택에 새 작업을 push, 현재 과제에서 진행한 시간만큼 빼준다

 */
		
	public String[] solution(String[][] plans) {
	    Assignment[] arr = new Assignment[plans.length];
	    for (int i = 0; i < plans.length; i++) {
	        Assignment assignment = new Assignment(plans[i][0], plans[i][1], plans[i][2]);
	        arr[i] = assignment;
	    }

	    Arrays.sort(arr, (o1, o2) -> {
	        return o1.start - o2.start;
	    });


	    Stack<Assignment> stack = new Stack<>();  // 진행 중인 과제
	    List<String> ans = new ArrayList<>();

	    int curTime = -1;                         // 현재 시간 초기화

	    for (int i = 0; i < arr.length; i++) {
	        /* 진행 중인 과제가 없는 경우 */
	        if (stack.isEmpty()) {
	            stack.push(arr[i]);
	            continue;
	        }

	        /* 진행 중인 과제와 새로운 과제가 있는 경우 */
	        Assignment curA = stack.peek();   // 진행중 과제
	        Assignment newA = arr[i];         // 새로운 과제

	        // 새로운 과제의 시작시간과 진행중 과제의 종료 시간 비교
	        curTime = curA.start;

	        // 현재 과제 시작 시간 + 작업 시간이 새로운 과제 시작 시간 보다 같거나 작은 경우
	        if (curTime + curA.time <= newA.start) {
	            recursivePop(stack, newA, curTime, ans);
	        } else {
	            // 현재 작업 중단하고 작업한 시간 갱신 및 새 작업 시작
	            curA.time -= newA.start - curTime;
	        }
	        stack.push(newA);
	    }

	    /* 새로운 과제가 없는 경우 */
	    while (!stack.isEmpty()) {
	        ans.add(stack.pop().name);
	    }
	    return ans.toArray(new String[0]);
	}

	public void recursivePop(Stack<Assignment> stack, Assignment newA, int curTime, List<String> ans) {
	    if (stack.isEmpty()) {
	        return;
	    }
	    Assignment curA = stack.peek();   // 진행중 과제
	    if (curTime + curA.time <= newA.start) {
	        ans.add(stack.pop().name);
	        recursivePop(stack, newA, curTime + curA.time, ans);
	    } else {
	        curA.time -= newA.start - curTime;
	    }
	}



	static class Assignment {
	    private String name;
	    private int start;
	    private int time;

	    public Assignment(String name, String start, String time) {
	        this.name = name;
	        this.start = timeToMinute(start);
	        this.time = Integer.parseInt(time);
	    }

	    public int timeToMinute(String start) {
	        String[] arr = start.split(":");
	        int h = Integer.parseInt(arr[0]) * 60;
	        int m = Integer.parseInt(arr[1]);
	        return h + m;
	    }
	}
}