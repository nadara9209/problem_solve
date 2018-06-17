import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
	static int[] Drow = {-1,  0,  1,  0};
	static int[] Dcol = { 0,  1,  0, -1};
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int N = scan.nextInt();
		int M = scan.nextInt();
		int[][] map = new int[N][M];
		LinkedList<CCTV> listOfCCTV = new LinkedList<>();
		
		// create
		for(int row = 0; row < N; ++row) {
			for(int col = 0; col < M; ++col) {
				int val = scan.nextInt();
				if(val > 0 && val < 6) {
					listOfCCTV.add(new CCTV(row, col, val));
					continue;
				}
				map[row][col] = val;
			}
		}
		
		// 초기 정보
		Room room = new Room(map, listOfCCTV);
		int answer = solve(room);
		System.out.println(answer);
		scan.close();
	}
	
	private static int solve(Room room) {
		int nSafety = 0;
		nSafety = makeAllCases(room, 0);
		return nSafety;
	}

	private static int makeAllCases(Room room, int i) {
		// 마지막 CCTV를 돌린 후
		if(i == room.listOfCCTV.size()) {
			return room.getNumOfSafetySector();
		}
		int ret = Integer.MAX_VALUE;
		for(int cnt = 0; cnt < 4; ++cnt) {
			room.listOfCCTV.get(i).rotate();
			int tmp = makeAllCases(room, i+1);
			if(ret > tmp) {
				ret = tmp;
			}
		}
		return ret;
	}

	static class Room {
		int[][] map;
		LinkedList<CCTV> listOfCCTV;
		
		Room(int[][] map, LinkedList<CCTV> listOfCCTV) {
			this.map = map;
			this.listOfCCTV = listOfCCTV;
		}

		public int getNumOfSafetySector() {
			int[][] tmpMap = this.copyMap(); 
			for(int i = 0; i < this.listOfCCTV.size(); ++i) {
				CCTV c = this.listOfCCTV.get(i);
				this.monitor(c, tmpMap);
			}
			int nSafety = this.count(tmpMap);
			return nSafety;
		}
		
		private int[][] copyMap() {
			int n = map.length;
			int m = map[0].length;
			
			int[][] tmpMap = new int[n][m];
			for(int row = 0; row < n; ++row) {
				for(int col = 0; col < m; ++col) {
					tmpMap[row][col] = this.map[row][col];
				}
			}
			return tmpMap;
		}
		
		private void monitor(CCTV c, int[][] tmpMap) {
			for(int i = 0; i < c.dirList.size(); ++i) {
				if(c.dirList.get(i) == 1) {
					this.check(c.row, c.col, i, tmpMap);
				}
			}
		}
		
		private void check(int row, int col, int i, int[][] tmpMap) {
			while(this.isValid(row, col, tmpMap)) {
				// 벽
				if(tmpMap[row][col] == 6) {
					break;
				}
				tmpMap[row][col] = 1;
				row += Drow[i];
				col += Dcol[i];
			}
		}
		
		private boolean isValid(int row, int col, int[][] tmpMap) {
			return (row >= 0 && col >= 0 && row < tmpMap.length && col < tmpMap[0].length);
		}
		
		private int count(int[][] tmpMap) {
			int cnt = 0;
			for(int row = 0; row < tmpMap.length; ++row) {
				for(int col = 0; col < tmpMap[0].length; ++col) {
					if(tmpMap[row][col] == 0) {
						cnt++;
					}
				}
			}
			return cnt;
		}
	}

	static class CCTV {
		int row;
		int col;
		LinkedList<Integer> dirList;
		
		CCTV(int row, int col, int n) {
			this.row = row;
			this.col = col;
			
			switch (n) {
			case 1: // 1번 
				this.dirList = new LinkedList<>(Arrays.asList(1, 0, 0, 0));
				break;
			case 2: // 2번 
				this.dirList = new LinkedList<>(Arrays.asList(1, 0, 1, 0));
				break;
			case 3: // 3번 
				this.dirList = new LinkedList<>(Arrays.asList(1, 1, 0, 0));
				break;
			case 4: // 4번 
				this.dirList = new LinkedList<>(Arrays.asList(1, 1, 1, 0));
				break;
			case 5: // 5번 
				this.dirList = new LinkedList<>(Arrays.asList(1, 1, 1, 1));
				break;
			default:
				break;
			}
		}

		public void rotate() {
			int dir = dirList.removeFirst();
			dirList.addLast(dir);
		}
	}
}

