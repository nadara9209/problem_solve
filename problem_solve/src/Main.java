import java.util.Scanner;

public class Main {
	static int N;
	static int M;
	static int[] Drow = {-1,  1,  0,  0};
	static int[] Dcol = { 0,  0, -1,  1};
	static Board board;
	static Space startSpaceWithRedBall;
	static Space startSpaceWithBlueBall;
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		N = scan.nextInt();
		M = scan.nextInt();
		Space[][] map = new Space[N][M];
		for(int row = 0; row < N; ++row) {
			String str = scan.nextLine();
			char[] statusArr = str.toCharArray();
			for(int col = 0; col < M; ++col) {
				char status = statusArr[col];
				Space s = new Space(row, col, status);
				switch (status) {
				case 'R':
					startSpaceWithRedBall = s;
					break;
				case 'B':
					startSpaceWithBlueBall = s;
					break;
				default:
					break;
				}
				map[row][col] = s;
			}
		}
		board = new Board(map);
		System.out.println(solve());
		scan.close();
	}
	private static int solve() {
		
		return null;
	}
}

class Board {
	Space[][] map;
	
	Board(Space[][] map) {
		this.map = map;
	}
}

class Space {
	int row;
	int col;
	char status;
	int nSteps;
	
	Space(int row, int col, char status) {
		this.row = row;
		this.col = col;
		this.status = '\0';
		this.nSteps = 0;
	}
}