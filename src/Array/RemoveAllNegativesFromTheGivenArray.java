package Array;

import java.io.*;
import java.util.*;

// GeeksforGeeks - Remove all negatives from the given Array
public class RemoveAllNegativesFromTheGivenArray {
    // Function to remove the negative elements
    static void removeNegative(int[] arr) {
        ArrayList<Integer> result = new ArrayList<Integer>();

        for (int x : arr) {
            if (x >= 0) {
                result.add(x);
            }
        }

        for (int x : result) {
            System.out.print(x + " ");
        }
    }

    public static void main(String args[]) {
        int[] arr = {-1, -2, -5, -8};
        removeNegative(arr);
    }
}

