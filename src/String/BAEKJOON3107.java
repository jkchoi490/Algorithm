import java.io.*;
import java.util.*;

public class BAEKJOON3107 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		
		String[] parts = str.split("::");
		
		String[] leftParts = parts[0].split(":");
		String[] rightParts = parts.length > 1 ? parts[1].split(":") : new String[0];
		
		String[] result = new String[8];
		int index = 0;
		
		for(String part : leftParts) {
			if(!part.isEmpty()) {
				result[index++] = part;
			}
		}
		
		int rightStart = 8 - rightParts.length;
		
		for(String part : rightParts) {
			if(!part.isEmpty()) {
				result[rightStart++] = part;
			}
		}
		for(int i = index; i<8-rightParts.length; i++) {
			result[i] = "0000";
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i<8; i++) {
			if(result[i] == null) {
				result[i] = "0000";
			}
			sb.append(String.format("%4s", result[i]).replace(' ', '0'));
            if (i < 7) {
                sb.append(':');
            }
		}
		System.out.println(sb.toString());
	}

}
