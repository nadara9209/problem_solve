public class Main {
	public static void main(String[] args) {
		Solution sol = new Solution();
		final int N = 3; 
		final int[][] house = {{0, 0}, {1, 0}, {2, 0}};
		int answer = sol.solution(N, house);
		System.out.println(answer);
	}
}

