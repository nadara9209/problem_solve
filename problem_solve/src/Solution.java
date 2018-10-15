import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
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
					map[row][col] = scan.nextInt();
				}
			}
			// answer
			int answer = solve(map, N);
			System.out.println("#"+ tc + " " + answer);
		}
		scan.close();
	}

	private static int solve(int[][] map, int n) {
		LinkedList<Person> personList = new LinkedList<>();
		ArrayList<Stair> stairList = new ArrayList<>();
		// needed lists create
		for(int row = 0; row < n; ++row) {
			for(int col = 0; col < n; ++col) {
				int val = map[row][col];
				if(val == 1) {
					personList.add(new Person(row, col));
				}
				else if(val > 1) {
					stairList.add(new Stair(row, col, val));
				}
			}
		}
		
		LinkedList<Person> selectedList = new LinkedList<>();
		int startId = 0;
		int minTime = checkAllCases(personList, stairList, selectedList, startId);
		return minTime;
	}
	

	private static int checkAllCases(LinkedList<Person> personList, ArrayList<Stair> stairList,
			LinkedList<Person> selectedList, int currId) {
		// 특정 시점을 찾는 것이 아니기 때문에 조합적인 경우의 수를 모두 찾기 위해
		// id가 더 이상 도달할 수 없을 때가 BaseCondition
		if(currId == personList.size()) {
			return countTime(personList, stairList, selectedList);
		}

		// 선택
		selectedList.add(personList.get(currId));
		int caseA = checkAllCases(personList, stairList, selectedList, currId+1);
		selectedList.removeLast();
		
		// 미선택
		int caseB = checkAllCases(personList, stairList, selectedList, currId+1);
		
		return Math.min(caseA, caseB);
	}


	private static int countTime(LinkedList<Person> personList, ArrayList<Stair> stairList,
			LinkedList<Person> selectedList) {
		
		// 특정 ListA를 먼저 도출하고
		// 전체 List에서 ListA를 제거하는 과정을 거쳐 ListB를 생성
		
		LinkedList<Person> personTempList1 = new LinkedList<>(personList);
		LinkedList<Person> personTempList2 = new LinkedList<>(selectedList);
		personTempList1.removeAll(personTempList2);
		
		Stair stair1 = stairList.get(0);
		Stair stair2 = stairList.get(1);
		
		stair1.candidateList = personTempList1;
		stair2.candidateList = personTempList2;
		
		stair1.process();
		stair2.process();
		
		if(stair1.totalTime == 0) {
			return stair2.totalTime;
		}
		else if(stair2.totalTime == 0) {
			return stair1.totalTime;
		}
		else {
			return (stair1.totalTime > stair2.totalTime) ? stair1.totalTime : stair2.totalTime;
		}
		
	}

	static class Person implements Comparable<Person>{
		int row;
		int col;
		int reachTime;
		
		Person(int row, int col) {
			this.row = row;
			this.col = col;
			this.reachTime = 0;
		}
		
		// Person to Stair의 도달 시간을 설정
		// 도달 시간으로 정렬한 후 빠르게 도착한 순으로 처리할 것.
		public void setReachTime(int row, int col) {
			this.reachTime = Math.abs(this.row - row) + Math.abs(this.col - col);
		}
		
		// 오름차순 정렬
		@Override
		public int compareTo(Person o) {
			if(this.reachTime < o.reachTime) {
				return 1;
			}
			else if(this.reachTime > o.reachTime) {
				return -1;
			}
			else {
				return 0;
			}	
		}
	}
	
	static class Stair {
		int row;
		int col;
		int processTime;
		int totalTime;
		LinkedList<Person> candidateList;
		ArrayList<State> stateList;
		
		Stair(int row, int col, int processTime) {
			this.row = row;
			this.col = col;
			this.processTime = processTime;
			this.totalTime = 0;
			this.candidateList = new LinkedList<>();
			
			// 계단 위에는 사람은 총 3명이 올라가 있을 수 있다.
			this.stateList = new ArrayList<State>(3);
			for(int i = 0; i < 3; ++i) {
				stateList.add(new State());
			}
		}
		
		// candidateList를 정렬하고
		// 3개씩 처리해서 total 소요 시간 최신화
		public void process() {
			if(this.candidateList.size() == 0) {
				this.totalTime = 0;
				return;
			}
			
			for(int i = 0; i < this.candidateList.size(); ++i) {
				this.candidateList.get(i).setReachTime(this.row, this.col);
			}
			Collections.sort(this.candidateList);
			
			this.setTime();
		}

		private void setTime() {
			int time = 0;
			
			// 시간을 증가시키면서
			while(true) {
				time++;
				for(int i = 0; i < this.stateList.size(); ++i) {
					// 사용 못하는 애들 중에
					if(!stateList.get(i).usable) {
						// 기다리지 않았던 사람이라면 
						if(!stateList.get(i).isWait) {
							// 계단길이 + 1까지 도달할 때까지 시간이 주어진 뒤 나오게 함.
							if((time - stateList.get(i).startTime) == this.processTime+1) {
								stateList.get(i).usable = true;
								if(this.candidateList.isEmpty() && this.isAllUsable()) {
									this.totalTime = time;
									return;
								}
							}
						}
						// 기다린 사람이었다면 바로 현재 계단의 길이와 비교하여 진행
						else if(stateList.get(i).isWait) {
							if((time - stateList.get(i).startTime) == this.processTime) {
								stateList.get(i).usable = true;
								if(this.candidateList.isEmpty() && this.isAllUsable()) {
									this.totalTime = time;
									return;
								}
							}
						}
					}
				}
				
				
				// 내림차순으로 정렬해서 뒤에서부터 지워도 됨.
				// 굳이 지울 갯수를 
				for(int i = candidateList.size() - 1; i >= 0; --i) {
					Person p = this.candidateList.get(i);
					for(int j = 0; j < this.stateList.size(); ++j) {
						if(stateList.get(j).usable) {
							// 기다리지 않은
							if(time == p.reachTime) {
								stateList.get(j).startTime = time;
								stateList.get(j).usable = false;
								stateList.get(j).isWait = false;
								candidateList.remove(i);
								break;
							}
							// 기다린
							else if(time > p.reachTime) {
								stateList.get(j).startTime = time;
								stateList.get(j).usable = false;
								stateList.get(j).isWait = true;
								candidateList.remove(i);
								break;
							}
						}
					}
				}
			}
		}

		private boolean isAllUsable() {
			for(int i = 0; i < this.stateList.size(); ++i) {
				if(!this.stateList.get(i).usable) {
					return false;
				}
			}
			return true;
		}
	}
	
	static class State {
		boolean usable;
		boolean isWait;
		int startTime;

		
		State() {
			this.usable = true;
			this.isWait = true;
			this.startTime = 0;
		}
	}
}