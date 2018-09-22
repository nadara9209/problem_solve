import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

class Solution {
    public boolean solution(int[] arr) {
        boolean answer = true;
        answer = solve(arr);
        process();
        return answer;
    }

	private void process() {
		int n = 10;
		ArrayList<Integer>[] list = new ArrayList<>()[];
		int[] arr = new int[n];
		
	}

	private boolean solve(int[] arr) {
		Set<Integer> set = new HashSet<>();
		int limit = arr.length;
		for (int number : arr) {
			if (number == 0 || number > limit) {
				return false;
			}
			if (set.contains(number)) {
				return false;
			}
			set.add(number);
		}
		return true;
	}
}