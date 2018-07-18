import java.util.Scanner;

public class Solution {
	static int[] Drow = {-1,  1,  0,  0};
	static int[] Dcol = { 0,  0, -1,  1};
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for(int tc = 1; tc <= T; ++tc) {
			int N = scan.nextInt();
			int K = scan.nextInt();
			
			int maxHeight = 0;
			// create map
			int[][] map = new int[N][N];
			for(int row = 0; row < N; ++row) {
				for(int col = 0; col < N; ++col) {
					int heightVal = scan.nextInt();
					map[row][col] = heightVal;
					if(maxHeight < heightVal) {
						maxHeight = heightVal;
					}
				}
			}
			
			int answer = solve(N, K, map, maxHeight);
			System.out.println("#" + tc + " " + answer);
		}
		scan.close();
	}

	private static int solve(int n, int k, int[][] map, int maxHeight) {
		int maxNumberOfRoute;
		maxNumberOfRoute = makeAllCases(n, k, map, maxHeight);
		return maxNumberOfRoute;
	}

	private static int makeAllCases(int n, int k, int[][] map, int maxHeight) {
		int ret = Integer.MAX_VALUE;
		for(int row = 0; row < n; ++row) {
			for(int col = 0; col < n; ++col) {
				int heightVal = map[row][col];
				if(heightVal == maxHeight) {
					int tmp = countAllCases(row, col, 0, map);
					if(ret > tmp) {
						ret = tmp;
					}
				}
			}
		}
		return ret;
	}

	private static int countAllCases(int row, int col, int currVal, int[][] map) {
		int nextVal = map[row][col];
		if(currVal <= nextVal) {
			return 1;
		}
		return 0;
	}
}