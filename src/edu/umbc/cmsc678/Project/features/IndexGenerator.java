package edu.umbc.cmsc678.Project.features;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import edu.umbc.cmsc678.Project.analysis.Analyzer;
import edu.umbc.cmsc678.Project.analysis.CharNGramAnalyzer;





public class IndexGenerator {

	private Map<String,Integer> dict;
	private static  Integer Doc_prune;

	public IndexGenerator() {
		dict = new HashMap<String, Integer>();
	}


	public Map<String, Map<String, Double>> getTfIdf(Map<String,Map<String,Double>> freqMatrix)
	{
		Doc_prune = (freqMatrix.size() * 1) /100; 
		Map<String,Map<String,Double>> tfIdfMatrix = new HashMap<String, Map<String,Double>>();
		for (Entry<String, Map<String, Double>> e : freqMatrix.entrySet() ) {
			Map<String, Double> doc = e.getValue();
			Map<String, Double> tfIdfDoc = new HashMap<String, Double>();
			
			HashMap<String, Integer> newDict = new HashMap<String, Integer>();
			for (Entry<String, Integer> e2 : dict.entrySet()) {
				if( e2.getValue() > Doc_prune ){
					newDict.put(e2.getKey(), e2.getValue());
				}
			}
			dict = newDict;
			
			
			
			for (Entry<String, Integer> e1 : dict.entrySet()) {
				
					Double tfFreq = doc.get(e1.getKey());
					if(tfFreq == null){
						tfIdfDoc.put(e1.getKey(), 0.0);
					}
					else{
						//* Math.log(freqMatrix.size()/e1.getValue())
						tfIdfDoc.put(e1.getKey(), tfFreq);
					}
			}
			tfIdfMatrix.put(e.getKey(), tfIdfDoc);
		}

		return tfIdfMatrix;
	}

	/**
	 * Generates tf measures out of plain text
	 * @param input
	 * @param analyzer
	 * @return
	 */
	public Map<String,Double> getDocAnalysis(String input,Analyzer analyzer){
		Map<String,Double>data = new HashMap<String, Double>();
		List<String> tok = analyzer.getTokens(input);

		for (String string : tok) {
			Double count = data.get(string);
			Integer DocCount = dict.get(string);
			if(count == null){
				data.put(string, 1.0);
				if(DocCount == null){
					dict.put(string, 1);
				}else{
					dict.put(string,DocCount+1);
				}
			}else{
				data.put(string,count+1.0);
				//System.out.println("tok: "+string+" Val: "+count+1);
			}
		}

		// get tf 
		Map<String,Double> tfData = new HashMap<String, Double>();
		for (Entry<String, Double> e : data.entrySet()) {
			tfData.put(e.getKey(), (e.getValue()/tok.size()));
		}
		//System.out.println(data);
		return tfData;
	}



	public Map<String,String> getText(String dirPath) throws IOException{
		File [] files = new File(dirPath).listFiles();
		Map<String,String> textData= new TreeMap<String,String>();
		for (File file : files) {
			FileReader fr =new FileReader(file);
			char[] text = new char [(int) file.length()]; 
			fr.read(text);
			textData.put(file.getName(),new String(text)) ;
			fr.close();
		}
		return textData;
	}


	public  void buildIndex(String InputFilePath,String outputFilePath,Analyzer analyzer) throws IOException {
		IndexGenerator ig = new IndexGenerator();
		Map<String,String> data = ig.getText(InputFilePath);
		Map<String,Map<String,Double>> tfMatrix = new HashMap<String,Map<String,Double>>();
		for (Entry<String, String> e : data.entrySet()) {
			tfMatrix.put(e.getKey(), ig.getDocAnalysis(e.getValue(), analyzer));
		}

		FileWriter fw = new FileWriter(outputFilePath);
		tfMatrix = ig.getTfIdf(tfMatrix);
		StringBuffer bf = new StringBuffer();
		//append all keys
		int j=0;
		for (Entry<String, Integer> e : ig.dict.entrySet()) {
			//if(args[2] == "1"){
				//bf.append("@ATTRIBUTE "+e.getKey().replaceAll("\"", " Double_QUOTE ").replaceAll("\r", " NewLines ").replaceAll("\n"," NewLines " ).replaceAll(" ", "_").replaceAll("\t", "_")+" NUMERIC"+"\n");
		//	bf.append("@ATTRIBUTE ATT"+j+" NUMERIC"+"\n");
			//	}else{
		//		bf.append("\""+e.getKey().replaceAll("\"", " Double_QUOTE ").replaceAll("\r", " NewLines ").replaceAll("\n"," NewLine " ).replaceAll(" ", " WHITESPACE ").replaceAll("\t", " TAB ")+"\""+",");
		//	}
			
		//	System.out.println("FileName,"+e.getKey().replaceAll("\r\n", " NewLines ")+",");
			j++;
		}
	//	System.err.println(j);
	//	bf.append("\n");
		//System.out.println(bf.toString());
	//	fw.append("FileName,"+bf.toString()+"\n");
		bf.append("\n");
		fw.flush();


		for (Entry<String, Map<String, Double>> e : tfMatrix.entrySet()) {
			//System.out.println("File Name"+e.getKey()+","+ig.dict.size()+","+e.getValue().size());
			Map<String, Double> docData = e.getValue();
			bf = new StringBuffer();
			int i=0;
			bf.append(e.getKey()+",");
			for (Entry<String, Integer> e2 : ig.dict.entrySet()) {
				//bf.append("\""+docData.get(e2.getKey())+"\" "+",");
				bf.append(docData.get(e2.getKey())+",");
				i++;
			}
		//	System.out.println(i);
			bf.append("\n");
			fw.append(bf.toString());
			fw.flush();
			//System.out.println(e.getValue());
		}
		fw.close();
		System.out.println(ig.dict.size());
	}
	
	public static void main(String[] args) throws IOException {
		IndexGenerator ig = new IndexGenerator();
		Analyzer analyzer = new CharNGramAnalyzer(2) ;
				//analyzer.getAnalyzer(2);
		ig.buildIndex(args[0], args[1], analyzer);
	}
}
