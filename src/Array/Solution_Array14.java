package Array;

import java.util.ArrayList;
import java.util.Arrays;

public class Solution_Array14 { // 프로그래머스 - 전국대회 선발고사

	public int solution(int[] rank, boolean[] attendance) {
        int[][] rankIndex = new int[rank.length][2]; //rank 배열의 각 요소와 해당 요소의 인덱스를 저장하기 위한 2차원 배열

        for (int i = 0; i < rank.length; i++) {
            rankIndex[i][0] = rank[i];
            rankIndex[i][1] = i;
        }
        
        Arrays.sort(rankIndex, (a, b) -> a[0] - b[0]);
        ArrayList<Integer> result = new ArrayList<>();
        
        for (int i = 0; i < attendance.length; i++) {
            if (attendance[rankIndex[i][1]]) {
                result.add(rankIndex[i][1]);
            }
            if (result.size() == 3) {
                break;
            }
        }
        
        return 10000 * result.get(0) + 100 * result.get(1) + result.get(2);
    }

}
