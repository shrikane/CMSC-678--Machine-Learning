package edu.umbc.cmsc678.hw1.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CentroidPicker {

	int centroidNum;
	int vectorSize;
	
	public int getCentroidNum() {
		return centroidNum;
	}
	
	public void setCentroidNum(int centroidNum) {
		this.centroidNum = centroidNum;
	}
	
	/**
	 * Sets number of centroids and vector size
	 */
	public CentroidPicker(int centroids,int vectorsize) {
		centroidNum = centroids;
		vectorSize = vectorsize;
	}
	
	public List<int []> getRandomCentroids(){
		
		Random r = new Random();
		List<int []>  centroids = new ArrayList<int[]>();
		for(int i =0;i< centroidNum; i++){
			int [] centroidVector = new int [vectorSize];
			for(int j=0;j<vectorSize;j++){
				centroidVector[j] =r.nextInt(255);
			}
			centroids.add(centroidVector);
		}
		return centroids;
	}
	
	
}
