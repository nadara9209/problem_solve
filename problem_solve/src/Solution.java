import java.util.Arrays;

public class Solution {
	static int[] cheArr = new int[1000001];
	public static void main(String[] args) {
		Arrays.fill(cheArr, 1);
		solve();
	}
	
	private static void solve() {
		int num = cheArr.length-1;
		// ���� �ε�����
		for(int i = 2; i <= num; ++i) {
			// �̹� üũ�ߴٸ� continue
			if(cheArr[i] == 0) {
				continue;
			}
			// ��� ��� �ε����� ���Ҹ� 0���� �ٲٰ�
			for(int j = i + i; j <= num; j += i) {
				cheArr[j] = 0;
			}
		}
		
		for(int i = 2; i <= num; ++i) {
			// � ���� ����� �ƴ� �ε����� �Ҽ�
			if(cheArr[i] != 0) {
				System.out.print(i + " ");
			}
		}
	}
}
