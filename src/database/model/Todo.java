package database.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import database.DatabaseManager;

public class Todo {
	private int id;
	private int completed;
	private String judul;
	private Date deadline;
	private int idProject;

	public Todo(int id, int completed, String judul, Date deadline, int idProject) {
			this.id = id;
			this.completed = completed;
			this.judul = judul;
			this.deadline = deadline;
			this.idProject = idProject;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCompleted() {
		return completed;
	}
	public void setCompleted(int completed) {
		this.completed = completed;
	}
	public String getJudul() {
		return judul;
	}
	public void setJudul(String judul) {
		this.judul = judul;
	}
	public int getIdProject() {
		return idProject;
	}
	public Date getDeadline() {
			return deadline;
	}
	public void setDeadline(Date deadline) {
			this.deadline = deadline;
	}
	public void setIdProject(int idProject) {
		this.idProject = idProject;
	}

	public boolean isCompleted() {
		return completed == 1;
	}

	public static void insertNew(int completed, String judul, Date deadline, int idProject) {
			final String INSERT_NEW = "INSERT INTO todo (completed, judul, deadline, id_project) VALUES (?, ?, ?, ?);";
			Connection connection = DatabaseManager.getConnection();
			try {
					PreparedStatement statement = connection.prepareStatement(INSERT_NEW);
					statement.setInt(1, completed);
					statement.setString(2, judul);
					statement.setTimestamp(3, new java.sql.Timestamp(deadline.getTime()));
					statement.setInt(4, idProject);
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

	public static void deleteTodo(int id) {
		final String DELETE_TODO = "DELETE FROM todo WHERE id = ?;";
		Connection connection = DatabaseManager.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(DELETE_TODO);
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

	public static void updateTodo(int id, int completed, Date deadline) {
		final String UPDATE_TODO = "UPDATE todo SET completed = ?, deadline = ? WHERE id = ?;";
		Connection connection = DatabaseManager.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(UPDATE_TODO);
			statement.setInt(1, completed);
			statement.setTimestamp(2, new java.sql.Timestamp(deadline.getTime()));
			statement.setInt(3, id);
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
		Todo.updateTodo(id, completed, deadline);
	}

	public static List<Todo> getAllTodo() {
		final String GET_ALL_TODO = "SELECT * FROM todo;";
		Connection connection = DatabaseManager.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(GET_ALL_TODO);
			ResultSet resultSet = statement.executeQuery();
			List<Todo> todos = new ArrayList<>();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				int completed = resultSet.getInt("completed");
				String judul = resultSet.getString("judul");
				int idProject = resultSet.getInt("id_project");
				Date deadline = resultSet.getTimestamp("deadline");
				Todo todo = new Todo(id, completed, judul, deadline, idProject);
				todos.add(todo);
			}
			resultSet.close();
			statement.close();
			return todos;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static List<Todo> getAllTodosInProject(int idProject) {
			final String GET_ALL_TODOS_IN_PROJECT = "SELECT * FROM todo WHERE id_project = ?;";
			Connection connection = DatabaseManager.getConnection();
			try {
				PreparedStatement statement = connection.prepareStatement(GET_ALL_TODOS_IN_PROJECT);
				statement.setInt(1, idProject);
				ResultSet resultSet = statement.executeQuery();
				List<Todo> todos = new ArrayList<>();
				while (resultSet.next()) {
					int id = resultSet.getInt("id");
					int completed = resultSet.getInt("completed");
					String judul = resultSet.getString("judul");
					Date deadline = resultSet.getTimestamp("deadline");
					Todo todo = new Todo(id, completed, judul, deadline, idProject);
					todos.add(todo);
				}
				resultSet.close();
				statement.close();
				return todos;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
		}
	}
}