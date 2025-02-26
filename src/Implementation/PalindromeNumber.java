class PalindromeNumber {
    public boolean isPalindrome(int x) {
        boolean answer = true;
        String str_x = String.valueOf(x);
        for(int i = 0; i<str_x.length(); i++){
            if(str_x.charAt(i) != str_x.charAt(str_x.length()-1-i)){
                return false;
            }
        }

        return answer;
    }
}
