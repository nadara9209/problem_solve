import java.util.Scanner;

class Main {
	static int N;
	static int[] Drow = {-1,  1,  0,  0};
	static int[] Dcol = { 0,  0, -1,  1};
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		N = scan.nextInt();
		// ������, ���ϻ��� �� �׸��� ���� ����
		char[][] colorMapForNormal = new char[N][N];
		char[][] colorMapForAbnormal = new char[N][N];
		
		for(int row = 0; row < N; ++row) {
			String line = scan.next();
			char[] charArr = line.toCharArray();
			
			for(int col = 0; col < N; ++col) {
				char selectedColor = charArr[col];
				
				colorMapForNormal[row][col] = selectedColor;
				// �ʷ��� ��� �������� �����Ͽ� ���ϻ���� �׸� ����
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
		// ������ ���� ����
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
		
		// ���ϻ���� �׸��� ������ ���� ����
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
		// �ܺ�
		if(row < 0 || col < 0 || row >= N || col >= N) {
			return;
		}
		// �湮�� ���� ���� �ٸ� ���
		if(colorMap[row][col] != prevColor) {
			return;
		}
		// �̹� �湮�� ���� ��� Ż��
		if(SectorMap[row][col] != 0) {
			return;
		}
		
		// ���� ����
		SectorMap[row][col] = sectorType;
		
		//��
		drawSameSectorArr(colorMap, prevColor, row + Drow[0], col + Dcol[0], SectorMap, sectorType);
		//��
		drawSameSectorArr(colorMap, prevColor, row + Drow[1], col + Dcol[1], SectorMap, sectorType);
		//��
		drawSameSectorArr(colorMap, prevColor, row + Drow[2], col + Dcol[2], SectorMap, sectorType);
		//��
		drawSameSectorArr(colorMap, prevColor, row + Drow[3], col + Dcol[3], SectorMap, sectorType);
	}
}
