

public class BAEKJOON4673 {

	public static void main(String[] args) {
		boolean[] ch = new boolean[10001];
		
		for(int i = 1; i<10001; i++) {
			int n = d(i);
			if(n<10001) {
				ch[n] = true;
			}
		}
		StringBuilder sb = new StringBuilder();
		for(int i = 1; i<10001; i++) {
			if(!ch[i]) {
				sb.append(i).append("\n");
			}
		}
		System.out.println(sb);
	}

	public static int d(int num) {
		int sum = num;
		while(num!=0) {
			sum += (num % 10);
			num = num/10;
		}
		return sum;
	}

}
