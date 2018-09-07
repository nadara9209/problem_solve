import java.util.Scanner;

public class Solution {
	static int limitCnt;
	static String prize;
	static int[][] memo = new int[1000000][20]; 
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
	
		for (int tc = 1; tc <= T; ++tc) {
			prize = scan.next();
			limitCnt = scan.nextInt();
			
			int answer = solve();
			System.out.println("#" + tc + " " + answer);
		}
		scan.close();
	}

	private static int solve() {
		int[] numbers = new int[prize.length()];
		for (int i = 0; i < numbers.length; ++i) {
			numbers[i] = prize.charAt(i) - '0';
		}
		
		int maxMoney = makeAllCases(numbers, 0);
		return maxMoney;
	}

	private static int makeAllCases(int[] numbers, int cnt) {
		if(memo[getSum(numbers)][cnt] != 0) {
			return memo[getSum(numbers)][cnt];
		}
		if(cnt == limitCnt) {
			return getSum(numbers);
		}
		
		int ret = 0;
		for (int i = 0; i < numbers.length-1; ++i) {
			for (int j = i+1; j < numbers.length; ++j) {
				int tmp = makeAllCases(swap(numbers, i, j), cnt+1);
				if(ret < tmp) {
					ret = tmp;
				}
			}
		}
		return memo[getSum(numbers)][cnt] = ret;
	}

	private static int getSum(int[] numbers) {
		int ret = 0;
		int cnt = numbers.length-1;
		for (int i = 0; i < numbers.length; ++i) {
			ret += numbers[i] * (Math.pow(10, cnt--));
		}
		return ret;
	}

	private static int[] swap(int[] numbers, int i, int j) {
		int[] tmp = numbers.clone();
		if(tmp[i] != tmp[j]) {
			tmp[i] ^= tmp[j];
			tmp[j] ^= tmp[i];
			tmp[i] ^= tmp[j];
		}
		
		return tmp;
	}
}