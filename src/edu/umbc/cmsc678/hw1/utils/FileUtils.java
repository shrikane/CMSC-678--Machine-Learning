package edu.umbc.cmsc678.hw1.utils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils implements Closeable {

	FileReader fr;
	File inputFile;
	BufferedReader br;

	public FileUtils(String FileName) throws FileNotFoundException {
		inputFile = new File(FileName);
		fr = new FileReader(inputFile);
		br = new BufferedReader(fr);
	}

	public List<int[]> getData() {
		boolean isEof = false;
		List<int []> inputData = new ArrayList<int []>();
		while (!isEof) {

			try {
				String line = br.readLine();
				if (line == null) {
					isEof = true;
				} else {
					String [] rowEntries = line.split(" ");
					int [] lineEntries = new int[rowEntries.length];
					int loopIndex =0;
					for (String entry : rowEntries) {
						lineEntries[loopIndex++] = Integer.parseInt(entry);
					}
					inputData.add(lineEntries);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("Falied to read Line");
				e.printStackTrace();
			}

		}

		return inputData;
	}

	@Override
	public void close() throws IOException {
		br.close();
		fr.close();

	}
	
	
	/*
	
	public static void main(String[] args) throws FileNotFoundException {
		FileUtils fu = new FileUtils("C:\\Users\\Shrinivas\\OneDrive\\ML\\HW1\\mnist_data.txt");
		List<int[]> d = fu.getData();
		for (int[] list : d) {
			for (Integer integer : list) {
				System.out.print(integer+"\t");
			}
		System.out.println();
		}
		
		
	}
	*/

}
