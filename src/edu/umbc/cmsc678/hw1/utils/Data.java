package edu.umbc.cmsc678.hw1.utils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.List;

public class Data {

	private double[] featureVector;
	private int actualLable;
	private int assignedLable;

	public Data(double[] vector, int label) {
		featureVector = vector;
		actualLable = label;
	}
	
	public Data(){
		
	}

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
					String[] rowEntries = line.split(" ");
					double[] lineEntries = new double[rowEntries.length];
					int loopIndex = 0;
					for (String entry : rowEntries) {
						lineEntries[loopIndex++] = Double.parseDouble(entry);
					}
					inputData.add(lineEntries);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Falied to read Line");
			e.printStackTrace();
		} finally {

			br.close();
			fr.close();

		}
		return inputData;
	}

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
				lablesData.add(Integer.parseInt(line));
				//System.out.println(line);
			}
		}
		return lablesData;
	}

	public List<Data> getData(String FeatureVectorFilePath, String labelFilePath)
			throws IOException, InvalidAlgorithmParameterException {
		List<double[]> inputData = getVector(FeatureVectorFilePath);
		List<Integer> lablesData = getlabels(labelFilePath);
		List<Data> data = new ArrayList<>();
		if (inputData.size() != lablesData.size()) {
			System.err.println("Lable and vectors mismatch");
			throw new InvalidAlgorithmParameterException("Lable and vectors mismatch");
		} else {
			for (int i=0; i<inputData.size(); i++ ) {
				Data d = new Data(inputData.get(i), lablesData.get(i));
				
				data.add(d);
			}
		}
		return data;

	}

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


	 public static void main(String[] args) throws IOException {
	
	 
	  }
	 

}
