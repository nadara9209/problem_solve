import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
	static int[] Drow = {-1,  1,  0,  0};
	static int[] Dcol = { 0,  0, -1,  1};
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int N = scan.nextInt();
		int M = scan.nextInt();
		
		// �ʱ� ����
		Point pRedBead = null;
		Point pBlueBead = null;
		Point pGoal = null;
		
		// create map
		char[][] map = new char[N][M];
		for(int row = 0; row < N; ++row) {
			String str = scan.next();
			char[] chArr = str.toCharArray();
			for(int col = 0; col < M; ++col) {
				char status = chArr[col];
				if(status == 'R') {
					pRedBead = new Point(row, col);
					status = '.';
				}
				else if(status == 'B') {
					pBlueBead = new Point(row, col);
					status = '.';
				}
				else if(status == 'O') {
					pGoal = new Point(row, col);
					status = '.';
				}
				else { }
				map[row][col] = status;
			}
		}
		
		PointPair startPair = new PointPair(pRedBead, pBlueBead);
		int answer = solve(map, startPair, pGoal);
		System.out.println(answer);
		scan.close();
	}

	private static int solve(char[][] map, PointPair startPair, Point pGoal) {
		Board givenBoard = new Board(map, startPair, pGoal, 0);
		int minNumOfSteps = count(givenBoard);
		return minNumOfSteps;
	}

	private static int count(Board givenBoard) {
		Queue<Board> q = new LinkedList<>();
		q.offer(givenBoard);
		
		int numOfSteps = 0;
		while(!q.isEmpty()) {
			Board currBoard = q.poll();
			// Ż�� �� �� ���ų�, 10�� �Ѱ� �̵������� ���
			if(currBoard.nSteps > 10) {
				numOfSteps = -1;
				break;
			}
			for(int dir = 0; dir < 4; ++dir) {
				Board nextBoard = new Board(currBoard.map, currBoard.beadPair, currBoard.pGoal, currBoard.nSteps);
				int dRow = Drow[dir];
				int dCol = Dcol[dir];
				// �̵��� �� ���� ���
				if(!nextBoard.isTiltOk(dRow, dCol)) {
					continue;
				}
				
				nextBoard.tilt(dRow, dCol);
				nextBoard.nSteps += 1;
				
				// �Ķ� ������ ���� Ż���� ���
				if(nextBoard.isBlueBeadGoal()) {
					continue;
				}
				
				// ���� ������  ���� Ż���� ���
				if(nextBoard.isRedBeadGoal()) {
					// �Ķ� ���� ���� Ż�� �� �� �ִ� ���
					if(nextBoard.isGoal(nextBoard.beadPair.pBlueBead.row + dRow, nextBoard.beadPair.pBlueBead.col + dCol)) {
						continue;
					}
					if(nextBoard.nSteps > 10) {
						return -1;
					}
					return nextBoard.nSteps;
				}
				q.offer(nextBoard);
			}
		}
		return numOfSteps;
	}
}

class Board {
	char[][] map;
	PointPair beadPair;
	Point pGoal;
	int nSteps;
	
	Board(char[][] map, PointPair currPair, Point pGoal, int nSteps) {
		this.map = map;
		
		this.beadPair = new PointPair();
		this.beadPair.pRedBead.row = currPair.pRedBead.row;
		this.beadPair.pRedBead.col = currPair.pRedBead.col;
		this.beadPair.pBlueBead.row = currPair.pBlueBead.row;
		this.beadPair.pBlueBead.col = currPair.pBlueBead.col;
		
		this.pGoal = pGoal;
		this.nSteps = nSteps;
	}

	public boolean isGoal(int row, int col) {
		// TODO Auto-generated method stub
		return (row == this.pGoal.row && col == this.pGoal.col);
	}

	public boolean isRedBeadGoal() {
		return (this.beadPair.pRedBead.row == this.pGoal.row && this.beadPair.pRedBead.col == this.pGoal.col);
	}
	
	public boolean isBlueBeadGoal() {
		return (this.beadPair.pBlueBead.row == this.pGoal.row && this.beadPair.pBlueBead.col == this.pGoal.col);
	}

