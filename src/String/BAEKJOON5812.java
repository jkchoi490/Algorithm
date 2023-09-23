import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BAEKJOON5812  {
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int L = Integer.parseInt(br.readLine()); // 크래이피쉬가 글을 쓸 시간 (1 ≤ N ≤ 100)
      
        StringTokenizer st;
        for (int i = 0; i < L; i++) {
        	st = new StringTokenizer(br.readLine());
        	String command = st.nextToken();
        	String question = st.nextToken();
        	init();
        	if(command.equals("T"))  TypeLetter(question);
        	else if(command.equals("U")) UndoCommands(question);
        	else if(command.equals("P")) TypeLetter(question);
        }

    }
	private static void init() {
		// TODO Auto-generated method stub
		
	}
	private static void UndoCommands(String question) {
		// TODO Auto-generated method stub
		
	}

	private static void TypeLetter(String question) {
		// TODO Auto-generated method stub
		
	}
}