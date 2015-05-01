package edu.umbc.cmsc678.Project.analysis;

import java.util.List;

public abstract class Analyzer {

	public Analyzer() {
		
	}
	public abstract List<String> getTokens(String input);
}
