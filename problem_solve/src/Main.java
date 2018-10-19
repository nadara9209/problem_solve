import java.util.Scanner;

public class Main {
	static int N;
	static int M;
	static int[][] map;
	
	static int[] dRow = {-1,  1,  0,  0,  0};
	static int[] dCol = { 0,  0, -1,  1,  0};
	static int[] opposite = { 1, 0, 3, 2, 4};
	
	static int[] caseA = {0, 1, 2};
	static int[] caseB = {1, 2, 3};
	static int[] caseC = {2, 3, 0};
	static int[] caseD = {3, 0, 1};
	
	public static void main(String[] args) {
		Scanner scan =  new Scanner(System.in);
		
		N = scan.nextInt();
		M = scan.nextInt();
		
		map = new int[N][M];
		for (int row = 0; row < N; ++row) {
			for (int col = 0; col < M; ++col) {
				map[row][col] = scan.nextInt();
			}
		}
		
		int answer = solve();
		System.out.println(answer);
		scan.close();
	}

	private static int solve() {
		int sumOfCaseA = getMaxSumA(4);
		return sumOfCaseA;
	}

	private static int getMaxSumA(int i) {
		int ret = 0;
		for (int row = 0; row < N; ++row) {
			for (int col = 0; col < M; ++col) {	
				int tmp = getMaxSumA(row, col, 0, 4, 4);
				if (ret < tmp) {
					ret = tmp;
				}
			}
		}
		return ret;
	}

	private static int getMaxSumA(int row, int col, int sum, int limit, int prevDir) {
		if (limit == 0) {
			return sum;
		}
		
		if (!isValid(row, col)) {
			return Integer.MIN_VALUE;
		}
		
		int ret = 0;
		for (int i = 0; i < 4; ++i) {
			if (opposite[prevDir] == i) {
				continue;
			}
			
			int caseA;
			int caseB;
		
			if (limit == 2) {
				caseA = sum;
				caseA += getMaxSumA(row + dRow[i], col + dCol[i], 0, 1, i);
				
				caseB = sum;
				caseB = 
			}
			
			int tmp = getMaxSumA(row + dRow[i], col + dCol[i], sum + map[row][col], limit - 1, i);
			if (ret < tmp) {
				ret = tmp;
			}
		}
		
		return ret;
	}

	private static boolean isValid(int row, int col) {
		return (row >= 0 && col >= 0 && row < N && col < M);
	}
}