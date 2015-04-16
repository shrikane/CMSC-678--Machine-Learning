package edu.umbc.cmsc678.hw4;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Qtable {

	double[][] qTable;
	Set<Integer> randomize;
	int counter;
	int noiseFactor;
	
	/***
	 * Generates Qtable from random non zero values 
	 * @param state - maximum possible states
	 * @param maxActions - maximum actions per state 
	 * @param percentNoise - noise in selecting state
	 */
	public Qtable(int state,int maxActions,int percentNoise) {
		qTable = new double [state][maxActions];
		Random r = new Random();
		for (int i=0;i<state;i++) {
			for(int j=0;j<maxActions;j++){
				qTable[i][j] = r.nextInt(100);
			}
		}
		noiseFactor = Math.round((percentNoise/10));
		getRandomActionsId(noiseFactor);
	}
	
	/**
	 * To add noise in state selection
	 * @param noiseFactor
	 */
	void getRandomActionsId(int noiseFactor){
		randomize =  new HashSet<Integer>();
		Random r = new Random();
		for(int i=0;i<noiseFactor;i++){
			randomize.add(r.nextInt(10));
		}
	}
	
	/**
	 * if all values in Q table are equal select random
	 * @param qTable2
	 * @return
	 */
	boolean isAllQvalueEqual(double[] qTable2){
		double startvalue=qTable2[0];
		for (int i=1;i<qTable2.length;i++) {
			if(qTable2[i] !=startvalue){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * gives next action based on noise probability
	 * @param state    current state
	 * @return action id  {@link Constants}
	 */
	int getNextAction(int state){
		counter = (counter +1)%10;
		if(isAllQvalueEqual(qTable[state])){	
			return new Random().nextInt(qTable[state].length);
		}else{
			if(randomize.contains(counter)){
				if(counter ==0){
					getRandomActionsId(noiseFactor);
				}
				return new Random().nextInt(qTable[state].length);
			}else{
				
			
			int index =0;
			double MaxVal = Double.MIN_VALUE;
			double [] testArray = qTable[state];
			for (int i=0 ;i< testArray.length ; i++) {
				if(testArray[i] > MaxVal){
					MaxVal =testArray[i];
					index = i;
				}
			}
			return index;
			}
		}
		
	}
	
	/**
	 * returns Q value for specific state and action
	 * @param state
	 * @param action
	 * @return value from Q table
	 */
	double getQval(int state,int action){
		return qTable[state][action];
	}
	
	/**
	 * Sets value in Qtable
	 * @param state
	 * @param action
	 * @param value
	 */
	void setQtableEntry(int state,int action,double value){
		qTable[state][action]= value;
	}
	
	/**
	 * returns maximum possible Qvalue for specific state
	 * @param state
	 * @return
	 */
	double getQmax(int state){
		double MaxVal = Double.MIN_VALUE;
		double [] testArray = qTable[state];
		for (double d : testArray) {
			if(d > MaxVal){
				MaxVal =d;
			}
		}
		return MaxVal;
	}

}
