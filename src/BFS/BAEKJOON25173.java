package BFS;
import java.util.*;
import java.io.*;

//용감한 아리의 동굴 대탈출
public class BAEKJOON25173 {

    static int R, C;
    static int[][] board;
    static int[][] tmp;

    static class Character {
        int x, y, dir, active, mana;
        Character(int x, int y, int dir, int active, int mana) {
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.active = active;
            this.mana = mana;
        }
        Character() {
            this(-1, -1, -1, -1, -1);
        }
        Character(Character c) {
            this(c.x, c.y, c.dir, c.active, c.mana);
        }
    }

    static Character ARI = new Character();
    static Character Will_Boss = new Character();
    static Character BOSS = new Character();

    static class Monster {
        int x, y, active;
        Monster(int x, int y, int active) {
            this.x = x;
            this.y = y;
            this.active = active;
        }
    }

    static Queue<Monster> Q = new ArrayDeque<>();
    static int[][] visited;

    // 0상 1우 2하 3좌
    static int[] dx = {-1,0,1,0};
    static int[] dy = {0,1,0,-1};

    static void input(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        board = new int[R][C];
        tmp = new int[R][C];

        for(int i=0;i<R;i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<C;j++){
                board[i][j] = Integer.parseInt(st.nextToken());
                if(board[i][j] == 2){
                    ARI.x = i; ARI.y = j; board[i][j] = 0;
                }
                if(board[i][j] == 3){
                    BOSS.x = i; BOSS.y = j; board[i][j] = 0;
                }
            }
        }

        st = new StringTokenizer(br.readLine());
        ARI.active = Integer.parseInt(st.nextToken());
        ARI.mana = Integer.parseInt(st.nextToken());
        BOSS.active = Integer.parseInt(st.nextToken());
        BOSS.mana = Integer.parseInt(st.nextToken());

        // 초기 방향
        int dir = -1;
        if (ARI.x < BOSS.x && ARI.y == BOSS.y) dir = 0;
        else if (ARI.x == BOSS.x && ARI.y > BOSS.y) dir = 1;
        else if (ARI.x > BOSS.x && ARI.y == BOSS.y) dir = 2;
        else if (ARI.x == BOSS.x && ARI.y < BOSS.y) dir = 3;

        ARI.dir = BOSS.dir = dir;
    }

    static void ari_attack() {
        BOSS.active -= ARI.mana;
    }

    static void ari_move() {
        Will_Boss = new Character(); // 초기화

        int x = ARI.x;
        int y = ARI.y;
        int nd = ARI.dir;
        boolean moved = false;

        for(int p=0;p<4;p++){
            nd = (ARI.dir + p > 3) ? ARI.dir + p - 4 : ARI.dir + p;
            int nx = x + dx[nd];
            int ny = y + dy[nd];
            int active = ARI.active - p;
            if(nx<0 || nx>=R || ny<0 || ny>=C) continue;
            if(board[nx][ny]==1) continue;
            if(nx == BOSS.x && ny == BOSS.y) continue;

            ARI.x = nx; ARI.y = ny; ARI.dir = nd; ARI.active = active;
            moved = true;
            break;
        }

        if(moved){
            Will_Boss = new Character(x,y,nd,0,0);
        }else{
            Will_Boss = new Character(-1,-1,-1,-1,-1);
            ARI.active -=4;
            ARI.dir = nd;
        }
    }

    static void BFS(int x, int y){
        Q.clear();
        visited = new int[R][C];
        Q.add(new Monster(x,y,BOSS.mana));
        visited[x][y] = 1;

        while(!Q.isEmpty()){
            Monster data = Q.poll();

            if(data.active==0){
                if(data.x!=ARI.x || data.y!=ARI.y) return;
            }

            if(data.x==ARI.x && data.y==ARI.y){
                ARI.active -= data.active;
                return;
            }

            for(int p=0;p<4;p++){
                int nx = data.x + dx[p];
                int ny = data.y + dy[p];

                if(nx<0 || nx>=R || ny<0 || ny>=C) continue;
                if(visited[nx][ny]==1) continue;
                if(nx==BOSS.x && ny==BOSS.y) continue;
                if(board[nx][ny]==1) continue;

                Q.add(new Monster(nx,ny,data.active-1));
                visited[nx][ny]=1;
            }
        }
    }

    static void boss_attack(){
        int x = BOSS.x;
        int y = BOSS.y;
        int dir = BOSS.dir;
        boolean find = false;
        int fx=-1, fy=-1;

        int Nsize = 1;
        int cann = 1;

        while(true){
            if(cann==R*C) break;
            for(int i=1;i<=2;i++){
                for(int j=1;j<=Nsize;j++){
                    int nx = x + dx[dir];
                    int ny = y + dy[dir];
                    if(nx>=0 && nx<R && ny>=0 && ny<C) cann++;
                    if(inRange(nx,ny) && board[nx][ny]==1){
                        fx = nx; fy = ny; find = true; break;
                    }
                    x = nx; y = ny;
                }
                if(find) break;
                dir = (dir+1)%4;
            }
            if(find) break;
            Nsize++;
        }

        if(find) BFS(fx,fy);
    }

    static void boss_move(){
        if(Will_Boss.x==-1) return;
        BOSS.x = Will_Boss.x;
        BOSS.y = Will_Boss.y;
        BOSS.dir = Will_Boss.dir;
    }

    static boolean simul(){
        while(true){
            ari_attack();
            if(ARI.active <=0) return false;
            if(BOSS.active <=0) return true;

            ari_move();
            if(ARI.active <=0) return false;
            if(BOSS.active <=0) return true;

            boss_attack();
            if(ARI.active <=0) return false;
            if(BOSS.active <=0) return true;

            boss_move();
        }
    }

    static boolean inRange(int x, int y){ return x>=0 && x<R && y>=0 && y<C; }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        input(br);
        if(simul()) System.out.println("VICTORY!");
        else System.out.println("CAVELIFE...");
    }
}
