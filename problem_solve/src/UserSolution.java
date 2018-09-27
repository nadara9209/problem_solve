import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

class UserSolution {
    public final static int N = 4;
    public static int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    public static Set<int[]> candidatesSet;
    
    public void doUserImplementation(int guess[]) {
        createAllSet();
        solve(guess);
    }

	private void solve(int[] guess) {
		while (true) {
			Iterator<int[]> iter1 = candidatesSet.iterator();
			
			int[] candidate = iter1.next();
			iter1.remove();
			for (int i = 0; i < guess.length; i++) {
				guess[i] = candidate[i]; 
			}
			
			if (candidatesSet.isEmpty()) {
				return;
			}
			
			Solution.Result resultA = Solution.query(guess);
			if (resultA.strike == 4) {
				return;
			}
			
			boolean[] flags = new boolean[numbers.length];
			for (int id : guess) {
				flags[id] = true;
			}

			Iterator<int[]> iter2 = candidatesSet.iterator();
			while (iter2.hasNext()) {
				int[] tmp = iter2.next();
				Solution.Result resultB = query_user(guess, tmp, flags);
				if (resultA.strike != resultB.strike || resultA.ball != resultB.ball) {
					iter2.remove();
				}
			}
		}
	}

	private Solution.Result query_user(int[] guess, int[] tmp, boolean[] flags) {
		Solution.Result result = new Solution.Result();
		for (int i = 0; i < N; ++i) {
			if (guess[i] == tmp[i]) {
				result.strike++;
			}
			else {
				if(flags[tmp[i]]) {
					result.ball++;
				}
			}
		}
		return result;
	}

	private void createAllSet() {
		candidatesSet = new HashSet<>();

		ArrayList<Integer> numList = new ArrayList<>();
		for (int number : numbers) {
			numList.add(number);
		}
		int[] candidate = new int[N];
//		createAllSet(candidate, numList, 0);
//		createAllSet_duplicate(candidate, numList, 0);
		createAllSet_nestedLoop(numList);
	}

	private void createAllSet(int[] candidate, ArrayList<Integer> numList, int id) {
		if (id == N) {
			candidatesSet.add(candidate.clone());
			return;
		}
		
		for (int i = 0; i < numbers.length - id; ++i) {
			candidate[id] = numList.remove(i);
			createAllSet(candidate, numList, id+1);
			numList.add(i, candidate[id]);
		}
	}
	
	private void createAllSet_duplicate(int[] candidate, ArrayList<Integer> numList, int id) {
		if (id == N) {
			candidatesSet.add(candidate.clone());
			return;
		}
		
		for (int i = 0; i < numbers.length; ++i) {
			candidate[id] = numList.get(i);
			createAllSet_duplicate(candidate, numList, id+1);
		}
	}
	
	private void createAllSet_nestedLoop(ArrayList<Integer> numList) {
		boolean[] flags = new boolean[10];
	
		for (int i0 = 0; i0 < 10; i0++) {
			int id = 0;
			int[] tmp = new int[N];
			tmp[id] = numList.get(i0);
			flags[i0] = true;
			for (int i1 = 0; i1 < 10; i1++) {
				if (flags[i1]) continue;
				tmp[++id] = numList.get(i1);
				flags[i1] = true;
				for (int i2 = 0; i2 < 10; i2++) {
					if (flags[i2]) continue;
					tmp[++id] = numList.get(i2);
					flags[i2] = true;
					for (int i3 = 0; i3 < 10; i3++) {
						if (flags[i3]) continue;
						tmp[++id] = numList.get(i3);
						candidatesSet.add(tmp.clone());
						--id;
					}
					flags[i2] = false;
					--id;
				}
				flags[i1] = false;
				--id;
			}
			flags[i0] = false;
			--id;
		}
	}
}