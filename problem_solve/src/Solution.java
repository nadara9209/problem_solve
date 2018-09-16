import java.util.LinkedList;

class Solution {
	static LinkedList<Food> food_list;
    public int solution(int[] food_times, long k) {
        int answer = solve(food_times, k);
        System.out.println(answer);
        return answer;
    }

    private int solve(int[] food_times, long k) {
        long cnt = 0;
        food_list = new LinkedList<>();
        for (int i = 0; i < food_times.length; ++i) {
        	food_list.add(new Food(i+1, food_times[i]));
        }
        
        while (cnt < k) {
        	if(food_list.isEmpty()) {
            	return -1;
            }
            Food food = food_list.removeFirst();
            food.time -= 1;
            if(food.time != 0) {
            	food_list.addLast(food);
            }
            cnt++;
        }
        
        return food_list.getFirst().num;
    }
}

class Food {
	int num;
	int time;
	
	public Food(int num, int time) {
		this.num = num;
		this.time = time;
	}
}