package ReservationModule.users.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ReservationModule.users.models.Professor;
import ReservationModule.users.models.User;

public class ProfessorDao {
	private String jdbcURL = "jdbc:mysql://127.0.0.1:3306/reservationdb";
	private String jdbcUsername = "root";
	private String jdbcPassword = "root";

	private static final String INSERT_USER_SQL = "INSERT INTO user (username, password, first_name, surname, role) VALUES (?, ?, ?, ?, ?)";
	private static final String INSERT_PROFESSOR_SQL = "INSERT INTO professor (username, department, school, specialty, id) VALUES (?, ?, ?, ?, ?)";
	private static final String LOGIN_PROFESSOR_SQL = "select * from professor where username = ? and password = ? ";
	private static final String LOGIN_USER_SQL = "select role from user where username = ? and password = ? ";
	


	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	public void insertProfessor(Professor professor) throws SQLException {
		System.out.println(INSERT_PROFESSOR_SQL);
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement userStatement = connection.prepareStatement(INSERT_USER_SQL);
	            PreparedStatement professorStatement = connection.prepareStatement(INSERT_PROFESSOR_SQL)) {
			userStatement.setString(1, professor.getUsername());
            userStatement.setString(2, professor.getPassword());
            userStatement.setString(3, professor.getName());
            userStatement.setString(4, professor.getSurname());
            userStatement.setInt(5, professor.getRole());
            userStatement.executeUpdate();
            
            // Set parameters for student table
            professorStatement.setString(1, professor.getUsername());
            professorStatement.setString(2, professor.getDepartment());
            professorStatement.setString(3, professor.getSchool());
            professorStatement.setString(4, professor.getSpecialty());
            professorStatement.setString(5, professor.getId());
            professorStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getStackTrace());
		}
	}
	public boolean LoginTheProfessor(Professor professor) throws SQLException {
		boolean status= false;
		System.out.println(LOGIN_PROFESSOR_SQL);
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement userStatement = connection.prepareStatement(LOGIN_USER_SQL);
				PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_PROFESSOR_SQL)) {
			userStatement.setInt(5, professor.getRole());
			userStatement.setString(1, professor.getUsername());
            userStatement.setString(2, professor.getPassword());
			preparedStatement.setString(1, professor.getUsername());
			
			
			System.out.println(preparedStatement);
			preparedStatement.executeQuery();
			status= preparedStatement.executeQuery().next();
		} catch (SQLException e) {
			System.out.println(e.getStackTrace());
		}
		return status;
	}
	

	public Professor getProfessor(String parameter) {
		// TODO Auto-generated method stub
		return null;
	}
}
