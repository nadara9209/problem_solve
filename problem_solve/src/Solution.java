import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
	static int D;
	static int W;
	static int K;
	static int[][] film;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for (int tc = 1; tc <= T; ++tc) {
			D = scan.nextInt();
			W = scan.nextInt();
			K = scan.nextInt();
			
			film = new int[D][W];
			for (int row = 0; row < D; ++row) {
				for (int col = 0; col < W; ++col) {
					film[row][col] = scan.nextInt();
				}
			}
			
			int answer = solve();
			System.out.println("#" + tc + " " + answer);
		}
		scan.close();
	}
	
	private static int solve() {
		int minNOfInjections = countAllCases();
		return minNOfInjections;
	}

	private static int countAllCases() {
		List<Integer> idList = new ArrayList<>();
		for (int i = 0; i < D; ++i) {
			idList.add(i);
		}
		
		List<Integer> candidateList = new ArrayList<>();
		
		int nOfInjections;
		for (nOfInjections = 0; nOfInjections < D; ++nOfInjections) {
			if (isPassed(candidateList, idList, 0, nOfInjections)) {
				break;
			}
		}
		return nOfInjections;
	}

	private static boolean isPassed(List<Integer> candidateList, List<Integer> idList, int i, int nOfInjections) {
		if (candidateList.size() == nOfInjections) {
			return check(candidateList);
		}
		
		if (i >= idList.size()) {
			return false;
		}
		
		boolean caseA;
		boolean caseB;
		
		candidateList.add(idList.get(i));
		caseA = isPassed(candidateList, idList, i+1, nOfInjections);
		if (caseA) {
			return true;
		}
		candidateList.remove(candidateList.size()-1);
		
		caseB = isPassed(candidateList, idList, i+1, nOfInjections);
		if (caseB) {
			return true;
		}
		
		return false;
	}

	private static boolean check(List<Integer> candidateList) {
		int limit = candidateList.size();
		for (int i = 0; i < (1 << limit); ++i) {
			boolean[] flag = new boolean[limit];
			for (int j = i; j >= 0; j >>>= 1) {
				if ((j & 1) != 0) {
					flag[i] = true;
				}
			}
			
			// injections
			int[][] injectedFilm = injection(flag, candidateList);
			// check ispassed
			if (checkPassed(injectedFilm)) {
				return true;
			}
		}
		return false;
	}


	private static boolean checkPassed(int[][] injectedFilm) {
		for (int col = 0; col < W; ++col) {
			int cntA = 0;
			int cntB = 0;
			boolean isPassedA = false;
			boolean isPassedB = false;
			for (int row = 0; row < D; ++row) {
				if (injectedFilm[row][col] == 0) {
					cntA++;
					if (cntA == K) {
						isPassedA = true;
					}
				}
				else {
					cntB++;
					if (cntB == K) {
						isPassedB = true;
					}
				}
				
				if (!(isPassedA || isPassedB)) {
					return false;
				}
			}
		}
		return true;
	}

	private static int[][] injection(boolean[] flag, List<Integer> candidateList) {
		int[][] copyFilm = new int[D][W];
		for (int row = 0; row < D; ++row) {
			copyFilm[row] = film[row].clone();
		}
		
		for (int i = 0; i < candidateList.size(); ++i) {
			int id = candidateList.get(i);
			fill(flag[i], id, copyFilm);
		}
		
		return copyFilm;
	}

	private static void fill(boolean flag, int id, int[][] copyFilm) {
		if (flag) {
			for (int col = 0; col < W; ++col) {
				copyFilm[id][col] = 0;
			}
		}
		else {
			for (int col = 0; col < W; ++col) {
				copyFilm[id][col] = 1;
			}
		}
	}
}