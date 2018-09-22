import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

class Solution1 {
    public int solution(String[][] relation) {
        int answer = solve(relation);
        return answer;
    }

	private int solve(String[][] relation) {
		List<Set<Integer>> dbSet;
		int limit = relation[0].length;
		dbSet = createDB(limit);
		
		int nOfCandidateKey = count(dbSet, relation);
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
					str += relation[row][j];
				}
				if (strSet.contains(str)) {
					isUnique = false;
					break;
				}
				strSet.add(str);
			}
			
			// 동시에 같은 resource 접근 삭제시 뒤에서 부터 삭제한다.
			if(isUnique) {
				retCnt++;
				for (int k = dbSet.size()-1; k >= 0; --k) {
					Set<Integer> categoryB = dbSet.get(k);
					if (categoryB.containsAll(categoryA)) {
						dbSet.remove(categoryB);
					}
				}
			}
			
//			if(isUnique) {
//				dbSet.removeIf((Set<Integer> categoryB) -> categoryB.contains(categoryA));
//			}
		}
		
		return retCnt;
	}
	
	private List<Set<Integer>> createDB(int n) {
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