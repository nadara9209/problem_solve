import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
	static int N;
	static int M;
	static int[][] map;
	static int[] Drow = {-1,  1,  0,  0};
	static int[] Dcol = { 0,  0, -1,  1};
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		N = scan.nextInt();
		M = scan.nextInt();
		map = new int[N+1][N+1];
		LinkedList<Point> candidateList = new LinkedList<>();
		// create
		for(int row = 1; row < N+1; ++row) {
			for(int col = 1; col < N+1; ++col) {
				int val = scan.nextInt();
				if(val == 2) {
					candidateList.add(new Point(row, col, row, col));
					continue;
				}
				map[row][col] = val;
			}
		}
		
		int answer = solve(candidateList);
		System.out.println(answer);
		scan.close();
	}
	
	private static int solve(LinkedList<Point> candidateList) {
		// M개를 구성할 수 있는 모든 치킨집의 예를 구하고
		ArrayList<Point> selectedList = new ArrayList<>();
		int startId = 0;
		int minSumOfDistance = makeAllCases(selectedList, candidateList, startId);
		// 최소한의 치킨 거리를 도출한다.
		return minSumOfDistance;
	}

	private static int makeAllCases(ArrayList<Point> selectedList, LinkedList<Point> candidateList, int currId) {
		if(selectedList.size() == M) {
			// 설정된 치킨 집으로 치킨거리의 합을 구한다.
			return operateSumOfDistance(selectedList);
		}
		
		if(currId >= candidateList.size()) {
			return Integer.MAX_VALUE;
		}
		
		// 선택
		selectedList.add(candidateList.get(currId));
		int caseA = makeAllCases(selectedList, candidateList, currId+1);
		selectedList.remove(selectedList.size()-1);
		
		// 미선택
		int caseB = makeAllCases(selectedList, candidateList, currId+1);
		
		return Math.min(caseA, caseB);
	}

	private static int operateSumOfDistance(ArrayList<Point> selectedList) {
		Queue<Point> q = new LinkedList<>();
		for(int i = 0; i < selectedList.size(); ++i) {
			q.offer(selectedList.get(i));
		}
		boolean[][] visited = new boolean[N+1][N+1];
		int sumOfDistance = 0;
		while(!q.isEmpty()) {
			Point p = q.poll();
			for(int i = 0; i < 4; ++i) {
				int nextRow = p.curRow + Drow[i];
				int nextCol = p.curCol + Dcol[i];
				if(!isValid(nextRow, nextCol) || visited[nextRow][nextCol]) {
					continue;
				}
				if(map[nextRow][nextCol] == 1) {
					int valRow = p.srcRow - nextRow;
					int valCol = p.srcCol - nextCol;
					sumOfDistance += Math.abs(valRow) + Math.abs(valCol);
				}
				visited[nextRow][nextCol] = true;
				q.offer(new Point(p.srcRow, p.srcCol, nextRow, nextCol));
			}
		}
		return sumOfDistance;
	}

	private static boolean isValid(int nextRow, int nextCol) {
		return (nextRow > 0 && nextCol > 0 && nextRow <= N && nextCol <= N);
	}
}

class Point {
	int srcRow;
	int srcCol;
	int curRow;
	int curCol;
	
	public Point(int srcRow, int srcCol, int curRow, int curCol) {
		this.srcRow = srcRow;
		this.srcCol = srcCol;
		this.curRow = curRow;
		this.curCol = curCol;
	}
}