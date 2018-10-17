import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	static int[] dRow = { -1,  1,  0,  0 };
	static int[] dCol = {  0,  0, -1,  1 };
	
	static final int UP = 0;
	static final int DOWN = 1;
	static final int LEFT = 2;
	static final int RIGHT = 3;
	
	static int[] dirs = { UP, DOWN, LEFT, RIGHT }; 
	static int N;
	
	static final int LIMIT_CNT = 5;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		N = scan.nextInt();
		
		int[][] map = new int[N][N];
		for (int row = 0; row < N; ++row) {
			for (int col = 0; col < N; ++col) {
				map[row][col] = scan.nextInt();
			}
		}
		
		Board board = new Board(map);
		
//		board.moveBlocks(1, board.map);
//		board.moveBlocks(2, board.map);
//		board.moveBlocks(3, board.map);
		
//		for (int row = 0; row < N; ++row) {
//			for (int col = 0; col < N; ++col) {
//				System.out.print(board.map[row][col] + " ");
//			}
//			System.out.println();
//		}
		
		
		int answer = solve(board);
		System.out.println(answer);
		
		scan.close();
	}
	
	private static int solve(Board board) {
		int ret = 0;
		for (int i = 0; i <= LIMIT_CNT; ++i) {
			int tmp = board.checkAllCases(i);
			if (ret < tmp) {
				ret = tmp;
			}
		}
		return ret;
	}

	static class Board {
		int[][] map;
		
		public Board(int[][] inputMap) {
			this.map = inputMap;
		}

		public int checkAllCases(int limit) {
			List<Integer> selectedDirList = new ArrayList<>();
			
			int maxNumOfBlocks = check(selectedDirList, limit);
			
			return maxNumOfBlocks;
		}

		private int check(List<Integer> selectedDirList, int limit) {
			if (selectedDirList.size() == limit) {
//				for (int i = 0; i < selectedDirList.size(); ++i) {
//					System.out.print(selectedDirList.get(i) + " ");
//				}
//				System.out.println();
				return countMaxNumOfBlocks(selectedDirList);
			}
			
			int ret = -1;
			
			for (int i = 0; i < dirs.length - selectedDirList.size(); ++i) {
				selectedDirList.add(dirs[i]);
				int tmp = check(selectedDirList, limit);
				if (ret < tmp) {
					ret = tmp;
				}
				selectedDirList.remove(selectedDirList.size() - 1);
			}
			
			return ret;
		}

		private int countMaxNumOfBlocks(List<Integer> selectedDirList) {
			// 맵 복사 
			int[][] copyMap = new int[N][N];
			for (int row = 0; row < N; ++row) {
				copyMap[row] = this.map[row].clone();
			}
			
			// 다 움직여 보기
			for (int i = 0; i < selectedDirList.size(); ++i) {
				// 현재 이동하려는 방향
				int currDir = selectedDirList.get(i);
			
				this.moveBlocks(currDir, copyMap);
			}
			
			int ret = this.countMaxNOfBlocks(copyMap); 
			
			return ret;
		}

		private int countMaxNOfBlocks(int[][] copyMap) {
			int ret = 0;
			for (int row = 0; row < N; ++row) {
				for (int col = 0; col < N; ++col) {
					int tmp = copyMap[row][col];
					if (ret < tmp) {
						ret = tmp;
					}
				}
			}
			return ret;
		}

		private void moveBlocks(int currDir, int[][] copyMap) {
			switch (currDir) {
			
			case UP:
				for (int col = 0; col < N; ++col) {
					// 블럭 정리
					List<Integer> tmpList = new ArrayList<>();
					for (int row = 0; row < N; ++row) {
						int val = copyMap[row][col];
						if (val == 0) {
							continue;
						}
						tmpList.add(val);
					}
					
					// 블럭 합치기
					List<Integer> retList = new ArrayList<>();
					for (int id = 0; id < tmpList.size(); ++id) {
						int src = id;
						int firstVal = tmpList.get(src);
						int trg = src + 1;
						
						if (trg < tmpList.size()) {
							if (firstVal == tmpList.get(trg)) {
								firstVal *= 2;
								retList.add(firstVal);
								++id;
								continue;
							}
						}
						
						retList.add(firstVal);
					}
					
					// 블럭 최신화
					for (int row = 0; row < N; ++row) {
						if (row < retList.size()) {
							copyMap[row][col] = retList.get(row);
							continue;
						}
						copyMap[row][col] = 0;
					}
				}
				break;
			
			case DOWN:
				for (int col = 0; col < N; ++col) {
					// 블럭 정리
					List<Integer> tmpList = new ArrayList<>();
					for (int row = N-1; row >= 0; --row) {
						int val = copyMap[row][col];
						if (val == 0) {
							continue;
						}
						tmpList.add(val);
					}
					
					// 블럭 합치기
					List<Integer> retList = new ArrayList<>();
					for (int id = 0; id < tmpList.size(); ++id) {
						int src = id;
						int firstVal = tmpList.get(src);
						int trg = src + 1;
						
						if (trg < tmpList.size()) {
							if (firstVal == tmpList.get(trg)) {
								firstVal *= 2;
								retList.add(firstVal);
								++id;
								continue;
							}
						}
						
						retList.add(firstVal);
					}
					
					// 블럭 최신화
					int rowId = 0;
					for (int row = N-1; row >= 0; --row) {
						if (rowId < retList.size()) {
							copyMap[row][col] = retList.get(rowId++);
							continue;
						}
						copyMap[row][col] = 0;
					}
				}
				break;
			
			case LEFT:
				for (int row = 0; row < N; ++row) {
					// 블럭 정리
					List<Integer> tmpList = new ArrayList<>();
					for (int col = 0; col < N; ++col) {
						int val = copyMap[row][col];
						if (val == 0) {
							continue;
						}
						tmpList.add(val);
					}
					
					// 블럭 합치기
					List<Integer> retList = new ArrayList<>();
					for (int id = 0; id < tmpList.size(); ++id) {
						int src = id;
						int firstVal = tmpList.get(src);
						int trg = src + 1;
						
						if (trg < tmpList.size()) {
							if (firstVal == tmpList.get(trg)) {
								firstVal *= 2;
								retList.add(firstVal);
								++id;
								continue;
							}
						}
						
						retList.add(firstVal);
					}
					
					// 블럭 최신화
					for (int col = 0; col < N; ++col) {
						if (col < retList.size()) {
							copyMap[row][col] = retList.get(col);
							continue;
						}
						copyMap[row][col] = 0;
					}
				}
				break;
			
			case RIGHT:
				for (int row = 0; row < N; ++row) {
					// 블럭 정리
					List<Integer> tmpList = new ArrayList<>();
					for (int col = N-1; col >= 0; --col) {
						int val = copyMap[row][col];
						if (val == 0) {
							continue;
						}
						tmpList.add(val);
					}
					
					// 블럭 합치기
					List<Integer> retList = new ArrayList<>();
					for (int id = 0; id < tmpList.size(); ++id) {
						int src = id;
						int firstVal = tmpList.get(src);
						int trg = src + 1;
						
						if (trg < tmpList.size()) {
							if (firstVal == tmpList.get(trg)) {
								firstVal *= 2;
								retList.add(firstVal);
								++id;
								continue;
							}
						}
						
						retList.add(firstVal);
					}
					
					// 블럭 최신화
					int colId = 0;
					for (int col = N-1; col >= 0; --col) {
						if (colId < retList.size()) {
							copyMap[row][col] = retList.get(colId++);
							continue;
						}
						copyMap[row][col] = 0;
					}
				}
				break;
				
			default:
				break;
			}
		}
	}
}