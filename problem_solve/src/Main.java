import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class Main {
	static int[] Dheight = {-1,  1,  0,  0,  0,  0};
	static int[] Drow	= { 0,  0, -1,  1,  0,  0};
	static int[] Dcol 	= { 0,  0,  0,  0, -1,  1};
	static int M;
	static int N;
	static int H;
	static boolean inFirstAlreadyAllRipen = true;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		M = scan.nextInt();
		N = scan.nextInt();
		H = scan.nextInt();
		Tomato[][][] Tomatoes = new Tomato[H][N][M];

		// create
		for(int height = 0; height < H; ++height) {
			for(int row = 0; row < N; ++row) {
				for(int col = 0; col < M; ++col) {
					int val = scan.nextInt();
					Tomato tomato = null;
					switch (val) {
					// 익은
					case 1:
						tomato = new Tomato(height, row, col, true, true, false, 0);
						break;
					// 익지 않은
					case 0:
						tomato = new Tomato(height, row, col, true, false, false, 0);
						inFirstAlreadyAllRipen = false;
						break;
					// 빈 칸
					case -1:
						tomato = new Tomato(height, row, col, false, false, false, 0);
						break;
					default:
						break;
					}
					Tomatoes[height][row][col] = tomato;

				}
			}
		}

		System.out.println(solve(Tomatoes));
		scan.close();
	}

	private static int solve(Tomato[][][] Tomatoes) {
		int ret = 0;
		// 처음부터 다 익어있지 않다면
		if(!inFirstAlreadyAllRipen) {
			progressToRipenAll(Tomatoes);
			ret = count(Tomatoes);
		}
		return ret;
	}

	private static int count(Tomato[][][] Tomatoes) {
		boolean isImpossibleToRipenAll = false;
		int ret = 0;
		for(int height = 0; height < H; ++height) {
			for(int row = 0; row < N; ++row) {
				for(int col = 0; col < M; ++col) {
					Tomato tomato = Tomatoes[height][row][col];
					if(!tomato.isExisted){
						continue;
					}
					if(!tomato.isRipen) {
						isImpossibleToRipenAll = true;
					}
					int tmp = tomato.nDays;
					if(ret < tmp) {
						ret = tmp;
					}
				}
			}
		}

		// 모든 과정의 마무리 후에 토마토가 존재하지만 익지 않았다면
		if(isImpossibleToRipenAll) {
			ret = -1;
		}
		return ret;
	}

	private static void progressToRipenAll(Tomato[][][] Tomatoes) {
		Queue<Tomato> q = new ArrayDeque<>();
		// 맨 처음 익은 걸 q에 넣고
		for(int height = 0; height < H; ++height) {
			for(int row = 0; row < N; ++row) {
				for(int col = 0; col < M; ++col) {
					Tomato tomato = Tomatoes[height][row][col];
					if(tomato.isRipen) {
						q.offer(tomato);
					}
				}
			}
		}

		while(!q.isEmpty()) {
			Tomato tomato = q.poll();
			int currnDays = tomato.nDays;
			int currHeight = tomato.height;
			int currRow = tomato.row;
			int currCol = tomato.col;

			if (tomato.isVisited) {
				continue;
			}
			tomato.isVisited = true;
			tomato.isRipen = true;

			for(int i = 0; i < 6; ++i) {
				int nextHeight = currHeight + Dheight[i];
				int nextRow = currRow + Drow[i];
				int nextCol = currCol + Dcol[i];
				int nextnDays = currnDays + 1;

				if(nextRow < 0 || nextCol < 0 || nextHeight < 0 || nextRow >= N || nextCol >= M || nextHeight >= H) {
					continue;
				}

				Tomato nextTomato = Tomatoes[nextHeight][nextRow][nextCol];
				if(!nextTomato.isExisted || nextTomato.isVisited || nextTomato.isRipen) {
					continue;
				}

				nextTomato.isRipen = true;
				nextTomato.nDays = nextnDays;
				q.offer(nextTomato);
			}
		}
	}
}

class Tomato {
	int height;
	int row;
	int col;
	boolean isExisted;
	boolean isRipen;
	boolean isVisited;
	int nDays;

	Tomato(int height, int row, int col, boolean isExisted, boolean isRipen, boolean isVisited, int nDays) {
		this.height = height;
		this.row = row;
		this.col = col;
		this.isExisted = isExisted;
		this.isRipen = isRipen;
		this.isVisited = isVisited;
		this.nDays = nDays;
	}
}