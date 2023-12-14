package database.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseManager;

public class Project {
	private int id;
	private String judul;

	public Project(int id, String judul) {
		this.id = id;
		this.judul = judul;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getJudul() {
		return judul;
	}
	public void setJudul(String judul) {
		this.judul = judul;
	}

	public static List<Project> getAllProject() {
		final String GET_ALL_PROJECT = "SELECT * FROM project;";
		Connection connection = DatabaseManager.getConnection();
		List<Project> projects = new ArrayList<>();
		try {
		    PreparedStatement statement = connection.prepareStatement(GET_ALL_PROJECT);
		    ResultSet resultSet = statement.executeQuery();
		    while (resultSet.next()) {
		        int id = resultSet.getInt("id");
		        String judul = resultSet.getString("judul");
		        Project project = new Project(id, judul);
		        projects.add(project);
					}
					resultSet.close();
					statement.close();
				} catch (SQLException e) {
		    e.printStackTrace();
		} finally {
		    try {
		        connection.close();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
		return projects;
	}
	
	public static void insertNew(String judul) {
		final String INSERT_NEW = "INSERT INTO project (judul) VALUES (?);";
		Connection connection = DatabaseManager.getConnection();
		try {
		    PreparedStatement statement = connection.prepareStatement(INSERT_NEW);
		    statement.setString(1, judul);
		    statement.executeUpdate();
		    statement.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		} finally {
		    try {
		        connection.close();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
	}

	public static void updateProject(int id, String judul) {
		final String UPDATE_PROJECT = "UPDATE project SET judul = ? WHERE id = ?;";
		Connection connection = DatabaseManager.getConnection();
		try {
		    PreparedStatement statement = connection.prepareStatement(UPDATE_PROJECT);
		    statement.setString(1, judul);
		    statement.setInt(2, id);
		    statement.executeUpdate();
		    statement.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		} finally {
		    try {
		        connection.close();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
	}

	public void save() {
		Project.updateProject(id, judul);
	}

	public static void deleteProject(int id) {
		final String DELETE_PROJECT = "DELETE FROM project WHERE id = ?;";
		Connection connection = DatabaseManager.getConnection();
		try {
		    PreparedStatement statement = connection.prepareStatement(DELETE_PROJECT);
		    statement.setInt(1, id);
		    statement.executeUpdate();
		    statement.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		} finally {
		    try {
		        connection.close();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
	}
}
