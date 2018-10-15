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
		// Ư�� ������ ã�� ���� �ƴϱ� ������ �������� ����� ���� ��� ã�� ����
		// id�� �� �̻� ������ �� ���� ���� BaseCondition
		if(currId == personList.size()) {
			return countTime(personList, stairList, selectedList);
		}

		// ����
		selectedList.add(personList.get(currId));
		int caseA = checkAllCases(personList, stairList, selectedList, currId+1);
		selectedList.removeLast();
		
		// �̼���
		int caseB = checkAllCases(personList, stairList, selectedList, currId+1);
		
		return Math.min(caseA, caseB);
	}


	private static int countTime(LinkedList<Person> personList, ArrayList<Stair> stairList,
			LinkedList<Person> selectedList) {
		
		// Ư�� ListA�� ���� �����ϰ�
		// ��ü List���� ListA�� �����ϴ� ������ ���� ListB�� ����
		
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
		
		// Person to Stair�� ���� �ð��� ����
		// ���� �ð����� ������ �� ������ ������ ������ ó���� ��.
		public void setReachTime(int row, int col) {
			this.reachTime = Math.abs(this.row - row) + Math.abs(this.col - col);
		}
		
		// �������� ����
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
			
			// ��� ������ ����� �� 3���� �ö� ���� �� �ִ�.
			this.stateList = new ArrayList<State>(3);
			for(int i = 0; i < 3; ++i) {
				stateList.add(new State());
			}
		}
		
		// candidateList�� �����ϰ�
		// 3���� ó���ؼ� total �ҿ� �ð� �ֽ�ȭ
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
			
			// �ð��� ������Ű�鼭
			while(true) {
				time++;
				for(int i = 0; i < this.stateList.size(); ++i) {
					// ��� ���ϴ� �ֵ� �߿�
					if(!stateList.get(i).usable) {
						// ��ٸ��� �ʾҴ� ����̶�� 
						if(!stateList.get(i).isWait) {
							// ��ܱ��� + 1���� ������ ������ �ð��� �־��� �� ������ ��.
							if((time - stateList.get(i).startTime) == this.processTime+1) {
								stateList.get(i).usable = true;
								if(this.candidateList.isEmpty() && this.isAllUsable()) {
									this.totalTime = time;
									return;
								}
							}
						}
						// ��ٸ� ����̾��ٸ� �ٷ� ���� ����� ���̿� ���Ͽ� ����
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
				
				
				// ������������ �����ؼ� �ڿ������� ������ ��.
				// ���� ���� ������ 
				for(int i = candidateList.size() - 1; i >= 0; --i) {
					Person p = this.candidateList.get(i);
					for(int j = 0; j < this.stateList.size(); ++j) {
						if(stateList.get(j).usable) {
							// ��ٸ��� ����
							if(time == p.reachTime) {
								stateList.get(j).startTime = time;
								stateList.get(j).usable = false;
								stateList.get(j).isWait = false;
								candidateList.remove(i);
								break;
							}
							// ��ٸ�
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