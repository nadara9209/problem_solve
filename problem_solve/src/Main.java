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
			// 벽 갯수 만족시 탈출
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
		// 벽 갯수를 만족한 case의 map정보 복사
		int[][] tmpMap = new int[lab.map.length][lab.map[0].length];
		for(int row = 0; row < N; ++row) {
			for(int col = 0; col < M; ++col) {
				tmpMap[row][col] = lab.map[row][col];
			}
		}
		
		// 방문지도
		int[][] visited = new int[N][M];
		
		// 순회하며 바이러스의 원천일 경우 재귀
		for(int row = 0; row < N; ++row) {
			for(int col = 0; col < M; ++col) {
				if(tmpMap[row][col] == 2) {
					spread(tmpMap, row, col, visited);
				}
			}
		}
		
		// 안전지역 갯수
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
		// 범위
		if(row < 0 || col < 0 || row >= N || col >= M) {
			return;
		}
		// 이미 방문
		if(visited[row][col] == 1) {
			return;
		}
		// 벽
		if(map[row][col] == 1) {
			return;
		}
		// 바이러스인데 방문
		if(map[row][col] == 2) {
			visited[row][col] = 1;
		}
		// 빈방인데 방문
		if(map[row][col] == 0) {
			map[row][col] = 2;
			visited[row][col] = 1;
		}
		
		//상
		spread(map, row + Drow[0], col + Dcol[0], visited);
		//하
		spread(map, row + Drow[1], col + Dcol[1], visited);
		//좌
		spread(map, row + Drow[2], col + Dcol[2], visited);
		//우
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