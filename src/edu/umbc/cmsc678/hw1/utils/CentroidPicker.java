package edu.umbc.cmsc678.hw1.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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
	public CentroidPicker(int centroids) {
		centroidNum = centroids;
	}

	public List<double[]> getCentroids(List<Data> data, int type) {

		Random r = new Random();
		int j=0;
		List<double[]> centroids = new ArrayList<double[]>();
		for (int i = 0; i < centroidNum; i++) {
			if (type == 1) {
				centroids.add((data.get(r.nextInt(data.size()))
						.getFeatureVector()));
			} else {
				
				while(true){
					//System.out.println(data.size());
				int index = r.nextInt(data.size());
				Data d = data.get(index);
				if(j == d.getActualLable()){
					centroids.add(data.get(index).getFeatureVector());
					j++;
					break;
				}
				}
			}
		}
		
		/*System.out.println("Centroids Are-");
		for (double[] is : centroids) {
			for (double i : is) {
				System.out.print(i+"\t");
			}
			System.out.println("\n");
		}*/
		
		//new Scanner(System.in).next();
		
		
		
		
		return centroids;
	}


	
	
	
	
}
