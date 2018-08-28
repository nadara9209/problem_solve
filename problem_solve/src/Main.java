import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		int N = scan.nextInt();
		long M = scan.nextLong();
		
		int[] Numbers = new int[N];
		for(int i = 0; i < N; ++i) {
			Numbers[i] = scan.nextInt();
		}
		
		int answer = solve(Numbers, N, M);
		System.out.println(answer);
		scan.close();
	}

	private static int solve(int[] numbers, int n, long m) {
		int startId = 0;
		int endId = 0;
		int cnt = 0;
		
		while(endId <= n-1) {
			long result = 0;
			for(int i = startId; i <= endId; ++i) {
				result += numbers[i];
			}
			
			if(result < m) {
				endId++;
			}
			else if(result == m) {
				endId++;
				cnt++;
			}
			else {
				startId++;
			}
		}
		
		return cnt;
	}
}