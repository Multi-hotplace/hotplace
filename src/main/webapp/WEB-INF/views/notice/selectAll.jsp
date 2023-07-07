<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<link rel="stylesheet" href="../resources/css/notice/list.css">
<link rel="stylesheet" href="../resources/css/notice/button.css">
<script type="text/javascript"> 
// 	$(function(){
// 		 history.replaceState({}, null, location.pathname); 
// 	})

	// a 태그로 이동 시 post 전송
	function listView(searchKey, searchWord, page){
	    let f = document.createElement('form');
	    
	    let sKey;
	    sKey = document.createElement('input');
	    sKey.setAttribute('type', 'hidden');
	    sKey.setAttribute('name', 'searchKey');
	    sKey.setAttribute('value', searchKey);
	    
	    let sWord;
	    sWord = document.createElement('input');
	    sWord.setAttribute('type', 'hidden'); 
	    sWord.setAttribute('name', 'searchWord');
	    sWord.setAttribute('value', searchWord);
	    
	    let pg;
	    pg = document.createElement('input');
	    pg.setAttribute('type', 'hidden');
	    pg.setAttribute('name', 'page');
	    pg.setAttribute('value', page);
	    
	    f.appendChild(sKey);
	    f.appendChild(sWord);
	    f.appendChild(pg);
	    f.setAttribute('method', 'post'); // post 전송
	    f.setAttribute('action', 'searchList.do');
	    document.body.appendChild(f);
	    f.submit();
	}


  
</script> 
</head>
<body>
	<h1>공지사항</h1>

	<div class="not_body">
		<div class="not_header">
			<div class="not_title">제목</div>
			<div class="not_writer">작성자</div>
			<div class="not_wdate">작성일</div>
			<div class="not_vcount">조회수</div>
		</div>
		<div class="not_content">
			<c:forEach var="vo" items="${vos}"> 
				<fmt:parseDate var="dateFmt" value="${vo.wdate}"  pattern="yyyy-MM-dd HH:mm:ss.SSS" />
				<fmt:formatDate var="fmtwdate" value="${dateFmt}" pattern="yyyy-MM-dd" />
				
				<div class="not_selectOne" onclick="location.href='selectOne.do?num=${vo.num}'" style="cursor:pointer">
					<div class="not_content_title">${vo.title}</div>
					<div class="not_content_writer">${vo.writer}</div>
					<div class="not_content_wdate">${fmtwdate}</div>
					<div class="not_content_vcount">${vo.viewCount}</div>
				</div>
			</c:forEach>
		</div>
	</div>
	
	<div class="not_footer">
		<div class="not_search">
			<form action="searchList.do" method="post">
				<select name="searchKey">
					<option value="title" <c:if test="${searchKey == 'title'}"> selected </c:if>>제목</option>
					<option value="content" <c:if test="${searchKey =='content'}"> selected </c:if>>내용</option>
				</select>
				<input type="text" name="searchWord" id="searchWord" value="${searchWord}">
				<input type="hidden" name="page" value=1>
				<input type="submit" value="검색">
			</form>
		</div>
		
		<div class="change_page">
<%-- 				<a href="javascript:listView('${searchKey}', '${searchWord}', ${page-1})" id="pre_page">이전</a> --%>
<%-- 				<a class="next_page" href="javascript:listView('${searchKey}', '${searchWord}', ${page+1})" id="next_page">다음</a> --%>
<%-- 				<button onclick="listView('${searchKey}', '${searchWord}', ${page-1})" id="pre_page">이전</button> --%>
<%-- 				<button class="next_page" onclick="listView('${searchKey}', '${searchWord}', ${page+1})" id="next_page">다음</button> --%>
				<button id="pre_page">이전</button>
				<button class="next_page" id="next_page">다음</button>
				<button onclick="location.href='insert.do'" class="not_grade_button">작성</button>
<!-- 				<a href="insert.do">글작성</a> -->
		</div>
	</div>
	
	
	<script type="text/javascript">
		if(${page}==1){
			$('#pre_page').click(function(){
				alert('첫번째 페이지입니다.');
				return false;
			});
			$('#next_page').click(function(){
				listView('${searchKey}', '${searchWord}', ${page+1});
			});
		}
		else if((${page}*5) >= ${cnt}){
			$('#next_page').click(function(){
				alert('마지막 페이지입니다.');
				return false;
			});
			$('#pre_page').click(function(){
				listView('${searchKey}', '${searchWord}', ${page-1})
			});
		}
		else{
			$('#next_page').click(function(){
				listView('${searchKey}', '${searchWord}', ${page+1});
			});
			$('#pre_page').click(function(){
				listView('${searchKey}', '${searchWord}', ${page-1})
			});
		}
		
		if(${grade}==1){
			$('.not_grade_button').css("display", "block");
		}
	</script>
	
	
<!-- 		<div> -->
<!-- 			<ul style="list-style:none"> -->
<!-- 				<li> -->
<!-- 					<div style="display:table-cell"> -->
<!-- 						<span>제목</span> -->
<!-- 					</div> -->
<!-- 					<div style="display:table-cell"> -->
<!-- 						<span>작성자</span> -->
<!-- 					</div> -->
<!-- 					<div style="display:table-cell"> -->
<!-- 						<span>작성일</span> -->
<!-- 					</div> -->
<!-- 					<div style="display:table-cell"> -->
<!-- 						<span>조회수</span> -->
<!-- 					</div> -->
<!-- 				</li> -->
<!-- 			</ul> -->
<!-- 		</div> -->
	
</body>
</html>