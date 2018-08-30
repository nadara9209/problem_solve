import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int N = scan.nextInt();
		int M = scan.nextInt();
		int K = scan.nextInt();
		
		int[] numbers = new int[N];
		for(int i = 0; i < N; ++i) {
			numbers[i] = scan.nextInt();
		}
		
		int nOfQ = M+K;
		int[][] questions = new int[nOfQ][3];
		for(int i = 0; i < nOfQ; ++i) {
			int command = scan.nextInt();
			int src = scan.nextInt();
			int dst = scan.nextInt();
			
			questions[i][0] = command;
			questions[i][1] = src;
			questions[i][2] = dst;
		}
		
		solve(nOfQ, numbers, questions);
		scan.close();
	}

	private static void solve(int nOfQ, int[] numbers, int[][] questions) {
		int[] sumsOfN = new int[numbers.length];
		sumsOfN[0] = numbers[0];
		// 누적합계배열 생성
		// 모배열의 변화가 없을 경우만 사용가능
		// 변화 발생시 누적합계과정 다시 진행.
		for(int i = 1; i < numbers.length; ++i) {
			sumsOfN[i] = sumsOfN[i-1] + numbers[i];
		}
		
		for(int i = 0; i < nOfQ; ++i) {
			int command = questions[i][0];
			int src = questions[i][1] - 1;
			int dst = questions[i][2] - 1;
			
			if(command == 2) {
				System.out.println(sumsOfN[dst] - sumsOfN[src-1]);
			}
			else {
				// 모배열의 변화가 발생하게 되므로
				// 누적합 배열생성 만으로 해결할 경우
				// tle 발생
			}
		}
	}

}