class longest_common_prefix  {
    public String longestCommonPrefix(String[] strs) {
        String answer = "";
        String max_str = "";
        boolean isContain = true;
        String next_str ="";

        if(strs.length > 1){
            int idx = 0;
            String cur = strs[idx]; //flower
            int i = 0;
            for(int j = i+1; j<=cur.length(); j++){
                String cur_subs = cur.substring(i, j); //f, fl, flo..
                boolean[] isContains = new boolean[strs.length];
                for(int next = idx+1; next<strs.length; next++){
                    next_str = strs[next]; //flow
                    if(next_str.contains(cur_subs)){
                        for(int k = 0; k<Math.min(cur_subs.length(),next_str.length()); k++){
                            if(next_str.charAt(k) == cur_subs.charAt(k))                                {
                                isContains[next] = true;
                            }
                            else isContains[next] = false;
                        }

                    }
                }
                for(int n = idx+1; n<strs.length; n++){
                    if(!isContains[n]) isContain = false;
                }

                if(isContain){
                    if(max_str.length() < cur_subs.length())
                        max_str = cur_subs;
                }

            }

            answer = max_str;
            return answer;
        }

        else return strs[0];
    }
}
