<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row">
	<c:forEach var="i" items="${applicationScope.images}" begin="0" end="${applicationScope.imagesNumber}">
		<div style="width:430px; height:400px;margin-top:20px;margin-left:5%;display:inline-block;">
			<img class="photo" src="${i.image}" style="width: 100%; height: 75%;display:block;" />
			
			<p style="text-align:center; font-size:21px; color: #0000FF;">${i.title}</p>
			<p style="text-align:center; font-size:19px;">${i.description}</p>
		</div>
	</c:forEach>
</div>