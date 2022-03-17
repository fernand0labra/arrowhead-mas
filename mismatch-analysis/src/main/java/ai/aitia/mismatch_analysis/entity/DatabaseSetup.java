package ai.aitia.mismatch_analysis.entity;

import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.ibatis.jdbc.ScriptRunner;

/**
 * Database Setup :: Mismatch Analysis System 
 * Course 'Project in Computer Science' :: Luleå Tekniska Universitet
 * 
 * @author 	Fernando Labra Caso (fernandolabracaso@gmail.com)
 * @version 1.0.0
 */

public class DatabaseSetup {

	//=================================================================================================
	// methods
	
	//-------------------------------------------------------------------------------------------------
	public static void main(String[] args) {
		Connection conn = null;
	
		try {
			// Obtain the DB driver
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
	
			// Create the connection
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mismatch_analysis?"
					+ "serverTimezone=UTC&user=root&password=root");
	
			// Initialize the script runner
		    ScriptRunner sr = new ScriptRunner(conn);
		    
		    // Creating a reader object
	        InputStreamReader reader = new InputStreamReader(DatabaseSetup.class.getResourceAsStream("/mismatchAnalysis/dbUtils/setup.sql"));
	     	        
		    // Running the script
		    sr.setLogWriter(null);
		    sr.runScript(reader);
	
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
