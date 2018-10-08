import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Solution {
	static int N;
	static int M;
	static int C;
	static int[][] honeyBarrels;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for (int tc = 1; tc <= T; ++tc) {
			N = scan.nextInt();
			M = scan.nextInt();
			C = scan.nextInt();
			
			honeyBarrels = new int[N][N];
			for (int row = 0; row < N; ++row) {
				for (int col = 0; col < N; ++col) {
					honeyBarrels[row][col] = scan.nextInt();
				}
			}
			
			int answer = solve();
			System.out.println("#" + tc + " " + answer);
		}
		scan.close();
	}
	
	private static int solve() {
		int answer = checkAllCases();
		return answer;
	}

	private static int checkAllCases() {
		int ret = 0;
		for (int row = 0; row < N; ++row) {
			for (int col = 0; col < N; ++col) {
				List<Integer> honeyListA = new ArrayList<>();
				boolean[][] visited = new boolean[N][N];
				int id = col;
				
				while (id < N) {
					honeyListA.add(honeyBarrels[row][id]);
					visited[row][id] = true;
					if (honeyListA.size() == M) {
						Worker workerA = new Worker(honeyListA);
						int tmp = checkAllCases(workerA, visited);
						if (ret < tmp) {
							ret = tmp;
						}
						break;
					}
					id++;
				}
			}
		}
		return ret;
	}

	private static int checkAllCases(Worker workerA, boolean[][] visited) {
		int ret = 0;
		for (int row = 0; row < N; ++row) {
			for (int col = 0; col < N; ++col) {
				// 벌통은 연결되야한다(추가)
				
				if (visited[row][col]) { // workerA 벌통 피하기
					continue;
				}
				List<Integer> honeyListB = new ArrayList<>();
				int id = col;
				
				while (id < N) {
					if (visited[row][id]) {
						break;
					}
					honeyListB.add(honeyBarrels[row][id]);
					if (honeyListB.size() == M) {
						Worker workerB = new Worker(honeyListB);
						int tmp = getTotalHoney(workerA, workerB);
						if (ret < tmp) {
							ret = tmp;
						}
						break;
					}
					id++;
				}
			}
		}
		
		return ret;
	}

	private static int getTotalHoney(Worker workerA, Worker workerB) {
		int totalHoneyOfA = workerA.getHoney();
		int totalHoneyOfB = workerB.getHoney();
		return totalHoneyOfA + totalHoneyOfB;
	}

	static class Worker {
		int coverage;
		List<Integer> honeyList;
		
		public Worker(List<Integer> honeyList) {
			this.coverage = C;
			this.honeyList = honeyList;
		}

		public int getHoney() {
			int coverage = 0;
			List<Integer> tmpList = new ArrayList<>();
			Collections.sort(this.honeyList, Collections.reverseOrder());
			for (int i = 0; i < this.honeyList.size(); ++i) {
				int val = this.honeyList.get(i);
				coverage += val;
				if (coverage > this.coverage) {
					break;
				}
				tmpList.add(val);
			}
			
			int ret = 0;
			for (int i = 0; i < tmpList.size(); ++i) {
				ret += (tmpList.get(i) * tmpList.get(i));
			}
			return ret;
		}
	}
}