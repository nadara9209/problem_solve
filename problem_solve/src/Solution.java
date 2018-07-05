import java.util.LinkedList;
import java.util.Scanner;

public class Solution {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = 10;
		for(int tc = 1; tc <= T; ++tc) {
			int N = scan.nextInt();
			char[][] map = new char[8][8];
			for(int row = 0; row < 8; row++) {
				String str = scan.next();
				char[] tempArr = str.toCharArray();
				for(int col = 0; col < 8; col++) {
					map[row][col] = tempArr[col];
				}
			}
			
			int answer = solve(map, N);
			System.out.println("#" + tc + " " + answer);
		}
		scan.close();
	}

	private static int solve(char[][] map, int n) {
		LinkedList<Character> checkList = new LinkedList<>();
		int retCnt = 0;
		// 가로
		for(int row = 0; row < 8; ++row) {
			checkList.clear();
			for(int col = 0; col < 8; ++col) {
				char val = map[row][col];
				if(checkList.size() == n) {
					checkList.removeFirst();
				}
				checkList.addLast(val);
				if(checkList.size() == n && isPalindrome(checkList)) {
					retCnt++;
				}
			}
		}
		// 세로
		for(int row = 0; row < 8; ++row) {
			checkList.clear();
			for(int col = 0; col < 8; ++col) {
				char val = map[col][row];
				if(checkList.size() == n) {
					checkList.removeFirst();
				}
				checkList.addLast(val);
				if(checkList.size() == n && isPalindrome(checkList)) {
					retCnt++;
				}
			}
		}
		return retCnt;
	}

	private static boolean isPalindrome(LinkedList<Character> checkList) {
		for(int i = 0; i < (checkList.size() / 2); ++i) {
			if(checkList.get(checkList.size()-1-i) != checkList.get(i)) {
				return false;
			}
		}
		return true;
	}
}