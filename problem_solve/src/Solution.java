import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Solution {
	static int N;
	static int M;
	static int[][] map;
	
	static int[] dRow = { -1,  1,  0,  0 };
	static int[] dCol = {  0,  0, -1,  1 };
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for (int tc = 1; tc <= T; ++tc) {
			N = scan.nextInt();
			M = scan.nextInt();
			
			map = new int[N][N];
			for (int row = 0; row < N; ++row) {
				for (int col = 0; col < N; ++col) {
					map[row][col] = scan.nextInt();
				}
			}
			
			int answer = solve();
			System.out.println("#" + tc + " " + answer);
		}
		scan.close();
	}
	
	private static int solve() {
		int maxNOfHouses = countAllCases();
		return maxNOfHouses;
	}

	private static int countAllCases() {
		int maxNOfHouses = 0;
		int limitOfK = (N % 2 == 1) ? N - 1 : N; 
		
		for (int K = 1; K <= limitOfK; ++K) {
			for (int row = 0; row < N; ++row) {
				for (int col = 0; col < N; ++col) {
					int tmp = count(row, col, K);
					if (maxNOfHouses < tmp) {
						maxNOfHouses = tmp;
					}
				}
			}
		}
		
		// 전체인 경우와 마지막 비교
		int totalCaseNOfHouses = 0;
		for (int row = 0; row < N; ++row) {
			for (int col = 0; col < N; ++col) {
				if (map[row][col] == 1) {
					totalCaseNOfHouses += 1;
				}
			}
		}
		
		int totalCaseCost = getCost(limitOfK + 1);
		int totalCaseMoney = getMoney(totalCaseNOfHouses);
		
		int totalCaseHouses = (totalCaseCost <= totalCaseMoney) ? totalCaseNOfHouses : 0;
		
		if (maxNOfHouses < totalCaseHouses) {
			maxNOfHouses = totalCaseHouses;
		}
		
		return maxNOfHouses;
	}
	
	private static int count(int row, int col, int K) {
		int nOfHouses = 0;
		
		Queue<Point> q = new LinkedList<>();
		q.offer(new Point(row, col, 1));
		
		boolean visited[][] = new boolean[N][N];
		visited[row][col] = true;
		
		while (!q.isEmpty()) {
			Point currPoint = q.poll();
			int currRow = currPoint.row;
			int currCol = currPoint.col;
			int currDistance = currPoint.distance;
			
			if (map[currRow][currCol] == 1) {
				nOfHouses += 1;
			}
			
			for (int i = 0; i < 4; ++i) {
				int nextRow = currRow + dRow[i];
				int nextCol = currCol + dCol[i];
				int nextDistance = currDistance + 1;
				
				if (!isValid(nextRow, nextCol) || visited[nextRow][nextCol] || nextDistance > K) {
					continue;
				}
				
				visited[nextRow][nextCol] = true;
				q.offer(new Point(nextRow, nextCol, nextDistance));
			}
		}
		
		int cost = getCost(K);
		int money = getMoney(nOfHouses);
		
		return (cost <= money) ? nOfHouses : Integer.MIN_VALUE;
	}

	private static int getMoney(int nOfHouses) {
		return nOfHouses * M;
	}

	private static int getCost(int K) {
		return (K * K + (K - 1) * (K - 1));
	}

	private static boolean isValid(int row, int col) {
		return (row >= 0 && col >= 0 && row < N && col < N);
	}

	static class Point {
		int row;
		int col;
		int distance;
		
		public Point(int row, int col, int distance) {
			this.row = row;
			this.col = col;
			this.distance = distance;
		}
	}
}