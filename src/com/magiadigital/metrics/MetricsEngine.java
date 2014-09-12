package com.magiadigital.metrics ;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import com.magiadigital.structs.CohText;
import com.magiadigital.utils.IFreelingAnalyzer;
import com.magiadigital.utils.ImplFreelingAnalyzer;
import com.magiadigital.utils.Utils;

public class MetricsEngine {
	private static MetricsEngine instance = new MetricsEngine() ;
	
	private IFreelingAnalyzer freeling = ImplFreelingAnalyzer.getInstance() ;
	private BagOfWordsAnalyzer bow = BagOfWordsAnalyzer.getInstance() ;
	
	private MetricsEngine(){
	}
	
	public static MetricsEngine getInstance(){
		return instance ;
	}
	
	public void processDialogs( String path ) throws FileNotFoundException{
		Scanner ddSc = new Scanner( new File( path ) ) ;
		while( ddSc.hasNextLine() ){
			Scanner sc = new Scanner( new File( ddSc.nextLine() ) ) ;
			String local = sc.nextLine() ;
			Utils.debug( local ) ;
			while( sc.hasNextLine() ){
				Utils.debug( sc.nextLine() ) ;
			}
			Utils.debug( "/* ======== FIN DIALOGO ======= */" ) ;
		}
	}
	
	public Map<String,Double> analyze( String text ){
		Map<String,Double> ans = new HashMap<>() ;
		CohText ctxt = new CohText( text ) ;
		ctxt.analyze( freeling ) ;
		bow.analyze( ans ,  ctxt ) ;
		return ans ;
	}
	
	public void process( String in , String out ){
		File folder = new File( in ) ;
		String ansDir = out ;
		File [] files = folder.listFiles() ;
		int ans = 0 ;
		for( File f : files ){
			if( f.isFile() ){
				if( f.getName().startsWith( "Texto" ) && f.getName().endsWith( "txt" ) ){
					System.out.println( f.getName() ) ;
					ans++ ;
					File ansFile = new File( ansDir + "Metrics_" + f.getName() ) ;
					try{
						if( ansFile.exists() ){}
						else ansFile.createNewFile() ;
						String text = new String( Files.readAllBytes( Paths.get( f.getPath() ) ) ) ;
						Map<String,Double> acum = analyze( text ) ;
						FileWriter fw = new FileWriter( ansFile ) ;
						BufferedWriter bfw = new BufferedWriter( fw ) ;
						for( Entry<String,Double> entry : acum.entrySet() ){
							bfw.write( entry.getKey() +  " " + entry.getValue()  + "\n" ) ;
						}
						bfw.close() ;
						fw.close() ;
					}catch( Exception e ){
						e.printStackTrace() ;
					}
				}
			}
		}
		System.out.println( ans ) ;
	}
}