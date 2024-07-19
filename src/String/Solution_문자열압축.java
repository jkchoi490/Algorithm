import java.util.*;

class Solution_문자열압축 {
	
	public static void main(String[] args) {
		System.out.println(solution("aabbaccc"));
		System.out.println(solution("ababcdcdababcdcd"));
		System.out.println(solution("abcabcdede"));
		System.out.println(solution("abcabcabcabcdededededede"));
		System.out.println(solution("xababcdcdababcdcd"));

	}
	
    public static int solution(String s) {
        int minLength = s.length();
        
        for (int i = 1; i <= s.length() / 2; i++) {
            String str = solve(s, i);
            minLength = Math.min(minLength, str.length());
        }
        
        return minLength;
    }
    
    private static String solve(String s, int length) {
        StringBuilder result = new StringBuilder();
        int count = 1;
      
        String prev = s.substring(0, length);
        
        for (int i = length; i <= s.length(); i += length) {
            String next;

            if (i + length > s.length()) {
                next = s.substring(i);
            } else {
                next = s.substring(i, i + length);
            }
            
            if (prev.equals(next)) {
                count++;
            } else {
                if (count > 1) {
                    result.append(count);
                }
                result.append(prev);
                prev = next;
                count = 1;
            }
        }
        
        if (count > 1) {
            result.append(count);
        }
        result.append(prev);
        
        return result.toString();
    }
}
