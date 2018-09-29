import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Solution {
	static int[] dRow = {-1,  1,  0,  0};
	static int[] dCol = { 0,  0, -1,  1};
	static int[][] MAP;
	static Set<int[]> beadPosSet;
	static int[] positions;
	static int N;
	static int W;
	static int H;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for (int tc = 1; tc <= T; ++tc) {
			N = scan.nextInt();
			W = scan.nextInt();
			H = scan.nextInt();
			
			MAP = new int[H][W];
			for (int row = 0; row < H; ++row) {
				for (int col = 0; col < W; ++col) {
					MAP[row][col] = scan.nextInt();
				}
			}
			
			int answer = solve();
			System.out.println("#" + tc + " " + answer);
		}
		scan.close();
	}
	
	private static int solve() {
		initSet();
		int answer = countAllCases();
		return answer;
	}

	private static int countAllCases() {
		int ret = Integer.MAX_VALUE;
		Iterator<int[]> it = beadPosSet.iterator();
		while(it.hasNext()) {
			int[] beadPosition = it.next();
			Board board = new Board(beadPosition);
			board.shootBead();
			int tmp = board.nOfRemainingBlocks;
			if (ret > tmp) {
				ret = tmp;
			}
		}
		return ret;
	}

	private static void initSet() {
		positions = new int[W];
		for (int i = 0; i < W; ++i) {
			positions[i] = i;
		}
		
		beadPosSet = new HashSet<>();
		int[] candidatePos = new int[N];
		
		initSet(candidatePos, 0);
	}

	private static void initSet(int[] candidatePos, int depth) {
		if (depth == N) {
			beadPosSet.add(candidatePos.clone());
			return;
		}
		
		for (int i = 0; i < positions.length; ++i) {
			candidatePos[depth] = positions[i];
			initSet(candidatePos, depth+1);
		}
	}
	
	static class Board {
		int[][] map;
		int[] beadPosition;
		int nOfRemainingBlocks;
		
		public Board (int[] beadPosition) {
			map = new int[H][W];
			for (int i = 0; i < MAP.length; ++i) {
				this.map[i] = MAP[i].clone();
			}
			this.beadPosition = beadPosition;
			this.nOfRemainingBlocks = 0;
		}

		public void shootBead() {
			for (int col : this.beadPosition) {
				for (int row = 0; row < this.map.length; ++row) {
					int val = this.map[row][col];
					if (val == 0) {
						continue;
					}
					
					Point bustPoint = new Point(row, col, val); 
					this.bust(bustPoint);
					break;
				}
			}
			this.setNOfRemainingBlocks();
		}

		private void setNOfRemainingBlocks() {
			int cnt = 0;
			for (int row = 0; row < H; ++row) {
				for (int col = 0; col < W; ++col) {
					int val = this.map[row][col];
					if (val != 0) {
						cnt++;
					}
				}
			}
			this.nOfRemainingBlocks = cnt;
		}

		private void bust(Point bustPoint) {
			Queue<Point> q = new LinkedList<>();
			q.add(bustPoint);
			while (!q.isEmpty()) {
				Point currP = q.poll();
				this.destroyBlocks(currP, q);
			}
			this.update();
//			System.out.println();
//			for (int row = 0; row < H; ++row) {
//				for (int col = 0; col < W; ++col) {
//					System.out.print(this.map[row][col] + " ");
//				}
//				System.out.println();
//			}
//			int i = 0;
		}

		private void update() {
			int[][] copyMap = new int[H][W];
			for (int col = 0; col < W; ++col) {
				List<Integer> backUpList = new ArrayList<>();
				for (int row = H-1; row >= 0; --row) {
					int val = this.map[row][col];
					if (val == 0) {
						continue;
					}
					backUpList.add(val);
				}
				
				for (int i = 0; i < backUpList.size(); ++i) {
					copyMap[(H-1) - i][col] = backUpList.get(i);
				}
			}
			this.map = copyMap;
		}

		private void destroyBlocks(Point currP, Queue<Point> q) {
			int currRow = currP.row;
			int currCol = currP.col;
			int currVal = currP.val;
			this.map[currRow][currCol] = 0;
			if (currP.val == 1) {
				return;
			}
			for (int i = 0; i < 4; ++i) {
				int nextRow = currRow;
				int nextCol = currCol;
				for (int j = 1; j < currVal; ++j) {
					nextRow += dRow[i];
					nextCol += dCol[i];
					if (!isValid(nextRow, nextCol)) {
						break;
					}
					int val = this.map[nextRow][nextCol];
					if (val == 1) {
						this.map[nextRow][nextCol] = 0;
					}
					else if (val > 1) {
						this.map[nextRow][nextCol] = 0;
						q.offer(new Point(nextRow, nextCol, val));
					}
				}
			}
		}

		private boolean isValid(int nextRow, int nextCol) {
			return (nextRow >= 0 && nextCol >= 0 && nextRow < H && nextCol < W);
		}
	}

	static class Point {
		int row;
		int col;
		int val;
		
		public Point (int row, int col, int val) {
			this.row = row;
			this.col = col;
			this.val = val;
		}
	}
}