package String;

public class Solution_간단한식계산하기 { //프로그래머스 - 간단한 식 계산하기

	    public int solution(String binomial) {
	        int answer = 0;
	        String[] arr = binomial.split(" ");
	        int a = Integer.parseInt(arr[0]);
	        int b = Integer.parseInt(arr[2]);
	        //for(String x : arr)
	        //    System.out.println(x);
	        if(arr[1].charAt(0)=='+') answer = a+b;
	        else if(arr[1].charAt(0)=='-') answer = a-b;
	        else answer = a*b;
	        
	        
	        return answer;
	    }
	}


