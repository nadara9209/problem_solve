import java.util.Scanner;

public class Solution {
	static int[] NumArr = new int[10];
	static int[] TimeArr = new int[28];
	public static void main(String[] args) {
		initNumArr();
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for(int tc = 1; tc <= T; ++tc) {
			for(int i = 0; i < 28; ++i) {
				int val = scan.nextInt();
				TimeArr[i] = val;
			}
			
			String answer = solve();
			System.out.println("#" + tc + " " + answer);
		}
		scan.close();
	}
	
	private static String solve() {
		int nOfBroken = 0;
		int id = 0;
		Clock retClock = new Clock();
		for(int i = 0; i <= 2; ++i) {
			Clock tmpClock = checkAllCases(id, nOfBroken, i);
			if(retClock.totalTime > tmpClock.totalTime) {
				retClock = tmpClock;
			}
		}
		String minTime = Integer.toString(retClock.totalTime / 100) + " " + 
						 Integer.toString(retClock.totalTime % 100);
		return minTime;
	}

	private static Clock checkAllCases(int id, int nOfBroken, int limitN) {
		if(nOfBroken == limitN) {
			Clock clock = new Clock(TimeArr);
			if(clock.isVaild()) {
				return clock;
			}
			else {
				return new Clock();
			}
		}
		
		Clock ret = new Clock();
		for(int i = id; i < TimeArr.length; ++i) {
			inverse(i);
			Clock tmp = checkAllCases(i+1, nOfBroken+1, limitN);
			if(ret.totalTime > tmp.totalTime) {
				ret = tmp;
			}
			inverse(i);
		}
		return ret;
	}

	private static void inverse(int i) {
		if(TimeArr[i] == 0) {
			TimeArr[i] = 1;
		}
		else {
			TimeArr[i] = 0;
		}
	}

	private static void initNumArr() {
		NumArr[0] = 0b1111110;
		NumArr[1] = 0b0000110;
		NumArr[2] = 0b1011011;
		NumArr[3] = 0b1001111;
		NumArr[4] = 0b0100111;
		NumArr[5] = 0b1101101;
		NumArr[6] = 0b1111101;
		NumArr[7] = 0b1000110;
		NumArr[8] = 0b1111111;
		NumArr[9] = 0b1101111;
	}
	
	static class Clock {
		int totalVal;
		int totalTime;
		int time[];
		int segment[];
		
		Clock(int[] timeArr) {
			this.totalVal = 0;
			int j = 0;
			for(int i = timeArr.length-1; i >= 0; --i) {
				this.totalVal += (timeArr[j++] << i);
			}
			
			this.segment = new int[4];
			for(int i = 0; i < 4; ++i) {
				this.segment[i] = (((1<<7)-1) & (this.totalVal >> (i*7)));
			}
			
			this.time = new int[4];
			this.totalTime = 0;
		}
		
		public void setTotalTime() {
			int ret = 0;
			ret += this.time[3] * 1000 +
				   this.time[2] * 100 +
				   this.time[1] * 10 +
				   this.time[0];
			this.totalTime = ret;
		}

		Clock() {
			this.totalTime = 9999;
		}

		public boolean isVaild() {
			int cnt = 0;
			for(int i = 0; i < 4; ++i) {
				for(int j = 0; j < 10; ++j) {
					if(this.segment[i] == NumArr[j]) {
						this.time[i] = j;
						this.totalTime += j * (Math.pow(10, i));
						cnt++;
					}
				}
			}
			if(cnt == 4) {
				int flag1 = (time[3] * 10) + time[2];
				int flag2 = (time[1] * 10) + time[0];
				if(flag1 > 23 || flag2 > 59) {
					return false;
				}
				else {
					return true;
				}
			}
			return false;
		}
	}
}