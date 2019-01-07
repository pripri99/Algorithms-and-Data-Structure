import java.io.*;
import java.util.*;

public class islands {
	private int nProblem;
	private char[][][] allImages;
	private int[][] imageDimension;
	private int[] nIslandAll;

	public islands(String file, String filePath) {
		try {
			Scanner f = new Scanner(new File(file));
			this.nProblem = Integer.parseInt(f.nextLine());
			this.imageDimension = new int[nProblem][2];
			this.allImages = new char[nProblem][][];

			int index = 0;
			while (f.hasNext()) {
				String[] line = f.nextLine().split("\\s+");

				// take dimension of image
				for (int j = 0; j < 2; j++) {
					this.imageDimension[index][j] = Integer.parseInt(line[j]);
				}
				int row = this.imageDimension[index][0];
				int col = this.imageDimension[index][1];
				char[][] image = new char[row][col];

				// store image in array
				for (int m = 0; m < this.imageDimension[index][0]; m++) {
					line = f.nextLine().split("\\s+");
					for (int n = 0; n < this.imageDimension[index][1]; n++) {
						image[m][n] = line[0].charAt(n);
					}
				}

				this.allImages[index] = image;

				index++;
			}

			f.close();
			
			this.nIslandAll = new int[nProblem];
			for (int j = 0; j < this.nProblem; j++) {
				this.nIslandAll[j] = countIsland(this.allImages[j]);
			}
			
			writeAnswer(filePath + "_solution.txt", this.nIslandAll);

		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			System.exit(1);
		}
	}

	public static int countIsland(char[][] image) {
		int nIsland = 0;
		int maxRow = image.length;
		int maxCol = image[0].length;

		boolean[][] visited = new boolean[maxRow][maxCol];

		for (int i = 0; i < maxRow; ++i)
			for (int j = 0; j < maxCol; ++j)
				if (image[i][j] == '-' && !visited[i][j]) {
					setNeighborToVisited(image, visited, i, j);
					nIsland++;
				}

		return nIsland;
	}

	public static void setNeighborToVisited(char[][] image, boolean[][] visited, int row, int col) {
		// These arrays are used to get row and column numbers
		// of 4 neighbors of a given cell
		int maxRow = image.length;
		int maxCol = image[0].length;
		int rowIndex[] = new int[] { 0, -1, 0, 1 };
		int colIndex[] = new int[] { -1, 0, 1, 0 };

		visited[row][col] = true;

		for (int k = 0; k < 4; k++) {
			int a = row + rowIndex[k];
			int b = col + colIndex[k];
			if (isNeighbor(image, a, b, visited, maxRow, maxCol)) {
				setNeighborToVisited(image, visited, a, b);
			}
		}

	}

	// A function to check if a given cell (row, col) is a neighbor
	static boolean isNeighbor(char[][] image, int row, int col, boolean visited[][], int maxRow, int maxCol) {

		// row number is in range, column number is in range
		// and value is '-' (white) and not yet visited
		return (row >= 0) && (row < maxRow) && (col >= 0) && (col < maxCol) && (image[row][col] == '-' && !visited[row][col]);
	}

	public static void writeAnswer(String path, int[] answer) {
		BufferedReader br = null;
		File file = new File(path);
		// if file doesnt exists, then create it

		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < answer.length; i++) {
				bw.write(String.valueOf(answer[i]));
				if (i < answer.length - 1) {
					bw.newLine();
				}
			}

			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		
		String file = args[0];
		File f = new File(file);
		String filepath = f.getAbsolutePath().replace(".txt", "");
		islands isle = new islands(file, filepath);
		
	}

}
