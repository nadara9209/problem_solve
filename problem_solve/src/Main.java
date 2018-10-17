import java.util.Scanner;

public class Main {
	static int N;
	static int B;
	static int C;
	static int[] testRoom;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		N = scan.nextInt();
		
		testRoom = new int[N];
		for (int i = 0; i < N; ++i) {
			testRoom[i] = scan.nextInt();
		}
		
		B = scan.nextInt();
		C = scan.nextInt();
		
		long answer = solve();
		System.out.println(answer);
		scan.close();
	}

	private static long solve() {
		long cnt = N;
		for (int i = 0; i < N; ++i) {
			int nOfPerson = testRoom[i];
			nOfPerson -= B;
			if (nOfPerson > 0) {
				int ¸ò = nOfPerson / C;
				int ³ª¸ÓÁö = nOfPerson % C;
				if (³ª¸ÓÁö == 0) {
					cnt += ¸ò;
				}
				else {
					cnt += ¸ò + 1;
				}
			}
		}
		
		return cnt;
	}
}