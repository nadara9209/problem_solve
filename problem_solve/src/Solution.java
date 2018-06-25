import java.util.Scanner;

public class Solution {
	static int[] Drow = {-1,  1,  0,  0, -1, -1,  1,  1};
	static int[] Dcol = { 0,  0, -1,  1, -1,  1, -1,  1};
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for(int tc = 1; tc <= T; ++tc) {
			int N = scan.nextInt();
			int[][] map = new int[N][N];
			System.out.println("#" + tc + " " + solve(map, N));
		}
		scan.close();
	}
	
	private static int solve(int[][] map, int N) {
		Board chessBoard = new Board(map, N);
		int answer = countAllCases(chessBoard);
		return answer;
	}
	
	private static int countAllCases(Board chessBoard) {
		if(chessBoard.numOfQueens == 0) {
			return 1;
		}
		
		int nCases = 0;
		int row = chessBoard.height - chessBoard.numOfQueens;
		for(int col = 0; col < chessBoard.width; ++col) {
			if(chessBoard.isQueenOk(row, col)) {
				chessBoard.putDownQueen(row, col);
				nCases += countAllCases(chessBoard);
				chessBoard.putUpQueen(row, col);
			}
		}
		
		return nCases;
	}
	
	static class Board {
		int[][] map;
		int numOfQueens;
		int width;
		int height;
		
		Board(int[][] map, int numOfQueen) {
			this.map = map;
			this.numOfQueens = numOfQueen;
			this.width = map[0].length;
			this.height = map.length;
		}

		private boolean isValid(int row, int col) {
			return (row >= 0 && col >= 0 && row < this.height && col < this.width);
		}
		
		private void score(int row, int col, int val) {
			this.map[row][col] += val;
			
			for(int i = 0; i < 8; ++i) {
				int nextRow = row;
				int nextCol = col;
				while(this.isValid(nextRow+Drow[i], nextCol+Dcol[i])) {
					nextRow += Drow[i];
					nextCol += Dcol[i];
					this.map[nextRow][nextCol] += val;
				}
			}
		}
		
		public void putUpQueen(int row, int col) {
			this.score(row, col, -1);
			this.numOfQueens++;
		}

		public void putDownQueen(int row, int col) {
			this.score(row, col, 1);
			this.numOfQueens--;
		}

		public boolean isQueenOk(int row, int col) {
			return (this.map[row][col] == 0);
		}
	}
}