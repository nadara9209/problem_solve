import java.util.Scanner;

public class Solution {
	static char[] BracketArr = {'(', ')', '[', ']', '{', '}', '<', '>'}; 
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = 10;
		for(int tc = 1; tc <= T; ++tc) {
			int N = scan.nextInt();
			String str = scan.next();
			
			int answer = solve(N, str);
			
			System.out.println("#" + tc + " " + answer);
		}
		scan.close();
	}
	
	private static int solve(int n, String str) {
		int[] numOfBracket = new int[8];
		
		for(int i = 0; i < n; ++i) {
			char ch = str.charAt(i);
			for(int j = 0; j < 8; ++j) {
				if(ch == BracketArr[j]) {
					numOfBracket[j]++;
					break;
				}
			}
		}
		
		for(int i = 0; i < 8; i+=2) {
			if(numOfBracket[i] != numOfBracket[i+1]) {
				return 0;
			}
		}
		
		return 1;
	}
}