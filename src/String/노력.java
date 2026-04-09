package String;

import java.io.*;
import java.util.*;

// Dovelet - 노력
// Dovelet의 노력 문제의 작성했던 솔루션에 주석을 작성하였습니다!
// 간단히 코드에 대한 설명만 나열하였습니다!
public class 노력 {

    public static void main(String[] args) throws Exception {
        // 입력을 빠르게 읽기 위해 BufferedReader 사용
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 읽어올 줄의 개수 N
        int N = Integer.parseInt(br.readLine());

        // 추출된 숫자들을 저장할 리스트
        List<String> numbers = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            int len = line.length();

            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < len; j++) {
                char c = line.charAt(j);
                // 문자가 숫자인 경우 StringBuilder에 추가
                if (Character.isDigit(c)) {
                    sb.append(c);
                } else {
                    // 숫자가 아닌 문자일 때 기존에 쌓인 숫자가 있다면 리스트에 추가
                    if (sb.length() > 0) {
                        // 불필요한 앞자리 수를 제거한 후 저장
                        numbers.add(normalize(sb.toString()));
                        sb.setLength(0);
                    }
                }
            }

            if (sb.length() > 0) {
                numbers.add(normalize(sb.toString()));
            }
        }

        // 숫자 정렬
        Collections.sort(numbers, (a, b) -> {
            // 자릿수가 다르면 자릿수가 적은 쪽이 더 작은 수
            if (a.length() != b.length()) return a.length() - b.length();
            // 자릿수가 같다면 문자열 사전순 정렬
            return a.compareTo(b);
        });

        // 정렬된 결과 출력 (출력 성능을 위해 StringBuilder 사용)
        StringBuilder out = new StringBuilder();
        for (String num : numbers) {
            out.append(num).append("\n");
        }
        System.out.print(out.toString());
    }

    // 숫자의 앞부분에 있는 불필요한 숫자를 제거하는 메서드
    static String normalize(String num) {
        int i = 0;
        int n = num.length();
        while (i < n && num.charAt(i) == '0') {
            i++;
        }
        if (i == n) return "0"; // "0", "00", "000" 등
        return num.substring(i);
    }
}
