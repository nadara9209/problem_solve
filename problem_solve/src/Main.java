import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class Main {
	static int N;
	static int M;
	static int H;
	static int[][] board;
		
	static int usedRow;
	static int usedCol;
	static final int STATE_EMPTY = 0;
	static final int STATE_VERTI = 1;
	static final int STATE_HORIZ = 2;
	
	static final int DIFF = 2;
	
	static int[] dRow = { 0,  1,  0 };
	static int[] dCol = {-1,  0,  1 };
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		N = scan.nextInt();
		M = scan.nextInt();
		H = scan.nextInt();
		
		usedRow = H + 2;
		usedCol = N * 2;
		
		board = new int[usedRow][usedCol];
		for (int col = 1; col < usedCol; col += DIFF) {
			for (int row = 1; row < usedRow; ++row) {
				board[row][col] = STATE_VERTI;
			}
		}
		
		for (int i = 0; i < M; ++i) {
			int a = scan.nextInt();
			int b = scan.nextInt();
			board[a][b * 2] = STATE_HORIZ;
		}
		
		int answer = solve();
		System.out.println(answer);
		
		scan.close();
	}
	
	private static int solve() {
		if (M == 0) {
			return 0;
		}
		
		for (int addLine = 0; addLine <= 3; ++addLine) {
			if (checkAllCases(1, 2, 0, addLine)) {
				return addLine;
			}
		}
		
		return -1;
	}

	private static boolean checkAllCases(int startRow, int startCol, int cnt, int limit) {
		if (cnt == limit) {
			Ladder ladder = new Ladder(board);
			return ladder.check();
		}
		
		if (startCol >= usedCol) {
			startRow += 1;
			startCol = DIFF;
		}
		
		if (!isValid(startRow, startCol)) {
			return false;
		}
		
		int row = startRow;
		int col = startCol;
		
		for (; row < usedRow; ++row) {
			for (; col < usedCol; col += DIFF) {
				if (isPossibleLine(row, col)) {
					board[row][col] = STATE_HORIZ;
					if (checkAllCases(row, col + DIFF, cnt + 1, limit)) {
						return true;
					}
					board[row][col] = STATE_EMPTY;
				}
			}
			col = DIFF;
		}
		
		return false;
	}

	private static boolean isPossibleLine(int row, int col) {
		if (board[row][col] == STATE_EMPTY) {
			Point leftPoint = new Point(row, col - DIFF);
			Point rightPoint = new Point(row, col + DIFF);
			
			if (isPossiblePoint(leftPoint) && isPossiblePoint(rightPoint)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isPossiblePoint(Point inputPoint) {
		int currRow = inputPoint.row;
		int currCol = inputPoint.col;
		
		if (!isValid(currRow, currCol)) {
			return true;
		}
		else {
			if (board[currRow][currCol] != STATE_HORIZ) {
				return true;
			}
		}
		
		return false;
	}

	private static boolean isValid(int currRow, int currCol) {
		return (currRow >= 1 && currCol >= 1 && currRow < usedRow && currCol < usedCol);
	}

	static class Ladder {
		int[][] board;
	
		public Ladder(int[][] map) {
			this.board = map;
		}

		public boolean check() {
			for (int col = 1; col < usedCol; col += DIFF) {
				Point firstP = new Point(1, col);
				if (!this.simulate(firstP)) {
					return false;
				}
			}
			return true;
		}

		private boolean simulate(Point firstP) {
			boolean[][] visited = new boolean[usedRow][usedCol];
			Queue<Point> q = new LinkedList<>();
			
			visited[firstP.row][firstP.col] = true;
			q.offer(firstP);
			
			while(!q.isEmpty()) {
				Point currP = q.poll();
				int currRow = currP.row;
				int currCol = currP.col;
				
				if (currRow == usedRow - 1) {
					if (currCol == firstP.col) {
						return true;
					}
					return false;
				}
				
				Map<Integer, Point> pointMap = new HashMap<>();
				
				for (int i = 0; i < 3; ++i) {
					int nextRow = currRow + dRow[i];
					int nextCol = currCol + dCol[i];
					
					if (!isValid(nextRow, nextCol) || visited[nextRow][nextCol]) {
						continue;
					}
					
					int type = this.board[nextRow][nextCol];
					if (type == 0) {
						continue;
					}
					pointMap.put(type, new Point(nextRow, nextCol, i));
				}
				
				if (pointMap.containsKey(STATE_HORIZ)) {
					Point nextPoint = pointMap.get(STATE_HORIZ);
					visited[nextPoint.row][nextPoint.col] = true;
					
					q.offer(new Point(nextPoint.row, nextPoint.col += dCol[nextPoint.dir]));
				}
				else {
					Point nextPoint = pointMap.get(STATE_VERTI);
					q.offer(nextPoint);
				}
				
			}
			return false;
		}
		
	}
	
	static class Point {
		int row;
		int col;
		int dir;
		
		public Point(int row, int col) {
			this.row = row;
			this.col = col;
		}
		
		public Point(int row, int col, int dir) {
			this.row = row;
			this.col = col;
			this.dir = dir;
		}
	}
}