import java.util.ArrayList;
import java.util.Scanner;

public class Solution {
	static ArrayList<Integer> numList;
	
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for(int tc = 1; tc <= T; ++tc) {
			numList = new ArrayList<>();
			// 결과 출력용
			ArrayList<Integer> retList = new ArrayList<>();
			int nOperation = scan.nextInt();
			for(int i = 0; i < nOperation; ++i) {
				int operation = scan.nextInt();
				switch (operation) {
				case 1:
					int num = scan.nextInt();
					insert(num);
					break;
				case 2:
					retList.add(removeRoot());
					break;
				default:
					break;
				}
			}
			System.out.print("#" + tc + " ");
			print(retList);
		}
		scan.close();
	}
	
	private static void insert(int num) {
		numList.add(num);
		buildheap();
	}
	
	private static Integer removeRoot() {
		int root = -1;
		if(numList.size() != 0) {
			root = numList.remove(0);
			buildheap();
		}
		return root;
	}

	// Heap Operations
	public static void Max_Heapify(int i)
	{
		int largest = i;
		int left = (2*i) + 1; 
		int right= (2*i) + 2; 
		int heap_size = numList.size();
		
		if(left < heap_size && numList.get(left) > numList.get(i)) {
			largest = left;
		}
	
		if(right < heap_size && numList.get(right) > numList.get(largest)) {
			largest = right;
		}
		
		if(largest != i){
			swap(i, largest);
			Max_Heapify(largest);
		}
	}
		
	// Build heap
	public static void buildheap(){ 
		int i = 0;
		int lastIndex = numList.size()-1;
		for(i = lastIndex/2; i >= 0; i--){
			Max_Heapify(i);
		}
	}
	
	private static void swap(int i, int largest) {
		int tmp = numList.get(i);
		numList.set(i, numList.get(largest));
		numList.set(largest, tmp);
	}
	
	private static void print(ArrayList<Integer> retList) {
		for(int i = 0; i < retList.size(); ++i) {
			System.out.print(retList.get(i) + " ");
		}
		System.out.println();
	}
}
