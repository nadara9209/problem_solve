import java.util.Scanner;

public class Solution {
	private static final double ERROR_WIDTH = Math.pow(10, -12);
	static Point[] points;
	static int N;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for (int tc = 1; tc <= T; ++tc) {
			N = scan.nextInt();
			
			points = new Point[N];
			for (int i = 0; i < N; ++i) {
				points[i] = new Point();
			}
			
			for (int i = 0; i < N; ++i) {
				points[i].setX(scan.nextInt());
			}
			for (int i = 0; i < N; ++i) {
				points[i].setM(scan.nextInt());
			}
			
			System.out.print("#" + tc + " ");
			solve();
		}
		scan.close();
	}
	
	private static void solve() {
		for (int i = 0; i < points.length-1; ++i) {
			double answer = search(i);
			System.out.printf("%.10f ", answer);
		}
		System.out.println();
	}

	private static double search(int id) {
		double left = points[id].x;
		double right = points[id+1].x;
		
		while (left <= right) {
			double mid = (left + right) / 2;
			
			double sumOfLeft = 0;
			double sumOfRight = 0;
			for (int i = 0; i < id+1; ++i) {
				double d = Math.abs(mid - points[i].x);
				sumOfLeft += (points[i].m / (d*d));
			}
			for (int i = id+1; i < points.length; ++i) {
				double d = Math.abs(mid - points[i].x);
				sumOfRight += (points[i].m / (d*d));
			}
			
			if (right - left < ERROR_WIDTH) {
				return mid;
			}
			
			if (sumOfLeft < sumOfRight) {
				right = mid;
			}
			else if (sumOfLeft > sumOfRight) {
				left = mid;
			}
			else {
				return mid;
			}
		}
		return -1;
	}

	
}

class Point {
	int x;
	int m;

	public Point() {
		this.x = 0;
		this.m = 0;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setM(int m) {
		this.m = m;
	}
}