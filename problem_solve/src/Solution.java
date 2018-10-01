import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Solution {
	static int[] dRow = {-1,  0,  1,  0};
	static int[] dCol = { 0, -1,  0,  1};
	
	static final int UP = 0;
	static final int LEFT = 1;
	static final int DOWN = 2;
	static final int RIGHT = 3;
	static int[] opposite = {DOWN, RIGHT, UP, LEFT};
	static int[][] nextDir;
	
	static Map<Integer, List<Point>> wormholes;
	
	static int[][] map;
	static int N;
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for (int tc = 1; tc <= T; ++tc) {
			N = scan.nextInt();
			map = new int[N][N];
			
			wormholes = new HashMap<>();
			
			for (int row = 0; row < N; ++row) {
				for (int col = 0; col < N; ++col) {
					int val = scan.nextInt();
					if (val >= 6 && val <= 10) {
						if (wormholes.containsKey(val)) {
							wormholes.get(val).add(new Point(row, col));
						} else {
							wormholes.put(val, new ArrayList<>());
							wormholes.get(val).add(new Point(row, col));
						}
					}
					map[row][col] = val;
				}
			}
			
			int answer = solve();
			System.out.println("#" + tc + " " + answer);
		}
		scan.close();
	}

	private static int solve() {
		InitNextDir();
		int answer = countAllCases();
		return answer;
	}


	private static void InitNextDir() {
		nextDir = new int[6][4];
		nextDir[1][UP] = opposite[UP];
		nextDir[1][LEFT] = UP;
		nextDir[1][DOWN] = RIGHT;
		nextDir[1][RIGHT] = opposite[RIGHT];
		
		nextDir[2][UP] = RIGHT;
		nextDir[2][LEFT] = DOWN;
		nextDir[2][DOWN] = opposite[DOWN];
		nextDir[2][RIGHT] = opposite[RIGHT];
		
		nextDir[3][UP] = LEFT;
		nextDir[3][LEFT] = opposite[LEFT];
		nextDir[3][DOWN] = opposite[DOWN];
		nextDir[3][RIGHT] = DOWN;
		
		nextDir[4][UP] = opposite[UP];
		nextDir[4][LEFT] = opposite[LEFT];
		nextDir[4][DOWN] = LEFT;
		nextDir[4][RIGHT] = UP;
		
		nextDir[5][UP] = opposite[UP];
		nextDir[5][LEFT] = opposite[LEFT];
		nextDir[5][DOWN] = opposite[DOWN];
		nextDir[5][RIGHT] = opposite[RIGHT];
	}

	private static int countAllCases() {
		int ret = 0;
		for (int row = 0; row < N; ++row) {
			for (int col = 0; col < N; ++col) {
				int val = map[row][col];
				if (val == 0) {
					int tmp = countScore(row, col);
					if (ret < tmp) {
						ret = tmp;
					}
				}
			}
		}
		return ret;
	}

	private static int countScore(int row, int col) {
		Point firstP = new Point(row, col);
		int ret = 0;
		for (int i = 0; i < 4; ++i) {
			int tmp = count(firstP, i);
			if (ret < tmp) {
				ret = tmp;
			}
		}
		
//		int ret = count(new Point(7, 0), UP);
		return ret;
	}

	private static int count(Point firstP, int Dir) {
		int score = 0;

		Point currP = new Point(firstP);
		do {
//			Point prevP = new Point(currP);
			currP.row += dRow[Dir];
			currP.col += dCol[Dir];
			
			if (!isValid(currP)){
				// 벽
				// 방향 반대 원래 자리 스코어 증가
				Dir = opposite[Dir];
				score++;
				continue;
			}
			
			int flag = map[currP.row][currP.col];
			
			if (flag == -1) {
				return score;
			}
			else if (flag >= 1 && flag <= 5) {
				// 블럭
				// 방향 변화, 좌표 고정
				Dir = nextDir[flag][Dir];
				score++;
			}
			else if (flag >= 6 && flag <= 10) {
				// 웜홀
				// 맵 상의 같은 웜홀 번호의 좌표로 변화, 방향 고정
				Point wormholeA = wormholes.get(flag).get(0);
//				System.out.println(wormholeA.row);
//				System.out.println(wormholeA.col);
				Point wormholeB = wormholes.get(flag).get(1);
//				System.out.println(wormholeB.row);
//				System.out.println(wormholeB.col);

				Point targetP = (currP.equals(wormholeA)) ? wormholeB : wormholeA;
				currP.set(targetP);
			}
			else {
				// 빈공간 혹은 시작점
			}
			
			// 시작점과, 블랙홀을 반복문의 시작에서 검사
		}while (!currP.equals(firstP));
		
		return score;
	}

	private static boolean isValid(Point p) {
		return isValid(p.row, p.col);
	}

	private static boolean isValid(int row, int col) {
		return (row >= 0 && col >= 0 && row < N && col < N);
	}
}

class Point {
	int row;
	int col;

	public Point(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public Point(Point p) {
		this(p.row, p.col);
	}
	
	public Point() {
		this(0, 0);
	}
	
	public void set(Point p) {
		this.row = p.row;
		this.col = p.col;
	}

	public boolean equals(Point p) {
		if (this.row != p.row) return false;
		if (this.col != p.col) return false;
		return true;
	}
}