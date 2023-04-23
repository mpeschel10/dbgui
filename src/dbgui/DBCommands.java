package dbgui;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.sql.*;
//import oracle.jdbc.*;

public class DBCommands {
	public DBCommands(String username, String password) throws SQLException {
		
		
	}
	
	public static void main(String[] args) throws Exception, SQLException {
		
		String dbUrl = "jdbc:oracle:thin:@h3oracle.ad.psu.edu:1521/orclpdb.ad.psu.edu"; 
		String username = "tcl5238"; //psu username, exclude the @psu.edu
		String password = "tcl5238"; //oracle database password
		

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Can load thing");
			Connection conn = DriverManager.getConnection(dbUrl, username, password);
			if(conn != null) System.out.println("You are connected!");

			conn.setAutoCommit(false);
   			
			switch (args[0]) {
				default:
					System.out.println("Invalid Command");
					break;
				case "addCustomer": 
					java.sql.Date dateOfBirth = java.sql.Date.valueOf(args[5]);
					System.out.println(addCustomer(conn, Integer.valueOf(args[1]), args[2], args[3], args[4], dateOfBirth) ? "Command Completed Successfully" : "Command Failed");
					break;
				case "addPolicy":
					java.sql.Date startDate = java.sql.Date.valueOf(args[4]);
					java.sql.Date endDate = java.sql.Date.valueOf(args[5]);
					System.out.println(addPolicy(conn, Integer.valueOf(args[1]), args[2], Integer.valueOf(args[3]),
						startDate, endDate, Integer.valueOf(args[6]), args[7]) ? "Command Completed Successfully" : "Command Failed");
					break;
				case "addCarInfo":
					System.out.println(addCarInfo(conn, args[1], Integer.valueOf(args[2]), args[3], args[4],
						Integer.valueOf(args[5]), Integer.valueOf(args[6])) ? "Command Completed Successfully" : "Command Failed");
					break;
				case "addHomeInfo":
					System.out.println(addHomeInfo(conn, Integer.valueOf(args[1]), args[2], Integer.valueOf(args[3]), Integer.valueOf(args[4]),
						Integer.valueOf(args[5]), Integer.valueOf(args[6]), Integer.valueOf(args[7])) ? "Command Completed Successfully" : "Command Failed");
					break;
				case "addLifeInfo":
					System.out.println(addLifeInfo(conn, Integer.valueOf(args[1]), Integer.valueOf(args[2]), Integer.valueOf(args[3])) ? "Command Completed Successfully" : "Command Failed");
					break;
				case "addConditions":
					System.out.println(addConditions(conn, Integer.valueOf(args[1]), args[2]) ? "Command Completed Successfully" : "Command Failed");
					break;
				case "browseCustomer":
					browseCustomer(conn);
					break;
				case "browsePolicy":
					browsePolicy(conn);
					break;
				case "searchCustomer":
					searchCustomer(conn, args);
					break;
				case "searchPolicy":
					searchPolicy(conn, args);
					break;
			}
			conn.close();
		}
		catch (SQLException error) {
			error.printStackTrace();
		}
	}

	public static boolean addCustomer(Connection conn, int SSN, String firstName, String lastName, String contactInfo, java.sql.Date dateOfBirth) throws SQLException {
		String sql = "INSERT INTO customer (ssn, firstName, lastName, contactInfo, dateOfBirth) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement statement = conn.prepareStatement(sql);

		// Set the parameter values for the PreparedStatement object
		statement.setInt(1, SSN);
		statement.setString(2, firstName);
		statement.setString(3, lastName);
		statement.setString(4, contactInfo);
		statement.setDate(5, dateOfBirth);

		// Execute the query
		int rowsInserted = statement.executeUpdate();
		System.out.println(rowsInserted);
		return true;
	}

	public static boolean addPolicy(Connection conn, int policyID, String coverage, int monthlyPayment, java.sql.Date startDate, java.sql.Date endDate, int owner, String policyType) throws SQLException {

		String sql = "INSERT INTO policy (policy_ID, coverage, monthly_payment, start_date, end_date, owner, policy_type) VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement statement = conn.prepareStatement(sql);
		
		// Set the parameter values for the PreparedStatement object
		statement.setInt(1, policyID);
		statement.setString(2, coverage);
		statement.setInt(3, monthlyPayment);
		statement.setDate(4, startDate);
		statement.setDate(5, endDate);
		statement.setInt(6, owner);
		statement.setString(7, policyType);
		
		// Execute the query
		int rowsInserted = statement.executeUpdate();
		System.out.println(rowsInserted);
		return true;
	}

	public static boolean addCarInfo(Connection conn, String VIN, int mileagePerYear, String make, String model, int year, int policyID) throws SQLException {

		String sql = "INSERT INTO car_info (vin, mileagePerYear, make, model, year, policy_ID) VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement statement = conn.prepareStatement(sql);
		
		// Set the parameter values for the PreparedStatement object
		statement.setString(1, VIN);
		statement.setInt(2, mileagePerYear);
		statement.setString(3, make);
		statement.setString(4, model);
		statement.setInt(5, year);
		statement.setInt(6, policyID);
		
		// Execute the query
		int rowsInserted = statement.executeUpdate();
		System.out.println(rowsInserted);
		return true;
	}

	public static boolean addHomeInfo(Connection conn, int homeID, String address, int area, int bedCount, int bathCount, int price, int policyID) throws SQLException {

		String sql = "INSERT INTO home_info (home_ID, address, area, bedCount, bathCount, price, policy_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement statement = conn.prepareStatement(sql);
		
		// Set the parameter values for the PreparedStatement object
		statement.setInt(1, homeID);
		statement.setString(2, address);
		statement.setInt(3, area);
		statement.setInt(4, bedCount);
		statement.setInt(5, bathCount);
		statement.setInt(6, price);
		statement.setInt(7, policyID);
		
		// Execute the query
		int rowsInserted = statement.executeUpdate();
		System.out.println(rowsInserted);
		return true;
	}

	public static boolean addLifeInfo(Connection conn, int policyID, int lifeID, int benefits) throws SQLException {
		
		String sql = "INSERT INTO life_info (policy_ID, life_ID, benefits) VALUES (?, ?, ?)";
		PreparedStatement statement = conn.prepareStatement(sql);
		
		// Set the parameter values for the PreparedStatement object
		statement.setInt(1, policyID);
		statement.setInt(2, lifeID);
		statement.setInt(3, benefits);
		
		// Execute the query
		int rowsInserted = statement.executeUpdate();
		System.out.println(rowsInserted);
		return true;
	}
	
	public static boolean addConditions(Connection conn, int lifeID, String condition) throws SQLException {

		String sql = "INSERT INTO conditions (life_ID, existing_conditions) VALUES (?, ?)";
		PreparedStatement statement = conn.prepareStatement(sql);
		
		// Set the parameter values for the PreparedStatement object
		statement.setInt(1, lifeID);
		statement.setString(2, condition);
		
		// Execute the query
		int rowsInserted = statement.executeUpdate();
		System.out.println(rowsInserted);
		return true;
	}

	public static void browseCustomer(Connection conn) throws SQLException {
		for (String person : listPeople(conn)) {
			System.out.println(person);
			System.out.println();
		}
	}

	private static String formatPerson(ResultSet result) throws SQLException {
		return String.format(
			"SSN: %d\nName: %s %s\nContact: %s\nDOB: %s",
			result.getInt("ssn"), result.getString("firstName"), result.getString("lastName"),
			result.getString("contactInfo"), result.getDate("dateofbirth")
		);
	}

	public static List<String> listPeople(Connection conn) throws SQLException {
		List<String> people = new ArrayList<String>();
		String sql = "select * from customer";
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(sql);
		while (result.next()) {
			people.add(formatPerson(result));
		}
		return people;
	}

	public static void browsePolicy(Connection conn) throws SQLException {
		for (String policy : listPolicies(conn)) {
			System.out.println(policy);
			System.out.println();
		}
	}

	private static String formatPolicy(ResultSet result) throws SQLException {
		return String.format(
			"Policy id: %d\nOwner SSN:%d\nCovered from %s to %s\nCoverage: %s\nMonthly payment: $%d\nPolicy type: %s",
			result.getInt("policy_id"), result.getInt("owner"),
			result.getDate("start_date"), result.getDate("end_date"),
			result.getString("coverage"), result.getInt("monthly_payment"),
			getPolicyType(result)
		);
	}

	private static String formatHomePolicy(ResultSet result) throws SQLException {
		return formatPolicy(result) + String.format(
			"\nAddress: %s\nArea: %d square feet\nBeds: %d Baths: %d\nPrice: $%d",
			result.getString("address"), result.getInt("area"),
			result.getInt("bedcount"), result.getInt("bathcount"),
			result.getInt("price")
		);
	}

	private static String formatCarPolicy(ResultSet result) throws SQLException {
		return 	formatPolicy(result) + String.format(
			"\nVIN: %s\nMileage: %d per year\nYear, make, and model: %d %s %s",
			result.getString("VIN"), result.getInt("mileageperyear"),
			result.getInt("year"), result.getString("make"), result.getString("model")
		);
	}

	private static String formatLifePolicy(ResultSet result) throws SQLException {
		return formatPolicy(result) + String.format(
			"\nBenefits: %d\nExisting conditions: %s",
			result.getInt("benefits"), result.getString("existing_conditions")
		);
	}



	private static String getPolicyType(ResultSet result) throws SQLException {
		String[] parts = result.getString("policy_type").split("_");
		return parts[0];
	}

	public static List<String> listPolicies(Connection conn) throws SQLException {
		List<String> policies = new ArrayList<String>();
		
		String home_sql = "select * from policy natural join home_info";
		ResultSet result = conn.createStatement().executeQuery(home_sql);
		while (result.next()) {
			policies.add(formatHomePolicy(result));
		}
		
		String car_sql = "select * from policy natural join car_info";
		result = conn.createStatement().executeQuery(car_sql);
		while (result.next()) {
			policies.add(formatCarPolicy(result));
		}
		
		String life_sql = "select * from policy natural join life_info natural left join (select life_id, LISTAGG(existing_conditions, ', ') as existing_conditions from conditions group by life_id)";
		result = conn.createStatement().executeQuery(life_sql);
		while (result.next()) {
			policies.add(formatLifePolicy(result));
		}
		return policies;
	}

	public static void searchCustomer(Connection conn, String[] args) throws SQLException {
		String sql = "select * from customer where ";

		if (args.length < 2) {
			throw new RuntimeException("You must provide a conditions to search customers for.");
		}

		sql += String.join(" ", Arrays.asList(args).subList(1, args.length));
		ResultSet result = conn.createStatement().executeQuery(sql);
		while (result.next()) {
			System.out.println(formatPerson(result));
			System.out.println();
		}
	}

	public static void searchHomePolicy(Connection conn, String[] args) throws SQLException {
		String sql = "select * from policy natural join home_info where policy_type = 'home_info' and ";

		sql += String.join(" ", Arrays.asList(args).subList(2, args.length));
		ResultSet result = conn.createStatement().executeQuery(sql);
		while (result.next()) {
			System.out.println(formatHomePolicy(result));
			System.out.println();
		}
	}

	public static void searchCarPolicy(Connection conn, String[] args) throws SQLException {
		String sql = "select * from policy natural join car_info where policy_type = 'car_info' and ";

		sql += String.join(" ", Arrays.asList(args).subList(2, args.length));
		ResultSet result = conn.createStatement().executeQuery(sql);
		while (result.next()) {
			System.out.println(formatCarPolicy(result));
			System.out.println();
		}
	}

	public static void searchLifePolicy(Connection conn, String[] args) throws SQLException {
		String sql = "select * from policy natural join life_info natural left join (select life_id, LISTAGG(existing_conditions, ', ') as existing_conditions from conditions group by life_id) where policy_type = 'life_info' and ";
		sql += String.join(" ", Arrays.asList(args).subList(2, args.length));
		
		ResultSet result = conn.createStatement().executeQuery(sql);
		while (result.next()) {
			System.out.println(formatLifePolicy(result));
			System.out.println();
		}
	}

	public static void searchPolicy(Connection conn, String[] args) throws SQLException {
		if (args.length < 2) throw new RuntimeException("The second argument after 'searchPolicy' must be one of [any | home | car | life].");
		switch(args[1]) {
			case "any":
				searchHomePolicy(conn, args);
				searchCarPolicy(conn, args);
				searchLifePolicy(conn, args);
				break;
			case "home":
				searchHomePolicy(conn, args);
				break;
			case "car":
				searchCarPolicy(conn, args);
				break;
			case "life":
				searchLifePolicy(conn, args);
				break;
			case "default":
				throw new RuntimeException("The second argument after 'searchPolicy' must be one of (any | home | car | life).");
		}
	}
}


