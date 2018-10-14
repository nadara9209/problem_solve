import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Solution {
	static List<Atom> atomList;
	static final int offset = 2000;
	
	// 실제 x, y에 대응하기 위해 상/하 방향 반대 
	static int[] dRow = { 1, -1,  0,  0 };
	static int[] dCol = { 0,  0, -1,  1 };
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for (int tc = 1; tc <= T; ++tc) {
			int N = scan.nextInt();
			atomList = new ArrayList<>();
			
			for (int i = 0; i < N; ++i) {
				int col = scan.nextInt();
				int row = scan.nextInt();
				int dir = scan.nextInt();
				int energy = scan.nextInt();
				
				// 0.5초 충돌 체크를 위한 좌표 확장
				int changedRow = (row * 2) + offset;
				int chaneedCol = (col * 2) + offset;
				atomList.add(new Atom(new Point(changedRow, chaneedCol), dir, energy));
			}
			
			int answer = solve();
			System.out.println("#" + tc + " " + answer);
		}
		scan.close();
	}

	private static int solve() {
		int maxEnergy = checkAllCases();
		return maxEnergy;
	}

	private static int checkAllCases() {
		int totalEnergy = 0;
		
		while (!atomList.isEmpty()) {
			// 충돌 정보 저장 map
			Map<Point, List<Atom>> collisionMap = new HashMap<>();
			
			// 이동
			for (Atom a : atomList) {
				a.move();
				Point currP = a.p;
				
				if (!collisionMap.containsKey(currP)) {
					List<Atom> candidateList = new ArrayList<>();
					candidateList.add(a);
					collisionMap.put(currP, candidateList);
				}
				else {
					collisionMap.get(currP).add(a);
				}
				
				// 위의 코드보다 약 1.2배의 시간을 소모하였다.
				// 새로운 좌표의 원소가 추가될 경우 계속해서 
				// 리스트를 생성하는 작업과 원소를 추가하는 작업을 비효율적으로 진행하고 있다.
//				if (!collisionMap.containsKey(currP)) {
//					collisionMap.put(currP, new ArrayList<>());
//				}
//				collisionMap.get(currP).add(a);
			}
			
			// 충돌 및 소멸, 충돌 불가능 처리
			for (Point p : collisionMap.keySet()) {
				List<Atom> candidateList = collisionMap.get(p);
				if (!isValid(p)) { // 충돌 불가능
					for (Atom a : candidateList) {
						atomList.remove(a);
					}
				}
				else { // 충돌 및 소멸
					if (candidateList.size() > 1) {
						for (Atom a : candidateList) {
							atomList.remove(a);
							totalEnergy += a.energy;
						}
					}
				}
			}
		}
		
		return totalEnergy;
	}

	private static boolean isValid(Point p) {
		return isValid(p.row, p.col);
	}

	private static boolean isValid(int row, int col) {
		return (row >= 0 && row <= 4001 && col >= 0 && col <= 4001);
	}

	static class Atom {
		Point p;
		int dir;
		int energy;
		
		public Atom(Point p, int dir, int energy) {
			this.p = p;
			this.dir = dir;
			this.energy = energy;
		}
	
		public void move() {
			this.p.move(this.dir);
		}
	}
	
	static class Point {
		int row;
		int col;
		
		public Point(int row, int col) {
			this.row = row;
			this.col = col;
		}
		
		public void move(int dir) {
			this.row += dRow[dir];
			this.col += dCol[dir];
		}

		@Override
		public boolean equals(Object obj) {
			if (this.row != ((Point) obj).row) return false;
			if (this.col != ((Point) obj).col) return false;
			return true;
		}
		
		@Override
		public int hashCode() {
			return (this.row * 111111) + this.col;
		}
	}
}