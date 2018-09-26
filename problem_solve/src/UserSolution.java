import java.util.HashSet;
import java.util.Set;

class UserSolution {
    public final static int N = 4;
    public static int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    public static Set<Integer[]> candidateSet;

    public void doUserImplementation(int guess[]) {
        candidateSet = createAllSet();
        
    }

	private Set<Integer[]> createAllSet() {
		// Use permutation to create powerSet
		// Set iterator remove
		Set<Integer[]> retSet = new HashSet<>();
		return null;
	}
}