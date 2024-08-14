import java.util.*;

class Solution_불량사용자 {
    static HashSet<HashSet<String>> set;
    static ArrayList<String> isin;
    static boolean[] visited;
    
    public int solution(String[] user_id, String[] banned_id) {
        set = new HashSet<>();
        isin = new ArrayList<>();
        HashMap<Integer, ArrayList<String>> map = new HashMap<>();
        
        for (int i = 0; i < banned_id.length; i++) {
            String banned = banned_id[i];
            ArrayList<String> list = new ArrayList<>();
            map.put(i, list);
            int banned_len = banned.length();
            for (String user : user_id) {
                int user_len = user.length();
                if (user_len == banned_len) {
                    boolean isMatch = true;
                    for (int j = 0; j < user_len; j++) {
                        if (banned.charAt(j) != '*' && user.charAt(j) != banned.charAt(j)) {
                            isMatch = false;
                            break;
                        }
                    }
                    if (isMatch) {
                        list.add(user);
                    }
                }
            }
        }

        visited = new boolean[user_id.length];
        DFS(map, banned_id, 0);
        
        return set.size();
    }

    public void DFS(HashMap<Integer, ArrayList<String>> map, String[] banned_id, int depth) {
        if (depth == banned_id.length) {
            HashSet<String> combination = new HashSet<>(isin);
            set.add(combination);
           // System.out.println(set);
            return;
        }

        for (String user : map.get(depth)) {
            if (!isin.contains(user)) {
                isin.add(user);
                DFS(map, banned_id, depth + 1);
                isin.remove(user);
            }
        }
    }
    
}