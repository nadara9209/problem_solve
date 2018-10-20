import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
	static int[] yearPlan;
	static final int NUMBER_OF_MONTHS = 12;
	static int[] costs;
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		int T = scan.nextInt();
		for (int tc = 1; tc <= T; ++tc) {
			costs = new int[4];
			for (int i = 0; i < 4; ++i) {
				costs[i] = scan.nextInt();
			}
			
			yearPlan = new int[NUMBER_OF_MONTHS];
			for (int i = 0; i < NUMBER_OF_MONTHS; ++i) {
				yearPlan[i] = scan.nextInt();
			}
			
			int answer = solve();
			System.out.println("#" + tc + " " + answer);
		}
		
		scan.close();
	}
	
	private static int solve() {
		int[] tmpYearPlan = yearPlan.clone();
		List<Integer> tickets = new ArrayList<>();
		int minCost = checkAllCases(tickets, tmpYearPlan, 0);
		
		return (minCost < costs[3]) ? minCost : costs[3];
	}

	private static int checkAllCases(List<Integer> tickets, int[] tmpYearPlan, int id) {
		if (isAllZero(tmpYearPlan)) {
			return getCost(tickets);
		}
		
		if (id >= tmpYearPlan.length) {
			return 0; 
		}
		
		// oneday
		int caseA = Integer.MAX_VALUE;
		if (tmpYearPlan[id] > 0) {
			int prevVal = tmpYearPlan[id];
			tmpYearPlan[id] = 0;
			for (int i = 0; i < prevVal; ++i) {
				tickets.add(0);
			}
			caseA = checkAllCases(tickets, tmpYearPlan, id+1);
			tmpYearPlan[id] = prevVal;
			for (int i = 0; i < prevVal; ++i) {
				tickets.remove(tickets.size()-1);
			}
		}
		
		// onemonth
		int caseB = Integer.MAX_VALUE;
		if (tmpYearPlan[id] > 0) {
			int prevVal = tmpYearPlan[id];
			tmpYearPlan[id] = 0;
			tickets.add(1);
			caseB = checkAllCases(tickets, tmpYearPlan, id+1);
			tmpYearPlan[id] = prevVal;
			tickets.remove(tickets.size()-1);
		}
		
		// threemonth
		int caseC = Integer.MAX_VALUE;
		if (isUsable(id, tmpYearPlan)) {
			List<Integer> prevVals = readPrevVals(id, tmpYearPlan);
			updateVals(id, tmpYearPlan);
			tickets.add(2);
			caseC = checkAllCases(tickets, tmpYearPlan, id+1);
			rollBackVals(prevVals, id, tmpYearPlan);
			tickets.remove(tickets.size()-1);
		}
		
		// zeroday
		int caseD = Integer.MAX_VALUE;
		if (tmpYearPlan[id] <= 0) {
			caseD = checkAllCases(tickets, tmpYearPlan, id+1);
		}

//		return Math.min(caseA, Math.min(caseB, caseC));
//		return Math.min(Math.min(caseA, caseB), Math.min(caseC, caseD));
		return Math.min(Math.min(Math.min(caseA, caseB), caseC), caseD);
	}


	private static void updateVals(int currId, int[] tmpYearPlan) {
		int limit = tmpYearPlan.length - currId;
		
		if (limit > 3) {
			int id = currId;
			for (int i = 0; i < 3; ++i) {
				tmpYearPlan[id++] = 0;
			}
		}
		else {
			int id = currId;
			for (int i = 0; i < limit; ++i) {
				tmpYearPlan[id++] = 0;
			}
		}
	}

	private static void rollBackVals(List<Integer> prevIds, int id, int[] tmpYearPlan) {
		for (int i = 0; i < prevIds.size(); ++i) {
			tmpYearPlan[id++] = prevIds.get(i);
		}
		
	}

	private static List<Integer> readPrevVals(int currId, int[] tmpYearPlan) {
		List<Integer> retList = new ArrayList<>();
		
		int limit = tmpYearPlan.length - currId;
		
		if (limit > 3) {
			int id = currId;
			for (int i = 0; i < limit; ++i) {
				if (retList.size() == 3) {
					break;
				}
				retList.add(tmpYearPlan[id++]);
			}
		}
		else {
			int id = currId;
			for (int i = 0; i < limit; ++i) {
				retList.add(tmpYearPlan[id++]);
			}
		}
		
		return retList;
	}

	private static boolean isUsable(int currId, int[] tmpYearPlan) {
		int limit = tmpYearPlan.length - currId;
		
		if (limit > 3) {
			int id = currId;
			for (int i = 0; i < 3; ++i) {
				if (tmpYearPlan[id++] != 0) {
					return true;
				}
			}
		}
		else {
			int id = currId;
			for (int i = 0; i < limit; ++i) {
				if (tmpYearPlan[id++] != 0) {
					return true;
				}
			}
		}
		
		return false;
	}

	private static boolean isAllZero(int[] tmpYearPlan) {
		for (int i = 0; i < tmpYearPlan.length; ++i) {
			int val = tmpYearPlan[i];
			if (val != 0) {
				return false;
			}
		}
		
		return true;
	}

	private static int getCost(List<Integer> tickets) {
		int sum = 0;
		for (int i = 0; i < tickets.size(); ++i) {
			int id = tickets.get(i);
			sum += costs[id];
		}
		
		return sum;
	}
}