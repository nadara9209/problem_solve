import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
	static int N;
	static int K;
	static int[][] map;
	static int highestHeight;
	
	static int[] dRow = { -1,  1,  0,  0 };
	static int[] dCol = {  0,  0, -1,  1 };
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for (int tc = 1; tc <= T; ++tc) {
			N = scan.nextInt();
			K = scan.nextInt();
			map = new int[N][N];
			
			highestHeight = 0;
			for (int row = 0; row < N; ++row) {
				for (int col = 0; col < N; ++col) {
					int height = scan.nextInt();
					map[row][col] = height;
					if (highestHeight < height) {
						highestHeight = height;
					}
				}
			}
			
			int answer = solve();
			System.out.println("#" + tc + " " + answer);
			
		}
		scan.close();
	}
	
	private static int solve() {
		List<Point> highestPointList = checkHighestPoint();
		int longestPathCount = checkPaths(highestPointList);
		return longestPathCount;
	}
	
	private static int checkPaths(List<Point> highestPointList) {
		int ret = 0;
		
		for (Point p : highestPointList) {
			for (int i = 1; i <= K; ++i) {
				int tmp = checkAllcases(p, i);
				if (ret < tmp) {
					ret = tmp;
				}
			}
		}
		return ret;
	}

	private static int checkAllcases(Point p, int diff) {
		int ret = 0;
		// 시작 p를 제외한 모든 map을 최대 공사가능 높이까지 깍아가면서 
		for (int row = 0; row < N; ++row) {
			for (int col = 0; col < N; ++col) {
				if (!isStart(row, col, p)) {
					map[row][col] -= diff;
					int tmp = check(p);
					if (ret < tmp) {
						ret = tmp;
					}
					map[row][col] += diff;
				}
			}
		}
		return ret;
	}

	private static int check(Point p) {
		int ret = 0;
		int currRow = p.row;
		int currCol = p.col;
		for (int dir = 0; dir < 4; ++dir) {
			int nextRow = currRow + dRow[dir];
			int nextCol = currCol + dCol[dir];
			int tmp = countAllCases(currRow, currCol, nextRow, nextCol, 1);
			if (ret < tmp) {
				ret = tmp;
			}
		}
		return ret;
	}

	private static int countAllCases(int row, int col, int nextRow, int nextCol, int pathCnt) {
		// 범위를 벗어난 경우
		if (!isValid(nextRow, nextCol)) {
			return pathCnt;
		}
		
		// 다음 지점이 더 높은경우
		if (map[row][col] <= map[nextRow][nextCol]) {
			return pathCnt;
		}
		
		int ret = 0;
		int currRow = nextRow;
		int currCol = nextCol;
		for (int dir = 0; dir < 4; ++dir) {
			int tmp = countAllCases(currRow, currCol, currRow + dRow[dir], currCol + dCol[dir], pathCnt+1);
			if (ret < tmp) {
				ret = tmp;
			}
		}
		
		return ret;
	}

	private static boolean isValid(int row, int col) {
		return (row >= 0 && col >= 0 && row < N && col < N);
	}

	private static boolean isStart(int row, int col, Point p) {
		return (row == p.row && col == p.col);
	}

	private static List<Point> checkHighestPoint() {
		List<Point> highestPointList = new ArrayList<>();
		
		for (int row = 0; row < N; ++row) {
			for (int col = 0; col < N; ++col) {
				int height = map[row][col];
				if (height == highestHeight) {
					highestPointList.add(new Point(row, col));
				}
			}
		}
		return highestPointList;
	}

	static class Point {
		int row;
		int col;
		
		public Point(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}
}