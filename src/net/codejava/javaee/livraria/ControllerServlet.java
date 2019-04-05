package net.codejava.javaee.livraria;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ControllerServlet.java
 * @autor joseliano
 */
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LivroDAO livroDAO;

	public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");

		livroDAO = new LivroDAO(jdbcURL, jdbcUsername, jdbcPassword);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertLivro(request, response);
				break;
			case "/delete":
				deleteLivro(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateLivro(request, response);
				break;
			default:
				listLivro(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void listLivro(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Livro> listLivro = livroDAO.listAllLivros();
		request.setAttribute("listLivro", listLivro);
		RequestDispatcher dispatcher = request.getRequestDispatcher("LvrLista.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("LivroForm.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Livro existingLivro = livroDAO.getLivro(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("LivroForm.jsp");
		request.setAttribute("livro", existingLivro);
		dispatcher.forward(request, response);

	}

	private void insertLivro(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		String titulo = request.getParameter("titulo");
		String autor = request.getParameter("autor");
		float preco = Float.parseFloat(request.getParameter("preco"));

		Livro newLivro = new Livro(titulo, autor, preco);
		livroDAO.insertLivro(newLivro);
		response.sendRedirect("list");
	}

	private void updateLivro(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String titulo = request.getParameter("titulo");
		String autor = request.getParameter("autor");
		float preco = Float.parseFloat(request.getParameter("preco"));

		Livro livro = new Livro(id, titulo, autor, preco);
		livroDAO.updateLivro(livro);
		response.sendRedirect("list");
	}

	private void deleteLivro(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		Livro livro = new Livro(id);
		livroDAO.deleteLivro(livro);
		response.sendRedirect("list");

	}

}
