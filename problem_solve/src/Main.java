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

	/** candidateList 중에서  6개를 뽑아 경우의 수를 모두 출력한다. */
	private static void printAllSets(ArrayList<Integer> candidateList) {
		ArrayList<Integer> selectedList = new ArrayList<Integer>();
		printSubSets(selectedList, candidateList);
	}

	/** 선택된 수랑 나머지 선택할 수들의 후보 리스트를 가지고 추가로 선택하고 출력한다. */
	private static void printSubSets(ArrayList<Integer> selectedList, ArrayList<Integer> candidateList) {
		
		// 선택된 수를 보고 6개 되면 출력 한다. (종결 조건)
		if(selectedList.size() == 6) {
			print(selectedList);
			return;
		}
		
		// selectedList에 6개의 수를 채우는데 남은 갯수
		int remainingSize = candidateList.size() - limitNum + selectedList.size() + 1;
		
		for(int i = 0; i < remainingSize; i++) {
			// 선택된 수를 포함하면서 후보리스트에서 1개의 수를 추가한다. 
			selectedList.add(candidateList.get(i));
			
			// 나머지를 후보리스트로 만든다.
			ArrayList<Integer> subCandidateList = new ArrayList<Integer>(candidateList.subList(i+1, candidateList.size()));
			
			// 선택된 수를 포함하고, 후보리스트에서 나머지 수를 뽑을 수 있는 경우의 수를 모두 출력한다.
			printSubSets(selectedList, subCandidateList);
			
			// 선택한 수를 내려놓는다.
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