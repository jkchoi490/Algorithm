class Solution_키패드누르기 {

	public String solution(int[] numbers, String hand) {
        String answer = "";
        int[][] keypad = { {1, 2, 3},
                            {4, 5, 6},
                            {7, 8, 9},
                            {10, 0, 11}};
        int lx = 3, ly = 0, rx = 3, ry = 2;
        
        for(int num : numbers){
            for(int i = 0; i<4; i++){
                for(int j = 0; j<3; j++){
                    if(num == keypad[i][j]){
                        if(num == 1 || num == 4 || num == 7) {
                        answer += 'L';
                            lx = i;
                            ly = j;
                            
                        }
                        else if(num == 3 || num == 6 || num ==9){
                             answer += 'R';
                            rx = i;
                            ry = j;
                        }
                        else{ 
                       
                        int l_dis = (Math.abs(lx - i)+Math.abs(ly-j));
                        int r_dis = (Math.abs(rx- i)+Math.abs(ry-j));
                        if(l_dis < r_dis){
                            answer += 'L';
                            lx = i;
                            ly = j;
                        }
                        else if(l_dis > r_dis){
                            answer += 'R';
                            rx = i;
                            ry = j;
                        }
                        else {
                            if(hand.equals("left")){
                                answer += 'L';
                                lx = i;
                                ly = j;
                            }
                            else if(hand.equals("right")){
                                answer +='R';
                                rx = i;
                                ry = j;
                            }
                        }
                    }
                    }     
                }
            }
                
        }
        
        return answer;
    }
}