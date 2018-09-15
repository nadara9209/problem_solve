import java.util.ArrayList;
import java.util.HashMap;

class Solution {
	static String[] expr = {"님이 들어왔습니다.",
					   	    "님이 나갔습니다."};
    public String[] solution(String[] record) {
    	HashMap<String, String> userMap = new HashMap<>();
    	// order
    	ArrayList<Order> orderList = new ArrayList<>();
    	String[] answer = solve(record, userMap, orderList);
        return answer;
    }

	private String[] solve(String[] record, HashMap<String, String> userMap, ArrayList<Order> orderList) {
		String[] answer;
		for (int i = 0; i < record.length; ++i) {
			String str = record[i];
			String[] temp = str.split(" ");
			
			String command = temp[0];
			String uId = temp[1];
			String name = "";
			if (temp.length == 3) {
				name = temp[2];
			}
			
			switch (command.charAt(0)) {
			case 'E':
				userMap.put(uId, name);
				orderList.add(new Order(uId, 0));
				break;
			case 'L':
				orderList.add(new Order(uId, 1));
				break;
			case 'C':
				userMap.put(uId, name);
				break;
			default:
				break;
			}
		}
		
		answer = new String[orderList.size()];
		
		for (int i = 0; i < orderList.size(); ++i) {
			String uId = orderList.get(i).uId;
			int exprNum = orderList.get(i).exprNum;
			if (exprNum == 0) {
				answer[i] = userMap.get(uId) + expr[exprNum];
			}
			else {
				answer[i] = userMap.get(uId) + expr[exprNum];
			}
		}
		return answer;
	}
}

class Order {
	String uId;
	int exprNum;
	
	public Order(String uId, int exprNum) {
		this.uId = uId;
		this.exprNum = exprNum;
	}
}