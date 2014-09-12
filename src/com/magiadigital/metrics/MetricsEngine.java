package com.magiadigital.metrics ;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.magiadigital.structs.CohText;
import com.magiadigital.utils.IFreelingAnalyzer;
import com.magiadigital.utils.ImplFreelingAnalyzer;
import com.magiadigital.utils.Utils;

import edu.upc.freeling.Word ;

public class MetricsEngine {
	private static MetricsEngine instance = new MetricsEngine() ;
	
	private HashMap<String,Boolean> mapWords ;
	private List<Word> allWords ;
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
		List<HashMap<Word,Integer>> lstDialogs = new ArrayList<>() ;
		// Process all dialogs
		while( ddSc.hasNextLine() ){
			Scanner sc = new Scanner( new File( ddSc.nextLine() ) ) ;
			Utils.debug( "/* ======== INI DIALOGO ======= */" ) ;
			while( sc.hasNextLine() ){
				String line = sc.nextLine() ;
				HashMap<Word,Integer> ctWords = analyze( line ) ;
				Set<Word> keys = ctWords.keySet() ;
				for( Word w : keys ){
					String lem = w.getLemma() ;
					if( !mapWords.containsKey( lem ) ){
						mapWords.put( lem , true ) ;
						allWords.add( w ) ;
					}
				}
				lstDialogs.add( ctWords ) ;
				Utils.debug( line ) ;
			}
			Utils.debug( "/* ======== FIN DIALOGO ======= */" ) ;
		}
		// Build bag of words
		List<List<Integer>> bagOfWords = new ArrayList<>() ;
		bow.setOfWords( allWords ) ;
		for( HashMap<Word,Integer> dialog : lstDialogs ) bagOfWords.add( bow.build( dialog ) ) ;
	}
	
	public HashMap<Word,Integer> analyze( String text ){
		HashMap<Word,Integer> ans = new HashMap<>() ;
		CohText ctxt = new CohText( text ) ;
		ctxt.analyze( freeling ) ;
		bow.analyze( ans ,  ctxt ) ;
		return ans ;
	}
	
	public void process( String in , String out ){
//		File folder = new File( in ) ;
//		String ansDir = out ;
//		File [] files = folder.listFiles() ;
//		int ans = 0 ;
//		for( File f : files ){
//			if( f.isFile() ){
//				if( f.getName().startsWith( "Texto" ) && f.getName().endsWith( "txt" ) ){
//					System.out.println( f.getName() ) ;
//					ans++ ;
//					File ansFile = new File( ansDir + "Metrics_" + f.getName() ) ;
//					try{
//						if( ansFile.exists() ){}
//						else ansFile.createNewFile() ;
//						String text = new String( Files.readAllBytes( Paths.get( f.getPath() ) ) ) ;
//						Map<String,Double> acum = analyze( text ) ;
//						FileWriter fw = new FileWriter( ansFile ) ;
//						BufferedWriter bfw = new BufferedWriter( fw ) ;
//						for( Entry<String,Double> entry : acum.entrySet() ){
//							bfw.write( entry.getKey() +  " " + entry.getValue()  + "\n" ) ;
//						}
//						bfw.close() ;
//						fw.close() ;
//					}catch( Exception e ){
//						e.printStackTrace() ;
//					}
//				}
//			}
//		}
//		System.out.println( ans ) ;
	}
}