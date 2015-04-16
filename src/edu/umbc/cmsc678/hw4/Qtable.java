package edu.umbc.cmsc678.hw4;

import java.util.Random;

public class Qtable {

	double[][] qTable;
	
	 class OptimalQTableEntry{
	 int action;
	 double value;
	}
	
	public Qtable(int state,int maxActions) {
		qTable = new double [state][maxActions];
		Random r = new Random();
		for (int i=0;i<state;i++) {
			for(int j=0;j<maxActions;j++){
				qTable[i][j] = r.nextInt(100);
			}
			
		}
	}
	
	boolean isAllQvalueEqual(double[] qTable2){
		double startvalue=qTable2[0];
		for (int i=1;i<qTable2.length;i++) {
			if(qTable2[i] !=startvalue){
				return false;
			}
		}
		return true;
	}
	
	int getNextAction(int state){
		//return new Random().nextInt(qTable[state].length);
		//OptimalQTableEntry e = getOptimalEntry(state);
		if(isAllQvalueEqual(qTable[state])){
			
			return new Random().nextInt(qTable[state].length);
		}else{
			
			int index =0;
			double MaxVal = Double.MIN_VALUE;
			double [] testArray = qTable[state];
			/*
			System.out.println();
			for (double d : testArray) {
				System.out.print(d+"\t");
			}*/
			for (int i=0 ;i< testArray.length ; i++) {
				if(testArray[i] > MaxVal){
					MaxVal =testArray[i];
					index = i;
				}
			}
			return index;
		}
		
	}
	
	double getQval(int state,int action){
		return qTable[state][action];
	}
	
	void updateQtable(int state,int action,double value){
		qTable[state][action]= value;
	}
	
	double getQmax(int state){
		//OptimalQTableEntry e = getOptimalEntry(state);
		double MaxVal = Double.MIN_VALUE;
		double [] testArray = qTable[state];
		for (double d : testArray) {
			if(d > MaxVal){
				MaxVal =d;
			}
		}
		return MaxVal;
	}
	
	
	/*
	OptimalQTableEntry getOptimalEntry(int state){
		OptimalQTableEntry e = new OptimalQTableEntry();
		int index =0;
		double MaxVal = qTable[state][0];
		double currentQVal ;
		for(int i=0;i<qTable[state].length;i++){
			currentQVal = qTable[state][i];
			if(currentQVal > MaxVal){
				index=i;
				MaxVal = currentQVal;
			}
		}
		
		e.action =index;
		e.value = MaxVal;
		//System.out.println("returned Q vals "+e.value);
		return e;
	}
	*/
	
}
