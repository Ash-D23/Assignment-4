package CRUD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import jdbcUtil.jdbcUtil;

public class DBMenu2 {

	public static void main(String[] args) {
		boolean exit = false;
		Scanner in = new Scanner(System.in);
		int choice = 0;
		while(!exit) {
			System.out.println("Menu of Student DB: ");
			System.out.println("1.Display All Students\n2.Create a Student\n3.Update Student Name\n4.Delete a Student\n5.Exit");
			System.out.println("Enter your choice");
			
			if(in.hasNext()) {
				choice = in.nextInt();
			}
			
			switch(choice) {
			case 1:
				ReadDB();
				break;
			case 2:
				System.out.println("Enter the name:");
				String name = in.next();
				System.out.println("Enter Age: ");
				int age = in.nextInt();
				System.out.println("Enter Address: ");
				String addr = in.next();
				InsertDB(name, age, addr);
				break;
			case 3:
				System.out.println("Enter ID: ");
				int id = in.nextInt();
				System.out.println("Enter New Name: ");
				String newName = in.next();
				
				UpdateDB(id, newName);
				break;
			case 4:
				System.out.println("Enter ID: ");
				id = in.nextInt();
				DeleteDB(id);
				break;
			case 5:
				exit = true;
				break;
			default:
				System.out.println("No such input");
				exit = true;
			}
			
			System.out.println();
		}
		
		if (in != null)
			in.close();
	}
	
	private static void ReadDB(){
		Connection connection = null;
		ResultSet resultSet = null;
		Statement stmt = null;

		String sqlSelectQuery = "select sid,sname,sage,saddr from student";

		try {
			connection = jdbcUtil.getJdbcConnection();
			if (connection != null)
				stmt = connection.createStatement();
			if (stmt != null) {
				resultSet = stmt.executeQuery(sqlSelectQuery);
			}
			if (resultSet != null) {
				System.out.println("SID\tSNAME\tSAGE\tSADDR");
				while(resultSet.next()) {
					
					System.out.println(resultSet.getInt(1) + "\t" + resultSet.getString(2) + "\t" + resultSet.getInt(3)
							+ "\t" + resultSet.getString(4));
					
				}
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				jdbcUtil.closeConnection(resultSet, stmt, connection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void DeleteDB(int id) {
		Connection connection = null;
		Statement stmt = null;

		String sqlDeleteQuery = "delete from student where sid = "+id;
		try {
			connection = jdbcUtil.getJdbcConnection();
			if (connection != null)
				stmt = connection.createStatement();
			if (stmt != null) {
				
				int rowAffected = stmt.executeUpdate(sqlDeleteQuery);
				System.out.println("No of rows Affected is :: " + rowAffected);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				jdbcUtil.closeConnection(null, stmt, connection);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		System.out.println("Dalete");
	}
	
	private static void UpdateDB(int id, String name) {
		Connection connection = null;
		Statement stmt = null;

		String sqlUpdateQuery = "update student set sname ="+"'"+name+"'"+" where sid = "+id;
		try {
			connection = jdbcUtil.getJdbcConnection();
			if (connection != null)
				stmt = connection.createStatement();
			
			if (stmt != null) {
;
				int rowAffected = stmt.executeUpdate(sqlUpdateQuery);
				System.out.println("No of rows Affected is :: " + rowAffected);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				jdbcUtil.closeConnection(null, stmt, connection);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		System.out.println("Update");
	}
	
	private static void InsertDB(String name, int age, String addr) {
		Connection connection = null;
		Statement stmt = null;


		String sqlInsertQuery = "insert into student(`sname`,`sage`,`saddr`) values(" + "'" + name + "',"+age+",'" + addr+"')";
		try {
			connection = jdbcUtil.getJdbcConnection();
			if (connection != null)
				stmt = connection.createStatement();
			
			if (stmt != null) {

				
				int rowAffected = stmt.executeUpdate(sqlInsertQuery);
				System.out.println("No of rows Affected is :: " + rowAffected);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				jdbcUtil.closeConnection(null, stmt, connection);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		System.out.println("Inserted");
	}

}
