import java.io.FileNotFoundException;

import com.magiadigital.metrics.MetricsEngine;

public class Main {
	public static final String all_dialogs = "dialogs/all.txt" ;
	public static final String bag_of_words_file = "dialogs/bow.txt" ;
	
	static public void main(String[] args) throws FileNotFoundException {
		final MetricsEngine engine = MetricsEngine.getInstance() ;
		engine.processDialogs( all_dialogs ) ;
		engine.saveBoWData( bag_of_words_file ) ;
	}
}