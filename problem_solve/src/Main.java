import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int N = scan.nextInt();
		int M = scan.nextInt();
		int maxVal = 0;
		
		int[] Trees = new int[N];
		for(int i = 0; i < N; ++i) {
			int val = scan.nextInt();
			if(maxVal < val) {
				maxVal = val;
			}
			Trees[i] = val;
		}
		
		int answer = solve(maxVal, N, M, Trees);
		System.out.println(answer);
		scan.close();
	}

	private static int solve(int maxVal, int n, int m, int[] trees) {
		int left = 0;
		int right = maxVal;
		int ret = 0;
		
		while(left <= right) {
			// ù ����Ŭ�� ��� 0�� ���� ������ �� ���� ū ������ ������ ������ �̵�� ����
			int mid = (left + right) / 2;
			long sum = 0;
			
			// ������ �̵带 ���� ����Ǵ� ���̸� ���ϰ�
			for(int i = 0; i < n; ++i) {
				if(mid < trees[i]) {
					sum += trees[i] - mid;
				}
			}
			
			// ���Ϸ��� ������ ũ�� ������ left��  mid+1�� ���� �Ͽ�
			// mid�� Ű���.
			if(sum >= m) {
				if(ret < mid) {
					ret = mid;
				}
				left = mid + 1;
			}
			else {
				// ������ �������� �ʰ� �̵带 ���δ�.
				right = mid - 1;
			}
		}
		
		return ret;
	}
}