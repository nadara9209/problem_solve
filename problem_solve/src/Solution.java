import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;

public class Solution {
	static int[][] TailVertexArr = new int[2][100];
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = 10;
		for(int tc = 1; tc <= T; ++tc) {
			Arrays.fill(TailVertexArr[0], -1);
			Arrays.fill(TailVertexArr[1], -1);
			
			tc = scan.nextInt();
			int N = scan.nextInt();
			for(int i = 0; i < N; ++i) {
				int tailId = scan.nextInt();
				int headId = scan.nextInt();
				for(int j = 0; j < 2; ++j) {
					if(TailVertexArr[j][tailId] == -1) {
						TailVertexArr[j][tailId] = headId;
						break;
					}
				}
			}
			int answer = solve();
			System.out.println("#" + tc + " " + answer);
		}
		scan.close();
	}
	
	private static int solve() {
		int nCases = findPossiblePath();
		return nCases;
	}

	private static int findPossiblePath() {
		Queue<Integer> q = new ArrayDeque<Integer>();
		q.offer(0);
		
		while(!q.isEmpty()) {
			int startVertex = q.poll();
			for(int i = 0; i < 2; ++i) {
				int headVertex = TailVertexArr[i][startVertex];
				if(headVertex == 99) {
					return 1;
				}
				if(headVertex > 0) {
					q.offer(headVertex);
				}
			}
		}
		return 0;
	}
}