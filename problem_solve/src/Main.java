import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	static int[] dRow = { 0, -1,  0,  1, -1, -1,  1,  1 };
	static int[] dCol = { 1,  0, -1,  0, -1,  1, -1,  1 };
	
	static int[] opposite = { 1, 2, 3, 0 };
	static int[][] map;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int N = scan.nextInt();
		
		// 맵을 두배로
		map = new int[201][201];
		
		for (int i = 0; i < N; ++i) {
			int x = scan.nextInt();
			int y = scan.nextInt();
			Point firstP = new Point(y * 2, x * 2);
			int dir = scan.nextInt();
			int limit = scan.nextInt();
			
			draw(firstP, dir, limit);
		}
		
		int answer = CountOneByOneSquare();
		System.out.println(answer);
	
		
		scan.close();
	}
	
	private static int CountOneByOneSquare() {
		int cnt = 0;
		for (int row = 1; row < map.length - 1; row += 2) {
			for (int col = 1; col < map.length - 1; col += 2) {
				if (isSquareOk(row, col)) {
					cnt++;
				}
			}
		}
		return cnt;
	}

	private static boolean isSquareOk(int currRow, int currCol) {
		for (int i = 4; i < 8; ++i) {
			int nextRow = currRow + dRow[i];
			int nextCol = currCol + dCol[i];
			if (map[nextRow][nextCol] != 2) {
				return false;
			}
		}
		return true;
	}

	private static void draw(Point firstP, int dir, int limit) {
		Point currP = new Point(firstP);
		List<Integer> currDirList = new ArrayList<>();
		currDirList.add(dir);
		currP.moveAndDraw(dir);
		
		for (int cnt = 0; cnt < limit; ++cnt) {
			List<Integer> transDirList = createTransDirList(currDirList);
			
			for (int i = 0; i < transDirList.size(); ++i) {
				int transDir = transDirList.get(i);
				currP.moveAndDraw(transDir);
				currDirList.add(transDir);
			}
		}
	}

	private static List<Integer> createTransDirList(List<Integer> currDirList) {
		List<Integer> transDirList = new ArrayList<>();
		for (int i = currDirList.size() - 1; i >= 0; --i) {
			int currDir = currDirList.get(i);
			int transDir = opposite[currDir];
			transDirList.add(transDir);
		}
		return transDirList;
	}

	static class Point {
		int row;
		int col;
		
		public Point(int row, int col) {
			this.row = row;
			this.col = col;
		}


		public Point(Point p) {
			this.row = p.row;
			this.col = p.col;
		}
		
		public void moveAndDraw(int dir) {
			// 시작 꼭지점 색칠하고
			if (map[this.row][this.col] != 2) {
				map[this.row][this.col] = 2;
			}
			
			// 시작 꼭지점에서 변으로 움직이고 
			this.row += dRow[dir];
			this.col += dCol[dir];
			
			// 변을 색칠
			if (map[this.row][this.col] != 1) {
				map[this.row][this.col] = 1;
			}
			
			// 목적 꼭지점으로 이동
			this.row += dRow[dir];
			this.col += dCol[dir];
			
			// 목적 꼭지점 색칠
			if (map[this.row][this.col] != 2) {
				map[this.row][this.col] = 2;
			}
		}
	}
}