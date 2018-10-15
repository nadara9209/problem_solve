import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Solution {
	static int[] Drow = { 1,  1, -1, -1, 0 };
	static int[] Dcol = { 1, -1, -1,  1, 0 };
	static int[][] map;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for(int tc = 1; tc <= T; ++tc) {
			int N = scan.nextInt();
			
			map = new int[N][N];
			for(int row = 0; row < N; ++row) {
				for(int col = 0; col < N; ++col) {
					int dessertType = scan.nextInt();
					map[row][col] = dessertType;
				}
			}
			
			int answer = solve();
			System.out.println("#" + tc + " " + answer);
		}
		scan.close();
	}
	private static int solve() {
		int maxNumOfDessertType = 0;
		maxNumOfDessertType = makeAllCases();
		return maxNumOfDessertType;
	}
	private static int makeAllCases() {
		int ret = 0;
		
		int idLimit = map.length;
		for(int row = 0; row < idLimit; ++row) {
			for(int col = 0; col < idLimit; ++col) {
				List<Integer> typeList = new LinkedList<>();
				int currDir = 0;
				int nRightDown = 0;
				int nLeftDown = 0;
				int nLeftUp = 0;
				int nRightUp = 0;
				int tmp = countDessertType(row, col, currDir, nRightDown, nLeftDown, nLeftUp, nRightUp, typeList, row, col);
				if(ret < tmp) {
					ret = tmp;
				}
			}
		}
		
		return (ret == 0) ? -1 : ret;
	}
	
	private static int countDessertType(int row, int col, int currDir, int nRightDown, int nLeftDown, int nLeftUp,
			int nRightUp, List<Integer> typeList, int firstRow, int firstCol) {
		
		if(!isValid(row, col)) {
			return -1;
		}
		
		if(nRightDown < nLeftUp || nLeftDown < nRightUp) {
			return -1;
		}
		
		int dessertType = map[row][col];
		
		if(!(nRightDown == 0) && nRightDown == nLeftUp && nLeftDown == nRightUp) {
			if (row == firstRow && col == firstCol) {
				return typeList.size();
			}
			else {
				return -1;
			}
		}
		
		if(typeList.contains(dessertType)) {
			return -1;
		}
		
		typeList.add(dessertType);   
		
		int nCaseA = 0;
		int nextRow = row + Drow[currDir];
		int nextCol = col + Dcol[currDir];
		
		switch (currDir) {
			case 0:
				nCaseA = countDessertType(nextRow, nextCol, currDir, nRightDown+1, nLeftDown, nLeftUp, nRightUp, typeList, firstRow, firstCol);
				break;
			case 1:	
				nCaseA = countDessertType(nextRow, nextCol, currDir, nRightDown, nLeftDown+1, nLeftUp, nRightUp, typeList, firstRow, firstCol);
				break;
			case 2:	
				nCaseA = countDessertType(nextRow, nextCol, currDir, nRightDown, nLeftDown, nLeftUp+1, nRightUp, typeList, firstRow, firstCol);
				break;
			case 3:	
				nCaseA = countDessertType(nextRow, nextCol, currDir, nRightDown, nLeftDown, nLeftUp, nRightUp+1, typeList, firstRow, firstCol);
				break;
			default:
				break;
		}
		
		
		int nCaseB = 0;
		nextRow = row + Drow[currDir + 1];
		nextCol = col + Dcol[currDir + 1];
		
		switch (currDir + 1) {
			case 1:	
				nCaseB = countDessertType(nextRow, nextCol, currDir + 1, nRightDown, nLeftDown+1, nLeftUp, nRightUp, typeList, firstRow, firstCol);
				break;
			case 2:	
				nCaseB = countDessertType(nextRow, nextCol, currDir + 1, nRightDown, nLeftDown, nLeftUp+1, nRightUp, typeList, firstRow, firstCol);
				break;
			case 3:	
				nCaseB = countDessertType(nextRow, nextCol, currDir + 1, nRightDown, nLeftDown, nLeftUp, nRightUp+1, typeList, firstRow, firstCol);
				break;
			default:
				break;
		}
			
		
		typeList.remove(typeList.size()-1);
		
		return Math.max(nCaseA, nCaseB);
	}
	
	private static boolean isValid(int row, int col) {
		return (row >= 0 && col >= 0 && row < map.length && col < map.length);
	}
}