package ai.aitia.mismatch_analysis.controller.input;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import ai.aitia.mismatch_analysis.controller.WrongMethodException;
import ai.aitia.mismatch_analysis.entity.serviceContract.ServiceContract;

/**
 * Main :: Input Module of the Mismatch Analysis System 
 * Course 'Project in Computer Science' :: Luleå Tekniska Universitet
 * 
 * @author 	Fernando Labra Caso (fernandolabracaso@gmail.com)
 * @version 1.0.0
 */

public class InputMain {
	
	//=================================================================================================
	// members
	
	private static final int DEFAULT_BUFFER_SIZE = 8192;
	
	//=================================================================================================
	// methods
	
	//-------------------------------------------------------------------------------------------------
	/**
	 * Obtains the Interface Definition files for each system with the operationId and parses them into Service Contracts objects
	 * 
	 * @param operationId 	The service identifier
	 * @param systems 		A structure defining the consumer and provider systems that offer the service
	 * @return 				A structure containing the parsed Service Contract objects for each system
	 */
	public static HashMap<String, ArrayList<ServiceContract>> main(String operationId, HashMap<String, ArrayList<String>> systems) {
		
		// DB variables
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		// File System variables
		File consumerFile = null;
		File providerFile = null;

		try {			
			// Obtain the DB driver
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

			// Create the connection
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mismatch_analysis?"
					+ "serverTimezone=UTC&user=root&password=root");

			// Create the statement object
			stmt = conn.createStatement();

			HashMap<String, ArrayList<ServiceContract>> serviceContracts = new HashMap<String, ArrayList<ServiceContract>>();
			
			/* *********************************************** */
			/*        Consumer SC Definition and Parsing       */
			/* *********************************************** */
			
			String consumer = systems.get("consumer").get(0);
			
			// Obtain consumer SC file path
			try {
				rs = stmt.executeQuery(""
						+ "SELECT `system`.`sc_path` "
						+ "FROM `system` "
						+ "INNER JOIN `operation` ON `system`.`id`=`operation`.`system_id` "
						+ "WHERE `system`.`system_name`='" + consumer + "' " 
						+ "AND `operation`.`operation_id`='" + operationId + "';");
			} catch(Exception e) {
				throw new WrongMethodException();
			}
			
			rs.next();			
			String filePath = rs.getString("sc_path");
			rs.close();

			String[] hierarchy = filePath.split("/");
			consumerFile = new File(hierarchy[hierarchy.length - 1]);
			
			// Obtain input stream from file path
			InputStream inputStream = InputMain.class.getResourceAsStream(filePath);
			
			// Generate temporary file from stream
			copyInputStreamToFile(inputStream, consumerFile);
			
			// Obtain consumer IDL utils
			Utils utilsConsumer = Utils.checkFileType(consumerFile);

			// Parse consumer interface description and store it
			ArrayList<ServiceContract> consumerList = new ArrayList<ServiceContract>();
			ServiceContract consumerSC = utilsConsumer.parse(consumerFile, operationId);
			consumerSC.setSystem(consumer);
			consumerSC.setService(operationId);
			consumerList.add(consumerSC);
			
			// Delete temporary file
			consumerFile.delete();
			
			/* *********************************************** */
			/*        Provider SC Definition and Parsing       */
			/* *********************************************** */
			
			// Build provider list of names (provider1, provider2, ..., providerN)
			String providerNames = "(";
			
			int index = systems.get("providers").size() - 1 ;
			while(index >= 0) {
				providerNames += "'" + systems.get("providers").get(index) + "'";
				
				if(index > 0)
					providerNames += ", ";
				
				index--;
			}
			
			providerNames+=")";
			
			// Obtain providers' SC file paths
			try {
				rs = stmt.executeQuery(""
						+ "SELECT `system`.`sc_path`, `system`.`system_name`"
						+ "FROM `system` "
						+ "INNER JOIN `operation` ON `system`.`id`=`operation`.`system_id` "
						+ "WHERE `system`.`system_name` IN " + providerNames + " " 
						+ "AND `operation`.`operation_id`='" + operationId + "';");
			} catch(Exception e) {
				throw new WrongMethodException();
			}
			
			Utils utilsProvider = null;
			ArrayList<ServiceContract> providerList = new ArrayList<ServiceContract>();
			
			while(rs.next()) { // For each of the provider SCs
				filePath = rs.getString("sc_path");
				
				hierarchy = filePath.split("/");
				providerFile = new File(hierarchy[hierarchy.length - 1]);
				
				// Obtain input stream from file path
				inputStream = InputMain.class.getResourceAsStream(filePath);
				
				// Generate temporary file from stream
				copyInputStreamToFile(inputStream, providerFile);
				
				// Obtain provider IDL utils
				utilsProvider = Utils.checkFileType(providerFile);
				
				// Parse provider interface description and store it
				ServiceContract providerSC = utilsProvider.parse(providerFile, operationId);
				providerSC.setSystem(rs.getString("system_name"));
				providerSC.setService(operationId);
				providerList.add(providerSC); 
				
				// Delete temporary file
				providerFile.delete();
			}
			
			/* ******************************** */
			/* Show and return the SC's objects */
			/* ******************************** */

			serviceContracts.put("consumer", consumerList);
			serviceContracts.put("provider", providerList);
						
			return serviceContracts;

		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		finally { // End usage of DB structures
			if (rs != null) {
				try { rs.close(); } catch (SQLException sqlEx) { }
				rs = null;
			}

			if (stmt != null) {
				try { stmt.close(); } catch (SQLException sqlEx) { }
				stmt = null;
			}
		}
		return null;
	}
	
	//=================================================================================================
	// assistant methods
	
	//-------------------------------------------------------------------------------------------------
	/**
	 * Creates a new file from an input stream
	 * 
	 * @param inputStream	The input stream of a file
	 * @param file			Empty file object
	 * @throws IOException	Unable to read from the input stream or to write to the file
	 */
	private static void copyInputStreamToFile(InputStream inputStream, File file) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
            int read;
            byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }
	}

}
