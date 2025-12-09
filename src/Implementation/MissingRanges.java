package Implementation;

import java.util.*;

//LeetCode - Missing Ranges
class MissingRanges {
    public List<List<Integer>> findMissingRanges(int[] nums, int lower, int upper) {
        int length = nums.length;
        List<List<Integer>> result = new ArrayList<>();

        // nums가 비어 있는 경우
        if (length == 0) {
            List<Integer> range = new ArrayList<>();
            range.add(lower);
            range.add(upper);
            result.add(range);
            return result;
        }
        // lower ~ nums[0]-1 구간 체크
        if (nums[0] > lower) {
            List<Integer> range = new ArrayList<>();
            range.add(lower);
            range.add(nums[0] - 1);
            result.add(range);
        }

        // nums[i-1] + 1 ~ nums[i] - 1 구간 체크
        for (int i = 1; i < length; i++) {
            if (nums[i] - nums[i - 1] > 1) {
                List<Integer> range = new ArrayList<>();
                range.add(nums[i - 1] + 1);
                range.add(nums[i] - 1);
                result.add(range);
            }
        }

        // nums[last] + 1 ~ upper 구간 체크
        if (nums[length - 1] < upper) {
            List<Integer> range = new ArrayList<>();
            range.add(nums[length - 1] + 1);
            range.add(upper);
            result.add(range);
        }

        return result;
    }
}