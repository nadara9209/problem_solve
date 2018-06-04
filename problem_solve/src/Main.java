import java.util.Scanner;

class Main {
	static int N;
	static int[] Drow = {-1,  1,  0,  0};
	static int[] Dcol = { 0,  0, -1,  1};
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		N = scan.nextInt();
		// 정상인, 적록색약 용 그림을 따로 저장
		char[][] colorMapForNormal = new char[N][N];
		char[][] colorMapForAbnormal = new char[N][N];
		
		for(int row = 0; row < N; ++row) {
			String line = scan.next();
			char[] charArr = line.toCharArray();
			
			for(int col = 0; col < N; ++col) {
				char selectedColor = charArr[col];
				
				colorMapForNormal[row][col] = selectedColor;
				// 초록일 경우 빨강으로 저장하여 적록색약용 그림 설정
				if(selectedColor == 'G') {
					colorMapForAbnormal[row][col] = 'R';
					continue;
				}
				
				colorMapForAbnormal[row][col] = selectedColor;
			}
		}
		System.out.println(solve(colorMapForNormal, colorMapForAbnormal));
		scan.close();
	}
	private static String solve(char[][] colorMapForNormal, char[][] colorMapForAbnormal) {
		int[][] normalSectorArr = new int[N][N];
		int[][] abnormalSectorArr = new int[N][N];
		// 구역의 종류 변수
		int sectorType = 0;
		for(int row = 0; row < N; ++row) {
			for(int col = 0; col < N; ++col) {
				if(normalSectorArr[row][col] != 0) {
					continue;
				}
				char selectedColor = colorMapForNormal[row][col];
				sectorType += 1;
				drawSameSectorArr(colorMapForNormal ,selectedColor, row, col, normalSectorArr, sectorType);
			}
		}
		
		// 적록색약용 그림의 구역의 종류 변수
		int sectorType2 = 0;
		for(int row = 0; row < N; ++row) {
			for(int col = 0; col < N; ++col) {
				if(abnormalSectorArr[row][col] != 0) {
					continue;
				}
				char selectedColor = colorMapForAbnormal[row][col];
				sectorType2 += 1;
				drawSameSectorArr(colorMapForAbnormal, selectedColor, row, col, abnormalSectorArr, sectorType2);
			}
		}

 		return Integer.toString(sectorType) + " " + Integer.toString(sectorType2);
	}
	private static void drawSameSectorArr(char[][] colorMap ,char prevColor, int row, int col, int[][] SectorMap,
			int sectorType) {
		// 외벽
		if(row < 0 || col < 0 || row >= N || col >= N) {
			return;
		}
		// 방문한 곳이 색이 다를 경우
		if(colorMap[row][col] != prevColor) {
			return;
		}
		// 이미 방문한 곳인 경우 탈출
		if(SectorMap[row][col] != 0) {
			return;
		}
		
		// 구역 설정
		SectorMap[row][col] = sectorType;
		
		//상
		drawSameSectorArr(colorMap, prevColor, row + Drow[0], col + Dcol[0], SectorMap, sectorType);
		//하
		drawSameSectorArr(colorMap, prevColor, row + Drow[1], col + Dcol[1], SectorMap, sectorType);
		//좌
		drawSameSectorArr(colorMap, prevColor, row + Drow[2], col + Dcol[2], SectorMap, sectorType);
		//우
		drawSameSectorArr(colorMap, prevColor, row + Drow[3], col + Dcol[3], SectorMap, sectorType);
	}
}
