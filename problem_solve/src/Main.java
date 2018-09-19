public class Main {
	public static void main(String[] args) {
		Solution sol = new Solution();
		
		final String[][] INPUT_relation = {{"100", "ryan", "music", "2"}, 
					 {"200", "apeach", "math", "2"}, 
					 {"300", "tube", "computer", "3"}, 
					 {"400", "con", "computer", "4"}, 
					 {"500", "muzi", "music", "3"}, 
					 {"600", "apeach", "music", "2"}};
		
		int answer = sol.solution(INPUT_relation);
		System.out.println(answer);
	}
}

