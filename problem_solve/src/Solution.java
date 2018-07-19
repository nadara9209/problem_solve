import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.Queue;

public class Solution {
	static Queue<Long> q = new PriorityQueue<Long>(100000);
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		for(int tc = 1; tc <= T; ++tc) {
			int N = Integer.parseInt(br.readLine());
			String line = br.readLine();
			String[] numbers = line.split(" ");
			
			q.clear();
			for(int i = 0; i < N; ++i) {
				q.offer(Long.parseLong(numbers[i]));
			}
			
			long answer = solve();
			System.out.println("#" + tc + " " + answer);
		}
		br.close();
	}

	private static long solve() {
		long ret = 0;
		while(q.size() >= 2) {
			long tmp1 = q.poll();
			long tmp2 = q.poll();
			long sumTmp = tmp1 + tmp2;
			ret += sumTmp;
			q.offer(sumTmp);
		}
		//ret += q.poll();
		
		return ret;
	}
}