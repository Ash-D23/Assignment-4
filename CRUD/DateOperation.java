package CRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import jdbcUtil.jdbcUtil;

public class DateOperation {

	public static void main(String[] args) throws ParseException {

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;

		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter the name:: ");
		String name = scanner.next();
		System.out.print("Enter the address:: ");
		String address = scanner.next();
		System.out.print("Enter the gender:: ");
		String gender = scanner.next();

		System.out.print("Enter the dob::(dd-mm-YYYY) ");
		String dob = scanner.next();
		
		System.out.print("Enter the doj::(mm-dd-YYYY) ");
		String doj = scanner.next();
		
		System.out.print("Enter the dom::(yyyy-mm-dd) ");
		String dom = scanner.next();

		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd-yyyy");
		
		java.util.Date udob = sdf1.parse(dob);
		java.util.Date udoj = sdf2.parse(doj);

		long time = udob.getTime();
		java.sql.Date sqldob = new java.sql.Date(time);
		
		time = udoj.getTime();
		java.sql.Date sqldoj = new java.sql.Date(time);
		
		java.sql.Date sqldom = java.sql.Date.valueOf(dom);


		String sqlInsertQuery = "insert into employee(`name`,`address`,`gender`,`dob`,`doj`,`dom`) values (?,?,?,?,?,?)";
		
		String sqlSelectQuery = "select * from employee";

		try {

			connection = jdbcUtil.getJdbcConnection();

			if (connection != null)
				pstmt = connection.prepareStatement(sqlInsertQuery);

			if (pstmt != null) {
				pstmt.setString(1, name);
				pstmt.setString(2, address);
				pstmt.setString(3, gender);
				pstmt.setDate(4,sqldob);
				pstmt.setDate(5,sqldoj);
				pstmt.setDate(6,sqldom);

				int rowAffected = pstmt.executeUpdate();

				System.out.println("No of rows affected is:: " + rowAffected);
				
				pstmt.close();
			}
			
			
			if (connection != null)
				pstmt = connection.prepareStatement(sqlSelectQuery);
			if (pstmt != null) {
				resultSet = pstmt.executeQuery();
			}
			if (resultSet != null) {
				System.out.println("EID\tNAME\tAddress\tGender\tDOB\tDOJ\tDOM");
				while(resultSet.next()) {
					
					System.out.print(resultSet.getInt(1) + "\t" + resultSet.getString(2) + "\t" + resultSet.getString(3)
							+ "\t" + resultSet.getString(4) + "\t" );
					java.sql.Date sdob = resultSet.getDate(5);
					String s = sdf1.format(sdob);
					
					java.sql.Date sdoj = resultSet.getDate(6);
					String s1 = sdf2.format(sdoj);
					
					java.sql.Date s3 = resultSet.getDate(7);
					
					System.out.print(s+"\t"+s1+"\t"+s3);
					
					
					System.out.println();
					
				}
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				jdbcUtil.closeConnection(null, pstmt, connection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (scanner != null) {
				scanner.close();
			}
		}

	
	}

}
