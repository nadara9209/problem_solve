import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Solution {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for(int tc = 1; tc <= T; ++tc) {
			int N = scan.nextInt();
			int M = scan.nextInt();
			int K = scan.nextInt();
			
			int[][] adjMatrix = new int[1001][1001];
			
			int tailVertex = 0;
			int headVertex = 0;
			for(int i = 0; i < M; ++i) {
				tailVertex = scan.nextInt();
				headVertex = scan.nextInt();
				adjMatrix[tailVertex][headVertex] = 1;
			}
			
			int answer = solve(N, K, adjMatrix);
			System.out.println("#" + tc + " " + answer);
		}
		scan.close();
	}

	private static int solve(int n, int k, int[][] adjMatrix) {
		Queue<Node> q = new LinkedList<>();
		q.offer(new Node(1));
		
		while(!q.isEmpty()) {
			Node node = q.poll();
			int currTime = node.time;
			if(node.tailVertex == n) {
				if(currTime <= k) {
					return currTime;
				}
			}
			for(int headVertex = 1; headVertex < n+1; ++headVertex) {
				if(adjMatrix[node.tailVertex][headVertex] == 1) {
					q.offer(new Node(headVertex, currTime+1));
				}
			}
		}
		
		return -1;
	}
}

class Node {
	int tailVertex;
	int time;
	
	Node(int tailVertex) {
		this.tailVertex = tailVertex;
		this.time = 0;
	}
	
	Node(int tailVertex, int time) {
		this.tailVertex = tailVertex;
		this.time = time;
	}
}