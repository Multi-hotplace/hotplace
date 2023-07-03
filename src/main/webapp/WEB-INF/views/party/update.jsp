<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항</title>
<link rel="stylesheet" href="../resources/css/notice/button.css">
</head>
<body>
	<h1>공지사항</h1>
<%-- 	<jsp:include page="../top_menujm.jsp"></jsp:include> --%>
	<form action="updateOK.do">
	<input type="hidden" name="num" value="${param.num}">
	<fmt:parseDate var="dateFmt" value="${vo2.wdate}"  pattern="yyyy-MM-dd HH:mm:ss.SSS" />
	<fmt:formatDate var="fmtwdate" value="${dateFmt}" pattern="yyyy-MM-dd HH:mm" />
	<div>
		<div>
			<input type="text" name="title" id="title" value="${vo2.title}" style="border:0 solid black; outline: none;">
			<div>
				<span style="font-size:10px">${vo2.writer}</span>
				<span style="font-size:10px">${fmtwdate}</span>
				<span style="font-size:10px">조회 ${vo2.viewCount}</span>
			</div>
		</div>
		<hr>
		<div>
			<textarea name="content" rows="10" cols="22"style="border:0 solid black; outline: none">${vo2.content}</textarea>
		</div>
		
		
		<div>
			<input type="submit" value="작성" class="myButton">
		</div>
	</div>
	</form>
	
</body>
</html>