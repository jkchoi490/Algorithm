import java.util.*;

class Solution_거리두기확인하기 {
    static char[][] map;
    static boolean flag = true;
    static int[] dx = {-1, 0, 1, 0, 1, 1,-1,-1,2,0,-2,0};
    static int[] dy = {0, 1, 0, -1, 1,-1,1, -1,0,2,0,-2};
    public static void step1(char[][] map, int x, int y){
        for(int d = 0; d<4; d++){
            int nx = x + dx[d];
            int ny = y + dy[d];
            if(nx>=0 && nx<5 && ny>=0 && ny<5 && map[nx][ny] == 'P'){
                flag = false;
                break;
            }
        }
         for(int d = 4; d<8; d++){
            int nx = x + dx[d];
            int ny = y + dy[d];
            if(nx>=0 && nx<5 && ny>=0 && ny<5 && map[nx][ny] == 'P'){
               for(int i = Math.min(x, nx); i<=Math.max(x,nx); i++){
                   for(int j = Math.min(y,ny); j <= Math.max(y, ny); j++){
                       if(map[i][j]=='O'){
                           flag = false;
                           break;
                       }
                   }
               }
            }
        }
        for(int d = 8; d<12; d++){
            int nx = x + dx[d];
            int ny = y + dy[d];
            if(nx>=0 && nx<5 && ny>=0 && ny<5 && map[nx][ny] == 'P'){
               for(int i = Math.min(x, nx); i<=Math.max(x,nx); i++){
                   for(int j = Math.min(y,ny); j <= Math.max(y, ny); j++){
                       if(map[i][j]=='O'){
                           flag = false;
                           break;
                       }
                   }
               }
            }
        }
    }
    
    public ArrayList<Integer> solution(String[][] places){
        ArrayList<Integer> answer = new ArrayList<>();
        ArrayList<int[]> plist = new ArrayList<>();
        map = new char[5][5];
        for(int i = 0; i<places.length; i++){
            
           String[] str = places[i];
            for(int j = 0; j<str.length; j++){
                char[] arr = str[j].toCharArray();
                map[j] = arr;
                }
                int cnt = 0;
              for(int k = 0; k<5; k++){
                    for(int l = 0; l<5; l++){
                        if(map[k][l] == 'P'){
                            cnt++;
                            step1(map, k, l);
                    
                        }
                    }
            }
            if(cnt == 0) answer.add(1);
            else if (flag) answer.add(1);
            else answer.add(0);
            flag = true;
        }
        
        
        return answer;
    }
}