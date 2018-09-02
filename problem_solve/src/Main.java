import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	static int[] indegrees;
	static ArrayList<Integer>[] adjList;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int N = scan.nextInt();
		int M = scan.nextInt();
		indegrees = new int[N+1];
		adjList = new (ArrayList<Integer>)ArrayList[N+1];
		for (int i = 1; i < N+1; ++i) {
			adjList[i] = new ArrayList<>();
		}
		
		for(int i = 0; i < M; ++i) {
			int studentA = scan.nextInt();
			int studentB = scan.nextInt();
			adjList[studentA].add(studentB);
			indegrees[studentB]++;
		}
		
		solve();
		scan.close();
	}
}