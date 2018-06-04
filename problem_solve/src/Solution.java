import java.util.PriorityQueue;
import java.util.Scanner;

public class Solution {
	static int[][] Map;
	static boolean[][] Visited;
	static int[] Dx = {-1, 0, 1, 0};
	static int[] Dy = {0, -1, 0, 1};
	static Point StartP;
	static Point EndP;
	static int N;
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for(int tc = 1; tc <= T; ++tc) {
			N = scan.nextInt();
			Map = new int[N][N];
			Visited = new boolean[N][N];
			for(int row = 0; row < N; ++row) {
				for(int col = 0; col < N; ++col) {
					Map[row][col] = scan.nextInt();
					Visited[row][col] = false;
				}
			}
			
			int startX = scan.nextInt();
			int startY = scan.nextInt();
			int endX = scan.nextInt();
			int endY = scan.nextInt();
			
			StartP = new Point(startX, startY, 0);
			EndP = new Point(endX, endY, 0);
			
			System.out.println("#" + tc + " " + solve());
		}
		scan.close();
	}

	private static int solve() {
		Point retP = null;
		// 큐에 넣고
		PriorityQueue<Point> q = new PriorityQueue<>();
		q.offer(StartP);
		Visited[StartP.x][StartP.y] = true;
		
		// 큐가 다 빌 때까지
		while(!q.isEmpty()) {
			// 확인할 point를 큐에서 빼고
			Point p = q.poll();
			Visited[p.x][p.y] = true;
			if(p.x == EndP.x && p.y == EndP.y) {
				retP = p;
				break;
			}
			// 현재 시각
			int currTime = p.t;
			// point의 모든 근접배열로 이동
			for(int i = 0; i < 4; ++i) {
				int nextX = p.x + Dx[i];
				int nextY = p.y + Dy[i];
				int nextTime = currTime + 1;
				
				// 벽
				if(nextX < 0 || nextY < 0 || nextX >= N || nextY >= N) {
					continue;
				}
				// 장애물
				if(Visited[nextX][nextY] || Map[nextX][nextY] == 1) {
					continue;
				}
				
				int tOffset = 0;
				if(Map[nextX][nextY] == 2) {
					tOffset = 2 - (currTime % 3);
				}
				
				q.offer(new Point(nextX, nextY, nextTime + tOffset));
			}
		}
		
		return Visited[EndP.x][EndP.y] ? retP.t : -1;
	}
}

class Point implements Comparable<Point> {
	public int x;
	public int y;
	public int t;
	
	Point(int x, int y, int t) {
		this.x = x;
		this.y = y;
		this.t = t;
	}

	public int compareTo(Point o) {
		if(t - o.t > 0) {
			return 1;
		}
		else if (t - o.t < 0) {
			return -1;
		}
		else {
			return 0;
		}
	}
}