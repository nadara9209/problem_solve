import java.util.Scanner;

public class Solution {
	static int V;
	static int E;
	static int[][] Tree;
	static int[] Parent;
	static int[] Depth;
	static int[] Size;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for (int tc = 1; tc <= T; ++tc) {
			V = scan.nextInt();
			E = scan.nextInt();
			Tree = new int[V+1][2];
			Parent = new int[V+1];
			Depth = new int[V+1];
			Size = new int[V+1];
			int nodeA = scan.nextInt();
			int nodeB = scan.nextInt();
			for (int i = 0; i < E; ++i) {
				int pNode = scan.nextInt();
				int cNode = scan.nextInt();
				if (Tree[pNode][0] != 0) {
					Tree[pNode][1] = cNode;
				}
				else {
					Tree[pNode][0] = cNode;
				}
				Parent[cNode] = pNode;
			}
			
			traversal(1, 0);
			
			String answer = solve(nodeA, nodeB);
			System.out.println("#" + tc + " " + answer);
		}
		scan.close();
	}
	
	private static int traversal(int node, int depth) {
		int cnt = 0;
		if (node != 0) {
			cnt++;
			cnt += traversal(Tree[node][0], depth+1);
			cnt += traversal(Tree[node][1], depth+1);
		}
		
		Depth[node] = depth;
		Size[node] = cnt;
		
		return cnt;
	}

	private static String solve(int nodeA, int nodeB) {
		int lca = getLca(nodeA, nodeB);
		return Integer.toString(lca) + " " + Integer.toString(Size[lca]);
	}

	private static int getLca(int nodeA, int nodeB) {
		int low = Depth[nodeA] > Depth[nodeB] ? nodeA : nodeB;
		int high = Depth[nodeA] > Depth[nodeB] ? nodeB : nodeA;
		
		int gap = Depth[low] - Depth[high];
		for (int i = 0; i < gap; ++i) {
			low = Parent[low];
		}
		
		while(low != high) {
			low = Parent[low];
			high = Parent[high];
		}
		
		return low;
	}
}

