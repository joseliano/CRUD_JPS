<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Crud Teste</title>
</head>
<body>
	<center>
		<h1>Livraria</h1>
        <h2>
        	<a href="new">Novo</a>
        	&nbsp;&nbsp;&nbsp;
        	<a href="list">Listar</a>
        	
        </h2>
	</center>
    <div align="center">
        <table border="1" cellpadding="5">
            <caption><h2>Lista de Livros</h2></caption>
            <tr>
                <th>ID</th>
                <th>Titulo</th>
                <th>Autor</th>
                <th>Preço</th>
                <th>Ações</th>
            </tr>
            <c:forEach var="livro" items="${listLivro}">
                <tr>
                    <td><c:out value="${livro.id}" /></td>
                    <td><c:out value="${livro.titulo}" /></td>
                    <td><c:out value="${livro.autor}" /></td>
                    <td><c:out value="${livro.preco}" /></td>
                    <td>
                    	<a href="edit?id=<c:out value='${livro.id}' />">Editar</a>
                    	&nbsp;&nbsp;&nbsp;&nbsp;
                    	<a href="delete?id=<c:out value='${livro.id}' />">Excluir</a>                    	
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>	
</body>
</html>
