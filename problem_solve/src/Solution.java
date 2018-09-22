import java.util.ArrayList;
import java.util.Arrays;

class Solution {
    public int solution(int[] people, int[] tshirts) {
        int answer = 0;
        Arrays.sort(people);
        Arrays.sort(tshirts);
        
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < tshirts.length; ++i) {
        	list.add(tshirts[i]);
        }
        
        int i = 0;
        int j = 0;
        for (int size : people) {
        	for (i = j; i < list.size(); ++i) {
        		if (size <= list.get(i)) {
        			list.remove(i);
        			answer++;
        			j = i;
        			break;
        		}
        		else {
        			continue;
        		}
        	}
        }
        return answer;
    }
}