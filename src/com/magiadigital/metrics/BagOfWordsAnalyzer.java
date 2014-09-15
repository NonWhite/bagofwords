package com.magiadigital.metrics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.magiadigital.structs.CohParagraph;
import com.magiadigital.structs.CohText;
import com.magiadigital.structs.FreelingWordIterable;
import com.magiadigital.utils.Utils;

import edu.upc.freeling.Sentence;
import edu.upc.freeling.Word;

public class BagOfWordsAnalyzer implements ICohAnalyzer{
	private static BagOfWordsAnalyzer instance ;

	private List<String> allWords ;
	private List<List<Integer>> bowVectors ;
	
	public static BagOfWordsAnalyzer getInstance(){
		if( instance == null ) return instance = new BagOfWordsAnalyzer() ;
		return instance ;
	}
	
	private BagOfWordsAnalyzer() {}

	@Override
	public void analyze( HashMap<String,Integer> toFill, CohText text ){
		List<CohParagraph> paragraphs = text.getParagraphs() ;
		for( CohParagraph p : paragraphs ){
			for( Sentence s : p ){
				FreelingWordIterable sIt = new FreelingWordIterable( s ) ;
				for( Word w : sIt ){
					if( isStopWord( w ) ) continue ;
					Integer cont = 0 ;
					String cad = w.getLemma() ;
					if( toFill.containsKey( cad ) ) cont = toFill.get( cad ) ;
					toFill.put( cad , cont + 1 ) ;
				}
			}
		}
	}
	
	public boolean isStopWord( Word w ){
		if( hasTag( w , "N" ) ) return false ; // TODO
		return false ;
	}
	
	public void setOfWords( List<String> setOfWords ){
		bowVectors.clear() ;
		for(int i = 0 ; i < setOfWords.size() ; i++) Utils.debug( i + ": " + setOfWords.get( i ) ) ;
		this.allWords = setOfWords ;
	}
	
	public List<Integer> build( HashMap<String,Integer> dialog ){
		List<Integer> desc = new ArrayList<Integer>() ;
		for(int i = 0 ; i < allWords.size() ; i++){
			String w = allWords.get( i ) ;
			desc.add( 0 ) ;
			if( dialog.containsKey( w ) ) desc.set( i , dialog.get( w ) ) ; 
		}
		return desc ;
	}
	
	public void buildAndSave( HashMap<String,Integer> dialog ){
		List<Integer> desc = build( dialog ) ;
		bowVectors.add( desc ) ;
	}
	
	public double countWordsByTag( CohParagraph p , String tag ){
		double ans = 0.0 ;
		for( Sentence s : p ){
			FreelingWordIterable sIt = new FreelingWordIterable( s ) ;
			for( Word w : sIt ) if( hasTag( w , tag ) ) ans++;
		}
		return ans ;
	}
	
	private boolean hasTag( Word w , String regex ){
		return w.getTag().matches( regex + ".*" ) ;
	}
	
	public List<String> getSetOfWords(){
		return allWords ;
	}
	
	public List<List<Integer>> getBowVectors(){
		return bowVectors ;
	}
}
