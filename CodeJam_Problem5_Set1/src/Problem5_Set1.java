import java.io.*;
import java.util.*;

public class Problem5_Set1 {
	public static void main(String[] args) {
		Scanner input;
		FileWriter output;
		
		ProbeCivilization probeCivilization;
		int result;
		int cases;
		int numOfCivils, periodOfExplore;
		int startX, startY, diffX, diffY;

		try {
			//input = new Scanner(new File("Set1.in"));
			//output = new FileWriter("Set1.out");
			input = new Scanner(new File("Set1.in"));
			output = new FileWriter("Set1.out");
			
			cases = input.nextInt();
			
			for(int i=0; i<cases; i++) {
				numOfCivils = input.nextInt();
				periodOfExplore = input.nextInt();
				
				probeCivilization = new ProbeCivilization(periodOfExplore);
				
				for(int j=0; j<numOfCivils; j++) {
					startX = input.nextInt();
					startY = input.nextInt();
					diffX = input.nextInt();
					diffY = input.nextInt();
					
					probeCivilization.addExplorer(startX, startY, diffX, diffY);
				}
				
				result = probeCivilization.exploreUniverse();

				output.write(result + "\n");

			}
			
			input.close();
			output.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class ProbeCivilization {
	private static int mPeriodOfExplore;
	private ArrayList<Explorer> mExplorerList;
	private HashMap<Integer, HashMap<Integer, Boolean>> mProbes;
	private int mCapsuleCount;
	
	public ProbeCivilization(int period) {
		mPeriodOfExplore = period;
		mExplorerList = new ArrayList<Explorer>();
		mProbes = new HashMap<Integer, HashMap<Integer, Boolean>>();
	}
	
	public void addExplorer(int startX, int startY, int diffX, int diffY) {
		mExplorerList.add(new Explorer(startX, startY, diffX, diffY));
		leaveProbe(startX, startY);
	}
	
	private boolean leaveProbe(int x, int y) {
		if(mProbes.get(x) == null) {
			mProbes.put(x, new HashMap<Integer, Boolean>());
		}
		
		if(mProbes.get(x).get(y) == null){
			mProbes.get(x).put(y, false);
	 	} else {
	 		System.out.println("leave probe fail");
			return false;
		}
		 
		return true;
	}
	
	private int checkAround(int x, int y) {
		int capsuleCount = 0;
		for(int i=(x-2); i<=(x+2); i++) {	// Find probe near this point from -2 ~ 2
			if(mProbes.get(i) != null) {	
				for(int j=(y-2); j<=(y+2); j++) {
					if((mProbes.get(i).get(j) != null) && (i != x && j != y)) {
						if(mProbes.get(i).get(j) == true) {
							capsuleCount += 2;
						} else {
							capsuleCount += 1;
						}
					}
				}
			}
		}
		
		return capsuleCount;
	}
	
	public int exploreUniverse() {
		for(Explorer e : mExplorerList) {
			if(!e.getCurrDuplicated()) {
				mCapsuleCount += checkAround(e.getX(), e.getY());
			}
		}

		for(int i=0; i<mPeriodOfExplore; i++) {
			for(Explorer e : mExplorerList) {
				mProbes.get(e.getX()).put(e.getY(), true);
				e.explore();
				e.setCurrDuplicated(!leaveProbe(e.getX(), e.getY()));
			}
			
			for(Explorer e : mExplorerList) {
				if(!e.getCurrDuplicated()) {
					mCapsuleCount += checkAround(e.getX(), e.getY());
				}
			}			
		}
		
		return mCapsuleCount;
	}
}

class Explorer {
	private int mCurrX;
	private int mCurrY;
	private int mDiffX;
	private int mDiffY;
	private boolean mIsCurrDup;
	
	public Explorer(int startX, int startY, int diffX, int diffY) {
		mCurrX = startX;
		mCurrY = startY;
		mDiffX = diffX;
		mDiffY = diffY;
	}
	
	public void explore() {
		mIsCurrDup = false;
		mCurrX += mDiffX;
		mCurrY += mDiffY;
	}
	
	public int getX() {
		return mCurrX;
	}
	
	public int getY() {
		return mCurrY;
	}
	
	public void setCurrDuplicated(boolean flag) {
		mIsCurrDup = flag;
	}
	
	public boolean getCurrDuplicated() {
		return mIsCurrDup;
	}
}

class Point {
	private int mX;
	private int mY;
	
	public Point(int x, int y) {
		mX = x;
		mY = y;
	}
	
	public int getX() {
		return mX;
	}

	public void setX(int x) {
		this.mX = x;
	}

	public int getY() {
		return mY;
	}

	public void setY(int y) {
		this.mY = y;
	}
}