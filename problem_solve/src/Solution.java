import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Solution {
	static Map<Character, Integer> numDb;
	static LinkedList<Character> list;
	static int N;
	static int K;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for (int tc = 1; tc <= T; ++tc) {
			N = scan.nextInt();
			K = scan.nextInt();
			String line = scan.next();
			
			list = new LinkedList<>();
			for (int i = 0; i < N; ++i) {
				list.add(line.charAt(i));
			}
			
			int answer = solve();
			System.out.print("#" + tc + " ");
			System.out.printf("%d\n", answer);
		}
		scan.close();
	}
	
	private static int solve() {
		initMap();
		int answer = checkNum();
		return answer;
	}

	private static int checkNum() {
		Box treasureBox = new Box(list);
		List<Integer> numList = new LinkedList<>();
		for (int i = 0; i < treasureBox.rotateCnt; ++i) {
			for (int j = 0; j < 4; ++j) {
				int val = treasureBox.numbers[j];
				if(numList.contains(val)) {
					continue;
				}
				numList.add(val);
			}
			treasureBox.rotate();
		}
		
		Collections.sort(numList, Collections.reverseOrder());
		return numList.get(K-1);
	}

	private static void initMap() {
		numDb = new HashMap<>();
		numDb.put('0', 0x0);
		numDb.put('1', 0x1);
		numDb.put('2', 0x2);
		numDb.put('3', 0x3);
		numDb.put('4', 0x4);
		numDb.put('5', 0x5);
		numDb.put('6', 0x6);
		numDb.put('7', 0x7);
		numDb.put('8', 0x8);
		numDb.put('9', 0x9);
		numDb.put('A', 0xA);
		numDb.put('B', 0xB);
		numDb.put('C', 0xC);
		numDb.put('D', 0xD);
		numDb.put('E', 0xE);
		numDb.put('F', 0xF);
	}

	static class Box {
		LinkedList<Character> cab;
		int[] numbers;
		int rotateCnt;
		
		public Box(LinkedList<Character> list) {
			this.cab = list;
			this.numbers = new int[4];
			this.setRotateCnt();
			this.setNumbers();
		}

		private void setRotateCnt() {
			this.rotateCnt = this.cab.size() / 4;
		}

		public void rotate() {
			char tmp = cab.removeLast();
			cab.addFirst(tmp);
			this.setNumbers();
		}

		private void setNumbers() {
			int limit = cab.size();
			int diff = this.rotateCnt;
			int id = 0;
			
			List<Character> copyList = new ArrayList<>();
			copyList.addAll(this.cab);
			
			for (int i = 0; i < limit; i += diff) {
				this.numbers[id] = 0;
				
				List<Character> subList = new ArrayList<>();
				subList = copyList.subList(0, diff);
				for (int j = diff-1; j >= 0; --j) {
					this.numbers[id] += numDb.get(subList.remove(0)) << 4 * j;
				}
				
				id++;
			}
		}
	}
}