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
		int sumOfCaseB = getMaxSumB(3);
		return Math.max(sumOfCaseA, sumOfCaseB);
	}

	private static int getMaxSumB(int limit) {
		int ret = 0;
		for (int row = 0; row < N; ++row) {
			for (int col = 0; col < M; ++col) {	
				int tmp = getMaxSumB(row, col);
				if (ret < tmp) {
					ret = tmp;
				}
			}
		}
		return ret;
	}
	

	private static int getMaxSumB(int row, int col) {
		int ret = 0;
	
		int sum0 = map[row][col];
		
		int sumA = sum0;
		for (int i = 0; i < caseA.length; ++i) {
			int nextRow = row + dRow[caseA[i]];
			int nextCol = col + dCol[caseA[i]];
			if (!isValid(nextRow, nextCol)) {
				sumA = 0;
				break;
			}
			sumA += map[nextRow][nextCol];
		}
		
		if (ret < sumA) {
			ret = sumA;
		}
		
		int sumB = sum0;
		for (int i = 0; i < caseB.length; ++i) {
			int nextRow = row + dRow[caseB[i]];
			int nextCol = col + dCol[caseB[i]];
			if (!isValid(nextRow, nextCol)) {
				sumB = 0;
				break;
			}
			sumB += map[nextRow][nextCol];
		}
		
		if (ret < sumB) {
			ret = sumB;
		}
		
		int sumC = sum0;
		for (int i = 0; i < caseC.length; ++i) {
			int nextRow = row + dRow[caseC[i]];
			int nextCol = col + dCol[caseC[i]];
			if (!isValid(nextRow, nextCol)) {
				sumC = 0;
				break;
			}
			sumC += map[nextRow][nextCol];
		}
		
		if (ret < sumC) {
			ret = sumC;
		}
		
		int sumD = sum0;
		for (int i = 0; i < caseD.length; ++i) {
			int nextRow = row + dRow[caseD[i]];
			int nextCol = col + dCol[caseD[i]];
			if (!isValid(nextRow, nextCol)) {
				sumD = 0;
				break;
			}
			sumD += map[nextRow][nextCol];
		}
		
		if (ret < sumD) {
			ret = sumD;
		}
		
		return ret;
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