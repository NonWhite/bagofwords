package com.magiadigital.utils;

import java.io.PrintWriter;
import java.util.List;

public class Utils {
	public static void debug( String deb ){
		System.out.println( deb ) ;
	}
	
	public static void printCommaSeparated( PrintWriter pw , List<?> lst ){
		Integer n = lst.size() ;
		for(int i = 0 ; i < n ; i++){
			if( i > 0 ) pw.print( "," ) ;
			pw.print( lst.get( i ) ) ;
		}
		pw.println() ;
	}
}
