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
		
		for(int i = 2; i <= n; i++) {
			numbers[i] = numbers[i-1] + numbers[i-2];
		}
		
		System.out.println(numbers[n]);
		scan.close();
	}
}