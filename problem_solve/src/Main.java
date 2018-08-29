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
			// 첫 사이클의 경우 0과 현재 나무들 중 가장 큰 나무의 높이의 절반을 미드로 설정
			int mid = (left + right) / 2;
			long sum = 0;
			
			// 설정된 미드를 통해 도출되는 길이를 구하고
			for(int i = 0; i < n; ++i) {
				if(mid < trees[i]) {
					sum += trees[i] - mid;
				}
			}
			
			// 구하려는 값보다 크면 현재의 left를  mid+1로 설정 하여
			// mid를 키운다.
			if(sum >= m) {
				if(ret < mid) {
					ret = mid;
				}
				left = mid + 1;
			}
			else {
				// 작으면 갱신하지 않고 미드를 줄인다.
				right = mid - 1;
			}
		}
		
		return ret;
	}
}