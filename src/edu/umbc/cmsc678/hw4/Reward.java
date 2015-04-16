package edu.umbc.cmsc678.hw4;

public class Reward {

	int rewardTable[][];
	int gridSize;

	int getNextState(int state, int action) {
		int reward = rewardTable[state][action];
		if (reward == -1) {
			return state;
		} else {

			switch (action) {
			case Constants.UP:
				return state - 15;
			case Constants.DOWN:
				return state + 15;
			case Constants.LEFT:
				return state - 1;
			case Constants.RIGHT:
				return state + 1;
			default:
				return state;

			}
		}
	}

	public Reward(int states, int actions) {
		rewardTable = new int[states][actions];
		gridSize = (int) Math.sqrt((double) states);
	}

	void setPenalty(int startIndex, int endIndex, int incremnet, int action) {
		for (int i = startIndex; i < endIndex; i = i + incremnet)
			rewardTable[i][action] = -1;
	}

	void initRewards() {

		setPenalty(0, 15, 1, Constants.UP);
		setPenalty(210, 225, 1, Constants.DOWN);
		setPenalty(0, 211, 15, Constants.LEFT);
		setPenalty(14, 225, 15, Constants.RIGHT);
		for (int i = 0; i < 4; i++)
			rewardTable[224][i] = 100;

		/*
		 * int k=0; for(int i=0;i<225;i++){ System.out.print("State "+k+++"\t");
		 * for(int j=0;j<4;j++){ System.out.print(rewardTable[i][j]+"\t"); }
		 * System.out.println(); }
		 */
	}

	int getReward(int state, int action) {
		return rewardTable[state][action];
	}

	public static void main(String[] args) {
		Reward r = new Reward(225, 4);
		r.initRewards();
	}

}
