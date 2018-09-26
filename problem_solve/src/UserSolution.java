import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

class UserSolution {
    public final static int N = 4;
    public static int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    public static Set<int[]> candidatesSet;

    public void doUserImplementation(int guess[]) {
        createAllSet();
        int n = candidatesSet.size();
        int a = 0;
        //guess = solve();
    }

	private void createAllSet() {
		candidatesSet = new HashSet<>();
		ArrayList<Integer> numList = new ArrayList<>();
		for (int number : numbers) {
			numList.add(number);
		}
		int[] candidate = new int[N];
		createAllSet(candidate, numList, 0);
	}

	private void createAllSet(int[] candidate, ArrayList<Integer> numList, int id) {
		if (id == N) {
			candidatesSet.add(candidate);
			return;
		}
		
		for (int i = 0; i < numbers.length - id; ++i) {
			candidate[id] = numList.remove(i);
			createAllSet(candidate, numList, id+1);
			numList.add(i, candidate[id]);
		}
	}

	
}