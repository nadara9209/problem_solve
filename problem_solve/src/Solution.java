import java.util.Arrays;
import java.util.Scanner;

public class Solution {
	static int[] numArr = new int[100000];
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for(int tc = 1; tc <= T; ++tc) {
			int N = scan.nextInt();
			for(int i = 0; i < N; ++i) {
				int numVal = scan.nextInt();
				numArr[i] = numVal;
			}
			
			long answer = solve(N);
			System.out.println("#" + tc + " " + answer);
		}
		scan.close();
	}
	
	private static long solve(int n) {
		int result = sumAllNumbers(n, result);
		return result;
	}

	private static int sumAllNumbers(int n, int result) {
		if()
		Arrays.sort(numArr, 0, n);
		
		return 0;
	}
}