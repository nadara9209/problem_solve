import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class Solution {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for(int tc = 1; tc <= T; ++tc) {
			int N = scan.nextInt();
			
			// create
			int[][] map = new int[N][N];
			for(int row = 0; row < N; ++row) {
				for(int col = 0; col < N; ++col) {
					int val = scan.nextInt();
					map[row][col] = val;
				}
			}
			
			
			int answer = solve(map, N);
			System.out.println("#" + tc + " " + answer);
		}
		scan.close();
	}
	
	private static int solve(int[][] map, int N) {
		ArrayList<Person> personList = new ArrayList<>();
		ArrayList<Stair> stairList = new ArrayList<>();
		for(int row = 0; row < N; ++row) {
			for(int col = 0; col < N; ++col) {
				int val = map[row][col];
				if(val == 1) {
					personList.add(new Person(row, col));
				}
				else if(val > 1) {
					stairList.add(new Stair(row, col, val));
				}
			}
		}
		
		int minTime = makeAllCases(personList, stairList);
		return minTime;
	}

	private static int makeAllCases(ArrayList<Person> personList, ArrayList<Stair> stairList) {
		int nPerson = personList.size();
		ArrayList<Person> subCandidateList1 = new ArrayList<>();
		
		Stair stair1 = stairList.get(0);
		Stair stair2 = stairList.get(1);
		
		int ret = Integer.MAX_VALUE;
		
		for(int i = 0; i < (1 << (nPerson)); ++i) {
			ArrayList<Person> subCandidateList2 = (ArrayList<Person>) personList.clone();
			subCandidateList1.clear();
			for(int j = 0; j < nPerson; ++j) {
				if((i & (1 << j)) != 0) {
					subCandidateList1.add(personList.get(j));
					subCandidateList2.removeAll(subCandidateList1);
				}
			}
			// Person 나눈 후
			// 각 계단에 넣어주고
			// 계단이 스스로 totalTime을 도출한다.
			
			stair1.candidateList = subCandidateList1;
			stair2.candidateList = subCandidateList2;
			
			stair1.totalTime = 0;
			stair2.totalTime = 0;
			
			stair1.process();
			stair2.process();
			
			int tmp = 0;
			if(stair1.totalTime == 0) {
				tmp = stair2.totalTime;
			}
			else if(stair2.totalTime != 0 && stair1.totalTime != 0) {
				tmp = (stair1.totalTime > stair2.totalTime) ? stair1.totalTime : stair2.totalTime;
			}
			else if(stair2.totalTime == 0) {
				tmp = stair1.totalTime;
			}
			
			if(ret > tmp) {
				ret = tmp;
			}	
		}
		return ret;
	}

	static class Person implements Comparable<Person> {
		int row;
		int col;
		int reachTime;
		int processTime;

		Person(int row, int col) {
			this.row = row;
			this.col = col;
			this.reachTime = 0;
		}

		public int compareTo(Person o) {
			if(this.reachTime > o.reachTime) {
				return 1;
			}
			else if(this.reachTime < o.reachTime) {
				return -1;
			}
			else {
				return 0;
			}
		}

		public void setTime(int row, int col) {
			this.reachTime = Math.abs(this.row - row) + Math.abs(this.col - col);
		}
	}
	
	static class Stair {
		int row;
		int col;
		int limitTime;
		int capacity;
		int totalTime;
		LinkedList<Person> q;
		ArrayList<Person> candidateList;
		ArrayList<Person> selectedList;
		
		Stair(int row, int col, int limitTime) {
			this.row = row;
			this.col = col;
			this.limitTime = limitTime;
			this.capacity = 3;
			this.totalTime = 0;
			this.q = new LinkedList<>();
			this.candidateList = new ArrayList<>();
			this.selectedList = new ArrayList<>();
		}

		public void process() {
			// candidate의 time 최신화 후 q에 넣기
			for(int i = 0; i < this.candidateList.size(); ++i) {
				Person p = candidateList.get(i);
				p.setTime(this.row, this.col);
				q.add(p);
			}
			
			Collections.sort(q);
			
			while(!q.isEmpty()) {
				// 타이머와 사람들의 도착시간을 비교 하여
				while(!q.isEmpty() && q.peek().reachTime <= this.totalTime) {
					if(this.selectedList.size() >= 3) {
						break;
					}
					this.putOnPerson(q.removeFirst());
				}
				
				// 전체 타이머 증가
				this.timerProcess();
				
				for(int i = 0; i < this.selectedList.size(); ++i) {
					Person p = selectedList.get(i);
					if(p.processTime >= this.limitTime) {
						selectedList.remove(i);
					}
				}
			}
		}

		private void timerProcess() {
			this.totalTime++;
			for(int i = 0; i < this.selectedList.size(); ++i) {
				Person p = selectedList.get(i);
				p.processTime++;
			}
		}

		private void putOnPerson(Person p) {
			p.processTime = -1;
			this.selectedList.add(p);
		}
	}
}