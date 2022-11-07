import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

class Solution {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		System.out.println(Solution2(str));

	}

	private static String Solution2(String str) {
		String dest ="";
		char[] arr = str.toCharArray();
		System.out.println(Arrays.toString(arr));
		
		
		return dest;
	}
	
	

}
