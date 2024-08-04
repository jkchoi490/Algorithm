import java.util.*;

class File{
    String name;
    String head;
    int number, cnt;
    public File(String name, String head, int number, int cnt){
        this.name=name;
        this.head=head;
        this.number=number;
        this.cnt=cnt;
    }
}
class Solution_파일명정렬 {
    
    public ArrayList<String> solution(String[] files) {
        ArrayList<String> answer = new ArrayList<>();
        ArrayList<File> list = new ArrayList<>();
        
        for(int i = 0; i<files.length; i++){
            String file = files[i];
            boolean firstNum = false;
            boolean isLastNum = false;
             String head = "";
                String number_str = "";
            int firstNumidx = 0, lastNumidx = 0;
            int number = 0;int count = 0;
            for(int j = 1; j<file.length(); j++){
    
                if(!firstNum && Character.isDigit(file.charAt(j))){
                    firstNum = true;
                    firstNumidx = j;
                    
                }
                
                if(count <= 4 && firstNum && j >= firstNumidx && Character.isDigit(file.charAt(j))&&
                  !isLastNum){
                    number_str += file.charAt(j);
                    count++;
                }
                if(Character.isDigit(file.charAt(j-1)) && !Character.isDigit(file.charAt(j))){
                    isLastNum = true;
                }
            }
             head = file.substring(0,firstNumidx);  
            number = Integer.parseInt(number_str);
            list.add(new File(file, head.toLowerCase(), number, i));
        }
        
        Collections.sort(list, new Comparator<File>(){
           
            @Override
            public int compare(File o1, File o2){
                if(o1.head.equals(o2.head)){
                    if(o1.number == o2.number){
                        return o1.cnt - o2.cnt;
                    }
                    return o1.number - o2.number;
                }
                return o1.head.compareTo(o2.head);
            }
        });
        
        for(File f : list){
            answer.add(f.name);
        }
        return answer;
    }
}