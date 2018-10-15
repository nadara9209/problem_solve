import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Solution {
	static int[] dRow = { 0, -1,  1,  0,  0 };
	static int[] dCol = { 0,  0,  0, -1,  1 };
	static int[] opposite = { 0,  2,  1,  4,  3 };
	static int N;
	static int M;
	static int K;
	
	static List<Group> groupList;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for (int tc = 1; tc <= T; ++tc) {
			N = scan.nextInt();
			M = scan.nextInt();
			K = scan.nextInt();
			
			groupList = new ArrayList<>();
			for (int i = 0; i < K; ++i) {
				int row = scan.nextInt();
				int col = scan.nextInt();
				int nOfMicrobes = scan.nextInt();
				int dir = scan.nextInt();
				groupList.add(new Group(row, col, nOfMicrobes, dir));
			}
			
			int answer = solve();
			System.out.println("#" + tc + " " + answer);
			
		}
		scan.close();
	}
	
	private static int solve() {
		int nOfRemainingMicrobes = simulate();
		return nOfRemainingMicrobes;
	}

	private static int simulate() {
		for (int time = 0; time < M; ++time) {
			Map<Point, List<Group>> posData = new HashMap<>();
			// 이동
			for (int i = groupList.size()-1; i >= 0; --i) {
				Group currG = groupList.get(i);
				if (currG.nOfMicrobes == 0) {
					groupList.remove(currG);
				}
				currG.move();
				Point p = new Point(currG.p);
				
				if (!posData.containsKey(p)) {
					posData.put(p, new ArrayList<>());
				}
				posData.get(p).add(currG);
				
			}
			
			// 벽 충돌 및 합쳐짐.
			for (Point p : posData.keySet()) {
				List<Group> list = posData.get(p);
				if (posData.get(p).size() >= 2) {
					// 많은 미생물을 가진 쪽의 방향을 따른다.
					Collections.sort(list);
					int dir = list.get(list.size()-1).dir;
					int nOfMicrobes = 0;
					for (Group g : list) {
						nOfMicrobes += g.nOfMicrobes;
						groupList.remove(g);
					}
					groupList.add(new Group(p.row, p.col, nOfMicrobes, dir));
				}
				else {
					Group g = list.get(0);
					if (!g.isValid()) {
						g.dir = opposite[g.dir];
						g.nOfMicrobes /= 2;
					}
				}
			}
		}
		
		int sum = 0;
		for (int i = 0; i < groupList.size(); ++i) {
			sum += groupList.get(i).nOfMicrobes;
		}
		
		return sum;
	}

	static class Group implements Comparable<Group>{
		Point p;
		int nOfMicrobes;
		int dir;
		
		public Group(int row, int col, int nOfMicrobes, int dir) {
			this.p = new Point(row, col);
			this.nOfMicrobes = nOfMicrobes;
			this.dir = dir;
		}

		public boolean isValid() {
			return this.p.isValid();
		}

		public void move() {
			this.p.move(this.dir);
		}

		@Override
		public int compareTo(Group o) {
			if (this.nOfMicrobes < o.nOfMicrobes) {
				return -1;
			}
			else if (this.nOfMicrobes > o.nOfMicrobes) {
				return 1;
			}
			return 0;
		}
	}
	
	static class Point {
		int row;
		int col;
		
		public Point(int row, int col) {
			this.row = row;
			this.col = col;
		}
		
		public boolean isValid() {
			return (this.row >= 1 && this.col >= 1 && this.row < N-1 && this.col < N-1);
		}

		public Point(Point p) {
			this.row = p.row;
			this.col = p.col;
		}
		
		public void move(int dir) {
			this.row += dRow[dir];
			this.col += dCol[dir];
		}

		public boolean equals(Object obj) {
			if (this.row != ((Point) obj).row) {
				return false;
			}
			if (this.col != ((Point) obj).col) {
				return false;
			}
			return true;
		}
		
		public int hashCode() {
			return (this.row * N) + this.col;
		}
	}
}

