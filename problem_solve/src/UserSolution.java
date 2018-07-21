
class UserSolution {
    public final static int N = 4;
    public static int[] result = new int[2];
    
    public void doUserImplementation(int guess[]) {
    	String answer = "0125";
    	input(guess, answer);
    	Solution.Result result = output(guess);
    }

	private Solution.Result output(int[] guess) {
		Solution.Result result = Solution.query(guess);
		return result;
	}

	private void input(int[] guess, String answer) {
		for(int i = 0; i < N; ++i) {
			guess[i] = Character.getNumericValue(answer.charAt(i));
		}
	}
	
	
}