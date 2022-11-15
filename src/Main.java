import java.util.*;

class Main {	
	public int[] solution(String s, char t){
		int[] answer=new int[s.length()];
		int p=1000;
		for(int i=0; i<s.length(); i++){ //왼쪽으로부터의 t로부터 떨어진 거리
			if(s.charAt(i)==t){
				p=0;
				answer[i]=p;
			}
			else{
				p++;
				answer[i]=p;
			}
		}
		p=1000;
		for(int i=s.length()-1; i>=0; i--){ //오른쪽에 있는 t로부터의 거리 구하기
			if(s.charAt(i)==t) p=0;
			else{
				p++;
				answer[i]=Math.min(answer[i], p);//기존값과 p값중 작은 거로 넣기
			}
		}
		return answer;
	}

	public static void main(String[] args){
		Main T = new Main();
		Scanner kb = new Scanner(System.in);
		String str=kb.next();
		char c=kb.next().charAt(0);
		for(int x : T.solution(str, c)){
			System.out.print(x+" ");
		}
	}
}