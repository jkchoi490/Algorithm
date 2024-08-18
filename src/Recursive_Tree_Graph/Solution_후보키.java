import java.util.*;

class Solution_후보키 {
    static int[] combi;
    static HashSet<int[]> result;
    static ArrayList<String> list;
    static boolean[] visited;
    
    public int solution(String[][] relation) {
        int answer = 0;
        int n = relation[0].length;
        visited = new boolean[n];
        result = new HashSet<>();
        list = new ArrayList<>();
        
        for (int i = 1; i <= n; i++) {
            combi = new int[i];
            DFS(0, n, relation, 0);
        }
        
        ArrayList<String> candidateKeys = new ArrayList<>();
        for (String key : list) {
            boolean isMinimal = true;
            for (String candidate : candidateKeys) {
                if (isSubSet(candidate, key)) {
                    isMinimal = false;
                    break;
                }
            }
            if (isMinimal) {
                candidateKeys.add(key);
            }
        }

        answer = candidateKeys.size();
        
        return answer;
    }
    
    public boolean isSubSet(String key1, String key2) {
        for (char c : key1.toCharArray()) {
            if (key2.indexOf(c) == -1) {
                return false;
            }
        }
        return true;
    }

    public void isMinimum(int[] combi) {
        String str = "";
        Arrays.sort(combi);
        for (int x : combi) {
            str += String.valueOf(x);
        }
        if (!list.contains(str)) list.add(str);
    }
    
    public void isUnique(int[] combi, String[][] relation) {
        HashSet<String> set = new HashSet<>();
        boolean unique = true;
        for (int i = 0; i < relation.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < combi.length; j++) {
                sb.append(relation[i][combi[j]]).append(" ");
            }
            if (!set.contains(sb.toString())) {
                set.add(sb.toString());
            } else {
                unique = false;
                break;
            }
        }
        if (unique) {
            isMinimum(combi);
        }
    }
    
    public void DFS(int L, int n, String[][] relation, int start) {
        if (L == combi.length) {
            isUnique(combi, relation);
            return;
        } else {
            for (int i = start; i < n; i++) {
                if (!visited[i]) {
                    visited[i] = true;
                    combi[L] = i;
                    DFS(L + 1, n, relation, i + 1);
                    visited[i] = false;
                }
            }
        }
    }
}
