import java.util.*;

class Solution_뉴스클러스터링{
    public int solution(String str1, String str2) {
        int answer = 0;
        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();

        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();

        for (int i = 0; i < str1.length() - 1; i++) {
            char c1 = str1.charAt(i);
            char c2 = str1.charAt(i + 1);
            if (Character.isAlphabetic(c1) && Character.isAlphabetic(c2)) {
                list1.add("" + c1 + c2);
            }
        }

        for (int i = 0; i < str2.length() - 1; i++) {
            char c1 = str2.charAt(i);
            char c2 = str2.charAt(i + 1);
            if (Character.isAlphabetic(c1) && Character.isAlphabetic(c2)) {
                list2.add("" + c1 + c2);
            }
        }

        Map<String, Integer> map1 = new HashMap<>();
        Map<String, Integer> map2 = new HashMap<>();

        for (String s : list1) {
            map1.put(s, map1.getOrDefault(s, 0) + 1);
        }

        for (String s : list2) {
            map2.put(s, map2.getOrDefault(s, 0) + 1);
        }

        HashMap<String, Integer> nMap = new HashMap<>();
        HashMap<String, Integer> uMap = new HashMap<>();
        
        for(String key1 : map1.keySet()){
            for(String key2 : map2.keySet()){
                if(key1.equals(key2)){
                    int min = Math.min(map1.get(key1), map2.get(key2));
                    nMap.put(key1, min);
                }
            }
        }
       
        for(String key : map1.keySet()){
            uMap.put(key, map1.get(key));
        }
        
        for(String k2 : map2.keySet()){
            if(uMap.containsKey(k2)){
                uMap.put(k2, Math.max(uMap.get(k2), map2.get(k2)));
            }
            else uMap.put(k2, map2.get(k2));
        }

        int n = 0, u = 0;
        for(String k : nMap.keySet()){
            n += nMap.get(k);
        }
        
        for(String k : uMap.keySet()){
            u += uMap.get(k);
        }
        
        double value = u == 0 ? 1 : ( (double) n / (double)u);

        answer = (int)(value*65536);
        return answer;
    }
}