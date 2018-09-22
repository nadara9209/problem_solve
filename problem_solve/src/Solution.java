import java.util.ArrayList;

class Solution {
    public int solution(int N, int[][] house) {
    	final int[][] location = new int[201][201];
    	final ArrayList<Point> pList = new ArrayList<>();
    	int answer = solve(0, 0, 0, location, house, N, pList);
        return answer;
    }

	private int solve(int i, int j, int n, int[][] location, int[][] house, int limit, ArrayList<Point> pList) {
		if (n == limit) {
			return computeMin(pList, house);
		}
		
		int ret = 0;
		int row = i;
		int col = j;
		for (; row < location.length; ++row) {
			for (; col < location[0].length; ++col) {
				if(!(row == 0 || row == location.length-1 || col == 0 || col == location.length-1)) {
					continue;
				}
				if(location[row][col] != 1) {
					location[row][col] = 1;
					pList.add(new Point(row, col));
					int tmp = solve(row, col, n+1, location, house, limit, pList); 
					if (ret < tmp) {
						ret = tmp;
					}
					location[row][col] = 0;
					pList.remove(pList.size()-1);
				}		
			}
		}
		return ret;
	}

	private int computeMin(ArrayList<Point> pList, int[][] house) {
		int ret = Integer.MAX_VALUE;
		for (Point p : pList) {
			for (int row_h = 0; row_h < house.length; ++row_h) {
				int tmp_h = compute(p.x-100, p.y-100, house[row_h][0], house[row_h][1]);
				if (ret > tmp_h) {
					ret = tmp_h;
				}
			}
		}
		return ret;
	}

	private int compute(int i, int j, int k, int l) {
		return (int)(Math.pow(Math.abs(i-k), 2) + Math.pow(Math.abs(j-k), 2));
	}
}

class Point {
	int x;
	int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}