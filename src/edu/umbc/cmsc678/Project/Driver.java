package edu.umbc.cmsc678.Project;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;

import edu.umbc.cmsc678.Project.analysis.Analyzer;
import edu.umbc.cmsc678.Project.analysis.CharNGramAnalyzer;
import edu.umbc.cmsc678.Project.analysis.TokenNGramAnalyzer;
import edu.umbc.cmsc678.Project.cluster.SimpleKMeans;
import edu.umbc.cmsc678.Project.features.IndexGenerator;
import edu.umbc.cmsc678.utils.LabelCreater;

public class Driver {

	public Driver() {
		
	}
	
	static void getReport(String impuFIle,String outFile,int runSize,int similarity,Analyzer analyzer,int clusterSize) throws InvalidAlgorithmParameterException, IOException{
		SimpleKMeans clustering = new SimpleKMeans();
		for(int j=1;j<5;j++){
			double acc =0;
			IndexGenerator ig = new IndexGenerator();
			analyzer = analyzer.getAnalyzer(j);
			ig.buildIndex(impuFIle, outFile, analyzer);
			System.out.println("Size: "+j);
		for(int i=1;i< runSize;i++){		
			//System.out.println("Index built");
			acc += clustering.BuildClusters(outFile, clusterSize, "out1.txt",similarity,false);
			//System.out.println("cluster built");
			//ig.buildIndex(args[2], args[1], new TokenNGramAnalyzer(i));
			//clustering.BuildClusters(args[1], 4, "out1.txt",SimpleKMeans.DISTANCE_SIMILARITY,true);
		//System.out.println(i);	
		}
		System.out.println("avg acc"+(double)acc/runSize);
		}
	}
	
	
	/**
	 * 
	 * @param args
	 * args[0] - folder path for input files
	 * args[1] - output file path for vector generation
	 * args[3] - inputLabelMapping file
	 * @throws IOException
	 * @throws InvalidAlgorithmParameterException 
	 */
	public static void main(String[] args) throws IOException, InvalidAlgorithmParameterException {
		
		String [] filePaths = { 
				"C:\\Users\\Shrinivas\\Desktop\\ML Project codes\\TICPP-2nd-ed-Vol-one\\Code" ,
				"C:\\Users\\Shrinivas\\Desktop\\ML Project codes\\Cpp Auth2\\Code",
				"C:\\Users\\Shrinivas\\Desktop\\ML Project codes\\Java - Thining in java",
				};
		//IndexGenerator ig = new IndexGenerator();
		LabelCreater lc = new LabelCreater();
		lc.getLables(filePaths);
		int clusterSize =3;
		int runSize =1;
		System.out.println("FIlePath creted");

		System.out.println("cosine token");
		getReport(args[0], args[1], runSize, SimpleKMeans.COSINE_SIMILARITY, new TokenNGramAnalyzer(1), clusterSize);
		System.out.println("distance token");
		getReport(args[0], args[1], runSize, SimpleKMeans.DISTANCE_SIMILARITY, new TokenNGramAnalyzer(1), clusterSize);
		
		System.out.println("Cosine-Char");
		getReport(args[0], args[1], runSize, SimpleKMeans.COSINE_SIMILARITY, new CharNGramAnalyzer(1), clusterSize);
		System.out.println("Distance char");
		getReport(args[0], args[1], runSize, SimpleKMeans.DISTANCE_SIMILARITY, new CharNGramAnalyzer(1), clusterSize);
		
		
	
		
	}

}
