import com.magiadigital.metrics.MetricsEngine;

public class Main {
	public static final String all_dialogs = "/home/nonwhite/dialogs/all.txt" ;
	
	static public void main(String[] args) {

		final MetricsEngine engine = MetricsEngine.getInstance() ;
		engine.processDialogs( all_dialogs ) ;
	}
}