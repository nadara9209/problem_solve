import java.util.Scanner;

public class Solution {
	static int[][] map;
	static int N;
	static int X;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for(int tc = 1; tc < T; ++tc) {
			N = scan.nextInt();
			X = scan.nextInt();
			
			map = new int[N][N];
			for(int row = 0; row < N; ++row) {
				for(int col = 0; col < N; ++col) {
					int val = scan.nextInt();
					map[row][col] = val;
				}
			}
			
			System.out.println("#" + tc + " " + solve());
		}
		scan.close();
	}
	private static int solve() {
		int answer = 0;
		// 행간의 한 라인을 받아서 따로 저장하고
		// 행 
		for(int row = 0; row < N; ++row) {
			for(int col = 0; col < N; ++col) {
				
			}
		}
		
		// 열
		for(int col = 0; col < N; ++col) {
			for(int row = 0; row < N; ++row) {
				
			}
		}
		
		// 저장된 한 라인을 검사하여 
		// 활주로 건설 여부 확인하고 
		// 여부에 맞게 cnt를 증가 시킨다.
		
		return answer;
	}
}