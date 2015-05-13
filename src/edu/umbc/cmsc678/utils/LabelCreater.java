package edu.umbc.cmsc678.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LabelCreater {

	public LabelCreater() {
		// TODO Auto-generated constructor stub
	}
	
	public  void getLables(String[] args) throws IOException {
		int i=0;
		FileWriter fw = new FileWriter("out.txt");
		for (String s : args) {
			File f = new File(s);
			int k=0;
			for (String string : f.list()) {
				fw.append(string+","+i+"\n");
				fw.flush();
				k++;
			}
			i++;
		 System.out.println("ith term: "+ i+"Files: "+k);
		}
		fw.close();
	}

}
