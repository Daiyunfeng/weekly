<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table class="table">
	<tbody>
		<c:forEach var="i" items="${applicationScope.publications}" begin="0" end="${applicationScope.publicationsNumber}">
			<tr>
				<td>${i.content}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>