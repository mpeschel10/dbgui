package dbgui;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class DBCommands {
	Connection conn;
	public DBCommands(String username, String password) throws SQLException, ClassNotFoundException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String dbUrl = "jdbc:oracle:thin:@h3oracle.ad.psu.edu:1521/orclpdb.ad.psu.edu"; 
		
		conn = DriverManager.getConnection(dbUrl, username, password);
		if(conn != null) System.out.println("You are connected!");
		conn.setAutoCommit(false);
	}
	
	public void close() throws SQLException { conn.close(); }
	
	public static void main(String[] args) throws Exception {
		try {
			DBCommands commands = new DBCommands("tcl5238", "tcl5238");
			printList(commands.interpret(args));
			commands.close();
		} catch (SQLException error) {
			error.printStackTrace();
		}
	}
	
	public List<String> interpret(String[] args) throws SQLException {
		List<String> result = new ArrayList<String>();
		switch (args[0]) {
		default:
			System.out.println("Invalid Command");
			break;
		case "addCustomer": 
			java.sql.Date dateOfBirth = java.sql.Date.valueOf(args[5]);
			result.add(addCustomer(Integer.valueOf(args[1]), args[2], args[3], args[4], dateOfBirth) ? "Add customer success" : "Failure");
			break;
		case "addPolicy":
			java.sql.Date startDate = java.sql.Date.valueOf(args[4]);
			java.sql.Date endDate = java.sql.Date.valueOf(args[5]);
			result.add(addPolicy(Integer.valueOf(args[1]), args[2], Integer.valueOf(args[3]),
				startDate, endDate, Integer.valueOf(args[6]), args[7]) ? "Add policy success" : "Failure");
			break;
		case "addCarInfo":
			result.add(this.addCarInfo(args[1], Integer.valueOf(args[2]), args[3], args[4],
				Integer.valueOf(args[5]), Integer.valueOf(args[6])) ? "Add car info success" : "Failure");
			break;
		case "addHomeInfo":
			result.add(this.addHomeInfo(Integer.valueOf(args[1]), args[2], Integer.valueOf(args[3]), Integer.valueOf(args[4]),
				Integer.valueOf(args[5]), Integer.valueOf(args[6]), Integer.valueOf(args[7])) ? "Add home info success" : "Failure");
			break;
		case "addLifeInfo":
			result.add(this.addLifeInfo(Integer.valueOf(args[1]), Integer.valueOf(args[2]), Integer.valueOf(args[3])) ? "Add life info success" : "Failure");
			break;
		case "addConditions":
			result.add(this.addConditions(Integer.valueOf(args[1]), args[2]) ? "Add conditions success" : "Failure");
			break;
		case "browseCustomer": return browseCustomer();
		case "browsePolicy":   return browsePolicy();
		case "searchCustomer": return searchCustomer(args);
		case "searchPolicy":   return searchPolicy(args);
		case "editCustomer":
			java.sql.Date newDateOfBirth = java.sql.Date.valueOf(args[6]);
			result.add(this.editCustomer(Integer.valueOf(args[1]), Integer.valueOf(args[2]), args[3], args[4], args[5], newDateOfBirth) ? "Edit customer success" : "Failure");
			break;
		case "removeCustomer":
			result.add(this.removeCustomer(Integer.valueOf(args[1])) ? "Remove customer success" : "Failure");
			break;
		case "removePolicy":
			result.add(this.removePolicy(Integer.valueOf(args[1])) ? "Remove policy success" : "Failure");
			break;
		case "editPolicy":
			java.sql.Date newStartDate = java.sql.Date.valueOf(args[5]);
			java.sql.Date newEndDate = java.sql.Date.valueOf(args[6]);
			result.add(this.editPolicy(Integer.valueOf(args[1]), Integer.valueOf(args[2]), args[3], Integer.valueOf(args[4]),
				newStartDate, newEndDate, args[7], args[8]) ? "Edit policy success" : "Failure");
			break;
		}
		return result;
	}

	public boolean addCustomer(int SSN, String firstName, String lastName, String contactInfo, java.sql.Date dateOfBirth) throws SQLException {
		String sql = "INSERT INTO customer (ssn, firstName, lastName, contactInfo, dateOfBirth) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement statement = conn.prepareStatement(sql);

		statement.setInt(1, SSN);
		statement.setString(2, firstName);
		statement.setString(3, lastName);
		statement.setString(4, contactInfo);
		statement.setDate(5, dateOfBirth);

		int rowsInserted = statement.executeUpdate();
		return rowsInserted != 0;
	}

	public boolean addPolicy(int policyID, String coverage, int monthlyPayment, java.sql.Date startDate, java.sql.Date endDate, int owner, String policyType) throws SQLException {
		String sql = "INSERT INTO policy (policy_ID, coverage, monthly_payment, start_date, end_date, owner, policy_type) VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement statement = conn.prepareStatement(sql);
		
		statement.setInt(1, policyID);
		statement.setString(2, coverage);
		statement.setInt(3, monthlyPayment);
		statement.setDate(4, startDate);
		statement.setDate(5, endDate);
		statement.setInt(6, owner);
		statement.setString(7, policyType);
		
		statement.executeUpdate();
		return true;
	}

	public boolean addCarInfo(String VIN, int mileagePerYear, String make, String model, int year, int policyID) throws SQLException {
		String sql = "INSERT INTO car_info (vin, mileagePerYear, make, model, year, policy_ID) VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement statement = conn.prepareStatement(sql);
		
		statement.setString(1, VIN);
		statement.setInt(2, mileagePerYear);
		statement.setString(3, make);
		statement.setString(4, model);
		statement.setInt(5, year);
		statement.setInt(6, policyID);
		
		// Execute the query
		statement.executeUpdate();
		return true;
	}

	public boolean addHomeInfo(int homeID, String address, int area, int bedCount, int bathCount, int price, int policyID) throws SQLException {
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
		statement.executeUpdate();
		return true;
	}

	public boolean addLifeInfo(int policyID, int lifeID, int benefits) throws SQLException {
		String sql = "INSERT INTO life_info (policy_ID, life_ID, benefits) VALUES (?, ?, ?)";
		PreparedStatement statement = conn.prepareStatement(sql);
		
		// Set the parameter values for the PreparedStatement object
		statement.setInt(1, policyID);
		statement.setInt(2, lifeID);
		statement.setInt(3, benefits);
		
		// Execute the query
		statement.executeUpdate();
		return true;
	}
	
	public boolean addConditions(int lifeID, String condition) throws SQLException {
		String sql = "INSERT INTO conditions (life_ID, existing_conditions) VALUES (?, ?)";
		PreparedStatement statement = conn.prepareStatement(sql);
		
		// Set the parameter values for the PreparedStatement object
		statement.setInt(1, lifeID);
		statement.setString(2, condition);
		
		// Execute the query
		statement.executeUpdate();
		return true;
	}

	public boolean editCustomer(int SSN, int newSSN, String firstName, String lastName, String contactInfo, java.sql.Date dateOfBirth) throws SQLException {
		String sql = "UPDATE customer SET ssn = ?, firstName = ?, lastName = ?, contactInfo = ?, dateOfBirth = ? WHERE ssn = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setInt(1, newSSN);
		stmt.setString(2, firstName);
		stmt.setString(3, lastName);
		stmt.setString(4, contactInfo);
		stmt.setDate(5, dateOfBirth);
		stmt.setInt(6, SSN);
		return stmt.executeUpdate() != 0;
	}

	public boolean editPolicy(int policyID, int newPolicyID, String coverage, int monthlyPayment, java.sql.Date startDate, java.sql.Date endDate, String owner, String policyType) throws SQLException {
		String sql = "UPDATE customer SET policy_ID = ?, coverage = ?, monthly_payment = ?, start_date = ?, end_date = ?, owner = ?, policy_type = ? WHERE policyID = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setInt(1, newPolicyID);
		stmt.setString(2, coverage);
		stmt.setInt(3, monthlyPayment);
		stmt.setDate(4, startDate);
		stmt.setDate(5, endDate);
		stmt.setString(6, owner);
		stmt.setString(7, policyType);
		stmt.setInt(8, policyID);
		return stmt.executeUpdate() != 0;
	}

	public boolean removeCustomer(int SSN) throws SQLException {
		String sql = "DELETE FROM customer WHERE ssn = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, SSN);
		System.out.println("about to execute removeCustomer");
		return stmt.executeUpdate() != 0;
	}

	public boolean removePolicy(int policyID) throws SQLException {
		String sql = "DELETE FROM policy WHERE policy_id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, policyID);
		return stmt.executeUpdate() != 0;
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
	private static final Function<ResultSet, String> formatPerson = new Function<ResultSet, String>() {
		@Override
		public String apply(ResultSet result) {
			try {
				return String.format(
						"SSN: %d\nName: %s %s\nContact: %s\nDOB: %s",
						result.getInt("ssn"), result.getString("firstName"), result.getString("lastName"),
						result.getString("contactInfo"), result.getDate("dateofbirth")
					);
			} catch(Exception e) { return""; }
		}
	};
	private static final Function<ResultSet, String> formatCarPolicy = new Function<>() {
		public String apply(ResultSet result) {
			try {
			return 	formatPolicy(result) + String.format(
					"\nVIN: %s\nMileage: %d per year\nYear, make, and model: %d %s %s",
					result.getString("VIN"), result.getInt("mileageperyear"),
					result.getInt("year"), result.getString("make"), result.getString("model")
				);
			} catch(Exception e) {return"";}
		}
	};
	private static final Function<ResultSet, String> formatHomePolicy = new Function<>() {
		public String apply(ResultSet result) {
			try {
			return formatPolicy(result) + String.format(
					"\nAddress: %s\nArea: %d square feet\nBeds: %d Baths: %d\nPrice: $%d",
					result.getString("address"), result.getInt("area"),
					result.getInt("bedcount"), result.getInt("bathcount"),
					result.getInt("price")
				);
			} catch(Exception e) {return"";}
		}
	};
	private static final Function<ResultSet, String> formatLifePolicy = new Function<>() {
		public String apply(ResultSet result) {
			try {
			return formatPolicy(result) + String.format(
					"\nBenefits: %d\nExisting conditions: %s",
					result.getInt("benefits"), result.getString("existing_conditions")
				);
			} catch(Exception e) {return"";}
		}
	};

	private static String getPolicyType(ResultSet result) throws SQLException { return result.getString("policy_type").split("_")[0]; }
	
	public List<String> browseCustomer() throws SQLException {
		String sql = "select * from customer";
		return packResults(conn.createStatement().executeQuery(sql), formatPerson);
	}
	public List<String> browsePolicy() throws SQLException {
		List<String> policies = new ArrayList<String>();
		String home_sql = "select * from policy natural join home_info";
		String car_sql  = "select * from policy natural join car_info";
		String life_sql = "select * from policy natural join life_info natural left join (select life_id, LISTAGG(existing_conditions, ', ') as existing_conditions from conditions group by life_id)";
		policies.addAll(packResults(conn.createStatement().executeQuery(home_sql), formatHomePolicy));
		policies.addAll(packResults(conn.createStatement().executeQuery(car_sql),  formatCarPolicy ));
		policies.addAll(packResults(conn.createStatement().executeQuery(life_sql), formatLifePolicy));
		return policies;
	}

	public List<String> searchCustomer(String[] args) throws SQLException {
		String sql = "select * from customer where ";
		if (args.length < 2) { throw new RuntimeException("You must provide a conditions to search customers for."); }
		sql += String.join(" ", Arrays.asList(args).subList(1, args.length));
		return packResults(conn.createStatement().executeQuery(sql), formatPerson);
	}
	
	public static List<String> packResults(ResultSet result, Function<ResultSet, String> f) throws SQLException {
		List<String> out = new ArrayList<String>();
		while (result.next()) { out.add(f.apply(result)); out.add(""); }
		return out;
	}

	public static void printList(List<String> results) { for (String s : results) System.out.println(s); }
	
	public List<String> searchHomePolicy(String[] args) throws SQLException {
		String sql = "select * from policy natural join home_info where policy_type = 'home_info' and ";
		sql += String.join(" ", Arrays.asList(args).subList(2, args.length));
		return packResults(conn.createStatement().executeQuery(sql), formatHomePolicy);
	}
	public List<String> searchCarPolicy(String[] args) throws SQLException {
		String sql = "select * from policy natural join car_info where policy_type = 'car_info' and ";
		sql += String.join(" ", Arrays.asList(args).subList(2, args.length));
		return packResults(conn.createStatement().executeQuery(sql), formatCarPolicy);
	}
	public List<String> searchLifePolicy(String[] args) throws SQLException {
		String sql = "select * from policy natural join life_info natural left join (select life_id, LISTAGG(existing_conditions, ', ') as existing_conditions from conditions group by life_id) where policy_type = 'life_info' and ";
		sql += String.join(" ", Arrays.asList(args).subList(2, args.length));
		return packResults( conn.createStatement().executeQuery(sql), formatLifePolicy );
	}

	public List<String> searchPolicy(String[] args) throws SQLException {
		if (args.length < 2) throw new RuntimeException("The second argument after 'searchPolicy' must be one of [any | home | car | life].");
		switch(args[1]) {
			case "any":
				List<String> result = searchHomePolicy(args);
				result.addAll(searchCarPolicy(args));
				result.addAll(searchLifePolicy(args));
				return result;
			case "home":
				return searchHomePolicy(args);
			case "car":
				return searchCarPolicy(args);
			case "life":
				return searchLifePolicy(args);
			default:
				throw new RuntimeException("The second argument after 'searchPolicy' must be one of (any | home | car | life).");
		}
	}
}


