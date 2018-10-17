import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	static int N;
	static int[][] synergy;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		N = scan.nextInt();
		
		synergy = new int[N][N];
		for (int row = 0; row < N; ++row) {
			for (int col = 0; col < N; ++col) {
				synergy[row][col] = scan.nextInt();
			}
		}
		
		int answer = solve();
		System.out.println(answer);
		
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
		int minGap = findAllCases(listA, idList, 0);
		return minGap;
	}

	private static int findAllCases(List<Integer> candidateList, List<Integer> inputList, int i) {
		if (candidateList.size() == N / 2) {
			return getGap(candidateList, inputList);
		}
		
		if (i >= inputList.size()) {
			return Integer.MAX_VALUE;
		}
		
		
		candidateList.add(inputList.get(i));
		int minGapCaseA = findAllCases(candidateList, inputList, i+1);
		candidateList.remove(candidateList.size()-1);
		
		int minGapCaseB = findAllCases(candidateList, inputList, i+1);
		
		return Math.min(minGapCaseA, minGapCaseB);
		
	}

	private static int getGap(List<Integer> listA, List<Integer> idList) {
		List<Integer> candidateListA = new ArrayList<>();
		int sumOfSynergyA = count(candidateListA, listA, 0);
		
		List<Integer> candidateListB = new ArrayList<>();
		List<Integer> listB = new ArrayList<>(idList);
		listB.removeAll(listA);
		
		int sumOfSynergyB = count(candidateListB, listB, 0);
		
		return Math.abs(sumOfSynergyA - sumOfSynergyB);
	}

	private static int count(List<Integer> candidateList, List<Integer> inputList, int i) {
		if (candidateList.size() == 2) {
			return getSynergy(candidateList);
		}
		
		if (i >= inputList.size()) {
			return 0;
		}
		
		candidateList.add(inputList.get(i));
		int scoreCaseA = count(candidateList, inputList, i+1);
		candidateList.remove(candidateList.size()-1);
		
		int scoreCaseB = count(candidateList, inputList, i+1);
		
		return scoreCaseA + scoreCaseB;
	}

	private static int getSynergy(List<Integer> candidateList) {
		int firstId = candidateList.get(0);
		int secondId = candidateList.get(1);
		
		return synergy[firstId][secondId] + synergy[secondId][firstId];
	}
}