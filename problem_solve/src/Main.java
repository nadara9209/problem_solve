import java.util.Arrays;
import java.util.Scanner;

public class Main {
	static int[] computers;
	static Line[] lines;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int N = scan.nextInt();
		int M = scan.nextInt();
		computers = new int[N+1];
		init();
		
		lines = new Line[M];
		for (int i = 0; i < M; ++i) {
			int prev = scan.nextInt();
			int next = scan.nextInt();
			int cost = scan.nextInt();
			lines[i] = new Line(prev, next, cost);
 		}
		
		int answer = solve();
		System.out.println(answer);
		scan.close();
	}
	
	private static int solve() {
		int ret = 0;
		Arrays.sort(lines);
		for (int i = 0; i < lines.length; ++i) {
			Line tmp = lines[i];
			if (find(tmp.prevCom) != find(tmp.nextCom)) {
				union(tmp.prevCom, tmp.nextCom);
				ret += tmp.cost;
			}
			else {
				continue;
			}
		}
		return ret;
	}

	private static void union(int prevCom, int nextCom) {
		int rootOfPrev = find(prevCom);
		int rootOfNext = find(nextCom);
		computers[rootOfPrev] = rootOfNext;
	}

	private static int find(int prevCom) {
		if (computers[prevCom] == prevCom) {
			return prevCom;
		}
		return computers[prevCom] = find(computers[prevCom]);
	}

	private static void init() {
		for (int i = 1; i < computers.length; ++i) {
			computers[i] = i;
		}
	}
}

class Line implements Comparable<Line>{
	int prevCom;
	int nextCom;
	int cost;
	
	public Line(int p, int n, int cost) {
		this.prevCom = p;
		this.nextCom = n;
		this.cost = cost;
	}

	@Override
	public int compareTo(Line o) {
		if (this.cost < o.cost) {
			return -1;
		}
		else if (this.cost > o.cost) {
			return 1;
		}
		else {
			return 0;
		}
	}
}
