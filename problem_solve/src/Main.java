public class Main {
	public static void main(String[] args) {
		Solution sol = new Solution();
		final int[] healths = {200, 120, 150}; 
		final int[][] items = {{30, 100}, {500, 30}, {100, 400}};
		int[] answer = sol.solution(healths, items);
		System.out.println(answer);
	}
}

