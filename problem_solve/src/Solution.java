import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class Solution {
	static Queue<Long> q = new PriorityQueue<Long>();
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for(int tc = 1; tc <= T; ++tc) {
			int N = scan.nextInt();
			for(int i = 0; i < N; ++i) {
				q.offer(scan.nextLong());
			}
			
			long answer = solve();
			System.out.println("#" + tc + " " + answer);
		}
		scan.close();
	}

	private static long solve() {
		long ret = 0;
		while(!q.isEmpty()) {
			long tmp1 = q.poll();
			long tmp2 = q.poll();
			long sumTmp = tmp1 + tmp2;
			ret += sumTmp;
			if(q.isEmpty()) {
				break;
			}
			q.offer(sumTmp);
		}
		
		return ret;
	}
}