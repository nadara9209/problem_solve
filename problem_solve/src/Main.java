import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
	static String[] COMMANDS = {"pus", "pop", "siz", "emp", "fro", "bac"};
	static LinkedList<String> commands;
	static ArrayList<String> stack;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int N = scan.nextInt();
		scan.nextLine();
	
		commands = new LinkedList<>();
		for(int i = 0; i < N; ++i) {
			commands.add(scan.nextLine());
		}
		
		stack = new ArrayList<>();
		doStacks();
		
		scan.close();
	}
	
	private static void doStacks() {
		int limit = commands.size();
		for(int i = 0; i < limit; ++i) {
			String str = commands.removeFirst();
			String command = str.substring(0, 3);
			
			if(COMMANDS[0].equals(command)) {
				stack.add(str.substring(5,str.length()));
			}
			else if(COMMANDS[1].equals(command)) {
				if(stack.isEmpty()) {
					System.out.println(-1);
				}
				else {
					System.out.println(stack.remove(0));
				}
			}
			else if(COMMANDS[2].equals(command)) {
				System.out.println(stack.size());
			}
			else if(COMMANDS[3].equals(command)) {
				if(stack.isEmpty()) {
					System.out.println(1);
				}
				else {
					System.out.println(0);
				}
			}
			else if(COMMANDS[4].equals(command)) {
				if(stack.isEmpty()) {
					System.out.println(-1);
				}
				else {
					System.out.println(stack.get(0));
				}
			}
			else if(COMMANDS[5].equals(command)) {
				if(stack.isEmpty()) {
					System.out.println(-1);
				}
				else {
					System.out.println(stack.get(stack.size()-1));
				}
			}
		}
	}
}