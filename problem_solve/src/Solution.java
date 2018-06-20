import java.util.Arrays;
import java.util.Scanner;

public class Solution {
	static int[] MEMO = new int[1001];
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for(int tc = 1; tc <= T; ++tc) {
			Arrays.fill(MEMO, 0);
			int N = scan.nextInt();
			int[] numArr = new int[N];
			for(int i = 0; i < N; ++i) {
				numArr[i] = scan.nextInt();
			}
			System.out.println("#" + tc + " " + solve(numArr));
		}
		scan.close();
	}

	private static int solve(int[] numArr) {
		int len = numArr.length;
		int retCnt = 0;
		// ������ ������ ���Ѵ�.
		for(int i = 0; i < len; ++i) {
			// ���ʿ� ����� �� �ʿ䰡 ���� ���
			int remainingLen = len - i;
			if(retCnt > remainingLen) {
				break;
			}
			int tmpCnt = count(numArr, i);
			if(retCnt < tmpCnt) {
				retCnt = tmpCnt;
			}
		}
		return retCnt;
	}

	private static int count(int[] numArr, int i) {
		if (MEMO[i] != 0) {
			return MEMO[i];
		}
		
		int endId = numArr.length-1;
		if(i == endId) {
			int size = 1;
			MEMO[i] = size;
			return size;
		}
		
		int prevVal = numArr[i];
		int maxSubSize = 0;
		for(int id = i+1; id <= endId; ++id) {
			// Ŀ���� �ű��
			int currVal = numArr[id];
			// ��� �������� ������ �� �ִ� ���
			if(prevVal <= currVal) {
				int tmpSize = count(numArr, id);
				if(maxSubSize < tmpSize) {
					maxSubSize = tmpSize;
				}
			}
		}
		
		int size = 1 + maxSubSize;
		MEMO[i] = size;
		return size;
	}
}
