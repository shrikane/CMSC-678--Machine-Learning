package edu.umbc.cmsc678.Project.cluster;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.umbc.cmsc678.utils.CentroidPicker;
import edu.umbc.cmsc678.utils.Data;




/***
 * Basic implementation of k-means clustering 
 * @author Shrinivas
 *
 */
public class SimpleKMeans {
	public static final int COSINE_SIMILARITY = 1;
	public static final int DISTANCE_SIMILARITY = 2;
	
	static int itrCOunter = 0;
	List<double[]> centroids;
	
	List<double[]> getCenrtoids(){
		if(centroids == null){
			throw new NullPointerException("Not yet trained");
		}else{
			return centroids;
		}
	}
	

	
	double dotProduct(double[] vector1, double[] vector2){
		double dotProductValue=0.0;
		for(int i=0;i < vector1.length;i++){
			dotProductValue += vector1[i] * vector2[i];
		}
		return dotProductValue;
	}
	
	int getClusterIdByCosineSimilarity(List<double[]> centroids, double[] vector){
		int clusterIndex = 0;
		int centroidIndex = 0;
		double minDistance = Double.MIN_VALUE;
		double score =0.0;
		for (double[] centroid : centroids) {
			score= (dotProduct(centroid, vector)) / (Math.sqrt(dotProduct(centroid, centroid)) * (Math.sqrt(dotProduct(vector, vector))));  
			if (score > minDistance) {
				clusterIndex = centroidIndex;
				minDistance = score;
			}
			centroidIndex++;
		}
		return clusterIndex;
		
	}
	
	
	
	
	
	/***
	 * get cluster id based on centroids. i have used euclidean distance to categorize the cluster 
	 * @param centroids centroid vector for each cluster
	 * @param vector feature vector from data set
	 * @return
	 */
	int getClusterId(List<double[]> centroids, double[] vector) {
		int centroidIndex = 0;
		double distSum = 0;
		double minDistance = Double.MAX_VALUE;
		int clusterIndex = 0;

		for (double[] centroid : centroids) {
			distSum = 0;
			for (int i = 0; i < vector.length; i++) {
				double d1 = Math.abs((vector[i] - centroid[i]));
				distSum = distSum + (d1 * d1);
			}

			double distance = Math.sqrt(distSum);
			// System.out.println(distance);
			if (distance < minDistance) {
				clusterIndex = centroidIndex;
				minDistance = distance;
			}
			centroidIndex++;
		}
		return clusterIndex;
	}

	/***
	 * get all vectors and update centroid vector by averaging out all data points in feature vector(s)
	 * @param clusters cluster id and associated feature vectors
	 * @return new set of centroids 
	 */
	List<double[]> updateCentoid(Map<Integer, List<Data>> clusters) {
		List<double[]> centoids = new ArrayList<double[]>();

		for (Entry<Integer, List<Data>> e : clusters.entrySet()) {
			int vectorSize = e.getValue().get(0).getFeatureVector().length;
			// System.err.println(vectorSize);
			double[] centroidArr = new double[vectorSize];
			for (int i = 0; i < vectorSize; i++) {
				for (Data d : e.getValue()) {
					centroidArr[i] += d.getFeatureVector()[i];
				}
			}

			for (int i = 0; i < vectorSize; i++) {
				centroidArr[i] = (int) (centroidArr[i] / e.getValue().size());
			}
			centoids.add(centroidArr);
		}

		/*
		 * System.out.println("Centroids Are-"); for (double[] is : centoids) {
		 * for (double i : is) { System.out.print(i+"\t"); }
		 * System.out.println("\n"); }
		 */
		return centoids;

	}

	/***
	 * 1. Generates centroids
	 * 2. Classify each data point to cluster
	 * 3. Check for convergence
	 * @param inputData  input data as feature vectors
	 * @param cnetroidType  1 if to select random data point 2 if to select specific point in cluster
	 * @param clusterNum  number of k in k-means cluster
	 * @return clusters as map of <cluster id,data vector(s)>
	 */
	public Map<Integer, List<Data>> getCluster(List<Data> inputData,
			int cnetroidType, int clusterNum,int distanceMeausre) {

		Map<Integer, List<Data>> clusters = null;
		List<double[]> centroid = centroids == null ? new CentroidPicker(clusterNum)
				.getCentroids(inputData, cnetroidType) : centroids;
		int loopIndex = 0;
		boolean isToStop = false;
		while (!isToStop) {

			clusters = new HashMap<Integer, List<Data>>();
			for (Data d : inputData) {
				int cid=0;
				if(distanceMeausre == COSINE_SIMILARITY){
				cid = getClusterIdByCosineSimilarity(centroid, d.getFeatureVector());
				}
				if(distanceMeausre == DISTANCE_SIMILARITY){
				 cid = getClusterId(centroid, d.getFeatureVector());
				}
				
				List<Data> clusteredData = clusters.get(cid);
				if (clusteredData == null) {
					clusteredData = new ArrayList<Data>();
				}
				clusteredData.add(d);
				clusters.put(cid, clusteredData);
			}

			List<double[]> newCentroids = updateCentoid(clusters);
			loopIndex++;
			if (isConverged(newCentroids, centroid)) {
				isToStop = true;
			}
			centroid = newCentroids;
		}
		// System.err.println(loopIndex+",0,0");
		itrCOunter += loopIndex;
		// System.out.println(itrCOunter+",0,0");
		return clusters;
	}

