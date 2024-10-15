package Implementation;

class Solution_부족한금액계산하기 {
    public long solution(int price, int money, int count) {
        long answer = -1;
        long total_price = 0;
        for(int cnt = 1; cnt<=count; cnt++){
            total_price += (price * cnt);
        }
        if(total_price > money) answer = total_price - money;
        else answer = 0;
        return answer;
    }
}