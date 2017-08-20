import java.io.*;
import java.util.*;

public class Problem1_Set3 {
	public static void main(String[] args) {
		Scanner input;
		FileWriter output;
		
		int result;
		int cases;
		int numOfInfestor;
		int row, col;

		try {
			input = new Scanner(new File("Set3.in"));
			output = new FileWriter("Set3.out");
		
			cases = input.nextInt();
			
			for(int i=0; i<cases; i++) {
				numOfInfestor = input.nextInt();
				
				for(int j=0; j<numOfInfestor; j++) {
					row = input.nextInt();
					col = input.nextInt();
					
					CellGrid.setInfestedCell(row-1, col-1, true);
					CellGrid.addInfestor(row-1, col-1);
				}
				result = CellGrid.run();
				output.write(result + "\n");
				
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
	public static final int MAX_GRID = 1000000000;
	
	public static final int CELL_NOT_INFESTED = 0;
	public static final int CELL_INFESTING = 1;
	public static final int CELL_INFESTED = 2;
	public static final int CELL_NOT_EXIST = 3;
	
	private static HashMap<Integer, HashMap<Integer, Boolean>> mInfestedCellMap = new HashMap<Integer, HashMap<Integer, Boolean>>();
	private static ArrayList<Infestor> mInfestorList = new ArrayList<Infestor>();
	
	public static boolean setInfestedCell(int row, int col, boolean isInfested) {
		if(row < MAX_GRID) {
			if(mInfestedCellMap.containsKey(row)) {
				mInfestedCellMap.get(row).put(col, isInfested);
			} else {
				mInfestedCellMap.put(row, new HashMap<Integer, Boolean>());
				mInfestedCellMap.get(row).put(col, isInfested);
			}
		} else {
			return false;
		}
		
		return true;
	}

	public static void addInfestor(int row, int col) {
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

				for(int i=numOfInfestor; i<mInfestorList.size(); i++) {
					mInfestorList.get(i).infestCell();	// Do real infestation
				}
				numOfInfestor = mInfestorList.size();
			} else {	// If there is nothing to infest, finish infestation. 
				isFinished = true;
			}			
		}
		
		return secCount;
	}
	
	public static int checkCellStatus(int row, int col) {
		try {
			if(row < 0 || col < 0 || row > MAX_GRID - 1 || col > MAX_GRID - 1) {
				return CELL_NOT_EXIST;
			} else if(mInfestedCellMap.get(row).get(col) == true) {
				return CELL_INFESTED;
			} else if(mInfestedCellMap.get(row).get(col) == false) {
				return CELL_INFESTING;
			}
		} catch(NullPointerException e) {
			//System.out.println("Cell isn't infested");
		}
		return CELL_NOT_INFESTED;
	}
		
	// For debug
	static boolean started = false;
	public static void printCellGrid(int width) {
		FileWriter output;
		try {			
			if(started)
				output = new FileWriter(new File("Set_1_Debug.out"), true);
			else
				output = new FileWriter(new File("Set_1_Debug.out"), false);
			for(int i=0; i<width; i++) {
				for(int j=0; j<width; j++) {
					if(mInfestedCellMap.get(i).get(j) != null) {
						//System.out.print("бс");
						output.write("бс");
					}
					else {
						//System.out.print("бр");
						output.write("бр");
					}
				}
				//System.out.println();
				output.write("\n");
			}
			//System.out.println();
			output.write("\n");
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			started = true;
		}
	}
	
	public static void clear() {
		mInfestedCellMap.clear();
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
		if(CellGrid.checkCellStatus(mRow-1, mCol) >= CellGrid.CELL_INFESTING) {	
			infestedCellCount++;
		} else if((CellGrid.checkCellStatus(mRow-1, mCol-1) == CellGrid.CELL_INFESTED) ||	// Check left cell is infestor
				(CellGrid.checkCellStatus(mRow-2, mCol) == CellGrid.CELL_INFESTED) || 		// Check top cell is infestor
				(CellGrid.checkCellStatus(mRow-1, mCol+1) == CellGrid.CELL_INFESTED)) {		// Check right cell is infestor
			CellGrid.addInfestor(mRow-1, mCol);
			CellGrid.setInfestedCell(mRow-1, mCol, false);
			infestedCellCount++;
			infestFlag = true;
		}
		
		// Check if bottom cell is infested
		// If not, check infestor around the cell to infest
		if(CellGrid.checkCellStatus(mRow+1, mCol) >= CellGrid.CELL_INFESTING) {
			infestedCellCount++;
		} else if((CellGrid.checkCellStatus(mRow+1, mCol-1) == CellGrid.CELL_INFESTED) ||	// Check left cell is infestor
				(CellGrid.checkCellStatus(mRow+2, mCol) == CellGrid.CELL_INFESTED) || 		// Check bottom cell is infestor
				(CellGrid.checkCellStatus(mRow+1, mCol+1) == CellGrid.CELL_INFESTED)) {		// Check right cell is infestor
			CellGrid.addInfestor(mRow+1, mCol);
			CellGrid.setInfestedCell(mRow+1, mCol, false);
			infestedCellCount++;
			infestFlag = true;
		}
		
		// Check if left cell is infested
		// If not, check infestor around the cell to infest
		if(CellGrid.checkCellStatus(mRow, mCol-1) >= CellGrid.CELL_INFESTING) {
			infestedCellCount++;
		} else if((CellGrid.checkCellStatus(mRow-1, mCol-1) == CellGrid.CELL_INFESTED) ||	// Check top cell is infestor
				(CellGrid.checkCellStatus(mRow, mCol-2) == CellGrid.CELL_INFESTED) || 		// Check left cell is infestor
				(CellGrid.checkCellStatus(mRow+1, mCol-1) == CellGrid.CELL_INFESTED)) {		// Check bottom cell is infestor
			CellGrid.addInfestor(mRow, mCol-1);
			CellGrid.setInfestedCell(mRow, mCol-1, false);
			infestedCellCount++;
			infestFlag = true;
		}
		
		// Check if right cell is infested
		// If not, check infestor around the cell to infest
		if(CellGrid.checkCellStatus(mRow, mCol+1) >= CellGrid.CELL_INFESTING) {
			infestedCellCount++;
		} else if((CellGrid.checkCellStatus(mRow-1, mCol+1) == CellGrid.CELL_INFESTED) ||	// Check top cell is infestor
				(CellGrid.checkCellStatus(mRow, mCol+2) == CellGrid.CELL_INFESTED) || 		// Check right cell is infestor
				(CellGrid.checkCellStatus(mRow+1, mCol+1) == CellGrid.CELL_INFESTED)) {		// Check bottom cell is infestor
			CellGrid.addInfestor(mRow, mCol+1);
			CellGrid.setInfestedCell(mRow, mCol+1, false);
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
	
	public void infestCell() {
		CellGrid.setInfestedCell(mRow, mCol, true);
	}
}