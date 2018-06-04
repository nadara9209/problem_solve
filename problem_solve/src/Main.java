import java.util.Scanner;

class Main {
	static int N;
	static int M;
	static int[] Drow = {-1, 1, 0, 0,}; 
	static int[] Dcol = {0 , 0, -1, 1};
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		N = scan.nextInt();
		M = scan.nextInt();
		int[][] labArr = new int[N][M];
		for(int row = 0; row < N; ++row) {
			for(int col = 0; col < M; ++col) {
				labArr[row][col] = scan.nextInt();
			}
		}
		System.out.println(solve(labArr));
		scan.close();
	}

	private static int solve(int[][] labArr) {
		int requiredWall = 3;
		Lab lab = new Lab(labArr, 0);
		int sizeOfMaxSafeSector = count(lab, requiredWall);
		return sizeOfMaxSafeSector;
	}

	private static int count(Lab lab, int requiredWall) {
		if(lab.nOfWall == requiredWall) {
			// �� ���� ������ Ż��
			return countSafeSector(lab);
		}
		
		int ret = 0;
		for(int row = 0; row < N; ++row) {
			for(int col = 0; col < M; ++col) {
				if(lab.isWallOk(row, col)) {
					lab.putDownWall(row, col);
					int tmp = count(lab, requiredWall);
					if(tmp > ret) {
						ret = tmp;
					}
					lab.putUpWall(row, col);
				}
			}
		}
		return ret;
	}

	private static int countSafeSector(Lab lab) {
		// �� ������ ������ case�� map���� ����
		int[][] tmpMap = new int[lab.map.length][lab.map[0].length];
		for(int row = 0; row < N; ++row) {
			for(int col = 0; col < M; ++col) {
				tmpMap[row][col] = lab.map[row][col];
			}
		}
		
		// �湮����
		int[][] visited = new int[N][M];
		
		// ��ȸ�ϸ� ���̷����� ��õ�� ��� ���
		for(int row = 0; row < N; ++row) {
			for(int col = 0; col < M; ++col) {
				if(tmpMap[row][col] == 2) {
					spread(tmpMap, row, col, visited);
				}
			}
		}
		
		// �������� ����
		int numOfSafeSector = 0;
		for(int row = 0; row < N; ++row) {
			for(int col = 0; col < M; ++col) {
				if(tmpMap[row][col] == 0) {
					numOfSafeSector++;
				}
			}
		}
		return numOfSafeSector;
	}

	private static void spread(int[][] map, int row, int col, int[][] visited) {
		// ����
		if(row < 0 || col < 0 || row >= N || col >= M) {
			return;
		}
		// �̹� �湮
		if(visited[row][col] == 1) {
			return;
		}
		// ��
		if(map[row][col] == 1) {
			return;
		}
		// ���̷����ε� �湮
		if(map[row][col] == 2) {
			visited[row][col] = 1;
		}
		// ����ε� �湮
		if(map[row][col] == 0) {
			map[row][col] = 2;
			visited[row][col] = 1;
		}
		
		//��
		spread(map, row + Drow[0], col + Dcol[0], visited);
		//��
		spread(map, row + Drow[1], col + Dcol[1], visited);
		//��
		spread(map, row + Drow[2], col + Dcol[2], visited);
		//��
		spread(map, row + Drow[3], col + Dcol[3], visited);
	}
}

class Lab {
	int[][] map;
	int nOfWall;
	
	Lab(int[][] map, int nOfWall) {
		this.map = map;
		this.nOfWall = nOfWall;
	}

	public void putUpWall(int row, int col) {
		this.map[row][col] = 0;
		this.nOfWall--;
	}

	public void putDownWall(int row, int col) {
		this.map[row][col] = 1;
		this.nOfWall++;
	}

	public boolean isWallOk(int row, int col) {
		if(map[row][col] != 0) {
			return false;
		}
		return true;
	}
}