<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row">
	<c:forEach var="i" items="${applicationScope.teachings}" begin="0" end="${applicationScope.teachingsNumber}">
		<div class="col-md-12 col-xs-12" style="margin:15px 0 0 40px">
			<p style="font-size:18px;">${i.content}</p>
		</div>
	</c:forEach>
</div>