	/***
	 * As centroid vectors datatype is double thus if centroids don't change by 1 percent we say k- means is converged 
	 * @param newCentroids old centroid vectors 
	 * @param oldCentroids new centroid vectors
	 * @return true if converged false else
	 */
	boolean isConverged(List<double[]> newCentroids, List<double[]> oldCentroids) {
		int j = 0;
		for (double[] oldVector : oldCentroids) {
			double[] newVector = newCentroids.get(j);
			//System.out.println(j);
			for (int i = 0; i < oldVector.length; i++) {
				//
				if (newVector[i] > oldVector[i] + 2
						|| newVector[i] < oldVector[i] - 2) {
					return false;
				}
			}
			j++;
		}
		centroids = newCentroids;
		return true;
	}

	/***
	 * 
	 * @param args
	 * @throws IOException
	 * @throws InvalidAlgorithmParameterException
	 */
	public double BuildClusters(String vectorInputFile,int NumCluster,String ClusterdOutpouFIlePath,int similarityMeasure,boolean isTest) throws IOException,
			InvalidAlgorithmParameterException {
		Data d = new Data();
		int errRate = 0;
		List<Data> testData = d.getDataLables(vectorInputFile);
		// get file sizes
		HashMap< Integer, Integer> h = new HashMap<>();
		
		for (Data data : testData) {
			Integer x = h.get(data.getActualLable());
			if(x == null){
				h.put(data.getActualLable(), 1);
			}else{
				h.put(data.getActualLable(), x+1);
			}
		}
		
		System.out.println("Distri:"+h);

		// for(int i=0;i< 10; i++){

		// System.out.println("Itration,"+i+",");

		Map<Integer, List<Data>> x = new SimpleKMeans().getCluster(testData,
				CentroidPicker.SPECIFIC_CENTROIDS, NumCluster,similarityMeasure);
		FileWriter fr = new FileWriter(new File(ClusterdOutpouFIlePath));
		List<Map<Integer, Integer>> outputStats = new ArrayList<Map<Integer, Integer>>();
		Map<Integer, Integer> clusterStats = null;
		for (Entry<Integer, List<Data>> e : x.entrySet()) {
			clusterStats = new HashMap<Integer, Integer>();

			// System.err.println("\n"+e.getKey());
			for (Data data : e.getValue()) {
				if (clusterStats.containsKey(data.getActualLable())) {
					int freq = clusterStats.get(data.getActualLable());
					clusterStats.put(data.getActualLable(), freq + 1);

				} else {
					clusterStats.put(data.getActualLable(), 1);
				}

				for (double p : data.getFeatureVector()) {
					fr.append(p + ",");
					// System.out.print(p+",");
				}
				// System.out.print(data.getActualLable()+","+e.getKey()+"\n");
				fr.append(data.getActualLable() + "," + e.getKey() + "\n");
			}
			outputStats.add(clusterStats);
			fr.flush();
		}

		fr.close();

		int totalCorrctInstance = 0;
		int totalInstance = 0;

		for (Map<Integer, Integer> map : outputStats) {
		//	System.out.println(map.toString());
			int maxIns = 0;
			int totCluster = 0;
			int cid = 0;
			for (Entry<Integer, Integer> e : map.entrySet()) {
				if (maxIns < e.getValue()) {
					maxIns = e.getValue();
					cid = e.getKey();
				}
				totCluster += e.getValue();
				totalInstance += e.getValue();
			}
			// System.err.println(cid+","+(totCluster-maxIns)+","+totCluster+","+(double)(((totCluster-maxIns)*100)/totCluster));
			totalCorrctInstance += maxIns;
		}

	//	System.out.println("Total," + totalCorrctInstance + ","
	//			+ (totalInstance - totalCorrctInstance) + "," + totalInstance);
	//	errRate += (totalInstance - totalCorrctInstance);
		if(isTest)
		System.out.println("Correct Classification"+(double)((totalCorrctInstance*100)/totalInstance));
		// System.out.println("avg itr:"+(itrCOunter/10));
		return (double)((totalCorrctInstance*100)/totalInstance);
	}
	//System.out.println("tota Err Rate:"+ (errRate/10));
	
	// }
}
