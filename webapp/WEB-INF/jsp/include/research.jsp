<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table class="table">
	<tbody>
		<c:forEach var="i" items="${applicationScope.researchs}" begin="0" end="${applicationScope.researchsNumber}">
			<tr>
				<td>${i.content}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>