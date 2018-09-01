import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int N = scan.nextInt();
		int M = scan.nextInt();
		int K = scan.nextInt();
		
		int[] numbers = new int[N];
		for (int i = 0; i < N; ++i) {
			numbers[i] = scan.nextInt();
		}
		
		int limit = M+K;
		Command[] commands = new Command[limit];
		for (int i = 0; i < limit; ++i) {
			int command = scan.nextInt();
			int src = scan.nextInt();
			int dst = scan.nextInt();
			commands[i] = new Command(command, src, dst);
		}
		
		solve(numbers, commands, limit);
		scan.close();
	}

	private static void solve(int[] numbers, Command[] commands, int limit) {
		// create segment Tree
		int n = numbers.length;
		double log2N = Math.log(n) / Math.log(2);  
		int h = (int)Math.ceil(log2N);
		long[] segTree = new long[(1 << (h+1))]; 
		init(numbers, segTree, 1, 0, n-1);
		
		for (int i = 0; i < commands.length; ++i) {
			Command c = commands[i];
			switch (c.command) {
			case 1:
				c.src -= 1;
				int val = c.dst - numbers[c.src];
				numbers[c.src] = val;
				update(segTree, 1, 0, n-1, c.src, val);
				break;
			case 2:
				long sum = sum(segTree, 1, 0, n-1, c.src-1, c.dst-1);
				System.out.println(sum);
				break;
			default:
				break;
			}
		}
	}

	private static long init(int[] numbers, long[] segTree, int node, int start, int end) {
		if (start == end) {
			return segTree[node] = numbers[start];
		}
		else {
			return segTree[node] = init(numbers, segTree, node*2, start, (start+end)/2) 
									+ init(numbers, segTree, node*2+1, (start+end)/2+1, end);
		}
	}
	
	private static long sum(long[] segTree, int node, int start, int end, int left, int right) {
		if (left > end || right < start) {
			return 0;
		}
		if (left <= start && right >= end) {
			return segTree[node];
		}
		return sum(segTree, node*2, start, (start+end)/2, left, right) +
			   sum(segTree, node*2+1, (start+end)/2+1, end, left, right);
	}
	
	// lazy propagation
	private static void update(long[] segTree, int node, int start, int end, int index, long val) {
		if (index < start || index > end) {
			return;
		}
		segTree[node] = segTree[node] + val;
		if (start != end) {
			update(segTree, node*2, start, (start+end)/2, index, val);
			update(segTree, node*2+1, (start+end)/2+1, end, index, val);
		}
	}
}

class Command {
	int command;
	int src;
	int dst;
	
	public Command(int command, int src, int dst) {
		this.command = command;
		this.src = src;
		this.dst = dst;
	}
}