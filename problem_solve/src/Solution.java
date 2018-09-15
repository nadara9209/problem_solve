import java.util.Scanner;

public class Solution {
	static int V;
	static int E;
	static int[][] Tree;
	static int[][] Parent;
	static int[] Depth;
	static int[] Size;
	static final int MAX_LIMIT = 14;	// 2^14 >= 10000
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for (int tc = 1; tc <= T; ++tc) {
			V = scan.nextInt();
			E = scan.nextInt();
			Tree = new int[V+1][2];
			Parent = new int[V+1][MAX_LIMIT+1];
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
				// 순서가 정해져 있지 않다면 순회 후 부모 : 자식 관계 지정
				Parent[cNode][0] = pNode;
			}
			
			// 모든 Parent[i][0]를 구해놨기 때문에
			for (int i = 0; i < MAX_LIMIT; ++i) {
				
				for (int j = 1; j <= V; ++j) {
					if (Parent[j][i] != 0) {
						Parent[j][i+1] = Parent[Parent[j][i]][i];
					}
				}
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
		for (int i = 0; gap != 0; ++i) {
			if ((gap % 2) == 0) {
				low = Parent[low][i];
			}
			gap /= 2;
		}
		
		if (low != high) {
			for (int i = MAX_LIMIT; i >= 0; --i) {
				if (Parent[low][i] != 0 && Parent[low][i] != Parent[high][i]) {
					low = Parent[low][i];
					high = Parent[high][i];
				}
			}
			low = Parent[low][0];
		}
		
		return low;
	}
}

