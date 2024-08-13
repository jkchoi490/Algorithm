import java.util.*;

class Music{
    int idx;
    String name;
    int time;
    String part;
    public Music(int idx, String name, int time, String part){
        this.idx=idx;
        this.name=name;
        this.time=time;
        this.part=part;
    }
}
class Solution_방금그곡 {
    public int getTime(String start, String end){
        String[] s = start.split(":");
        int sh = Integer.parseInt(s[0]);
        int sm = Integer.parseInt(s[1]);
        String[] e = end.split(":");
        int eh = Integer.parseInt(e[0]);
        int em = Integer.parseInt(e[1]);
        return (eh*60+em)-(sh*60+sm);
    }
    public String getPart(String part, int time){
        String result = "";
        int idx = 0;
        while(time-- > 0){
            result += part.charAt(idx);
            idx++;
            if(idx == part.length()) idx = 0;
        }
        return result;
    }
    
    public boolean isIn(String p, String m){
        return p.contains(m);
    }
    public String solution(String m, String[] musicinfos){
        String answer = "";
        ArrayList<Music> list = new ArrayList<>();
        int maxTime = -1;
        for(int i = 0; i<musicinfos.length; i++){
            String[] infos = musicinfos[i].split(",");
            int time = getTime(infos[0], infos[1]);
            String name = infos[2];
            String part = infos[3];
            m = m.replace("C#","I").replace("D#", "O").replace("B#","S").replace("F#","P").replace("G#","Z").replace("A#", "X");
            part = part.replace("C#","I").replace("D#", "O").replace("B#","S").replace("F#","P").replace("G#","Z").replace("A#", "X");
            
            String p = getPart(part, time);
            //System.out.println(part+" "+m+" "+p);
            if(p.length() >= m.length()){
               
                if(isIn(p, m)){
                    //System.out.println(p+" "+name);
                    list.add(new Music(i, name, time, p));
                }
            }
        
            
            if(list.size() == 0) answer = "(None)";
            else{
            Collections.sort(list, new Comparator<Music>(){
               @Override
                public int compare(Music o1, Music o2){
                    if(o2.time == o1.time){
                        return o1.idx - o2.idx;
                    }
                    return o2.time-o1.time;
                }
            });
            String ans = list.get(0).name;
            answer = ans;
            }
            
        }
        
        return answer;
    }
}