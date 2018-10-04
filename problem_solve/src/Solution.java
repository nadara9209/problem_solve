import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Solution {
	// 0-X 1-상 2-우 3-하 4-좌 
	static int[] dRow = { 0, -1,  0,  1,  0 };
	static int[] dCol = { 0,  0,  1,  0, -1 };
	// 이동시간, BC 갯수
	static int M;
	static int A;
	// userA, userB의 이동정보 
	static List<Integer> moveInfoList_A;
	static List<Integer> moveInfoList_B;
	// BC 정보
	static List<BC> BClist;
	// map 정보
	static Space[][] map;
	static final int LIMIT_OF_MAP = 11;
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		int T = scan.nextInt();
		for (int tc = 1; tc <= T; ++tc) {
			M = scan.nextInt();
			A = scan.nextInt();
			
			// userA
			moveInfoList_A = new ArrayList<>();
			for (int i = 0; i < M; ++i) {
				moveInfoList_A.add(scan.nextInt());
			}
			// userB
			moveInfoList_B = new ArrayList<>();
			for (int i = 0; i < M; ++i) {
				moveInfoList_B.add(scan.nextInt());
			}
			
			// BClist
			BClist = new ArrayList<>();
			for (int i = 0; i < A; ++i) {
				Location location = new Location(scan.nextInt(), scan.nextInt());
				BClist.add(new BC(location, scan.nextInt(), scan.nextInt()));
			}
			
			int answer = solve();
			System.out.println("#" + tc + " " + answer);
		}
		
//		List<Charge> list1 = new ArrayList<>();
//		List<Charge> list2 = new ArrayList<>();
//		
//		list1.add(new Charge(1, 2));
//		list1.add(new Charge(1, 1));
//		list2.add(new Charge(1, 2));
//		System.out.println(list1.indexOf(new Charge(1, 1)));
//		System.out.println(list2.contains(new Charge(1, 1)));
//		
		scan.close();
	}

	private static int solve() {
		// 맵 생성
		map = new Space[LIMIT_OF_MAP][LIMIT_OF_MAP];
		for (int row = 1; row < LIMIT_OF_MAP; ++row) {
			for (int col = 1; col < LIMIT_OF_MAP; ++col) {
				map[row][col] = new Space();
			}
		}
		// User 생성
		User userA = new User(new Location(1, 1), moveInfoList_A);
		User userB = new User(new Location(10, 10), moveInfoList_B);
		
		// BC 생성
		constructBC();
		
		// 이동 및 충전
		for (int i = 0; i <= M; ++i) {
			if (i == 0) {
				charge(userA, userB);
				continue;
			}
			userA.move();
			userB.move();
			charge(userA, userB);
		}
		
		// 결과
		int totalChargeOf2Users = userA.totalCharge + userB.totalCharge;
		
		return totalChargeOf2Users;
	}

	private static void charge(User userA, User userB) {
		List<Charge> chargeAmountListUserA = new ArrayList<>(map[userA.location.row][userA.location.col].chargeList);
		List<Charge> chargeAmountListUserB = new ArrayList<>(map[userB.location.row][userB.location.col].chargeList);
		List<Charge> duplicatedList = new ArrayList<>();
		
		for (int i = chargeAmountListUserB.size()-1; i >= 0; --i) {
			Charge charge = chargeAmountListUserB.get(i);
			// 겹쳤다
			if (chargeAmountListUserA.contains(charge)) {
				chargeAmountListUserA.remove(chargeAmountListUserA.indexOf(charge));
				chargeAmountListUserB.remove(chargeAmountListUserB.indexOf(charge));
				duplicatedList.add(charge);
			}
		}
		
		Collections.sort(chargeAmountListUserA);
		Collections.sort(chargeAmountListUserB);
		Collections.sort(duplicatedList);
		
		// 상관 엇ㅂ다.
		if (chargeAmountListUserA.isEmpty() && chargeAmountListUserB.isEmpty() && duplicatedList.isEmpty()) {
			
		}
		else if (duplicatedList.isEmpty()) {
			userA.totalCharge += getMax(chargeAmountListUserA); 
			userB.totalCharge += getMax(chargeAmountListUserB); 
		}
		else if (chargeAmountListUserA.isEmpty() && chargeAmountListUserB.isEmpty()) {
			if (duplicatedList.size() > 1) {
				userA.totalCharge += duplicatedList.get(duplicatedList.size()-1).performance +
									 duplicatedList.get(duplicatedList.size()-2).performance;
			}
			else {
				userA.totalCharge += duplicatedList.get(duplicatedList.size()-1).performance;
			}
		}
		else {
			ArrayList<Integer> list = new ArrayList<>();
			list.add(getMax(chargeAmountListUserA));
			list.add(getMax(chargeAmountListUserB));
			list.add(getMax(duplicatedList));
			list.add(getMaxSecond(duplicatedList));
			Collections.sort(list);
			userA.totalCharge += list.get(list.size()-1) + list.get(list.size()-2);
		}
	}

	private static int getMaxSecond(List<Charge> inputList) {
		if (inputList.size() >= 2) {
			return inputList.get(inputList.size()-2).performance;
		}
		return 0;
	}

	private static int getMax(List<Charge> inputList) {
		return (inputList.isEmpty()) ? 0 : inputList.get(inputList.size()-1).performance;
	}
	

	private static void constructBC() {
		for (int i = 0; i < BClist.size(); ++i) {
			BC bc = BClist.get(i);
			Location location = bc.location;
			int coverage = bc.coverage;
			int performance = bc.performance;
			boolean[][] visited = new boolean[LIMIT_OF_MAP][LIMIT_OF_MAP];
			
			construct(location.row, location.col, coverage, performance, i, visited);
		}
	}

	private static void construct(int row, int col, int coverage, int performance, int bc_num, boolean[][] visited) {
		if (coverage < 0) {
			return;
		}
		
		if (!isValid(row, col)) {
			return;
		}
		
		if (!visited[row][col]) {
			visited[row][col] = true;
			map[row][col].chargeList.add(new Charge(performance, bc_num));
		}
		
		for (int i = 1; i < 5; ++i) {
			construct(row + dRow[i], col + dCol[i], coverage-1, performance, bc_num, visited);
		}
	}

	private static boolean isValid(int row, int col) {
		return (row >= 1 && col >= 1 && row <= 10 && col <= 10);
	}

	static class User {
		Location location;
		List<Integer> moveInfoList;
		int totalCharge;
		
		public User(Location location, List<Integer> moveInfoList) {
			this.location = location;
			this.moveInfoList = moveInfoList;
			this.totalCharge = 0;
		}

		public void move() {
			int currDir = this.moveInfoList.remove(0);
			this.location.move(currDir);
		}
	}
	
	static class BC {
		Location location;
		int coverage;
		int performance;
		
		public BC(Location location, int coverage, int performance) {
			this.location = location;
			this.coverage = coverage;
			this.performance = performance;
		}
	}
	
	static class Space {
		List<Charge> chargeList;
		
		public Space() {
			this.chargeList = new ArrayList<>();
		}
	}
	
	static class Location {
		int col;
		int row;
		
		public Location(int col, int row) {
			this.col = col;
			this.row = row;
		}

		public void move(int currDir) {
			this.row += dRow[currDir];
			this.col += dCol[currDir];
		}
	}
	
	static class Charge implements Comparable<Charge> {
		int performance;
		int bc_num;
		
		public Charge(int performance, int bc_num) {
			this.performance = performance;
			this.bc_num = bc_num;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this.performance != ((Charge)obj).performance) {
				return false;
			}
			if (this.bc_num != ((Charge)obj).bc_num) {
				return false;
			}
			return true;
		}

		@Override
		public int compareTo(Charge o) {
			if (this.performance < o.performance) {
				return -1;
			}
			else if (this.performance > o.performance) {
				return 1;
			}
			return 0;
		}
	}
}

