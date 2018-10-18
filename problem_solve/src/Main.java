import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
	static int[] dRow = { -1,  1,  0,  0 };
	static int[] dCol = {  0,  0, -1,  1 };
	
	static final int UP = 0;
	static final int DOWN = 1;
	static final int LEFT = 2;
	static final int RIGHT = 3;
	
	static int[] dL = { LEFT, RIGHT, DOWN, UP };
	static int[] dD = { RIGHT, LEFT, UP, DOWN };
	
	static int N;
	static boolean[][] map;
	
	static int K;
	static int L;
	static Map<Integer, Character> changeDirMap;
	
	static List<Point> snakeList;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		N = scan.nextInt();
		map = new boolean[N + 1][N + 1];
		
		K = scan.nextInt();
		for (int i = 0; i < K; ++i) {
			map[scan.nextInt()][scan.nextInt()] = true;
		}
		
		L = scan.nextInt();
		changeDirMap = new HashMap<>();
		for (int i = 0; i < L; ++i) {
			changeDirMap.put(scan.nextInt(), scan.next().charAt(0));
		}
		
		snakeList = new LinkedList<>();
		snakeList.add(new Point(1, 1));
		
		int answer = solve();
		System.out.println(answer);
		
		scan.close();
	}
	
	private static int solve() {
		int gameOverTime = simulate();
		return gameOverTime;
	}	


	private static int simulate() {
		int prevDir = RIGHT;
		int time;
		for (time = 1; time <= 10000; ++time) {
			Point prevHead = snakeList.get(0);
			Point currHead = new Point(prevHead);
			currHead.move(prevDir);
			
			// º®¿¡ ºÎµúÈ÷°Å³ª, ÀÚ±â ¸ö¿¡ ºÎµúÈù °æ¿ì
			if (!isValid(currHead.row, currHead.col) || snakeList.contains(currHead)) {
				return time;
			}
			// »ç°ú¸¦ ¸ÔÁö ¸øÇÑ °æ¿ì
			if (!map[currHead.row][currHead.col]) {
				snakeList.remove(snakeList.size() - 1);
			}
			snakeList.add(0, currHead);
			
			if (changeDirMap.containsKey(time)) {
				prevDir = transDir(prevDir, changeDirMap.get(time));
			}
		}
		
		return time;
	}

	private static boolean isValid(int row, int col) {
		return (row > 0 && col > 0 && row <= N && col <= N);
	}

	private static int transDir(int prevDir, char dir) {
		int retDir = 0;
		
		switch (dir) {
		case 'L':
			retDir = dL[prevDir];
			break;
		case 'D':
			retDir = dD[prevDir];
			break;	
		default:
			break;
		}
		
		return retDir;
	}

	static class Point {
		int row;
		int col;
		
		public Point(int row, int col) {
			this.row = row;
			this.col = col;
		}

		public Point(Point prevHead) {
			this.row = prevHead.row;
			this.col = prevHead.col;
		}
		
		public void move(int prevDir) {
			this.row += dRow[prevDir];
			this.col += dCol[prevDir];
		}
		
		@Override
		public boolean equals(Object obj) {
			if (((Point)obj).row != this.row) {
				return false;
			}
			if (((Point)obj).col != this.col) {
				return false;
			}
			return true;
		}
	}
}