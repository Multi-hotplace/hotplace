<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>selectAll</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script type="text/javascript">

$(function(){
	console.log('onload...');
})

</script>
</head>
<body>
	<h1>매장목록</h1>
	
	<div style="padding:5px">
		<form action="searchList.do">
			<select name="searchKey" id="searchKey">
				<option value="name" ${param.searchKey == 'name' ? 'selected' : ''}>name</option>
				<option value="cate" ${param.searchKey == 'cate' ? 'selected' : ''}>cate</option>
			</select>
			<input type="text" name="searchWord" id="searchWord" value="${param.searchWord}">
			<input type="hidden" name="pageNum" id="pageNum" value=1>
			<input type="submit" value="검색">
		</form>
	</div>

	<table id="shopList">
	<thead>
		<tr>
			<th>num</th>
			<th>name</th>
			<th>cate</th>
			<th>tel</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="vo" items="${vos}">
			<tr>
				<td><a href="selectOne.do?num=${vo.num}">${vo.num}</a></td>
				<td>${vo.name}</td>
				<td>${vo.cate}</td>
				<td>${vo.tel}</td>
			</tr>
		</c:forEach>
	</tbody>
	<tfoot></tfoot>
	</table>
	
	<div>
		<a href="selectAll.do?searchKey=${param.searchKey}&searchWord=${param.searchWord}&pageNum=${param.pageNum-1}" id="pre_page">이전</a>
		<a href="selectAll.do?searchKey=${param.searchKey}&searchWord=${param.searchWord}&pageNum=${param.pageNum+1}" id="next_page">다음</a>
	</div>
	<script type="text/javascript">
		if(${param.pageNum}==1){
 			$('#pre_page').hide();
		}
		if(${cnt}!=10){
			$('#next_page').hide();
		}
</script>
	
</body>
</html>