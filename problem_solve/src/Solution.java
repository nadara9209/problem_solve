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
		// �ణ�� �� ������ �޾Ƽ� ���� �����ϰ�
		// �� 
		for(int row = 0; row < N; ++row) {
			for(int col = 0; col < N; ++col) {
				
			}
		}
		
		// ��
		for(int col = 0; col < N; ++col) {
			for(int row = 0; row < N; ++row) {
				
			}
		}
		
		// ����� �� ������ �˻��Ͽ� 
		// Ȱ�ַ� �Ǽ� ���� Ȯ���ϰ� 
		// ���ο� �°� cnt�� ���� ��Ų��.
		
		return answer;
	}
}