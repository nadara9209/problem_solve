import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/*
 * 모든 코드는 존재 이유가 있다.
 */

public class Main {
	static int N;
	static int M;
	static int[][] map;
	static List<Point> chickenList = new ArrayList<>();
	
	static int[] dRow = { -1,  1,  0,  0 };
	static int[] dCol = {  0,  0, -1,  1 };
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		N = scan.nextInt();
		M = scan.nextInt();
		
		map = new int[N][N];
		for (int row = 0; row < N; ++row) {
			for (int col = 0; col < N; ++col) {
				int val = scan.nextInt();
				if (val == 2)
				chickenList.add(new Point(row, col));
				map[row][col] = val;
			}
		}

		int answer = solve();
		
		System.out.println(answer);
		scan.close();
	}

	private static int solve() {
		List<Point> selectedList = new ArrayList<>();
		int ret = Integer.MAX_VALUE;
		for (int i = 1; i <= M; ++i) {
			int minChickenLength = checkAllCases(selectedList, 0, i);
			if (ret > minChickenLength) {
				ret = minChickenLength;
			}
		}
		
		return ret;
	}

	private static int checkAllCases(List<Point> selectedList, int id, int limit) {
		// 특정 갯수를 뽑아야 하기에
		if (selectedList.size() == limit) {
			return countChickenLength(selectedList);
		}
		
		// 인덱스 넘어가는 거 잡아주고
		if (id >= chickenList.size()) {
			return Integer.MAX_VALUE;
		}
		
		selectedList.add(chickenList.get(id));
		int caseA = checkAllCases(selectedList, id + 1, limit);
		selectedList.remove(selectedList.size() - 1);
		
		int caseB = checkAllCases(selectedList, id + 1, limit);
		
		return Math.min(caseA, caseB);
	}

	private static int countChickenLength(List<Point> selectedList) {
		int sumOfChickenLength = 0;
		
		Queue<Point> q = new LinkedList<>();
		
		boolean[][] visited = new boolean[N][N];
		// 뽑힌거 큐에넣고
		for (int i = 0; i < selectedList.size(); ++i) {
			Point chicken = selectedList.get(i); 
			q.offer(chicken);
			visited[chicken.srcRow][chicken.srcCol] = true;
		}
		
		// 달리기
		while (!q.isEmpty()) {
			Point p = q.poll();
			int currRow = p.currRow;
			int currCol = p.currCol;

			for (int i = 0; i < 4; ++i) {
				int nextRow = currRow + dRow[i];
				int nextCol = currCol + dCol[i];
				
				if (!isValid(nextRow, nextCol) || visited[nextRow][nextCol]) {
					continue;
				}
				
				// 먼저 도착한놈 치킨거리 더해주고
				if (map[nextRow][nextCol] == 1) {
					sumOfChickenLength += (Math.abs(p.srcRow - nextRow) + Math.abs(p.srcCol - nextCol));
				}
				
				// 도착한 곳 문닫고
				visited[nextRow][nextCol] = true;
				// 다시 달리기
				q.offer(new Point(nextRow, nextCol, p.srcRow, p.srcCol));
			}
			
		}
		
		return sumOfChickenLength;
	}

	private static boolean isValid(int inputRow, int inputCol) {
		return (inputRow >= 0 && inputRow < N && inputCol >= 0 && inputCol < N);
	}

	static class Point {
		int currRow;
		int currCol;
		int srcRow;
		int srcCol;
		
		public Point(int row, int col) {
			this.currRow = row;
			this.currCol = col;
			this.srcRow = row;
			this.srcCol = col;
		}
		
		public Point(int currRow, int currCol, int srcRow, int srcCol) {
			this.currRow = currRow;
			this.currCol = currCol;
			this.srcRow = srcRow;
			this.srcCol = srcCol;
		}
	}
}