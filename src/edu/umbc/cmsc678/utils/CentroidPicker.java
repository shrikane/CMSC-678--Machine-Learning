package edu.umbc.cmsc678.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/***
 * Picks centroid vectors
 * @author Shrinivas
 *
 */
public class CentroidPicker {

	int centroidNum;
	int vectorSize;
	// Switch to Randomly sample k = 10 instances
	public static final int RANDOM_CENTROIDS =1;
	// Switch to Randomly choose an instance that represents each of the digits 
	public static final int SPECIFIC_CENTROIDS=2;
	
	
	public int getCentroidNum() {
		return centroidNum;
	}

	public void setCentroidNum(int centroidNum) {
		this.centroidNum = centroidNum;
	}

	/**
	 * Sets number of centroids 
	 */
	public CentroidPicker(int centroids) {
		centroidNum = centroids;
	}

	/***
	 * Generates random centroids from data
	 * @param data Data as feature vectors
	 * @param type Switch 1 for Randomly sample or 2 for instance that represents each of the digits
	 * @return list of centroids
	 */
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
					//System.out.println("j "+j+"Vales "+data.get(index).getActualLable()+" Index Vale"+ index);
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
