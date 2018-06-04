import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Solution {
	static char[] OPERS = {'+', '-', '*', '/'};
	// 피연산자 배열
	static int[] NUMARR;
	// 연산자의 수
	static int NOPER = 0;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for(int tc = 1; tc <= T; ++tc) {
			int operArrLen = OPERS.length;
			int[] nOperArr = new int[operArrLen];
			int N = scan.nextInt();
			NUMARR = new int[N];
			NOPER = N - 1;
			// operArr 구성
			for(int i = 0; i < operArrLen; ++i) {
				int nOper = scan.nextInt();
				nOperArr[i] = nOper;
			}
			// numArr 구성
			for(int i = 0; i < N; ++i) {
				int tmpNum = scan.nextInt();
				NUMARR[i] = tmpNum;
			}
			System.out.println("#" + tc + " " + solve(nOperArr));
		}
		scan.close();
	}

	private static int solve(int[] nOperArr) {
		// 연산자 갯수를 통해 연산자를 담은 operArr을 만든다.
		ArrayList<Character> candidateList = new ArrayList<>();
		for(int i = 0; i < nOperArr.length; ++i) {
			int nOper = nOperArr[i];
			for(int j = 0; j < nOper; ++j) {
				candidateList.add(OPERS[i]);
			}
		}
		
		// 선택된 리스트
		ArrayList<Character> selectedList = new ArrayList<>();
		// 결과를 저장할 리스트
		ArrayList<Integer> retList = new ArrayList<>();
		// 입력 받은 연산자들로 구성할 수 있는 모든 경우를 계산
		operateAllOperStrCases(selectedList, candidateList, retList);
		
		Collections.sort(retList);
		int minVal = retList.get(0);
		int maxVal = retList.get(retList.size()-1);
		return maxVal - minVal;
	}
	
	private static void operateAllOperStrCases(ArrayList<Character> selectedList, ArrayList<Character> candidateList, ArrayList<Integer> retList) {
		// 선택된 리스트가 연산자의 갯수와 같다면 연산하여 저장
		if(selectedList.size() == NOPER) {
			retList.add(operate(selectedList));
		}
		
		int candidateSize = candidateList.size();
		for(int i = 0; i < candidateSize; ++i) {
			char tmpChar = candidateList.get(i);
			// 중복 가지치기
			if(i + 1 < candidateSize && tmpChar == candidateList.get(i+1)) {
				continue;
			}
			// 추가
			selectedList.add(tmpChar);
			// 다시 인자로 날려줄 subCandidateList 생성
			ArrayList<Character> subCandidateList = new ArrayList<>(candidateList.subList(0, i));
			subCandidateList.addAll(new ArrayList<>(candidateList.subList(i+1, candidateList.size())));
			// 추가된 selectedList와 subCandidateList 재귀
			operateAllOperStrCases(selectedList, subCandidateList, retList);
			// 해당 수행 후 연산자 수거.
			selectedList.remove(selectedList.size()-1);
		}
	}

	private static int operate(ArrayList<Character> selectedList) {
		int ret = NUMARR[0];
		for(int i = 0; i < selectedList.size(); ++i) {
			char oper = selectedList.get(i);
			switch (oper) {
			case '+':
				ret = ret + NUMARR[i+1];
				break;
			case '-':
				ret = ret - NUMARR[i+1];
				break;
			case '*':
				ret = ret * NUMARR[i+1];
				break;
			case '/':
				ret = ret / NUMARR[i+1];
				break;
			default:
				break;
			}
		}
		return ret;
	}
}