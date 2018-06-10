import java.util.Scanner;
 
class Solution {
	static int[][] Memo = new int[1001][1001];
	static String StrA;
	static String StrB;
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int T = scan.nextInt();
        for(int tc = 1; tc <= T; ++tc) {
        	StrA = scan.next();
            StrB = scan.next();
            System.out.println("#" + tc + " " + solve());
        }
        scan.close();
    }
	
    private static int solve() {
    	// 패딩 채우기
    	int lenOfStrA = StrA.length();
    	int lenOfStrB = StrB.length();
    	for(int col = 0; col <= lenOfStrA; ++col) {
    		Memo[lenOfStrB][col] = 0;
    	}
    	for(int row = 0; row <= lenOfStrB; ++row) {
    		Memo[row][lenOfStrA] = 0;
    	}
    	
    	// 일반 항목 채우기
    	int endIdOfStrA = lenOfStrA - 1;
    	int endIdOfStrB = lenOfStrB - 1;
    	for(int row = endIdOfStrB; row >= 0; --row) {
    		for(int col = endIdOfStrA; col >= 0; --col) {
    			int match = (StrA.charAt(col) == StrB.charAt(row)) ? 1 : 0;
    			int right = Memo[row][col+1];
    			int bottom = Memo[row+1][col];
    			int diagonal = Memo[row+1][col+1] + match;
    			
    			Memo[row][col] = Math.max(Math.max(diagonal, right), bottom);
    		}
    	}
    	
    	// 결과 구하기
    	int ret = Memo[0][0];
    	return ret;
	}
}