import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class Main {
	static Tomato[][][] Tomatoes;
	static int H;
	static int M;
	static int N;
	
	static int[] Dheight = {-1,  1,  0,  0,  0,  0};
	static int[] Drow    = { 0,  0, -1,  1,  0,  0};
	static int[] Dcol    = { 0,  0,  0,  0, -1,  1};
	
	static boolean isAlreadyAllRipened = true;
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		M = scan.nextInt();
		N = scan.nextInt();
		H = scan.nextInt();
		// create Tomatoes
		Tomatoes = new Tomato[H][N][M];
		for(int height = 0; height < H; ++height) {
			for(int row = 0; row < N; ++row) {
				for(int col = 0; col < M; ++col) {
					int val = scan.nextInt();
					Tomato t = null;
					switch (val) {
					case -1:
						break;
					case 0:
						t = new Tomato(height, row, col, false);
						isAlreadyAllRipened = false;
						break;
					case 1:
						t = new Tomato(height, row, col, true);
						break;
					default:
						break;
					}
					Tomatoes[height][row][col] = t;
				}
			}
		}
		// process
		System.out.println(solve());
		scan.close();
	}
	
	private static int solve() {
		int minDaysOfAllRipened = 0;
		if(!isAlreadyAllRipened) {
			processToAllRipened();
			minDaysOfAllRipened = count();
		}
		return minDaysOfAllRipened;
	}

	private static int count() {
		int maxNumOfDays = 0;
		for(int height = 0; height < H; ++height) {
			for(int row = 0; row < N; ++row) {
				for(int col = 0; col < M; ++col) {
					Tomato t = Tomatoes[height][row][col];
					if(t == null) {
						continue;
					}
					if(!t.isRipened) {
						return -1;
					}
					int nDays = t.nDays;
					if(maxNumOfDays < nDays) {
						maxNumOfDays = nDays;
					}
				}
			}
		}
		return maxNumOfDays;
	}

	private static void processToAllRipened() {
		Queue<Tomato> q = new ArrayDeque<>();
		for(int height = 0; height < H; ++height) {
			for(int row = 0; row < N; ++row) {
				for(int col = 0; col < M; ++col) {
					Tomato t = Tomatoes[height][row][col];
					if(t == null) {
						continue;
					}
					if(t.isRipened) {
						q.offer(t);
					}
				}
			}
		}
		
		while(!q.isEmpty()) {
			Tomato t = q.poll();
			int currHeight = t.height;
			int currRow = t.row;
			int currCol = t.col;
			int currDays = t.nDays;
			
			for(int i = 0; i < 6; ++i) {
				int nextHeight = currHeight + Dheight[i];
				int nextRow = currRow + Drow[i];
				int nextCol = currCol + Dcol[i];
				int nextDays = currDays + 1;
				
				if(nextHeight < 0 || nextRow < 0 || nextCol < 0 ||
				   nextHeight >= H || nextRow >= N || nextCol >= M) {
					continue;
				}
				
				t = Tomatoes[nextHeight][nextRow][nextCol];
				if(t == null || t.isRipened) {
					continue;
				}
				
				t.isRipened = true;
				t.nDays = nextDays;
				q.offer(t);
			}
		}
	}
}

class Tomato {
	int height;
	int row;
	int col;
	boolean isRipened;
	int nDays;
	
	Tomato(int height, int row, int col, boolean isRipened) {
		this.height = height;
		this.row = row;
		this.col = col;
		this.isRipened = isRipened;
		this.nDays = 0;
	}
}