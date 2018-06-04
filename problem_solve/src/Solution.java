import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Solution {
	static char[] OPERS = {'+', '-', '*', '/'};
	// �ǿ����� �迭
	static int[] NUMARR;
	// �������� ��
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
			// operArr ����
			for(int i = 0; i < operArrLen; ++i) {
				int nOper = scan.nextInt();
				nOperArr[i] = nOper;
			}
			// numArr ����
			for(int i = 0; i < N; ++i) {
				int tmpNum = scan.nextInt();
				NUMARR[i] = tmpNum;
			}
			System.out.println("#" + tc + " " + solve(nOperArr));
		}
		scan.close();
	}

	private static int solve(int[] nOperArr) {
		// ������ ������ ���� �����ڸ� ���� operArr�� �����.
		ArrayList<Character> candidateList = new ArrayList<>();
		for(int i = 0; i < nOperArr.length; ++i) {
			int nOper = nOperArr[i];
			for(int j = 0; j < nOper; ++j) {
				candidateList.add(OPERS[i]);
			}
		}
		
		// ���õ� ����Ʈ
		ArrayList<Character> selectedList = new ArrayList<>();
		// ����� ������ ����Ʈ
		ArrayList<Integer> retList = new ArrayList<>();
		// �Է� ���� �����ڵ�� ������ �� �ִ� ��� ��츦 ���
		operateAllOperStrCases(selectedList, candidateList, retList);
		
		Collections.sort(retList);
		int minVal = retList.get(0);
		int maxVal = retList.get(retList.size()-1);
		return maxVal - minVal;
	}
	
	private static void operateAllOperStrCases(ArrayList<Character> selectedList, ArrayList<Character> candidateList, ArrayList<Integer> retList) {
		// ���õ� ����Ʈ�� �������� ������ ���ٸ� �����Ͽ� ����
		if(selectedList.size() == NOPER) {
			retList.add(operate(selectedList));
		}
		
		int candidateSize = candidateList.size();
		for(int i = 0; i < candidateSize; ++i) {
			char tmpChar = candidateList.get(i);
			// �ߺ� ����ġ��
			if(i + 1 < candidateSize && tmpChar == candidateList.get(i+1)) {
				continue;
			}
			// �߰�
			selectedList.add(tmpChar);
			// �ٽ� ���ڷ� ������ subCandidateList ����
			ArrayList<Character> subCandidateList = new ArrayList<>(candidateList.subList(0, i));
			subCandidateList.addAll(new ArrayList<>(candidateList.subList(i+1, candidateList.size())));
			// �߰��� selectedList�� subCandidateList ���
			operateAllOperStrCases(selectedList, subCandidateList, retList);
			// �ش� ���� �� ������ ����.
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