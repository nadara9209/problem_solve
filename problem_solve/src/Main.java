public class Main {
	public static void main(String[] args) {
		Solution sol = new Solution();
		final int[] people = {100, 1, 2}; 
		final int[] tshirts = {1, 2, 100};
		int answer = sol.solution(people, tshirts);
		System.out.println(answer);
	}
}

