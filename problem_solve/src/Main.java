import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
	static String Corporate_Name = "SAMSUNG";
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int T = scan.nextInt();
		for(int tc = 1; tc <= T; ++tc) {
			int N = scan.nextInt();
			ArrayList<Interviewer> interviewerList = new ArrayList<>(); 
			for(int nInterviewer = 0; nInterviewer < N; ++nInterviewer) {
				int L = scan.nextInt();
				String name = "";
				for(int lname = 0; lname < L; ++lname) {
					name += scan.next();
				}
				int score = scan.nextInt();
				interviewerList.add(new Interviewer(name, score));
			}
			System.out.println("#" + tc + " " + solve(interviewerList));
		}
		scan.close();
	}

	private static int solve(ArrayList<Interviewer> interviewerList) {
		int nId = interviewerList.size();
		int[] idArr = new int[nId];
		for(int i = 0; i < nId; ++i) {
			idArr[i] = i;
		}
		
		int retScore = Integer.MAX_VALUE;
		// 면접관 리스트의 인덱스을 원소로 가지는 집합으로 하여 모든 부분집합 case에 해당하는 
		for(int i = 0; i < (1 << nId); ++i) {
			String name = "";
			int score = 0;
			for(int j = 0; j < nId; ++j) {
				if((i & (1 << j)) != 0) {
					// 면접관들의 이름 문자열과 합계 점수 정보를 생성하여 
					name += interviewerList.get(idArr[j]).getName();
					score += interviewerList.get(idArr[j]).getScore();
				}
			}
			// "SAMSUNG"을 만들 수 있는지 확인한다.
			if(name.length() < Corporate_Name.length() || !isMatchable(name)) {
				continue;
			}
			if(score < retScore) {
				retScore = score;
			}
		}
		
		return (retScore != Integer.MAX_VALUE) ? retScore : -1;
	}

	private static boolean isMatchable(String name) {
		Map<Character, Integer> answerMap = createMap(Corporate_Name);
		Map<Character, Integer> interviewerMap = createMap(name);
		// Corporte_Name을 이루고 있는 모든 문자를 포함하고 있는지 확인
		subMap(answerMap, interviewerMap);
		
		for(char c : answerMap.keySet()) {
			int val = answerMap.get(c);
			if(val > 0) {
				return false;
			}
		}
		
		return true;
	}

	private static void subMap(Map<Character, Integer> answerMap, Map<Character, Integer> interviewerMap) {
		for (char c : answerMap.keySet()) {
			if (interviewerMap.containsKey(c)) {
				int val = answerMap.get(c) - interviewerMap.get(c);
				answerMap.put(c, val);
	        }
	    }
	}

	private static Map<Character, Integer> createMap(String str) {
		Map<Character, Integer> chMap = new HashMap<>();
		
		for(int i = 0 ; i < str.length(); ++i) {
			char c = str.charAt(i);
			
			int val = 0;
			if(chMap.containsKey(c)) {
				val = chMap.get(c);
			}
			
			chMap.put(c, val+1);
		}
		return chMap;
	}
}

class Interviewer {
	private String _name;
	private int _score;
	
	public Interviewer(String tmpName, int tmpScore) {
		this._name = tmpName;
		this._score = tmpScore;
	}

	public String getName() {
		return this._name;
	}
	
	public int getScore() {
		return this._score;
	}
}