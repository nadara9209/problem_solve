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
		
		// 초기 정보
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
			// 탈출 할 수 없거나, 10을 넘게 이동시켰을 경우
			if(currBoard.nSteps > 10) {
				numOfSteps = -1;
				break;
			}
			for(int dir = 0; dir < 4; ++dir) {
				Board nextBoard = new Board(currBoard.map, currBoard.beadPair, currBoard.pGoal, currBoard.nSteps);
				int dRow = Drow[dir];
				int dCol = Dcol[dir];
				// 이동할 수 없는 경우
				if(!nextBoard.isTiltOk(dRow, dCol)) {
					continue;
				}
				
				nextBoard.tilt(dRow, dCol);
				nextBoard.nSteps += 1;
				
				// 파란 구슬이 먼저 탈출한 경우
				if(nextBoard.isBlueBeadGoal()) {
					continue;
				}
				
				// 빨간 구슬이  먼저 탈출한 경우
				if(nextBoard.isRedBeadGoal()) {
					// 파란 구슬 역시 탈출 할 수 있는 경우
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
		// 구슬 우선순위
		Point pFirstPoint = null;
		Point pNextPoint = null;
		
		int pRedBeadRow = this.beadPair.pRedBead.row;
		int pRedBeadCol = this.beadPair.pRedBead.col;
		int pBlueBeadRow = this.beadPair.pBlueBead.row;
		int pBlueBeadCol = this.beadPair.pBlueBead.col;
		
		switch (flag) {
			// map의 하, 우 방향
			case 1:
				if((pRedBeadRow + pRedBeadCol) > (pBlueBeadRow + pBlueBeadCol)) {
					// 우선순위 설정
					pFirstPoint = new Point(pRedBeadRow, pRedBeadCol);
					pNextPoint = new Point(pBlueBeadRow, pBlueBeadCol);
					// 보드를 기울인 방향대로 이동
					move(pFirstPoint, pNextPoint , dRow, dCol);
					// 이동된 값으로 보드의 구슬 상태를 설정
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
			// map의 상, 좌 방향
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
		// 우선순위의 구슬을 먼저 이동
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
		
		// 벽
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