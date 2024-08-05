import java.util.*;

class Point{
    int x, y;
    public Point(int x, int y){
        this.x=x;
        this.y=y;
    }
}

class Solution_프렌즈4블록 {
   
    public static char[][] arr;
    public static int answer;
    public static boolean flag = true;
    public int solution(int m, int n, String[] board) {
        answer = 0;
        int ans = 0;
        arr = new char[m][n];
        for(int i = 0; i<m; i++){
            String str = board[i];
            for(int j = 0; j<n; j++){
                arr[i][j] = str.charAt(j);
            }
        }
        
        while(true){
            find4blocks(m,n);
            dropBlocks(m,n);
            if(flag == false) break;
        
        }
        
        for(int i = 0; i<m; i++){
            for(int j = 0; j<n; j++){
                if(arr[i][j]==' ') ans++;
            }
            //System.out.println();
        }
        
        return ans;
        
    }
    public void dropBlocks(int m, int n){
        
        while(true){
            int cnt = 0;
            for(int i = 0; i<m-1; i++){
                for(int j = 0; j<n; j++){
                    if(arr[i][j]!=' ' && arr[i+1][j]==' '){
                        char tmp = arr[i][j];
                        arr[i][j] = arr[i+1][j];
                        arr[i+1][j] = tmp;
                        cnt = 1;
                    }
                }
             }
            if(cnt==0) break;
        }
        
    }
    public void find4blocks(int m, int n){
        ArrayList<Point> list = new ArrayList<>();
        for(int i = 0; i<m-1; i++){
            for(int j = 0; j<n-1; j++){
                char one = arr[i][j];
                char two = arr[i][j+1];
                char three = arr[i+1][j];
                char four = arr[i+1][j+1];
                if(one == two && two==three && three==four && one!=' '){
                    list.add(new Point(i,j));
                    list.add(new Point(i,j+1));
                    list.add(new Point(i+1,j));
                    list.add(new Point(i+1,j+1));                    
                }
                
            }
        }
        
    
        if(list.size() > 0 ) {
             for(Point p : list){
                arr[p.x][p.y] = ' ';
        
             }
            answer += list.size();
            flag = true;
        }
        else flag = false;
        
        
    }
}