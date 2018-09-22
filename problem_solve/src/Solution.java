import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

class Solution {
    public int solution(String[][] relation) {
        int answer = solve(relation);
        return answer;
    }

	private int solve(String[][] relation) {
		List<Set<Integer>> categoryList;
		int limit = relation[0].length;
		categoryList = createList_1(limit);
		
		int nOfCandidateKey = count(categoryList, relation);
		return nOfCandidateKey;
	}

	private int count(List<Set<Integer>> dbSet, String[][] relation) {
		int retCnt = 0;
		for (int i = 0; i < dbSet.size(); ++i) {
			Set<Integer> categoryA = dbSet.get(i);
			Set<String> strSet = new HashSet<>();
			boolean isUnique = true;
			for (int row = 0; row < relation.length; ++row) {
				String str = "";
				for (int j : categoryA) {
					str += relation[row][j] + "_";
				}
				if (strSet.contains(str)) {
					isUnique = false;
					break;
				}
				strSet.add(str);
			}
			
			if(isUnique) {
				retCnt++;
				dbSet.removeIf((Set<Integer> categoryB) -> categoryB.containsAll(categoryA));
			}
		}
		return retCnt;
	}
	
	private List<Set<Integer>> createList(int n) {
		List<Set<Integer>> retList = new LinkedList<>();
		for (int i = 1; i < (1 << n); ++i) {
			Set<Integer> tmpSet = new HashSet<>();
			
			int attributeId = 0;
			for (int j = i; j != 0; j >>>= 1) {
				if ((j & 1) != 0) {
					tmpSet.add(attributeId);
				}
				attributeId += 1;
			}
			retList.add(tmpSet);
		}
		return retList;
	}
	
	private List<Set<Integer>> createList_1(int n) {
		List<Set<Integer>> retList = new LinkedList<>();
		for (int i = 1; i < (1 << n); ++i) {
			Set<Integer> tmpSet = new HashSet<>();
			
			for (int j = 0; j < n; ++j) {
				if ((i & (1 << j)) != 0) {
					tmpSet.add(j);
				}
			}
			retList.add(tmpSet);
		}
		return retList;
	}
	
//	private Set<Integer> createDB_1(int n) {
//		Set<Integer> retSet = new HashSet<>();
//		for (int i = 1; i < (1 << n); ++i) {
//			int attributeId = 0;
//			int sumOfAttributeId = 0;
//			for (int j = i; j != 0; j >>>= 1) {
//				if ((j & 1) != 0) {
//					sumOfAttributeId += attributeId;
//				}
//				attributeId <<= 1;
//			}
//			retSet.add(sumOfAttributeId);
//		}
//		
//		Iterator<Integer> iter = retSet.iterator();
//		while(iter.hasNext()) {
//			Integer i = iter.next();
//			System.out.println(i);
//		}
//		
//		return retSet;
//	}
}