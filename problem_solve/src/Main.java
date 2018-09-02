import java.util.Scanner;

public class Main {
	static int[] parent;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int m = scan.nextInt();
		parent = new int[n+1];
		
		Command[] commands = new Command[m];
		for (int i = 0; i < m; ++i) {
			int command = scan.nextInt();
			int setA = scan.nextInt();
			int setB = scan.nextInt();
			commands[i] = new Command(command, setA, setB);
		}
		
		solve(commands);
		scan.close();
	}

	private static void solve(Command[] commands) {
		init(parent);
		for (int i = 0; i < commands.length; ++i) {
			Command c = commands[i];
			int command = c.command;
			switch (command) {
			case 0:
				union(c.setA, c.setB);
				break;
			case 1:		
				if (find(c.setA) != find(c.setB)) {
					System.out.println("NO");
				}
				else {
					System.out.println("YES");
				}
				break;
			default:
				break;
			}
		}
	}
	
	
	private static void union(int setA, int setB) {
		int rootOfA = find(setA);
		int rootOfB = find(setB);
		parent[rootOfB] = rootOfA;
	}

	private static int find(int setA) {
		if(parent[setA] == setA) {
			return setA;
		}
		else {
			return parent[setA] = find(parent[setA]);
		}
	}

	// 초기에 각 배열의 원소를 자신으로 지정 
	private static void init(int[] parent) {
		for (int i = 0; i < parent.length; ++i) {
			parent[i] = i;
		}
	}	
}

class Command {
	int command;
	int setA;
	int setB;
	
	public Command(int command, int setA, int setB) {
		this.command = command;
		this.setA = setA;
		this.setB = setB;
	}
}