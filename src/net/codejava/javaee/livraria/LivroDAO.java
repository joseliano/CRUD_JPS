package net.codejava.javaee.livraria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * AbstractDAO.java
 * in the database.
 * @autor joseliano
 *
 */
public class LivroDAO {
	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection;
	
	public LivroDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
		this.jdbcURL = jdbcURL;
		this.jdbcUsername = jdbcUsername;
		this.jdbcPassword = jdbcPassword;
	}
	
	protected void connect() throws SQLException {
		if (jdbcConnection == null || jdbcConnection.isClosed()) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				throw new SQLException(e);
			}
			jdbcConnection = DriverManager.getConnection(
										jdbcURL, jdbcUsername, jdbcPassword);
		}
	}
	
	protected void disconnect() throws SQLException {
		if (jdbcConnection != null && !jdbcConnection.isClosed()) {
			jdbcConnection.close();
		}
	}
	
	public boolean insertLivro(Livro book) throws SQLException {
		String sql = "INSERT INTO livro (titulo, autor, preco) VALUES (?, ?, ?)";
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, book.getTitulo());
		statement.setString(2, book.getAutor());
		statement.setFloat(3, book.getPreco());
		
		boolean rowInserted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowInserted;
	}
	
	public List<Livro> listAllLivros() throws SQLException {
		List<Livro> listLivro = new ArrayList<>();
		
		String sql = "SELECT * FROM livro";
		
		connect();
		
		Statement statement = jdbcConnection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		while (resultSet.next()) {
			int id = resultSet.getInt("id");
			String titulo = resultSet.getString("titulo");
			String autor = resultSet.getString("autor");
			float preco = resultSet.getFloat("preco");
			
			Livro book = new Livro(id, titulo, autor, preco);
			listLivro.add(book);
		}
		
		resultSet.close();
		statement.close();
		
		disconnect();
		
		return listLivro;
	}
	
	public boolean deleteLivro(Livro book) throws SQLException {
		String sql = "DELETE FROM livro where id = ?";
		
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, book.getId());
		
		boolean rowDeleted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowDeleted;		
	}
	
	public boolean updateLivro(Livro book) throws SQLException {
		String sql = "UPDATE livro SET titulo = ?, autor = ?, preco = ?";
		sql += " WHERE id = ?";
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, book.getTitulo());
		statement.setString(2, book.getAutor());
		statement.setFloat(3, book.getPreco());
		statement.setInt(4, book.getId());
		
		boolean rowUpdated = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowUpdated;		
	}
	
	public Livro getLivro(int id) throws SQLException {
		Livro book = null;
		String sql = "SELECT * FROM livro WHERE id = ?";
		
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, id);
		
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			String titulo = resultSet.getString("titulo");
			String autor = resultSet.getString("autor");
			float preco = resultSet.getFloat("preco");
			
			book = new Livro(id, titulo, autor, preco);
		}
		
		resultSet.close();
		statement.close();
		
		return book;
	}
}
