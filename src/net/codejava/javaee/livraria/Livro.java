package net.codejava.javaee.livraria;

/**
 * Book.java
 * This is a model class represents a book entity
 * @autor joseliano
 *
 */
public class Livro {
	protected int id;
	protected String titulo;
	protected String autor;
	protected float preco;

	public Livro() {
	}

	public Livro(int id) {
		this.id = id;
	}

	public Livro(int id, String titulo, String autor, float preco) {
		this(titulo, autor, preco);
		this.id = id;
	}
	
	public Livro(String title, String autor, float preco) {
		this.titulo = title;
		this.autor = autor;
		this.preco = preco;
	}

	public int getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getAutor() {
		return autor;
	}

	public float getPreco() {
		return preco;
	}
 

}
