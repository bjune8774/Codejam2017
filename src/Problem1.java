import java.util.ArrayList;

public class Problem1 {
	public final static int MAX_INFEST = 100;
	public final static int MAX_GRID = 500;
	private static ArrayList<Infestor> mInfestorList;
	
	public static void main(String[] args) {
		mInfestorList = new ArrayList<Infestor>();
		
		mInfestorList.add(new Infestor(1, 5));
		mInfestorList.add(new Infestor(3, 5));
		mInfestorList.add(new Infestor(4, 2));
		mInfestorList.add(new Infestor(5, 3));
		
		for(Infestor i : mInfestorList) {
			int ret;
			ret = i.infest();
		}
//		for(int i=0; i<2; i++) {
//			mInfestorList.add(new Infestor(1, 5));
//		}
	}
}

class Infestor {
	public static final int INFESTOR_COMPLETED = 0;
	public static final int INFESTOR_INFEST_SUCCESS = 1;
	public static final int INFESTOR_INFEST_FAIL = 2;
		
	private int mRow;	// Row value
	private int mCol;	// Column value
	private boolean mIsCompleted;	// For checking whether near cells are all infested
	
	public Infestor(int r, int c) {
		mRow = r;
		mCol = c;
		mIsCompleted = false;
	}
	
	public int infest() {
		if(mIsCompleted) 
			return INFESTOR_COMPLETED;
		
		
	}	
}