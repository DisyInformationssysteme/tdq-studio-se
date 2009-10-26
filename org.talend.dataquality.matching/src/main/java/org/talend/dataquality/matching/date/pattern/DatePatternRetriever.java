// ============================================================================
//
// Copyright (C) 2006-2009 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataquality.matching.date.pattern;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
/**
 * 
 * @author Hallam Mohamed Amine
 * @date 11/08/2009
 */
public class DatePatternRetriever {
    // TODO put these mappings in a file so that it can be easily enriched    
    private List<ModelMatcher> modelMatchers = new ArrayList<ModelMatcher>();
    // constructor
    public DatePatternRetriever() {
        // TODO initialization should be done in a method
    	File file = new File("PatternsNameAndRegularExpressions.txt");
        this.initModel2Regex(file);
    }
    
    //initialization method of modelMatchers from external file
    public void initModel2Regex(File PatternFile) {
	    	// loading of file with all patterns names and their regular expressions
	    try{	
	    	FileReader fr = new FileReader(PatternFile);
	    	BufferedReader br  = new BufferedReader(fr);
	    	 String line;
	         while ((line = br.readLine()) != null) {  
	        	StringTokenizer string = new StringTokenizer(line,","); 
	        	while(string.hasMoreTokens()) {
	        		String key = string.nextToken(); 
	        		String val = string.nextToken(); 
	        		System.out.print(key+"\t");
	        		modelMatchers.add(new ModelMatcher(key, val));
	        	}           
	         }
	         // TODO should be closed even when an exception appears (use finally close)
	        br.close();
	    }
	    catch(FileNotFoundException e){
	    	System.out.print("File not found");
	    }
	    catch(IOException e){
	    	System.out.print("Problem when reading");
	    }
    }

    public void handle(String expression) {
        // TODO check matching and update weights
    }

    // this method returns patterns
    public void toPattern(String expression) {
        // TODO this method should return an ordered list of objects which contain the model and the score

        for (ModelMatcher patternMatcher : this.modelMatchers) {
            if (patternMatcher.matches(expression)) {
                // affichage integr�
                System.out.println(patternMatcher.getModel());
                System.out.println("----------------------");

            } else {
                // not matched
            }
        }
        // showMyResults(myResults) ;
    }

    /*
     * methode qui affiche les r�sulatats public static void showMyResults(List<String> MyResults){ for (Iterator
     * iterator = MyResults.iterator(); iterator.hasNext();) { String string = (String) iterator.next(); //
     * System.out.println(MyResults.iterator().next()); } }
     */
}
