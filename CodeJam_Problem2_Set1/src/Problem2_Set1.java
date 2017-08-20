import java.io.*;
import java.util.*;

public class Problem2_Set1 {
	public static void main(String[] args) {
		Scanner input;
		FileWriter output;
		
		int result;
		int cases;
		int numOfPoints;
		int row, col;

		try {
			//input = new Scanner(new File("Set1.in"));
			//output = new FileWriter("Set1.out");
			input = new Scanner(new File("Set1_Sample.in"));
			output = new FileWriter("Set1_Sample.out");
			cases = input.nextInt();
			System.out.println("cases " + cases);
			for(int i=0; i<cases; i++) {
				numOfPoints = input.nextInt();
				System.out.println("numOfPoints " + numOfPoints);
				for(int j=0; j<numOfPoints; j++) {
					row = input.nextInt();
					col = input.nextInt();
					System.out.println("row " + row + ", col " + col);
				}

				//output.write(result + "\n");

			}
			
			input.close();
			output.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
