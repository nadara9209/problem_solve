import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/*
 * ��� �ڵ�� ���� ������ �ִ�.
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
		// Ư�� ������ �̾ƾ� �ϱ⿡
		if (selectedList.size() == limit) {
			return countChickenLength(selectedList);
		}
		
		// �ε��� �Ѿ�� �� ����ְ�
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
		// ������ ť���ְ�
		for (int i = 0; i < selectedList.size(); ++i) {
			Point chicken = selectedList.get(i); 
			q.offer(chicken);
			visited[chicken.srcRow][chicken.srcCol] = true;
		}
		
		// �޸���
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
				
				// ���� �����ѳ� ġŲ�Ÿ� �����ְ�
				if (map[nextRow][nextCol] == 1) {
					sumOfChickenLength += (Math.abs(p.srcRow - nextRow) + Math.abs(p.srcCol - nextCol));
				}
				
				// ������ �� ���ݰ�
				visited[nextRow][nextCol] = true;
				// �ٽ� �޸���
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