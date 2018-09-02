import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
	static int[] indegrees;
	static ArrayList<Integer>[] adjList;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int N = scan.nextInt();
		int M = scan.nextInt();
		indegrees = new int[N+1];
		adjList = (ArrayList<Integer>[]) new ArrayList[N+1];
		for (int i = 1; i < N+1; ++i) {
			adjList[i] = new ArrayList<>();
		}
		
		for (int i = 0; i < M; ++i) {
			int studentA = scan.nextInt();
			int studentB = scan.nextInt();
			adjList[studentA].add(studentB);
			indegrees[studentB]++;
		}
		
		solve(N, M);
		scan.close();
	}
	
	private static void solve(int n, int m) {
		ArrayList<Integer> retList = new ArrayList<>();
		Queue<Integer> q = new LinkedList<>();
		for (int i = 1; i <= n; ++i) {
			if (indegrees[i] == 0) {
				q.offer(i);
			}
		}
		
		while (!q.isEmpty()) {
			int studentA = q.poll();
			if (indegrees[studentA] == 0) {
				retList.add(studentA);
				for (int i = 0; i < adjList[studentA].size(); ++i) {
					int studentB = adjList[studentA].get(i);
					indegrees[studentB]--;
					if (indegrees[studentB] == 0) {
						q.offer(studentB);
					}
				}
			}
		}
		
		for (int i = 0; i < retList.size(); ++i) {
			System.out.print(retList.get(i) + " ");
		}
	}
}