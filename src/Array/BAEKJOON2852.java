import java.io.*;
import java.util.*;

public class BAEKJOON2852 {
	
	static int[] score, times, result;
	static int now;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		score = new int[3];
		times = new int[3];
		result = new int[48*60+1];
		now = 0;
		StringTokenizer st;
		for(int i = 0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			int team = Integer.parseInt(st.nextToken());
			int time = getTime(st.nextToken());
			setScore(team, time);
		}
		setScore(0, 60*48);
		getScore();
	}

	private static void getScore() {
		int one = 0, two = 0;
		for(int i = 0; i<result.length; i++) {
			if(result[i] == 1) {
				one++;
			}else if(result[i] == 2) {
				two++;
			}
		}
		System.out.println(changeTime(one));
		System.out.println(changeTime(two));
	}

	private static String changeTime(int time) {
		int hour = time/60;
		int min = time%60;
		StringBuilder sb = new StringBuilder();
		if(hour < 10 && min < 10) {
			sb.append("0").append(hour).append(":").append("0").append(min);
		}
		else if(hour < 10 && min >= 10) {
			sb.append("0").append(hour).append(":").append(min);
		}
		else if(hour >= 10 && min < 10) {
			sb.append(hour).append(":").append("0").append(min);
		}
		else if(hour >= 10 && min >= 10) {
			sb.append(hour).append(":").append(min);
		}
		return sb.toString();
	}

	private static void setScore(int team, int time) {
		
		
		for(int t = now; t<time; t++) {
			if(score[1]>score[2]) {
				result[t] = 1;
				now = time;
			}else if(score[1] < score[2]) {
				result[t] = 2;
				now = time;
			}else {
				result[t] = 0;
				now = time;
			}
		}
		score[team] += 1;
		
	}

	private static int getTime(String time) {
		int h = Integer.parseInt(time.split(":")[0]);
		int m = Integer.parseInt(time.split(":")[1]);
		return h*60+m;
	}

}
