package edu.umbc.cmsc678.hw1;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import edu.umbc.cmsc678.hw1.utils.CentroidPicker;
import edu.umbc.cmsc678.hw1.utils.Data;

public class SimpleKMeans {


	int getClusterId(List<double[]> centroids,double [] vector){
		int centroidIndex =0;
		double distSum = 0;
		double minDistance = Double.MAX_VALUE;
		int clusterIndex =0;

		for (double [] centroid : centroids) {
			distSum =0;
			for(int i=0;i< vector.length ; i++ ){
				double d1 = Math.abs((vector[i]-centroid[i]));
				distSum = distSum + (d1 * d1) ;
			}

			double distance = Math.sqrt(distSum);
			//System.out.println(distance);
			if(distance <  minDistance){
				clusterIndex = centroidIndex;
				minDistance = distance;
			}
			centroidIndex++;
		}
		return clusterIndex;
	}

	List<double[]> updateCentoid(Map<Integer, List<Data>> clusters) {
		List<double[]> centoids = new ArrayList<double[]>();

		for (Entry<Integer, List<Data>> e : clusters.entrySet()) {
			int vectorSize = e.getValue().get(0).getFeatureVector().length;
			//System.err.println(vectorSize);
			double[] centroidArr = new double[vectorSize];
			for (int i = 0; i < vectorSize; i++) {
				for (Data d : e.getValue()) {
					centroidArr[i] += d.getFeatureVector()[i];
				}
			}

			for (int i = 0; i < vectorSize; i++) {
				centroidArr[i] =(int)(centroidArr[i] / e.getValue().size()) ;
			}
			centoids.add(centroidArr);
		}
		
		/*
		System.out.println("Centroids Are-");
		for (double[] is : centoids) {
			for (double  i : is) {
				System.out.print(i+"\t");
			}
			System.out.println("\n");
		}*/
		return centoids;

	}

	public Map<Integer, List<Data>> getCluster(List<Data> inputData) {
		
		Map<Integer, List<Data>> clusters = null;
		List<double[]> centroids = new CentroidPicker(10)
		.getCentroids(inputData, 1);
		int loopIndex =0;
		boolean isToStop = false;
		while (!isToStop) {
			
			clusters = new HashMap<Integer, List<Data>>();
			for (Data d : inputData) {
				int cid = getClusterId(centroids, d.getFeatureVector());
				List<Data> clusteredData = clusters.get(cid);
				if (clusteredData == null) {
					clusteredData = new ArrayList<Data>();
				}
				clusteredData.add(d);
				clusters.put(cid, clusteredData);
			}

			List<double[]> newCentroids = updateCentoid(clusters);
			loopIndex++;
			if(isConverged(newCentroids, centroids)){
				isToStop = true;
			}
			centroids = newCentroids;
			System.err.println(loopIndex);

			
	


			/*
				 System.err.println("Clusters are:");
				 for (Entry<Integer, List<Data>> e : clusters.entrySet()) {
					System.err.println(e.getKey());
					for (Data data : e.getValue()) {
						for (int p : data.getFeatureVector()) {
							System.out.print(p+"\t");
						}
						System.out.println(","+data.getActualLable()+","+e.getKey()+"\n");

					}
				}




				// */
			//System.out.println(loopIndex);
		}
		return clusters;
	}



	boolean isConverged(List<double[]> newCentroids  , List<double[]> oldCentroids){
		int j=0;
		
		/*
		for (int i = 0; i < newCentroids.size(); i++) {
			double [] old = newCentroids.get(i);
			double [] newcen = oldCentroids.get(i);
			for (int k = 0; k < old.length; k++) {
				System.out.print(old[k]+"\t");
			}
			System.out.println("\n");
			
			
			for (int k = 0; k < old.length; k++) {
				System.out.print(newcen[k]+"\t");
			}
			System.out.println("\n-----------------------------------------------------------\n");
			
		}*/
		
		
		for (double[] oldVector : oldCentroids) {
			double [] newVector = newCentroids.get(j);
			for (int i = 0; i < oldVector.length; i++) {
				//
				if( newVector[i] > oldVector[i] + 0.5 || newVector[i] < oldVector[i] - 0.5 ){
					return false;
				}
			}
			j++;
		}
		return true;
	}	

	public static void main(String[] args) throws IOException, InvalidAlgorithmParameterException {
		Data d = new Data();
		List<Data> testData =  d.getData(args[0], args[1]);
		Map<Integer, List<Data>> x = new SimpleKMeans().getCluster(testData);
		//CentroidPicker c = new CentroidPicker(2);
		//List<int[]> testData1 = c.getCentroids(testData,1 );
		FileWriter fr = new FileWriter(new File("C:\\Users\\Shrinivas\\Desktop\\out.csv"));
		for (Entry<Integer, List<Data>> e : x.entrySet()) {
			
			//System.err.println("\n"+e.getKey());
			for (Data data : e.getValue()) {
				for (double p : data.getFeatureVector()) {
					fr.append(p+",");
				//	System.out.print(p+",");
				}
				//System.out.print(data.getActualLable()+","+e.getKey()+"\n");
				fr.append(data.getActualLable()+","+e.getKey()+"\n");

			}
			fr.flush();
		}



	
		fr.close();
	}

}
