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
			
			// ������ ��� ������ ��
			int answer = sumOfPossibleCases();
			System.out.println("#" + tc + " " + answer);
		}
		scan.close();
	}

	private static int sumOfPossibleCases() {
		LinkedList<Point> pointList = new LinkedList<>();
		
		int cntOfPossibleCases = 0;
		// ��
		for(int row = 0; row < N; ++row) {
			for(int col = 0; col < N; ++col) {
				checkArr[col] = map[row][col];
			}
			if(isAvailable(checkArr)) {
				cntOfPossibleCases++;
			}
		}
		// ��
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

	// map�� �� ��, ���� �޾ƿͼ� Ȱ�ַ� �Ǽ��� ���ɼ� ������ Ȯ��
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