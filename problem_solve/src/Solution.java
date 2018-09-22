import java.util.ArrayList;

class Solution {
    public int[] solution(int[] healths, int[][] items) {
        int[] answer = solve(healths, items);
        return answer;
    }

	private int[] solve(int[] healths, int[][] items) {
		int[] answer = new int[healths.length];
		int used = 0;
		answer = permutation(answer, items, healths, 0, used);
		return null;
	}

	private int[] permutation(int[] answer, int[][] items, int[] health, int curr, int used) {
		for (int i = 0; i < items.length; ++i) {
			if(((1 << curr) & i) != 1) {
				answer
			}
		}
		return null;
	}
}