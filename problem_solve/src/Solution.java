import java.util.Scanner;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;

public class Solution {
	static CounterDesk ReceptionDesk;
	static CounterDesk RepairDesk;
	static int K;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for(int tc = 1; tc <= T; ++tc) {
			Queue<Customer> customerList = new LinkedList<>();
			List<Counter> receptionCounterList = new ArrayList<>();
			List<Counter> repairCounterList = new ArrayList<>();
			
			int N = scan.nextInt();
			int M = scan.nextInt();
			K = scan.nextInt();
			// goal Customer info
			int A = scan.nextInt();
			int B = scan.nextInt();
			
			// create reception Desk 
			for(int i = 1; i <= N; ++i) {
				int val = scan.nextInt();
				receptionCounterList.add(new Counter(i, val));
			}
			ReceptionDesk = new CounterDesk(receptionCounterList);
			
			// create repair Desk
			for(int i = 1; i <= M; ++i) {
				int val = scan.nextInt();
				repairCounterList.add(new Counter(i, val));
			}
			RepairDesk = new CounterDesk(repairCounterList);
			
			// create Customer waiting line
			for(int i = 1; i <= K; ++i) {
				int val = scan.nextInt();
				customerList.offer(new Customer(i, val));
			}

			// result 
			int answer = solve(customerList, A, B);
			System.out.println("#" + tc + " " + answer);
		}
		
		scan.close();
	}

	private static int solve(Queue<Customer> customerList, int a, int b) {
		Queue<Customer> afterReceptionList = new LinkedList<>();
		afterReceptionList = processWithReceptionDesk(customerList);
		Queue<Customer> afterRepairList = new LinkedList<>();
		afterRepairList = processWithRepairDesk(afterReceptionList);
		
		int sumOfCustomerId = 0;
		while(!afterRepairList.isEmpty()) {
			Customer c = afterRepairList.poll();
			if(c.receptionCounterId == a && c.repairCounterId == b) {
				sumOfCustomerId += c.id;
			}
		}
		return (sumOfCustomerId == 0) ? -1 : sumOfCustomerId;
	}

	private static Queue<Customer> processWithRepairDesk(Queue<Customer> afterReceptionList) {
		Queue<Customer> retList = new LinkedList<>();
		int time = 0;
		while(retList.size() != K) {
			RepairDesk.putUpCustomerToCounter(time, retList);
			RepairDesk.putDownCustomerToCounter(time, afterReceptionList);
			time++;
		}
		return retList;
	}

	private static Queue<Customer> processWithReceptionDesk(Queue<Customer> customerList) {
		Queue<Customer> retList = new LinkedList<>();
		int time = 0;
		while(retList.size() != K) {
			ReceptionDesk.putUpCustomerToCounter(time, retList);
			ReceptionDesk.putDownCustomerToCounter(time, customerList);
			time++;
		}
		return retList;
	}
}

class CounterDesk {
	List<Counter> counterList;
	CounterDesk(List<Counter> list) {
		this.counterList = list;
	}
	
	public void putDownCustomerToCounter(int time, Queue<Customer> customerList) {
		for(int i = 0; i < this.counterList.size(); ++i) {
			if(customerList.isEmpty() || !(time >= customerList.peek().arrivalTime)) {
				break;
			}
			Counter c = counterList.get(i);
			if(c.isUsable) {
				c.startTime = time;
				c.currCustomer = customerList.poll();
				c.isUsable = false;
			}
		}
	}

	public void putUpCustomerToCounter(int time, Queue<Customer> retList) {
		List<Customer> tmpList = new ArrayList<>();
		boolean isReceptionDesk = false;
		for(int i = 0; i < this.counterList.size(); ++i) {
			Counter c = this.counterList.get(i);
			if(!c.isUsable) {
				if((time - c.startTime) == c.processingTime) {
					if(c.currCustomer.receptionCounterId == 0) {
						c.currCustomer.receptionCounterId = c.id;
						isReceptionDesk = true;
					}
					else if(c.currCustomer.repairCounterId == 0) {
						c.currCustomer.repairCounterId = c.id;
					}
					c.currCustomer.arrivalTime = time;
					tmpList.add(c.currCustomer);
					c.startTime = 0;
					c.isUsable = true;
					c.currCustomer = null;
				}
			}
		}
		// only needed for Reception Process
		if(isReceptionDesk && tmpList.size() > 1) {
			Collections.sort(tmpList);
		}
		for(int i = 0; i < tmpList.size(); ++i) {
			retList.offer(tmpList.get(i));
		}
	}
}

class Counter {
	int id;
	int processingTime;
	int startTime;
	boolean isUsable;
	Customer currCustomer;
	
	Counter(int id, int processingTime) {
		this.id = id;
		this.processingTime = processingTime;
		this.startTime = 0;
		this.isUsable = true;
		this.currCustomer = null;
	}
}

class Customer implements Comparable<Customer>{
	int id;
	int arrivalTime;
	int receptionCounterId;
	int repairCounterId;
	Customer(int id, int arrivalTime) {
		this.id = id;
		this.arrivalTime = arrivalTime;
		this.receptionCounterId = 0;
		this.repairCounterId = 0;
	}
	
	public int compareTo(Customer o) {
		if(this.receptionCounterId > o.receptionCounterId) {
			return 1;
		}
		else if(this.receptionCounterId < o.receptionCounterId) {
			return -1;
		}
		else {
			return 0;
		}
	}
}