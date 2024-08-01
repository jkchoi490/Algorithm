import java.util.*;
class Point {
    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
class Load {
    Point front, back;

    public Load(Point front, Point back) {
        this.front = front;
        this.back = back;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Load load = (Load) o;
        return (front.equals(load.front) && back.equals(load.back)) ||
               (front.equals(load.back) && back.equals(load.front));
    }

    @Override
    public int hashCode() {
        return front.hashCode() + back.hashCode();
    }
}
class Solution_방문길이 {
    public int solution(String dirs) {
        int answer = 0;
        ArrayList<Load> load = new ArrayList<>();
        int x = 0, y = 0, nx = 0, ny = 0;
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0}; // U D R L
         HashSet<Load> set = new HashSet<>();

        for(char dir : dirs.toCharArray()){
            Point now = new Point(x, y);
            if(dir == 'U'){
                nx = x + dx[0];
                ny = y + dy[0];
                if(nx >= -5 && nx<=5 && ny>=-5 && ny<=5){
                     Point next = new Point(nx, ny);
                set.add(new Load(now, next));
                x = nx;
                y = ny;
                }
               
            }else if(dir == 'D'){
                nx = x + dx[1];
                ny = y + dy[1];
                if(nx >= -5 && nx<=5 && ny>=-5 && ny<=5){
                Point next = new Point(nx, ny);
                set.add(new Load(now, next));
                x = nx;
                y = ny;
                }
            }else if(dir == 'R'){
                 nx = x + dx[2];
                ny = y + dy[2];
                if(nx >= -5 && nx<=5 && ny>=-5 && ny<=5){
                Point next = new Point(nx, ny);
                set.add(new Load(now, next));
                x = nx;
                y = ny;
                }
                
            }else if(dir == 'L'){
                nx = x + dx[3];
                ny = y + dy[3];
                if(nx >= -5 && nx<=5 && ny>=-5 && ny<=5){
                Point next = new Point(nx, ny);
                set.add(new Load(now, next));
                x = nx;
                y = ny;
                }
            }
        }
        
        answer = set.size();
        return answer;
    }
}
