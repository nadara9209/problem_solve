import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Solution {
	static int N;
	static int M;
	static int[] dRow = { -1,  1,  0,  0 };
	static int[] dCol = {  0,  0, -1,  1 };
	static final int LIMIT = 400;
	static final int DIFF = 175;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		
		for (int tc = 1; tc <= T; ++tc) {
			N = scan.nextInt();
			M = scan.nextInt();
			int K = scan.nextInt();
			
			int[][] tempCells = new int[N][M];
			for (int row = 0; row < N; ++row) {
				for (int col = 0; col < M; ++col) {
					tempCells[row][col] = scan.nextInt();
				}
			}
			
			
			Cell[][] cells = new Cell[LIMIT][LIMIT];
			for (int row = 0; row < LIMIT; ++row) {
				for (int col = 0; col < LIMIT; ++col) {
					cells[row][col] = new Cell();
				}
			}
			
				
			List<Point> searchList = new LinkedList<>();
			int row_limit = N + DIFF;
			int col_limit = M + DIFF;
			for (int row = DIFF; row < row_limit; ++row) {
				for (int col = DIFF; col < col_limit; ++col) {
					int val = tempCells[row - DIFF][col - DIFF];
					if (val != 0) {
						cells[row][col].setLife(val);
						searchList.add(new Point(row, col));
					}
				}
			}
			
			int answer = solve(cells, searchList, K);
			System.out.println("#" + tc + " " + answer);
		}
		
		scan.close();
	}

	private static int solve(Cell[][] cells, List<Point> list, int k) {
		Vessel vessel = new Vessel(cells, list, k);
		vessel.cultivate();
		return vessel.aliveCellList.size();
	}

	static class Vessel {
		Cell[][] cells;
		List<Point> aliveCellList;
		Queue<Point> breedCellQ;
		int time;
		
		public Vessel(Cell[][] cells, List<Point> list, int time) {
			this.cells = cells;
			this.aliveCellList = list;
			this.breedCellQ = new LinkedList<>();
			this.time = time;
		}

		public void cultivate() {
			for (int i = 0; i < this.time; ++i) {
				this.process();
				this.breed();
			}
			
			// 죽은 세포 삭제.
			this.process();
		}

		private void process() {
			for (int i = this.aliveCellList.size() - 1; i >= 0; --i) {
				Point currP = this.aliveCellList.get(i);
				int currRow = currP.row;
				int currCol = currP.col;
				
				Cell currCell = this.cells[currRow][currCol];
				
				if (!currCell.isActivated) {
					if (currCell.unactivatedTime == currCell.life) {
						currCell.isActivated = true;
						currCell.activatedTime++;
						this.breedCellQ.offer(currP);
					}
					else {
						currCell.unactivatedTime++;
					}
				}
				else {
					if (currCell.activatedTime == currCell.life) {
						currCell.isDeath = true;
						this.aliveCellList.remove(i);
					}
					else {
						currCell.activatedTime++;
					}
				}
			}	
		}

		private void breed() {
			boolean[][] visited = new boolean[LIMIT][LIMIT];
			while(!this.breedCellQ.isEmpty()) {
				Point currP = this.breedCellQ.poll();
				int currRow = currP.row;
				int currCol = currP.col;
				
				Cell currCell = this.cells[currRow][currCol];
				int currLife = currCell.life;
				
				for (int i = 0; i < 4; ++i) {
					int nextRow = currRow + dRow[i];
					int nextCol = currCol + dCol[i];
					
					Cell nextCell = this.cells[nextRow][nextCol];
					
					if (nextCell.life == 0) {
						nextCell.setLife(currLife);
						visited[nextRow][nextCol] = true;
						this.aliveCellList.add(new Point(nextRow, nextCol));
					}
					else if (visited[nextRow][nextCol]) {
						if (nextCell.life < currLife) {
							nextCell.setLife(currLife);
						}
					}
				}
				
			}
		}
	}
	
	static class Cell {
		boolean isDeath;
		boolean isActivated;
		int activatedTime;
		int unactivatedTime;
		int life;
		
		public Cell(int val) {
			this.isDeath = false;
			this.isActivated = false;
			this.activatedTime = 0;
			this.unactivatedTime = 0;
			this.life = val;
		}

		public Cell() {
			this.isDeath = false;
			this.isActivated = false;
			this.activatedTime = 0;
			this.unactivatedTime = 0;
			this.life = 0;
		}
		
		public void setLife(int val) {
			this.life = val;
		}
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