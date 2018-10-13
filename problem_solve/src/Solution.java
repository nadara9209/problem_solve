import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class Solution {
	static int[][] map;
	static int[] dRow = { -1,  1,  0,  0 };
	static int[] dCol = {  0,  0, -1,  1 };
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for (int tc = 1; tc <= T; ++tc) {
			int N = scan.nextInt();
			
			map = new int[N][N];
			for (int row = 0; row < N; ++row) {
				String line = scan.next();
				for (int col = 0; col < N; ++col) {
					int val = line.charAt(col) - '0';
					map[row][col] = val;
				}
			}
			
			int answer = solve();
			System.out.println("#" + tc + " " + answer);
		}
		scan.close();
	}
	
	private static int solve() {
		int answer = getMinTime();
		return answer;
	}

	private static int getMinTime() {
		int limit = map.length;
		Point goalP = new Point(limit - 1, limit - 1);
		
		Queue<Point> q = new PriorityQueue<>();
		boolean[][] visited = new boolean[limit][limit];
		q.offer(new Point(0, 0));
		visited[0][0] = true;
		
		while(!q.isEmpty()) {
			Point currP = q.poll();
			int currRow = currP.row;
			int currCol = currP.col;
			int currTime = currP.time;
			
			if (currRow == goalP.row && currCol == goalP.col) {
				return currTime;
			}
			
			for (int i = 0; i < 4; ++i) {
				int nextRow = currRow + dRow[i];
				int nextCol = currCol + dCol[i];
				
				if(!isValid(nextRow, nextCol)) {
					continue;
				}
				
				if(visited[nextRow][nextCol]) {
					continue;
				}
				
				int nextTime = currTime + map[nextRow][nextCol];
				
				visited[nextRow][nextCol] = true;
				q.offer(new Point(nextRow, nextCol, nextTime));
				
			}
		}
		
		return 0;
	}

	private static boolean isValid(int row, int col) {
		return (row >= 0 && col >= 0 && row < map.length && col < map.length);
	}

	static class Point implements Comparable<Point>{
		int row;
		int col;
		int time;
		
		public Point(int row, int col) {
			this.row = row;
			this.col = col;
			this.time = 0;
		}

		public Point(int row, int col, int time) {
			this.row = row;
			this.col = col;
			this.time = time;
		}

		@Override
		public int compareTo(Point o) {
			if (this.time < o.time) {
				return -1;
			}
			else if (this.time > o.time) {
				return 1;
			}
			else {
				return 0;
			}
		}
	}
}