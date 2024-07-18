import java.util.*;

class Solution_영어끝말잇기 {
    public int[] solution(int n, String[] words) {
       
        int[] answer = new int[2];
        HashSet<String> set = new HashSet<>();
        String lastWord = words[0];

        for (int i = 0; i < words.length; i++) {
            int idx = (i % n) + 1;
            int num = (i / n) + 1;
            String word = words[i];
            
            boolean isContain = set.contains(word);
            if (isContain || (i > 0 && lastWord.charAt(lastWord.length() - 1) != word.charAt(0))) {
                return new int[]{idx, num};
            }

            set.add(word);
            lastWord = word;
        }

        return answer;
    }
}
