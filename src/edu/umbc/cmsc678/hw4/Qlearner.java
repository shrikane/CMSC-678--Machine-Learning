package edu.umbc.cmsc678.hw4;
public class Qlearner {
	
	double learningRate = 0.5;
	double discountRate = 0.8;
	

	public Qlearner() {
		
	}
	
	int episodes=1000;
	
	
	
	
	void learn(){
		int counter=0;
		Qtable q= new Qtable(255, 4);
		Reward r = new Reward(255, 4);
		r.initRewards();
		do{
		int currentState=0;
		int finalState=224;
		int stepCounter=0;
		do{
			
			int action= q.getNextAction(currentState);
			int nextState = r.getNextState(currentState, action);
			//System.out.println("Moving from "+currentState+"\t" + "to " + nextState + "\t Action Taken "+action);
			
			double discountedReward = q.getQmax(nextState) * discountRate;
			double teampSum = discountedReward + r.getReward(currentState, action) - q.getQval(currentState, action);		
			double value = q.getQval(currentState, action) + (learningRate * teampSum);
					//r.getReward(currentState, action) + (int)(discountRate * q.getQmax());
					// q.getQval(currentState, action) + (int) ( learningRate *(r.getReward(currentState, action) + (discountRate * q.getQmax(r.getNextState(currentState, action)))- q.getQval(currentState, action)));
		//	System.out.println("val is "+value);
			q.updateQtable(currentState, action, value);
			currentState = nextState;
			//System.out.print("Moving to "+currentState+"\t" );
			//System.out.println();
		//	Scanner in = new Scanner(System.in);
		//	in.next();
			stepCounter++;
		}while(finalState != currentState);
		System.err.println(counter+","+stepCounter);
			counter++;
		}while(counter <= episodes);
		
		
		
		
	}
	
	public static void main(String[] args) {
		new Qlearner().learn();
	}

}
