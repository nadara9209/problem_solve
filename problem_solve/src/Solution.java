import java.util.Scanner;
 
class Solution {
	static int[][] Memo = new int[1001][1001];
	static String StrA;
	static String StrB;
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int T = scan.nextInt();
        for(int tc = 1; tc <= T; ++tc) {
            //Memo init
            for(int i = 0; i < 1000; ++i) {
            	for( int j = 0; j < 1000; ++j) {
            		Memo[i][j] = 0;
            	}
            }
        	StrA = scan.next();
            StrB = scan.next();
            System.out.println("#" + tc + " " + solve());
        }
        scan.close();
    }
	
    private static int solve() {
    	int ret = 0;
    	int lenOfStrA = StrA.length();
    	int lenOfStrB = StrB.length();
    	for(int i = 0; i < lenOfStrA; ++i) {
    		char selectedChar = StrA.charAt(i);
    		for(int j = 0; j < lenOfStrB; ++j) {
    			if(ret >= lenOfStrA - i || ret >= lenOfStrB - j) {
    				continue;
    			}
    			if(selectedChar == StrB.charAt(j)) {
					int tmp = countLCS(i, j);
					if(ret < tmp) {
	    				ret = tmp;
	    			}
    			}
    		}
    	}
		return ret;
	}

	private static int countLCS(int idOfstrA, int idOfstrB) {
		if(Memo[idOfstrA][idOfstrB] != 0) {
			return Memo[idOfstrA][idOfstrB];
		}
		int lenOfStrA = StrA.length();
    	int lenOfStrB = StrB.length();
    	
		if(idOfstrA == lenOfStrA-1 || idOfstrB == lenOfStrB-1) {
			int size = 1;
			Memo[idOfstrA][idOfstrB] = 1;
			return size;
		}
    	
		int maxSubSize = 0;
    	for(int i = idOfstrA+1; i < lenOfStrA; ++i) {
			char selectedCh = StrA.charAt(i);
			for(int j = idOfstrB+1; j < lenOfStrB; ++j) {
				if(selectedCh == StrB.charAt(j)) {
					int tmpSize = countLCS(i, j);
					if(maxSubSize < tmpSize) {
						maxSubSize = tmpSize;
					}
				}
				
			}
		}
    	
    	int size = 1 + maxSubSize;
    	Memo[idOfstrA][idOfstrB] = size;
		return size;
	}
}