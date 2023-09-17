import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

class olympiad {
    int country;
    int student;
    int score;

    public olympiad(int country, int student, int score) {
        this.country = country;
        this.student = student;
        this.score = score;
    }
}

public class BAEKJOON2535{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
        int n = Integer.parseInt(br.readLine()); 
        olympiad[] results = new olympiad[n]; 
        int[] countrys = new int[101]; 
        
        for (int i = 0; i < n; i++) { 
            StringTokenizer st = new StringTokenizer(br.readLine());
            int country = Integer.parseInt(st.nextToken()); 
            int student = Integer.parseInt(st.nextToken()); 
            int score = Integer.parseInt(st.nextToken());
            results[i] = new olympiad(country, student, score); 
        }

        Arrays.sort(results, new Comparator<olympiad>() { 
            @Override
            public int compare(olympiad o1, olympiad o2) {
                return o2.score - o1.score;
            }
        });
        int cnt= 0; 
        int print = 0; 
        while (print <3) {  
            int country = results[cnt].country; 
            countrys[country]++;
            if (countrys[country] <3) { 
                System.out.println(results[cnt].country+" "+results[cnt].student);
                print++; 
            }
            cnt++;
        }
    }
}
