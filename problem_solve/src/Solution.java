import java.util.Scanner;

public class Solution {
	static int N;
	static int B;
	static int[] Heights;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for(int tc = 1; tc <= T; ++tc) {
			N = scan.nextInt();
			B = scan.nextInt();
			
			Heights = new int[N];
			for(int i = 0; i < N; ++i) {
				int height = scan.nextInt();
				Heights[i] = height;
			}
			int answer = solve();
			System.out.println("#" + tc + " " + answer);
		}
		scan.close();
	}
	
	private static int solve() {
		int minGap = 0;
		
		int sum = 0;
		int index = 0;
		minGap = makeAllCases(sum, index);
		
		return minGap;
	}
	
	private static int makeAllCases(int sum, int index) {
		if(index == N) {
			if(sum >= B) {
				int gap = sum-B;
				return gap;
			}
			else {
				return Integer.MAX_VALUE;
			}
		}
		
		int sumOfCaseA = makeAllCases(sum+Heights[index], index+1);
		int sumOfCaseB = makeAllCases(sum, index+1);
	
		return Math.min(sumOfCaseA, sumOfCaseB);
	}
}