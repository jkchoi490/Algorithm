import java.io.*;
import java.util.*;

class Player{
	int level;
	String name;
	public Player(int level, String name) {
		this.level=level;
		this.name=name;
	}
}
public class BAEKJOON20006 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int p = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        ArrayList<ArrayList<Player>> rooms = new ArrayList<>();
        ArrayList<Player> players = new ArrayList<>();
        for (int i = 0; i < p; i++) {
        	st = new StringTokenizer(br.readLine());
            int level = Integer.parseInt(st.nextToken());
            String name = st.nextToken();
            players.add(new Player(level, name));
        }

        for (Player player : players) {
            boolean flag = false;
            for(ArrayList<Player> room : rooms) {
                if (room.size() < m && room.get(0).level - 10 <= player.level && player.level <= room.get(0).level + 10) {
                    room.add(player);
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                ArrayList<Player> new_room = new ArrayList<>();
                new_room.add(new Player(player.level, player.name));
                rooms.add(new_room);
            }
        }

        for (ArrayList<Player> room : rooms) {
            room.sort((a, b) -> a.name.compareTo(b.name));
            if (room.size() == m) {
                System.out.println("Started!");
            } else {
                System.out.println("Waiting!");
            }
            for (Player player : room) {
                System.out.println(player.level+" "+player.name);
            }
        }
    }
}

