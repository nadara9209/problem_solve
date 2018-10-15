import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Solution {
	static int[][] map;
	static Point startP;
	static ArrayList<Integer>[] tunnels;
	
	static int[] opposite = {2, 3, 0, 1};
	static int[] dRow = {-1,  0,  1,  0};
	static int[] dCol = { 0, -1,  0,  1};
	static int limitT;
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for (int tc = 1; tc <= T; ++tc) {
			int N = scan.nextInt();
			int M = scan.nextInt();
			int R = scan.nextInt();
			int C = scan.nextInt();
			limitT = scan.nextInt();
			
			startP = new Point(R, C);
			
			map = new int[N][M];
			for (int row = 0; row < N; ++row) {
				for (int col = 0; col < M; ++col) {
					int val = scan.nextInt();
					map[row][col] = val;
				}
			}
			
			int answer = solve();
			System.out.println("#" + tc + " " + answer);
		}
		scan.close();
	}
	
	private static int solve() {
		initTunnels();
		int answer = countAllTraces();
		return answer;
	}

	private static int countAllTraces() {
		Queue<Point> q = new LinkedList<>();
		q.offer(startP);
		boolean[][] visited = new boolean[map.length][map[0].length];
		visited[startP.row][startP.col] = true;
		
		// 탈주범은 1시간 뒤에 웜홀을 통해 1거리를 갔다고 생각해야 한다.
		int retCnt = 1;
		
		while (!q.isEmpty()) {
			Point p = q.poll();
		
			int currRow = p.row;
			int currCol = p.col;
			int currT = p.time;
			int currTunnelType = map[currRow][currCol];
			
			for (int i = 0; i < 4; ++i) {
				if (tunnels[currTunnelType].get(i) != 0) {
					int nextRow = currRow + dRow[i];
					int nextCol = currCol + dCol[i];
					int nextT = currT + 1;
					
					// 시간당 1이라는 거리가 고정된 일반 bfs이기 때문에
					// nextT > limitT를 넘어버리는 순간 반환해도 됨.
					if(nextT > limitT) {
						return retCnt;
					}
					
					if(!isValid(nextRow, nextCol) || visited[nextRow][nextCol]) {
						continue;
					}
					
					int nextTunnelType = map[nextRow][nextCol];
					
					if(nextTunnelType == 0) {
						continue;
					}
					
					if(tunnels[nextTunnelType].get(opposite[i]) == 0) {
						continue;
					}
					
					// visited 체크하고 넣어야됨
					visited[nextRow][nextCol] = true;
					retCnt++;
					
					q.offer(new Point(nextRow, nextCol, nextT));
				}
			}
			
		}

		// 그러나 주어진 시간 안에 모든 갈 수 있는 경로를 다간 경우
		// 위의 ret에 걸릴지 않을 수도 있으므로 안전하게 반환 루트 설정.
		
		return retCnt;
	}

	private static boolean isValid(int nextX, int nextY) {
		return (nextX >= 0 && nextY >= 0 && nextX < map.length && nextY < map[0].length);
	}

	private static void initTunnels() {
		tunnels = (ArrayList<Integer>[]) new ArrayList[8];
		tunnels[1] = new ArrayList<>(Arrays.asList(1, 1, 1, 1));
		tunnels[2] = new ArrayList<>(Arrays.asList(1, 0, 1, 0));
		tunnels[3] = new ArrayList<>(Arrays.asList(0, 1, 0, 1));
		tunnels[4] = new ArrayList<>(Arrays.asList(1, 0, 0, 1));
		tunnels[5] = new ArrayList<>(Arrays.asList(0, 0, 1, 1));
		tunnels[6] = new ArrayList<>(Arrays.asList(0, 1, 1, 0));
		tunnels[7] = new ArrayList<>(Arrays.asList(1, 1, 0, 0));
	}
}

class Point {
	int row;
	int col;
	int time;
	
	public Point(int row, int col) {
		this.row = row;
		this.col = col;
		this.time = 1;
	}
	
	public Point(int row, int col, int time) {
		this.row = row;
		this.col = col;
		this.time = time;
	}
}