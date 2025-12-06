package Implementation;

//LeetCode - Missing Number
class MissingNumber  {
    public int missingNumber(int[] nums) {
        int n = nums.length;
        int expected = n * (n + 1) / 2;

        int sum = 0;
        for (int v : nums) {
            sum += v;
        }

        return expected - sum;
    }
}