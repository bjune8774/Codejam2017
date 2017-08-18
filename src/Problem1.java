import java.util.*;

public class Problem1 {
	public static void main(String[] args) {
		
		CellGrid cellGrid = new CellGrid();
		ArrayList<Infestor> mInfestorList = new ArrayList<Infestor>();
		
//		for(int i=0; i<2; i++) {
//			mInfestorList.add(new Infestor(1, 5));
//		}
		cellGrid.infestCell(1, 5);
		cellGrid.infestCell(3, 5);
		cellGrid.infestCell(4, 2);
		cellGrid.infestCell(5, 3);
		
		cellGrid.run();

	}
}

class CellGrid {
	public static final int MAX_INFEST = 100;
	public static final int MAX_GRID = 500;
	
	private int mSecCount;
	private Cell[][] mCellArray;
	private ArrayList<Infestor> mInfestorList;
	
	public CellGrid() {
		mSecCount = 0;
		mCellArray = new Cell[MAX_GRID][MAX_GRID];
		mInfestorList = new ArrayList<Infestor>();
	}
	
	public void infestCell(int row, int col) {
		mCellArray[row][col].infest();
		mInfestorList.add(new Infestor(row, col));
	}
	
	public boolean run() {
		boolean isFinished = false;
		
		while(!isFinished) {
			for(Infestor i : mInfestorList) {
				int ret;
				ret = i.tryInfest(mCellArray);
			}
		}
		return false;
	}
}

class Cell {
	private boolean mIsInfested;
	
	public Cell() {
		mIsInfested = false;
	}
	
	public void infest() {
		mIsInfested = true;
	}
	
	public boolean isInfested() {
		return mIsInfested;
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
	
	public int tryInfest(Cell[][] cellArray) {
		int infestCellCount = 0;
		if(mIsCompleted) 
			return INFESTOR_INFEST_COMPLETED;
		
//		if(mRow > 0 && cellArray[mRow - 1][mCol].isInfested()) {
//			infestCellCount++;
//		} else {
//			
//		}
//		
//		if(mRow < (CellGrid.MAX_GRID - 1) && !cellArray[mRow + 1][mCol].isInfested()) {
//			infestCellCount++;
//		} else {
//			
//		}
//		
//		if(mCol > 0 && !cellArray[mRow][mCol - 1].isInfested()) {
//			infestCellCount++;
//		} else {
//			
//		}
//		
//		if(mCol < (CellGrid.MAX_GRID - 1) && !cellArray[mRow][mCol + 1].isInfested()) {
//			infestCellCount++;
//		} else {
//			
//		}
		
//		if(infestCellCount == 4)
		return INFESTOR_INFEST_FAIL;
	}
	
}