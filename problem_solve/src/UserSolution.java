
class UserSolution {
    public final static int N = 4;
    public static int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    public static MyArrayList<int[]> candidatesList;
    
    public void doUserImplementation(int guess[]) {
        createAllSet();
        solve(guess);
    }

	private void solve(int[] guess) {
		while (true) {
			for (int i = 0; i < candidatesList.size(); ++i) {
				for (int id = 0; id < guess.length; ++id) {
					guess[id] = candidatesList.get(i)[id];
				}
				
				Solution.Result result = Solution.query(guess);
				if (isGameOver(result)) {
					return;
				}
				
				boolean[] flags = new boolean[numbers.length];
				for (int id = 0; id < guess.length; ++id) {
					flags[guess[id]] = true;
				}
				
				for (int id = candidatesList.size()-1; id >= 0; --id) {
					int[] candidate = candidatesList.get(id);
					if (isSame(candidate, guess, flags, result)) {
						candidatesList.remove(candidate);
					}
				}
			}
		}
	}

	private boolean isSame(int[] candidate, int[] guess, boolean[] flags, Solution.Result inputResult) {
		Solution.Result currResult = new Solution.Result();
		for (int i = 0; i < guess.length; ++i) {
			if (guess[i] == candidate[i]) {
				currResult.strike++;
			}
			else {
				if (flags[candidate[i]]) {
					currResult.ball++;
				}
			}
		}
		
		if (currResult.strike == inputResult.strike && currResult.ball == inputResult.ball) {
			return false;
		}
//		else if (currResult.strike == 4) {
//			return true;
//		}
		else {
			return true;
		}
	}

	private boolean isGameOver(Solution.Result result) {
		return (result.strike == 4);
	}

	private void createAllSet() {
		candidatesList = new MyArrayList<>(10000);
//		MyEqaulator<int[]> myEq = new MyEqaulator<int[]>() {
//			@Override
//			public boolean isEqual(int[] o1, int[] o2) {
//				if (o1.length != o2.length) {
//					return false;
//				}
//				for (int i = 0; i < o1.length; i++) {
//					if (o1[i] != o2[i])
//						return false;
//				}
//				return true;
//				
//			}
//		};
//		candidatesList.setEqualator(myEq);

		MyArrayList<Integer> numList = new MyArrayList<>(10);
		for (int number : numbers) {
			numList.add(number);
		}
		int[] candidate = new int[N];
		createAllSet(candidate, numList, 0);
//		createAllSet_duplicate(candidate, numList, 0);
//		createAllSet_nestedLoop(numList);
	}

	private void createAllSet(int[] candidate, MyArrayList<Integer> numList, int id) {
		if (id == N) {
			candidatesList.add(candidate.clone());
			return;
		}
		
		for (int i = 0; i < numbers.length - id; ++i) {
			candidate[id] = numList.remove(i);
			createAllSet(candidate, numList, id+1);
			numList.add(i, candidate[id]);
		}
	}
	
	private void createAllSet_duplicate(int[] candidate, MyArrayList<Integer> numList, int id) {
		if (id == N) {
			candidatesList.add(candidate.clone());
			return;
		}
		
		for (int i = 0; i < numbers.length; ++i) {
			candidate[id] = numList.get(i);
			createAllSet_duplicate(candidate, numList, id+1);
		}
	}
	
	private void createAllSet_nestedLoop(MyArrayList<Integer> numList) {
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
						candidatesList.add(tmp.clone());
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
	
	static interface MyEqaulator<T> {
		boolean isEqual(T o1, T o2);
	}
	
	static class MyDefaultEqualator implements MyEqaulator {
		@Override
		public boolean isEqual(Object o1, Object o2) {
			return o1 == o2;
		}
		
	}

	static class MyArrayList<E> {
		private E[] elements;
		private int size;
		private MyEqaulator<E> cmp;
		
		
		@SuppressWarnings("unchecked")
		public MyArrayList(int capacity) {
			this.elements = (E[]) new Object[capacity];
			this.size = 0;
			this.cmp = new MyDefaultEqualator();
		}
		
		public void setEqualator(MyEqaulator<E> eq) {
			this.cmp = eq;
		}

		public void add(int index, E value) {
			for (int i = this.size; i > index; i--) {
				this.elements[i] = this.elements[i - 1];
			}
			this.elements[index] = value;
			this.size++;
		}

		public void add(E value) {
			this.elements[this.size] = value;
			this.size++;
		}

		public void remove(E value) {
			int index = this.indexOf_(value);
			this.remove(index);
		}

		public E remove(int index) {
			E ret = this.elements[index];
			this.size--;
			for (int i = index; i < this.size; i++) {
				this.elements[i] = this.elements[i + 1];
			}
			this.elements[this.size] = null;
			return ret;
		}

		private int indexOf_(E value) {
			for (int i = 0; i < this.size; i++) {
				E e = this.elements[i];
				if (this.cmp.isEqual(e, value)) {
					return i;
				}
			}
			return -1;
		}

		public E get(int index) {
			if (index < 0) return null;
			if (index >= this.size) return null;
			return this.elements[index];
		}

		public int size() {
			return this.size;
		}
		
	}
}