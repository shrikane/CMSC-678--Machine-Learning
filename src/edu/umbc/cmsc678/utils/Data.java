package edu.umbc.cmsc678.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
/**
 * 
 * @author Shrinivas
 * Class to hold 
 * 1. input data 
 * 2. assign actual labels(if available)
 */
public class Data {

	private double[] featureVector;
	private int actualLable;
	private int assignedLable;
	private String fileName;;

	public Data(double[] vector, int label) {
		featureVector = vector;
		actualLable = label;
	}
	
	
	public Data(double[] vector, int label,String FileName) {
		featureVector = vector;
		actualLable = label;
		fileName = FileName;
	}
	
	public Data(){
		
	}

	/***
	 * Get feature vector from input data
	 * @param FileName input file name for data
	 * @return feature vectors as a list
	 * @throws IOException
	 */
	public List<double[]> getVector(String FileName) throws IOException {
		FileReader fr = null;
		File inputFile = null;
		BufferedReader br = null;

		inputFile = new File(FileName);

		boolean isEof = false;
		List<double[]> inputData = new ArrayList<double[]>();
		try {
			fr = new FileReader(inputFile);
			br = new BufferedReader(fr);
			while (!isEof) {
				String line = br.readLine();
				if (line == null) {
					isEof = true;
				} else {
					String[] rowEntries = line.split(",");
					double[] lineEntries = new double[rowEntries.length];
					int loopIndex = 0;
					for (String entry : rowEntries) {
						lineEntries[loopIndex++] = Double.parseDouble(entry);
					}
					inputData.add(lineEntries);
				}
			}
		} catch (IOException e) {
			System.err.println("Falied to read Line");
			e.printStackTrace();
		} finally {

			br.close();
			fr.close();

		}
		return inputData;
	}

	/***
	 * get label from input file 
	 * @param labelFileName
	 * @return actual cluster labels 
	 * @throws IOException
	 */
	public List<Integer> getlabels(String labelFileName) throws IOException {
	
		
		
		
		List<Integer> lablesData = new ArrayList<Integer>();
		BufferedReader br = new BufferedReader(new FileReader(new File(
				labelFileName)));
		boolean isEof = false;
		while (!isEof) {
			String line = br.readLine();
			if (line == null) {
				isEof = true;
			} else {
				//System.err.println(line);
				lablesData.add(Integer.parseInt(line));
				//System.out.println(line);
			}
		}
		return lablesData;
	}

	
	private Map<String, Integer> initData() throws IOException {
		 Map<String,Integer> LabelMapping = new HashMap<String,Integer>();
		 BufferedReader br = new BufferedReader(new FileReader(new File(
					"out.txt")));
		 boolean isEof = false;
			while (!isEof) {
				String line = br.readLine();
				if (line == null) {
					isEof = true;
				} else {
					//System.err.println(line);
					String[] lables = line.split(",");
					LabelMapping.put(lables[0],Integer.parseInt(lables[1]));
					//System.out.println(line);
				}
			}
			
			 return LabelMapping;
	}


	/***
	 * 
	 * @param FeatureVectorFilePath read raw data from input text file
	 * @param labelFilePath read actual cluster label from input text file path
	 * @return Data object with feature vector along with actual label (if available) 
	 * @throws IOException
	 * @throws InvalidAlgorithmParameterException
	 */
	public List<Data> getData(String FeatureVectorFilePath, String labelFilePath)
			throws IOException, InvalidAlgorithmParameterException {
		List<double[]> inputData = getVector(FeatureVectorFilePath);
		List<Integer> lablesData = getlabels(labelFilePath);
		List<Data> data = new ArrayList<>();
		if (inputData.size() != lablesData.size()) {
			throw new InvalidAlgorithmParameterException("Lable and vectors mismatch");
		} else {
			for (int i=0; i<inputData.size(); i++ ) {
				Data d = new Data(inputData.get(i), lablesData.get(i));
				data.add(d);
			}
		}
		return data;

	}

	// getters and setters 
	public double[] getFeatureVector() {
		return featureVector;
	}

	public void setFeatureVector(double[] featureVector) {
		this.featureVector = featureVector;
	}

	public int getActualLable() {
		return actualLable;
	}

	public void setActualLable(int actualLable) {
		this.actualLable = actualLable;
	}

	public int getAssignedLable() {
		return assignedLable;
	}

	public void setAssignedLable(int assignedLable) {
		this.assignedLable = assignedLable;
	}

	
	public List<Data> getDataLables(String filePath) throws IOException{
		FileReader fr = null;
		File inputFile = null;
		BufferedReader br = null;

		inputFile = new File(filePath);
		 Map<String,Integer> LabelMapping = initData();
		boolean isEof = false;
		List<Data> inputData = new ArrayList<Data>();
		try {
			fr = new FileReader(inputFile);
			br = new BufferedReader(fr);
			while (!isEof) {
				String line = br.readLine();
				if (line == null) {
					isEof = true;
				} else {
					String[] rowEntries = line.split(",");
					double[] lineEntries = new double[rowEntries.length];
					
					for (int i=0,j=1;j<rowEntries.length;i++,j++) {
						lineEntries[i] = Double.parseDouble(rowEntries[j]);
					}
					//System.out.println(rowEntries[0]);
					inputData.add(new Data(lineEntries,LabelMapping.get(rowEntries[0])));
				}
			}
		} catch (IOException e) {
			System.err.println("Falied to read Line");
			e.printStackTrace();
		} finally {

			br.close();
			fr.close();

		}
		return inputData;
	}
	
	public static void main(String[] args) {
		
	}
}
