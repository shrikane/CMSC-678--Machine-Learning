package edu.umbc.cmsc678.Project.analysis;

import java.util.ArrayList;
import java.util.List;

public class CharNGramAnalyzer extends Analyzer{


	int tokenize;
	public CharNGramAnalyzer(int p_minSize) {
		 tokenize =p_minSize;
	}
	
	
	public Analyzer getAnalyzer(int size){
		return new CharNGramAnalyzer(size);
	}
	
	
	
	public  List<String> getTokens(String str) {
        List<String> ngrams = new ArrayList<String>();
        String[] words = str.split(" ");
        for (int i = 0; i < words.length - tokenize + 1; i++)
            ngrams.add(concat(words, i, i+tokenize));
        return ngrams;
    }

    public String concat(String[] words, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++)
            sb.append((i > start ? " " : "") + words[i]);
        return sb.toString();
    }
	
	
	
	
	
	
	
	private static void addNgrams(final int size, final String input, 
		    final List<String> list)
		{
		    final int maxStartIndex = input.length() - size;
		    for (int i = 0; i < maxStartIndex; i++)
		        list.add(input.substring(i, i + size));
		}

	/*
		public List<String> generateNGrams(final String input, final int minSize, 
		    final int maxSize)
		{
		    final List<String> ret = new ArrayList<String>();
		    for (int size = minSize; size <= maxSize; size++)
		        addNgrams(size, input, ret);
		    return ret;
		}
	
	
	
	
	

public List<String> getTokens(String input){
	List< String> tokens = generateNGrams(input,3,3);
	
	/*
		
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<=input.length()-tokenize;i++){
			int k=i;
			sb = new StringBuffer();
			for(int j=0;j<tokenize;j++){
				sb.append(input.charAt(k));
				k++;
			}
			//System.out.println(sb.toString());
			tokens.add(sb.toString());
		
		return tokens;
	}
	*/
	
	public static void main(String[] args) {
		List< String> tokens  = new CharNGramAnalyzer(3).getTokens("xyzzyspoon1 abc");
		for (String string : tokens) {
			System.out.println(string);
		}
	}


	


	

}
