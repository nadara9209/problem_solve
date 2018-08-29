import java.util.Arrays;
import java.util.Scanner;

public class Main {
	static long[] numbers = new long[91];
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		
		Arrays.fill(numbers, -1);
		numbers[0] = 0;
		numbers[1] = 1;
		
		long answer = solve(n);
		System.out.println(answer);
		scan.close();
	}
	
	private static long solve(int n) {
		if(numbers[n] != -1) {
			return numbers[n];
		}
		
		return numbers[n] = (solve(n-1)+ solve(n-2));
	}
}