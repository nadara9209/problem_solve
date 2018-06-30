import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
	static int[][] map;
	static int N;
	static int X;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for(int tc = 1; tc <= T; ++tc) {
			N = scan.nextInt();
			X = scan.nextInt();
			
			map = new int[N][N];
			for(int row = 0; row < N; ++row) {
				for(int col = 0; col < N; ++col) {
					int val = scan.nextInt();
					map[row][col] = (val * 10);
				}
			}
			
			int answer = solve();
			System.out.println("#" + tc + " " + answer);
		}
		scan.close();
	}
	
	private static int solve() {
		int numOfPossibleCaseWithRow = 0;
		int numOfPossibleCaseWithCol = 0;
		
		for(int row = 0; row < N; ++row) {
			int[] heightArr = new int[N];
			for(int col = 0; col < N; ++col) {
				heightArr[col] = map[row][col];
			}
			if(isPossible(heightArr)) {
				numOfPossibleCaseWithRow++;
			}else {
			}
		}
		
		for(int col = 0; col < N; ++col) {
			int[] heightArr = new int[N];
			for(int row = 0; row < N; ++row) {
				heightArr[row] = map[row][col];
			}
			if(isPossible(heightArr)) {
				numOfPossibleCaseWithCol++;
			}
			else {
			}
		}
		
		return numOfPossibleCaseWithRow + numOfPossibleCaseWithCol;
	}

	private static boolean isPossible(int[] heightArr) {
		List<Point> turnPointList = new ArrayList<>();
		for(int i = 0; i < heightArr.length-1; ++i) {
			int flag = heightArr[i] - heightArr[i+1];
			// 둘의 높이차이가 1이상일때
			if(Math.abs(flag) > 10) {
				return false;
			}
			if(flag == 10) {
				// down
				turnPointList.add(new Point(i+1, false));
			}
			else if (flag == -10) {
				// up
				turnPointList.add(new Point(i, true));
			}
		}
		
		for(int i = 0; i < turnPointList.size(); ++i) {
			Point currP = turnPointList.get(i);
			if(currP.isUp) {
				int offset = currP.id;
				offset -= (X-1);
				if(isValid(offset)) {
					build(currP, heightArr);
				}
				else {
					return false;
				}
			}
			else {
				int offset = currP.id;
				offset += (X-1);
				if(isValid(offset)) {
					build(currP, heightArr);
				}
				else {
					return false;
				}
			}
		}
		
		boolean flag = true;
		for(int i = 0; i < heightArr.length-1; ++i) {
			if(!(heightArr[i] % 10 == 1 && heightArr[i+1] % 10 == 1)) {
				if(Math.abs(heightArr[i] - heightArr[i+1]) == 10) {
					flag = false;
				}
			}
			if(heightArr[i] % 10 > 1) {
				flag = false;
			}
		}
		
		return flag;
	}

	private static void build(Point currP, int[] heightArr) {
		if(currP.isUp) {
			int cnt = 0;
			int id = currP.id;
			while(cnt < X) {
				heightArr[id--] += 1;
				cnt++;
			}
		}
		else {
			int cnt = 0;
			int id = currP.id;
			while(cnt < X) {
				heightArr[id++] += 1;
				cnt++;
			}
		}
	}

	private static boolean isValid(int offset) {
		return (offset >= 0 && offset < N);
	}
}

class Point {
	int id;
	boolean isUp;
	
	Point(int id, boolean isUp) {
		this.id = id;
		this.isUp = isUp;
	}
}