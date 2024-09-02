import java.util.*;

class Solution_가장큰수 { 

    public static String solution(int[] numbers) {
        String answer = "";
        String[] nums = new String[numbers.length];

        for(int i = 0; i<numbers.length; i++){
            nums[i] = String.valueOf(numbers[i]);
        }
       
        Arrays.sort(nums, new Comparator<String>(){
           
            @Override
            public int compare(String o1, String o2){
                return (o2+o1).compareTo(o1+o2);
            }
        });
        answer = String.join("", nums);
        if(answer.startsWith("0")){
            return "0";
        }
      
        return answer;
    }

}