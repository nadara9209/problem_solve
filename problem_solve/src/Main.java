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
		adjList = new ArrayList[N+1];
		for (int i = 1; i < N+1; ++i) {
			adjList[i] = new ArrayList<>();
		}
		
		for (int i = 0; i < M; ++i) {
			int studentA = scan.nextInt();
			int studentB = scan.nextInt();
			// 간선 정보 저장
			adjList[studentA].add(studentB);
			// 선수 정보 저장
			indegrees[studentB]++;
		}
		
		solve(N, M);
		scan.close();
	}
	
	private static void solve(int n, int m) {
		ArrayList<Integer> retList = new ArrayList<>();
		Queue<Integer> q = new LinkedList<>();
		// 초기 선수가 없는 학생을 큐에 넣고
		for (int i = 1; i <= n; ++i) {
			if (indegrees[i] == 0) {
				q.offer(i);
			}
		}
		
		// bfs
		while (!q.isEmpty()) {
			int studentA = q.poll();
			// 선수가 없다면
			if (indegrees[studentA] == 0) {
				// 리턴
				retList.add(studentA);
				for (int i = 0; i < adjList[studentA].size(); ++i) {
					// 선수가 없어 리턴된 정점의 간선 관계인 정점들을 뽑아
					int studentB = adjList[studentA].get(i);
					// 관계 삭제
					indegrees[studentB]--;
					// 관계 삭제 후 선수정점의 갯수가 0이 되어버린 정점들을 다시 큐에 넣음
					if (indegrees[studentB] == 0) {
						q.offer(studentB);
					}
					// 선수가 남아 있다면 무시. 어짜피 다시 만나게 됨.
				}
			}
		}
		
		for (int i = 0; i < retList.size(); ++i) {
			System.out.print(retList.get(i) + " ");
		}
	}
}