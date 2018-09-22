public class Main {
	public static void main(String[] args) {
		Solution sol = new Solution();
		final int N = 5;
		final int[][] directory = {{1, 2}, {1, 3}, {2, 4}, {2, 5}};
		final int[][] query = {{1, 5}, {2, 5}, {3, 5}, {4, 5}};
		int[] answer = sol.solution(N, directory, query);
		System.out.println(answer);
	}
}

