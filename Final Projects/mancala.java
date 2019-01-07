import java.io.*;
import java.util.*;

public class mancala {
	int[][] board;
	int nProblem;
	int[] remainingPebbleAll;
	int sizeBoard;

	public mancala(String file, String filePath) {

		try {
			this.sizeBoard = 12;
			Scanner f = new Scanner(new File(file));
			this.nProblem = Integer.parseInt(f.nextLine());
			this.board = new int[nProblem][this.sizeBoard];

			for (int j = 0; j < this.nProblem; j++) {
				String[] ln = f.nextLine().split("\\s+");
				for (int i = 0; i < 12; i++) {
					this.board[j][i] = Integer.parseInt(ln[i]);
				}
			}
			f.close();
			
			this.remainingPebbleAll = new int[nProblem];
			for (int j = 0; j < this.nProblem; j++) {
				this.remainingPebbleAll[j] = playMancala(this.board[j]);
			}
			
			
			writeAnswer(filePath + "_solution.txt", this.remainingPebbleAll);
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			System.exit(1);
		}
	}
	
	/*
	 * remove pebbles recursively
	 * return the minimum number of pebble that can be left
	 */
	public static int playMancala(int[] board) {
		int nPebbles = countPebbles(board);
		int n = board.length;
		
		int remainingPebbles = n;
		
		if (nPebbles == 0 ) return 0;
		if (nPebbles == 1 ) return 1;
		if (nPebbles == n) return n;
		
		

		
		int[] copyBoardEPP = new int[n];
		int[] copyBoardPPE = new int[n];
		
		for (int i = 0; i< n; i++) {
			copyBoardEPP[i] = board[i];
			copyBoardPPE[i] = board[i];
					
		}
		
		
		for (int i = 0; i<n; i++) {
			int c;
			if ((i < n-1) && (board[i] == 1) && (board[i+1] == 1)) {
				if ((i > 0) && (board[i-1] == 0) ) {
					copyBoardEPP[i-1] = 1;
					copyBoardEPP[i] = 0;
					copyBoardEPP[i+1] = 0;
					c = playMancala(copyBoardEPP);
					if (c < remainingPebbles) remainingPebbles = c;
					
				}
				
				if ((i+2 < n-1) && (board[i+2] == 0) ) {
					copyBoardPPE[i] = 0;
					copyBoardPPE[i+1] = 0;
					copyBoardPPE[i+2] = 1;
					c = playMancala(copyBoardPPE);
					
					if (c < remainingPebbles) remainingPebbles = c;
				}
			}
		}
		int cEPP = countPebbles(copyBoardEPP);
		int cPPE = countPebbles(copyBoardPPE);
		
		if (cEPP < remainingPebbles) {
		
			remainingPebbles = cEPP;
			
		}
		if (cPPE < remainingPebbles) {
			
			remainingPebbles = cPPE;
			
		}
		
		
		return remainingPebbles;
		
	}
	
	
	public static int countPebbles(int[] board) {
		int nPebbles = 0;
		//count pebble on board
		for (int i = 0; i<board.length; i++) {
			if (board[i] == 1) {
				nPebbles++;
			}
		}
		
		return nPebbles;
		
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
		mancala m = new mancala(file, filepath);
		

	}

}
