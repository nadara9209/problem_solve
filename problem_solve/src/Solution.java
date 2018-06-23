import java.util.LinkedList;
import java.util.Scanner;

public class Solution {
	static int N;
	static int X;
	static int[][] map;
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for(int tc = 1; tc < T; ++tc) {
			N = scan.nextInt();
			X = scan.nextInt();
			// create
			map = new int[N][N];
			for(int row = 0; row < N; ++row) {
				for(int col = 0; col < N; ++col) {
					int height = scan.nextInt();
					map[row][col] = height;
				}
			}
			
			// 가능한 경우 수들의 합
			int answer = sumOfPossibleCases();
			System.out.println("#" + tc + " " + answer);
		}
		scan.close();
	}

	private static int sumOfPossibleCases() {
		LinkedList<Point> pointList = new LinkedList<>();
		
		int cntOfPossibleCases = 0;
		// 행
		for(int row = 0; row < N; ++row) {
			for(int col = 0; col < N; ++col) {
				checkArr[col] = map[row][col];
			}
			if(isAvailable(checkArr)) {
				cntOfPossibleCases++;
			}
		}
		// 열
		for(int col = 0; col < N; ++col) {
			for(int row = 0; row < N; ++row) {
				checkArr[row] = map[row][col];
			}
			if(isAvailable(checkArr)) {
				cntOfPossibleCases++;
			}
		}
		
		return cntOfPossibleCases;
	}

	// map의 한 행, 열을 받아와서 활주로 건설의 가능성 유무를 확인
	private static boolean isAvailable(int[] checkArr) {
		
		return false;
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