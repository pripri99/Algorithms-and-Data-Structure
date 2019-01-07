import java.io.*;
import java.util.*;
/* Written by Priscilia Momo

Problem:
N balloons are left in the room, floating from left to right.  
Player position themselves at the left end of the room and shoot an arrow towards the
right end of the room from an arbitrary height they choose.  Assume the arrows fly in a straight 
line at the height H chosen by the shooter.  When an arrow comes in contact with a balloon, the balloon disappears and the
arrow continues its way from left to right at a slightly lower height H-1, in a straight line.  In
other words, if the arrow travelling at height H comes in contact with a balloon at position i,
then at position i+1, the arrow will be at height H-1, and will maintain that height until it hits
another balloon or the wall.  

This program count the number of arrow needed to pop a set of balloon
*/

public class balloon {
	int[] numberOfBalloon;
	int[][] balloonHeightAll;
	int nProblem;
	int[] arrowNeededAll;

	balloon(String file, String filePath) {
		try {
			Scanner f = new Scanner(new File(file));
			this.nProblem = Integer.parseInt(f.nextLine());
			this.numberOfBalloon = new int[nProblem];
			this.balloonHeightAll = new int[nProblem][]; /* height of balloon for all test */
			String[] ln = f.nextLine().split("\\s+");
			for (int i = 0; i < nProblem; i++) {
				numberOfBalloon[i] = Integer.parseInt(ln[i]);
			}

			int index = 0;
			while (f.hasNext()) {
				String[] line = f.nextLine().split("\\s+");
				int[] heightBalloon = new int[line.length];

				for (int j = 0; j < heightBalloon.length; j++) {
					heightBalloon[j] = Integer.parseInt(line[j]);
				}
				balloonHeightAll[index] = heightBalloon;


				index++;

			}
			f.close();

			
			arrowNeededAll = new int[this.nProblem];

			for (int x = 0; x < this.nProblem; x++) {
				arrowNeededAll[x] = countArrowNeeded(this.balloonHeightAll[x]);
			}

			writeAnswer(filePath + "_solution.txt", arrowNeededAll);

		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			System.exit(1);
		}
	}

	public static int countArrowNeeded(int[] arrowHeight) {
		int nBalloon = arrowHeight.length;
		int nArrow = 1;
		int currentheight = arrowHeight[0];
		int currentIndex = 0; /* start with the first balloon on the left */
		boolean allBalloonPopped = false;
		boolean[] balloonPopped = new boolean[nBalloon];

		for (int i = 0; i < nBalloon; i++) {
			balloonPopped[i] = false;
		}

		while (!allBalloonPopped) {
			for (int i = currentIndex; i < nBalloon; i++) {
				if (currentheight == arrowHeight[i] && !balloonPopped[i]) {
					balloonPopped[i] = true;
					currentheight = currentheight - 1;
				}
			}

			for (int j = 0; j < nBalloon; j++) {
				allBalloonPopped = true;
				if (!balloonPopped[j]) {
					allBalloonPopped = false;
					break;
				}
			}

			if (!allBalloonPopped) {
				nArrow++;
				for (int i = 0; i < nBalloon; i++) {
					if (balloonPopped[i] == false) {
						currentIndex = i;
						currentheight = arrowHeight[i];
						break;
					}
				}

			}
		}

		return nArrow;
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

		// read file from command line to run

		String file = args[0];
		File f = new File(file);
		String filepath = f.getAbsolutePath().replace(".txt", "");

		// return file as output
		balloon b = new balloon(file, filepath);

	}

}
