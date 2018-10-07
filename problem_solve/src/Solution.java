import java.util.LinkedList;
import java.util.Scanner;

public class Solution {
	static Wheel[] wheels = new Wheel[5];
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for (int tc = 1; tc <= T; ++tc) {
			int cnt = scan.nextInt();
			
			for (int i = 1; i <= 4; i++) {
				wheels[i] = new Wheel();
				for (int j = 0; j < 8; ++j) {
					wheels[i].gears.addLast(scan.nextInt());
				}
			}
			
			for (int i = 1; i <= 3; i++) {
				wheels[i].setRightWheel(wheels[i+1]);
				wheels[i+1].setLeftWheel(wheels[i]);
			}
			
			for (int i = 0; i < cnt; i++) {
				int wheelId = scan.nextInt();
				int direction = scan.nextInt();
				
				wheels[wheelId].rotate(direction);
			}
			
			int answer = calculateScore();
			System.out.println("#" + tc + " " + answer);
		}
		
		scan.close();
	}

	private static int calculateScore() {
		int totalScore = 0;

		totalScore += wheels[1].getTop();
		totalScore += wheels[2].getTop() * 2;
		totalScore += wheels[3].getTop() * 4;
		totalScore += wheels[4].getTop() * 8;
		
		return totalScore;
	}
}

class Wheel {
	public LinkedList<Integer> gears;
	private Wheel leftWheel;
	private Wheel rightWheel;

	public Wheel() {
		this.gears = new LinkedList<>();
		this.leftWheel = null;
		this.rightWheel = null;
	}

	public void setLeftWheel(Wheel wheel) {
		this.leftWheel = wheel;
	}

	public void setRightWheel(Wheel wheel) {
		this.rightWheel = wheel;
	}

	public void rotate(int direction) {
		this.rotate(this, direction);
	}

	public void rotate(Wheel sourceWheel, int direction) {
		if (this.hasLeftWheel() && this.leftWheel != sourceWheel) {
			if (this.getLeft() != this.leftWheel.getRight()) {
				this.leftWheel.rotate(this, -direction);
			}
		}
		if (this.hasRightWheel() && this.rightWheel != sourceWheel) {
			if (this.getRight() != this.rightWheel.getLeft()) {
				this.rightWheel.rotate(this, -direction);
			}
		}
		
		if (direction > 0)
			this.turnRight();
		else
			this.turnLeft();
	}

	private void turnLeft() {
		this.gears.addLast(this.gears.removeFirst());
	}

	private void turnRight() {
		this.gears.addFirst(this.gears.removeLast());
	}

	private boolean hasLeftWheel() {
		return (this.leftWheel != null);
	}

	private boolean hasRightWheel() {
		return (this.rightWheel != null);
	}

	public int getRight() {
		return this.gears.get(2);
	}

	public int getLeft() {
		return this.gears.get(6);
	}

	public int getTop() {
		return this.gears.get(0);
	}
	
}
