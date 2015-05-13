package edu.umbc.cmsc678.Project.analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TokenNGramAnalyzer extends edu.umbc.cmsc678.Project.analysis.Analyzer  {

	int minSize;
	Analyzer analyzer ;
	

	public TokenNGramAnalyzer(int p_minSize) {
		 minSize =p_minSize;
	}


	public Analyzer getAnalyzer() {
		return analyzer;
	}

	
	
public List<String> getTokens(String input){
		
	String[] splitInput = input.split("[ |\\t]");
		ArrayList< String> tokens = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<=splitInput.length-minSize;i++){
			int k=i;
			sb = new StringBuffer();
			for(int j=0;j<minSize;j++){
				sb.append(splitInput[k]+" ");
				k++;
			}
			tokens.add(sb.toString());
			//System.out.println(sb.toString());
		}
		return tokens;
	}


@Override
public Analyzer getAnalyzer(int size) {
	return new TokenNGramAnalyzer(size);
}
	
	/*
	public static void main(String[] args) throws IOException {
		IndexGenerator ig = new IndexGenerator();
		Map<String,String> d = ig.getText("C:\\Users\\Shrinivas\\Desktop\\JavaTest\\SingleClass");
		List<String> tokens  = new TokenNGramAnalyzer(3).getTokens(d.get("ActiveObjectDemo.java"));
		for (String string : tokens) {
			System.out.println(string.split(" ").length+"String is: "+string);
			System.out.println("new Tok---------------------------------------");
		}
		
	}*/
}
