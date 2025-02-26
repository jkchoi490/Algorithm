class ReverseInteger {
    public int reverse(int x) {
        long reverse_x = 0;
        String str_x = String.valueOf(x);
        boolean isMinus = false;

        String str_reverse_x = "";
        for(int i = 0; i<str_x.length(); i++){
            if(str_x.charAt(i) == '-'){
                isMinus = true;
            }
            str_reverse_x += str_x.charAt(str_x.length()-1-i);
        }



        if(isMinus) reverse_x = Long.parseLong(str_reverse_x.substring(0,str_reverse_x.length()-1))*-1;
        else reverse_x = Long.parseLong(str_reverse_x);


        if (reverse_x <= Integer.MIN_VALUE || reverse_x > Integer.MAX_VALUE) {
            return 0;
        }
        else
            return (int)(reverse_x);
    }
}
