import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	static Scanner scan = new Scanner(System.in);
	static ArrayList<Integer> numList = new ArrayList<Integer>();
	static int limitNum = 6;
	
	public static void main(String[] args) {
		while(true) {	
			int cnt = scan.nextInt();
			if(cnt == 0) {
				return;
			}
			numList.clear();
			for(int i = 0; i < cnt; i++) {
				int num = scan.nextInt();
				numList.add(num);
			}
			
			printAllSets(numList);
			System.out.println();
		}
	}

	/** candidateList �߿���  6���� �̾� ����� ���� ��� ����Ѵ�. */
	private static void printAllSets(ArrayList<Integer> candidateList) {
		ArrayList<Integer> selectedList = new ArrayList<Integer>();
		printSubSets(selectedList, candidateList);
	}

	/** ���õ� ���� ������ ������ ������ �ĺ� ����Ʈ�� ������ �߰��� �����ϰ� ����Ѵ�. */
	private static void printSubSets(ArrayList<Integer> selectedList, ArrayList<Integer> candidateList) {
		
		// ���õ� ���� ���� 6�� �Ǹ� ��� �Ѵ�. (���� ����)
		if(selectedList.size() == 6) {
			print(selectedList);
			return;
		}
		
		// selectedList�� 6���� ���� ä��µ� ���� ����
		int remainingSize = candidateList.size() - limitNum + selectedList.size() + 1;
		
		for(int i = 0; i < remainingSize; i++) {
			// ���õ� ���� �����ϸ鼭 �ĺ�����Ʈ���� 1���� ���� �߰��Ѵ�. 
			selectedList.add(candidateList.get(i));
			
			// �������� �ĺ�����Ʈ�� �����.
			ArrayList<Integer> subCandidateList = new ArrayList<Integer>(candidateList.subList(i+1, candidateList.size()));
			
			// ���õ� ���� �����ϰ�, �ĺ�����Ʈ���� ������ ���� ���� �� �ִ� ����� ���� ��� ����Ѵ�.
			printSubSets(selectedList, subCandidateList);
			
			// ������ ���� �������´�.
			selectedList.remove(selectedList.size()-1);
		}
	}

	private static void print(ArrayList<Integer> selectedList) {
		for(int i = 0; i < selectedList.size(); i++) {
			System.out.print(selectedList.get(i) + " ");
		}
		System.out.println();
	}
}