	public void tilt(int dRow, int dCol) {
		int flag = dRow + dCol;
		// ���� �켱����
		Point pFirstPoint = null;
		Point pNextPoint = null;
		
		int pRedBeadRow = this.beadPair.pRedBead.row;
		int pRedBeadCol = this.beadPair.pRedBead.col;
		int pBlueBeadRow = this.beadPair.pBlueBead.row;
		int pBlueBeadCol = this.beadPair.pBlueBead.col;
		
		switch (flag) {
			// map�� ��, �� ����
			case 1:
				if((pRedBeadRow + pRedBeadCol) > (pBlueBeadRow + pBlueBeadCol)) {
					// �켱���� ����
					pFirstPoint = new Point(pRedBeadRow, pRedBeadCol);
					pNextPoint = new Point(pBlueBeadRow, pBlueBeadCol);
					// ���带 ����� ������ �̵�
					move(pFirstPoint, pNextPoint , dRow, dCol);
					// �̵��� ������ ������ ���� ���¸� ����
					this.beadPair.pRedBead = pFirstPoint;
					this.beadPair.pBlueBead = pNextPoint;
				}
				else {
					pFirstPoint = new Point(pBlueBeadRow, pBlueBeadCol);
					pNextPoint = new Point(pRedBeadRow, pRedBeadCol);
					move(pFirstPoint, pNextPoint , dRow, dCol);
					this.beadPair.pRedBead = pNextPoint;
					this.beadPair.pBlueBead = pFirstPoint;
				}
				break;
			// map�� ��, �� ����
			case -1:
				if((pRedBeadRow + pRedBeadCol) < (pBlueBeadRow + pBlueBeadCol)) {
					pFirstPoint = new Point(pRedBeadRow, pRedBeadCol);
					pNextPoint = new Point(pBlueBeadRow, pBlueBeadCol);
					move(pFirstPoint, pNextPoint , dRow, dCol);
					this.beadPair.pRedBead = pFirstPoint;
					this.beadPair.pBlueBead = pNextPoint;
				}
				else {
					pFirstPoint = new Point(pBlueBeadRow, pBlueBeadCol);
					pNextPoint = new Point(pRedBeadRow, pRedBeadCol);
					move(pFirstPoint, pNextPoint , dRow, dCol);
					this.beadPair.pRedBead = pNextPoint;
					this.beadPair.pBlueBead = pFirstPoint;
				}
				break;
			default:
				break;
		}
	}

	private void move(Point pFirstPoint, Point pNextPoint, int dRow, int dCol) {
		// �켱������ ������ ���� �̵�
		while((pFirstPoint.row != this.pGoal.row || pFirstPoint.col != this.pGoal.col)) {
			pFirstPoint.row += dRow;
			pFirstPoint.col += dCol;
			if(this.map[pFirstPoint.row][pFirstPoint.col] == '#') {
				pFirstPoint.row -= dRow;
				pFirstPoint.col -= dCol;
				break;
			}
		}
		
		while((pNextPoint.row != this.pGoal.row || pNextPoint.col != this.pGoal.col)) {
			pNextPoint.row += dRow;
			pNextPoint.col += dCol;
			if(this.map[pNextPoint.row][pNextPoint.col] == '#' ||
			   (pNextPoint.row == pFirstPoint.row && pNextPoint.col == pFirstPoint.col)) {
				pNextPoint.row -= dRow;
				pNextPoint.col -= dCol;
				break;
			}
		}
	}

	public boolean isTiltOk(int dRow, int dCol) {
		boolean isRedBallOk = true;
		boolean isBlueBallOk = true;
		
		int pRedBeadRow = this.beadPair.pRedBead.row + dRow;
		int pRedBeadCol = this.beadPair.pRedBead.col + dCol;
		int pBlueBeadRow = this.beadPair.pBlueBead.row + dRow;
		int pBlueBeadCol = this.beadPair.pBlueBead.col + dCol;
		
		char redBallStatus = this.map[pRedBeadRow][pRedBeadCol];
		char blueBallStatus = this.map[pBlueBeadRow][pBlueBeadCol];
		
		// ��
		if(redBallStatus == '#') {
			isRedBallOk = false;
		}
		if(blueBallStatus == '#') {
			isBlueBallOk = false;
		}
		return (isRedBallOk || isBlueBallOk);
	}
}

class PointPair {
	Point pRedBead;
	Point pBlueBead;
	
	PointPair() {
		this.pRedBead = new Point(0, 0);
		this.pBlueBead = new Point(0, 0);
	}
	PointPair(Point pRedBead, Point pBlueBead) {
		this.pRedBead = pRedBead;
		this.pBlueBead = pBlueBead;
	}
}

class Point {
	int row;
	int col;
	
	Point(int row, int col) {
		this.row = row;
		this.col = col;
	}
}