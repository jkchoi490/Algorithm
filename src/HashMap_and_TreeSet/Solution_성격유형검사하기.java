import java.util.*;

class Solution_성격유형검사하기 {
    public String solution(String[] survey, int[] choices) {
        String answer = "";
        int n = choices.length;
        HashMap<Character, Integer> map = new HashMap<>();
        for(int i = 0; i<n; i++){
            int choice = choices[i]-1;
            if(choice>=0 && choice<=2) 
            {
                if(map.containsKey(survey[i].charAt(0))){
                    map.put(survey[i].charAt(0), map.get(survey[i].charAt(0))+3-choice);
                }
                else map.put(survey[i].charAt(0), 3-choice);
            
            }
            else if(choice >=4 && choice<=6) {
                if(map.containsKey(survey[i].charAt(1))){
                    map.put(survey[i].charAt(1), map.get(survey[i].charAt(1))+choice-3);
                }
                else map.put(survey[i].charAt(1), choice-3); 
            }
        }
        System.out.println(map);
        char[] types = {'R','T','C','F','J','M','A','N'};
        for(char type : types){
            if(map.containsKey(type)) continue;
            else map.put(type, 0);
        }
        System.out.println(map);
        int idx = 0;
        char[] ans = new char[4];
        for(char key : map.keySet()){
            
            if(key == 'R'){
                if(map.get(key) < map.get('T')) ans[0] = 'T';
                else if(map.get(key) > map.get('T')) ans[0] = key;
                else if(map.get(key)==map.get('T')){
                    ArrayList<Character> list = new ArrayList<>();
                    list.add('R');
                    list.add('T');
                    Collections.sort(list);
                    ans[0] = list.get(0);
                }
            }
            
            if(key == 'A'){
                if(map.get(key) < map.get('N'))  ans[3] = 'N';
                else if(map.get(key) > map.get('N'))  ans[3] = key;
                else if(map.get(key)==map.get('N')){
                    ArrayList<Character> list = new ArrayList<>();
                    list.add('A');
                    list.add('N');
                    Collections.sort(list);
                     ans[3] = list.get(0);
                }
            }
            
            
            if(key == 'C'){
                if(map.get(key) < map.get('F')) ans[1] = 'F';
                else if(map.get(key) > map.get('F')) ans[1] = key;
                else if(map.get(key)==map.get('F')){
                    ArrayList<Character> list = new ArrayList<>();
                    list.add('C');
                    list.add('F');
                    Collections.sort(list);
                    ans[1]=list.get(0);
                }
            }
            
            if(key == 'J'){
                if(map.get(key) < map.get('M')) ans[2] = 'M';
                else if(map.get(key) > map.get('M')) ans[2]= key;
                else if(map.get(key)==map.get('M')){
                    ArrayList<Character> list = new ArrayList<>();
                    list.add('J');
                    list.add('M');
                    Collections.sort(list);
                    ans[2] = list.get(0);
                }
            }
        }
        for(char a : ans) answer += a;
        return answer;
    }
}