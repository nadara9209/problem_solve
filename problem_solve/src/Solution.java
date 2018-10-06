import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
	static int N;
	static int[][] synergy;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for (int tc = 1; tc <= T; ++tc) {
			N = scan.nextInt();
			
			synergy = new int[N][N];
			for (int row = 0; row < N; ++row) {
				for (int col = 0; col < N; ++col) {
					synergy[row][col] = scan.nextInt();
				}
			}
			
			int answer = solve();
			System.out.println("#" + tc + " " + answer);
		}
		scan.close();
	}
	
	private static int solve() {
		int answer = findMinGap();
		return answer;
	}

	private static int findMinGap() {
		List<Integer> idList = new ArrayList<>();
		for (int i = 0; i < N; ++i) {
			idList.add(i);
		}
		List<Integer> listA = new ArrayList<>();
		int minGap = findAllCases(idList, listA, 0);
		return minGap;
	}

	private static int findAllCases(List<Integer> inputList, List<Integer> listA, int i) {
		if (listA.size() == N / 2) {
			return getGap(listA, inputList);
		}
		
		if (i >= inputList.size()) {
			return Integer.MAX_VALUE;
		}
		
		int minGapCaseA = 0;
		int minGapCaseB = 0;
		
		listA.add(inputList.get(i));
		minGapCaseA = findAllCases(inputList, listA, i+1);
		listA.remove(listA.size()-1);
		
		minGapCaseB = findAllCases(inputList, listA, i+1);
		
		return Math.min(minGapCaseA, minGapCaseB);
		
	}

	private static int getGap(List<Integer> listA, List<Integer> idList) {
		List<Integer> candidateListA = new ArrayList<>();
		int sumOfSynergyA = count(listA, candidateListA, 0);
		
		List<Integer> copyList = new ArrayList<>(idList);
		List<Integer> candidateListB = new ArrayList<>();
		List<Integer> listB = null;
		if (copyList.removeAll(listA)) {
			listB = copyList;
		}
		int sumOfSynergyB = count(listB, candidateListB, 0);
		
		return Math.abs(sumOfSynergyA - sumOfSynergyB);
	}

	private static int count(List<Integer> inputList, List<Integer> candidateList, int i) {
		if (candidateList.size() == 2) {
			return getSynergy(candidateList);
		}
		
		if (i >= inputList.size()) {
			return 0;
		}
		
		int scoreCaseA = 0;
		int scoreCaseB = 0;
		
		candidateList.add(inputList.get(i));
		scoreCaseA = count(inputList, candidateList, i+1);
		candidateList.remove(candidateList.size()-1);
		
		scoreCaseB = count(inputList, candidateList, i+1);
		
		return scoreCaseA + scoreCaseB;
	}

	private static int getSynergy(List<Integer> candidateList) {
		int firstId = candidateList.get(0);
		int secondId = candidateList.get(1);
		
		return synergy[firstId][secondId] + synergy[secondId][firstId];
	}
}