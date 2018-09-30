import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Solution {
	static final int LIMIT = 400;
	static final int DIFF = 150;
	static Cell[][] totalCells = new Cell[LIMIT][LIMIT];
	static int[] dRow = {-1,  1,  0,  0};
	static int[] dCol = { 0,  0, -1,  1};
	static int N;
	static int M;
	static int K;
	public static void main(String[] args) {		
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for (int tc = 1; tc <= T; ++tc) {
			N = scan.nextInt();
			M = scan.nextInt();
			K = scan.nextInt();
			
			//init
			for (int row = 0; row < LIMIT; ++row) {
				for (int col = 0; col < LIMIT; ++col) {
					totalCells[row][col] = new Cell();
				}
			}
			
			Cell[][] cells = new Cell[N][M];
			for (int row = 0; row < N; ++row) {
				for (int col = 0; col < M; ++col) {
					int val = scan.nextInt();
					cells[row][col] = new Cell(val);
				}
			}
			
			for (int row = DIFF; row < N+DIFF; ++row) {
				for (int col = DIFF; col < M+DIFF; ++col) {
					totalCells[row][col] = cells[row-DIFF][col-DIFF];
				}
			}
			
			int answer = solve(totalCells);
			System.out.println("#" + tc + " " + answer);
		}
		scan.close();
	}

	private static int solve(Cell[][] cells) {
		Board board = new Board(cells);
		board.process();
		int remainingNOfCells = board.countRemainigNOfCells();
		return remainingNOfCells;
	}

	static class Board {
		Cell[][] cells;
		
		public Board(Cell[][] cells) {
			this.cells = cells;
		}

		public int countRemainigNOfCells() {
			int cnt = 0;
			for (int row = 0; row < LIMIT; ++row) {
				for (int col = 0; col < LIMIT; ++col) {
					Cell cell = this.cells[row][col];
					if (cell.isDeath || cell.lifeSpan == 0) {
						continue;
					}
					cnt++;
				}
			}
			return cnt;
		}

		public void process() {
			for (int t = 0; t < K; ++t) {
				this.curtivate();
			}
		}

		private void curtivate() {
			Queue<Point> q = new LinkedList<>();
			for (int row = 0; row < LIMIT; ++row) {
				for (int col = 0; col < LIMIT; ++col) {
					Cell cell = this.cells[row][col];
					if (cell.isDeath || cell.lifeSpan == 0) {
						continue;
					}
					else if (!cell.isActive) {
						cell.inactiveTime++;
						if (cell.inactiveTime == cell.lifeSpan) {
							cell.isActive = true;
						}
					}
					else if (cell.isActive) {
						cell.activeTime++;
						if (cell.activeTime == 1) {
							q.offer(new Point(row, col));
						}
						
						if (cell.activeTime == cell.lifeSpan) {
							cell.isDeath = true;
						}
					}
				}
			}
			
			this.spread(q);
		}

		private void spread(Queue<Point> q) {
			boolean[][] visited = new boolean[LIMIT][LIMIT];
			while (!q.isEmpty()) {
				Point currP = q.poll();
				int currRow = currP.row;
				int currCol = currP.col;
				for (int i = 0; i < 4; ++i) {
					int nextRow = currRow + dRow[i];
					int nextCol = currCol + dCol[i];
					if (!isValid(nextRow, nextCol)) {
						continue;
					}
					if (!visited[nextRow][nextCol] && isExist(nextRow, nextCol)) {
						continue;
					}
					Cell currCell = this.cells[currRow][currCol];
					Cell nextCell = this.cells[nextRow][nextCol];
					
					if (!visited[nextRow][nextCol]) {
						nextCell.lifeSpan = currCell.lifeSpan;
						visited[nextRow][nextCol] = true;
					}
					else {
						if (nextCell.lifeSpan < currCell.lifeSpan) {
							nextCell.lifeSpan = currCell.lifeSpan;
						}
					}
				}
			}
		}

		private boolean isExist(int nextRow, int nextCol) {
			return (this.cells[nextRow][nextCol].lifeSpan > 0);
		}

		private boolean isValid(int nextRow, int nextCol) {
			return (nextRow >= 0 && nextCol >= 0 && nextRow < LIMIT && nextCol < LIMIT);
		}
	}
}

class Cell {
	boolean isDeath;
	boolean isActive;
	int lifeSpan;
	int inactiveTime;
	int activeTime;
	
	public Cell() {
		this.isDeath = false;
		this.isActive = false;
		this.lifeSpan = 0;
		this.inactiveTime = 0;
		this.activeTime = 0;
	}
	
	public Cell(int val) {
		this.isDeath = false;
		this.isActive = false;
		this.lifeSpan = val;
		this.inactiveTime = 0;
		this.activeTime = 0;
	}
}

class Point {
	int row;
	int col;
	int time;
	
	public Point(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public Point(int row, int col, int time) {
		this.row = row;
		this.col = col;
		this.time = time;
	}
}