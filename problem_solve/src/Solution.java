import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public int solution(int[] people, int[] tshirts) {
        int cnt = 0;
        Arrays.sort(people);
        Arrays.sort(tshirts);
        
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < tshirts.length; ++i) {
        	list.add(tshirts[i]);
        }
        
        int i = 0;
        int j = 0;
        for (int size : people) {
        	for (i = j; i < list.size(); ++i) {
        		if (size <= list.get(i)) {
        			list.remove(i);
        			j = i;
        			cnt++;
        			break;
        		}
        		else {
        			continue;
        		}
        	}
        }
        return cnt;
    }
}