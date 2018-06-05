import java.util.Arrays;

public class Solution {
	static int[] cheArr = new int[1000001];
	public static void main(String[] args) {
		Arrays.fill(cheArr, 1);
		solve();
	}
	
	private static void solve() {
		int num = cheArr.length-1;
		// 선택 인덱스의
		for(int i = 2; i <= num; ++i) {
			// 이미 체크했다면 continue
			if(cheArr[i] == 0) {
				continue;
			}
			// 모든 배수 인덱스의 원소를 0으로 바꾸고
			for(int j = i + i; j <= num; j += i) {
				cheArr[j] = 0;
			}
		}
		
		for(int i = 2; i <= num; ++i) {
			// 어떤 수의 배수가 아닌 인덱스는 소수
			if(cheArr[i] != 0) {
				System.out.print(i + " ");
			}
		}
	}
}
