import java.io.*;
import java.util.*;

public class Problem1 {
	public static void main(String[] args) {
		Scanner input;
		FileWriter output;
		
		int result;
		int cases;
		int numOfInfestor;
		int row, col;

		try {
			input = new Scanner(new File("Set1.in"));
			output = new FileWriter("Set1.out");
		
			cases = input.nextInt();
			
			for(int i=0; i<cases; i++) {
				numOfInfestor = input.nextInt();
				
				for(int j=0; j<numOfInfestor; j++) {
					row = input.nextInt();
					col = input.nextInt();
					
					CellGrid.setInfestorOnCell(row-1, col-1);
				}
				//CellGrid.printCellGrid(20);
				result = CellGrid.run();
				output.write(result + "\n");
				//CellGrid.printCellGrid(20);
				CellGrid.clear();
			}
			
			input.close();
			output.close();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class CellGrid {
	public static final int MAX_GRID = 500;
	
	public static final int CELL_NOT_INFESTED = 0;
	public static final int CELL_INFESTED = 1;
	public static final int CELL_NOT_EXIST = 2;
	
	private static boolean[][] mInfestedCellArray = new boolean[MAX_GRID][MAX_GRID];
	private static ArrayList<Infestor> mInfestorList = new ArrayList<Infestor>();
	
	public static void setInfestorOnCell(int row, int col) {
		mInfestedCellArray[row][col] = true;
		mInfestorList.add(new Infestor(row, col));
	}
	
	public static int run() {
		int secCount = 0;
		int infestCount = 0;
		boolean isFinished = false;
		int numOfInfestor = mInfestorList.size();
		
		while(!isFinished) {
			for(int i=0; i<numOfInfestor; i++) {
				int ret = mInfestorList.get(i).tryInfest();
				
				switch(ret) {
				case Infestor.INFESTOR_INFEST_COMPLETED :
				case Infestor.INFESTOR_INFEST_FAIL :
					// Do nothing
					break;
				case Infestor.INFESTOR_INFEST_SUCCESS :
					infestCount++;
					break;
				}
			}
			
			if(infestCount > 0) {	// If cell is infested, count seconds.
				secCount++;
				infestCount = 0;
				numOfInfestor = mInfestorList.size();
			} else {	// If there is nothing to infest, finish infestation. 
				isFinished = true;
			}			
		}
		
		return secCount;
	}
	
	public static int checkCellStatus(int row, int col) {
		if(row < 0 || col < 0 || row > MAX_GRID - 1 || col > MAX_GRID - 1) {
			return CELL_NOT_EXIST;
		} else if(mInfestedCellArray[row][col]) {
			return CELL_INFESTED;
		} 
		
		return CELL_NOT_INFESTED;
	}
	
	// for debug
	public static void printCellGrid(int width) {
		for(int i=0; i<width; i++) {
			for(int j=0; j<width; j++) {
				if(mInfestedCellArray[i][j])
					System.out.print("бс");
				else
					System.out.print("бр");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static void clear() {
		mInfestedCellArray = new boolean[MAX_GRID][MAX_GRID];
		mInfestorList.clear();
	}
}

class Infestor {
	public static final int INFESTOR_INFEST_COMPLETED = 0;	// When infestation is completed
	public static final int INFESTOR_INFEST_SUCCESS = 1;	// When infestation is succeeded
	public static final int INFESTOR_INFEST_FAIL = 2;	// When infestation is failed
			
	private int mRow;	// Row value
	private int mCol;	// Column value
	private boolean mIsCompleted;	// For checking whether near cells are all infested
	
	public Infestor(int row, int col) {
		mRow = row;
		mCol = col;
		mIsCompleted = false;
	}
	
	public int tryInfest() {
		int infestedCellCount = 0;
		boolean infestFlag = false;
		
		if(mIsCompleted) 
			return INFESTOR_INFEST_COMPLETED;
		
		// Check if top cell is infested
		// If not, check infestor around the cell to infest
		if(CellGrid.checkCellStatus(mRow-1, mCol) >= CellGrid.CELL_INFESTED) {	
			infestedCellCount++;
		} else if((CellGrid.checkCellStatus(mRow-1, mCol-1) == CellGrid.CELL_INFESTED) ||	// Check left cell is infestor
				(CellGrid.checkCellStatus(mRow-2, mCol) == CellGrid.CELL_INFESTED) || 		// Check top cell is infestor
				(CellGrid.checkCellStatus(mRow-1, mCol+1) == CellGrid.CELL_INFESTED)) {		// Check right cell is infestor
			CellGrid.setInfestorOnCell(mRow-1, mCol);
			infestedCellCount++;
			infestFlag = true;
		}
		
		// Check if bottom cell is infested
		// If not, check infestor around the cell to infest
		if(CellGrid.checkCellStatus(mRow+1, mCol) >= CellGrid.CELL_INFESTED) {
			infestedCellCount++;
		} else if((CellGrid.checkCellStatus(mRow+1, mCol-1) == CellGrid.CELL_INFESTED) ||	// Check left cell is infestor
				(CellGrid.checkCellStatus(mRow+2, mCol) == CellGrid.CELL_INFESTED) || 		// Check bottom cell is infestor
				(CellGrid.checkCellStatus(mRow+1, mCol+1) == CellGrid.CELL_INFESTED)) {		// Check right cell is infestor
			CellGrid.setInfestorOnCell(mRow+1, mCol);
			infestedCellCount++;
			infestFlag = true;
		}
		
		// Check if left cell is infested
		// If not, check infestor around the cell to infest
		if(CellGrid.checkCellStatus(mRow, mCol-1) >= CellGrid.CELL_INFESTED) {
			infestedCellCount++;
		} else if((CellGrid.checkCellStatus(mRow-1, mCol-1) == CellGrid.CELL_INFESTED) ||	// Check top cell is infestor
				(CellGrid.checkCellStatus(mRow, mCol-2) == CellGrid.CELL_INFESTED) || 		// Check left cell is infestor
				(CellGrid.checkCellStatus(mRow+1, mCol-1) == CellGrid.CELL_INFESTED)) {		// Check bottom cell is infestor
			CellGrid.setInfestorOnCell(mRow, mCol-1);
			infestedCellCount++;
			infestFlag = true;
		}
		
		// Check if right cell is infested
		// If not, check infestor around the cell to infest
		if(CellGrid.checkCellStatus(mRow, mCol+1) >= CellGrid.CELL_INFESTED) {
			infestedCellCount++;
		} else if((CellGrid.checkCellStatus(mRow-1, mCol+1) == CellGrid.CELL_INFESTED) ||	// Check top cell is infestor
				(CellGrid.checkCellStatus(mRow, mCol+2) == CellGrid.CELL_INFESTED) || 		// Check right cell is infestor
				(CellGrid.checkCellStatus(mRow+1, mCol+1) == CellGrid.CELL_INFESTED)) {		// Check bottom cell is infestor
			CellGrid.setInfestorOnCell(mRow, mCol+1);
			infestedCellCount++;
			infestFlag = true;
		}
		
		if(infestedCellCount == 4) {
			mIsCompleted = true;
		}
		
		if(infestFlag)
			return INFESTOR_INFEST_SUCCESS;
		else
			return INFESTOR_INFEST_FAIL;
	}
}