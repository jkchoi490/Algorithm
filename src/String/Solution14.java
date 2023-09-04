package String;

import java.io.*;
import java.util.*;

public class Solution14 { // 문서 통계
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String document = br.readLine();
        char[] words = document.toCharArray();
        System.out.println(document.length());
        String word = document.replaceAll(" ", ""); 
        System.out.println(word.length());
        int cnt = 0;
        String[] word_arr = document.split(" ");
        for(String w : word_arr){
            if(!w.equals(' '))
            cnt++;
        }
        System.out.println(cnt);
    }

}
