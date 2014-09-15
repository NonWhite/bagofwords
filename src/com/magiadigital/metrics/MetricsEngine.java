package com.magiadigital.metrics ;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.magiadigital.structs.CohText;
import com.magiadigital.utils.IFreelingAnalyzer;
import com.magiadigital.utils.ImplFreelingAnalyzer;
import com.magiadigital.utils.Utils;

public class MetricsEngine {
	private static MetricsEngine instance = new MetricsEngine() ;
	
	private HashMap<String,Boolean> mapWords ;
	private List<String> allWords ;
	private IFreelingAnalyzer freeling = ImplFreelingAnalyzer.getInstance() ;
	private BagOfWordsAnalyzer bow = BagOfWordsAnalyzer.getInstance() ;
	
	private MetricsEngine(){
	}
	
	public static MetricsEngine getInstance(){
		return instance ;
	}
	
	public void processDialogs( String path ) throws FileNotFoundException{
		mapWords = new HashMap<>() ;
		allWords = new ArrayList<>() ;
		Scanner ddSc = new Scanner( new File( path ) ) ;
		List<HashMap<String,Integer>> lstDialogs = new ArrayList<>() ;
		// Process all dialogs
		while( ddSc.hasNextLine() ){
			Scanner sc = new Scanner( new File( ddSc.nextLine() ) ) ;
//			Utils.debug( "/* ======== INI DIALOGO ======= */" ) ;
			HashMap<String,Integer> ctDialog = new HashMap<>() ;
			while( sc.hasNextLine() ){
				String line = sc.nextLine() ;
				HashMap<String,Integer> ctWords = analyze( line ) ;
				Set<String> keys = ctWords.keySet() ;
				for( String w : keys ){
					if( !mapWords.containsKey( w ) ){
						mapWords.put( w , true ) ;
						allWords.add( w ) ;
					}
					Integer cont = 0 ;
					if( ctDialog.containsKey( w ) ) cont = ctDialog.get( w ) ;
					ctDialog.put( w , cont + ctWords.get( w ) ) ;
				}
			}
			lstDialogs.add( ctDialog ) ;
			sc.close() ;
//			Utils.debug( "/* ======== FIN DIALOGO ======= */" ) ;
		}
		ddSc.close() ;
		// Build bag of words
		bow.setOfWords( allWords ) ;
		for( HashMap<String,Integer> dialog : lstDialogs ) bow.buildAndSave( dialog ) ;
	}
	
	public HashMap<String,Integer> analyze( String text ){
		HashMap<String,Integer> ans = new HashMap<>() ;
		CohText ctxt = new CohText( text ) ;
		ctxt.analyze( freeling ) ;
		bow.analyze( ans ,  ctxt ) ;
		return ans ;
	}
	
	public void saveBoWData( String path ) throws FileNotFoundException{
		File file = new File( path ) ;
		PrintWriter pw = new PrintWriter( file ) ;
		List<String> lstWords = bow.getSetOfWords() ;
		List<List<Integer>> bowVectors = bow.getBowVectors() ;
		System.out.println( bowVectors.size() ) ;
		Utils.printCommaSeparated( pw ,  lstWords ) ;
		for( List<Integer> lst : bowVectors ) Utils.printCommaSeparated( pw ,  lst ) ;
	}
}