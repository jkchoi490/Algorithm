
class Solution_행렬테두리회전하기 {
    
    static int[][] arr;
    
    public int[] solution(int rows, int columns, int[][] queries) {
        int[] answer = new int[queries.length];
        arr = new int[rows][columns];
        

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                arr[i][j] = i * columns + j + 1;
            }
        }
        

        for(int i = 0; i < queries.length; i++){
            answer[i] = rotate(queries[i]);
        }
        
        return answer;
    }
    
    public int rotate(int[] query){
        int x1 = query[0] - 1;
        int y1 = query[1] - 1;
        int x2 = query[2] - 1;
        int y2 = query[3] - 1;
        
        int tmp = arr[x1][y1];
        int minVal = tmp;
        
        for(int i = x1; i < x2; i++) {
            arr[i][y1] = arr[i + 1][y1];
            minVal = Math.min(minVal, arr[i][y1]);
        }
        
        for(int i = y1; i < y2; i++) {
            arr[x2][i] = arr[x2][i + 1];
            minVal = Math.min(minVal, arr[x2][i]);
        }
        
        for(int i = x2; i > x1; i--) {
            arr[i][y2] = arr[i - 1][y2];
            minVal = Math.min(minVal, arr[i][y2]);
        }
        
        for(int i = y2; i > y1; i--) {
            arr[x1][i] = arr[x1][i - 1];
            minVal = Math.min(minVal, arr[x1][i]);
        }
        
        arr[x1][y1 + 1] = tmp;
        
        return minVal;
    }
}
