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
		LinkedList<Point> selectedList = new LinkedList<>();
		int minSumOfDistance = makeAllCases(selectedList, candidateList);
		// 최소한의 치킨 거리를 도출한다.
		return minSumOfDistance;
	}

	private static int makeAllCases(LinkedList<Point> selectedList, LinkedList<Point> candidateList) {
		if(selectedList.size() == M) {
			// 설정된 치킨 집으로 치킨거리의 합을 구한다.
			return operateSumOfDistance(selectedList);
		}
		int ret = Integer.MAX_VALUE;
		int remainingSizeOfCandidateList = candidateList.size() - M + selectedList.size() + 1;
		for(int i = 0; i < remainingSizeOfCandidateList; ++i) {
			selectedList.add(candidateList.get(i));
			LinkedList<Point> remainCandidateList = new LinkedList<>(candidateList.subList(i+1, candidateList.size()));
			int tmp = makeAllCases(selectedList, remainCandidateList);
			if(ret > tmp) {
				ret = tmp;
			}
			selectedList.remove(selectedList.size()-1);
		}
		return ret;
	}

	private static int operateSumOfDistance(LinkedList<Point> selectedList) {
		Queue<Point> q = new LinkedList<>();
		for(int i = 0; i < selectedList.size(); ++i) {
			q.offer(selectedList.get(i));
		}
		int[][] visited = new int[N+1][N+1];
		int sumOfDistance = 0;
		while(!q.isEmpty()) {
			Point p = q.poll();
			for(int i = 0; i < 4; ++i) {
				int nextRow = p.curRow + Drow[i];
				int nextCol = p.curCol + Dcol[i];
				if(!isValid(nextRow, nextCol) || visited[nextRow][nextCol] == 1) {
					continue;
				}
				if(map[nextRow][nextCol] == 1) {
					int valRow = p.srcRow - nextRow;
					int valCol = p.srcCol - nextCol;
					sumOfDistance += Math.abs(valRow) + Math.abs(valCol);
				}
				visited[nextRow][nextCol] = 1;
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
	Point(int srcRow, int srcCol, int curRow, int curCol) {
		this.srcRow = srcRow;
		this.srcCol = srcCol;
		this.curRow = curRow;
		this.curCol = curCol;
	}
}