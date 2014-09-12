import java.io.FileNotFoundException;

import com.magiadigital.metrics.MetricsEngine;

public class Main {
	public static final String all_dialogs = "/home/nonwhite/magia/bagofwords/dialogs/all.txt" ;
	
	static public void main(String[] args) throws FileNotFoundException {
		final MetricsEngine engine = MetricsEngine.getInstance() ;
		engine.processDialogs( all_dialogs ) ;
	}
}