<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="util" uri="/ELFunctions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<link href="${pageContext.request.contextPath}/assets/css/main.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/font-awesome.min.css">

<script type="text/javascript">
	function read(num) {
		var url = "read";
		url += "?num=" + num;
		url += "&nowPage=${nowPage}";
		url += "&col=${col}";
		url += "&word=${word}";
		location.href = url;
	}
	function downFile(filename) {
		var url = "${pageContext.request.contextPath}/download";
		url += "?dir=/storage";
		url += "&filename=" + filename;
		location.href = url;
	}
</script>
</head>
<!-- *********************************************** -->
<body>

	<!-- *********************************************** -->
	<section id="list" name="contact"></section>
	<div id="w">
		<DIV class="title">
			<h3>게시판 목록</h3>
		</DIV>
		<FORM name='frm' method='POST' action='./list.do'>
			<SELECT name='col'>
				<option value="name"
				<c:if test="${col=='name' }">selected='selected'</c:if>
				>성명</option>
				<option value="subject"
				<c:if test="${col=='subject' }">selected='selected'</c:if>
				>제목</option>
				<option value="content"
				<c:if test="${col=='content' }">selected='selected'</c:if>
				>내용</option>
				<option value="subject_content"
				<c:if test="${col=='subject_content' }">selected='selected'</c:if>
				>제목+내용</option>
				<option value="total"
				<c:if test="${col=='total' }">selected='selected'</c:if>
				>전체출력</option>
			</SELECT> <input type="text" name="word" value="${word}"> <input
				type="submit" value="검색" class="button_mini">
		</FORM>
		<br>
		<TABLE>
			<TR class="tr_g">
				<TH class="table-bordered th">번호</TH>
				<TH class="table-bordered th">성명</TH>
				<TH class="table-bordered th">제목</TH>
				<TH class="table-bordered th">조회수</TH>
				<TH class="table-bordered th">등록날짜</TH>
				<TH class="table-bordered th">파일</TH>
			</TR>
			<c:forEach var="dto" items="${list}">
			<TR>
				<TD class="table-bordered td">${dto.num}</TD>
				<TD class="table-bordered td">${dto.name}</TD>
				<TD class="table-bordered td">
					<!-- 답변 처리  -->
					<c:forEach var="j" begin="1" end="${dto.indent }">
						&nbsp;&nbsp;
					</c:forEach>
					<c:if test="${dto.indent>0}">
						<img src="${pageContext.request.contextPath}/images/reply-back.png" width="5%">
					</c:if>
					<c:set var="rcount" value="${util:rcount(dto.num,rdao) }"/>
 					<a href="javascript:read('${dto.num}')">${dto.subject}</a>
 					<!-- new 이미지 -->
 					<c:if test="${rcount>0 }">
			        	<span style="color:red;">(${rcount})</span>
			        </c:if>
 					<c:if test="${util:newImg(fn:substring(dto.regdate,0,10)) }">
					<img src="${pageContext.request.contextPath}/images/new.gif">
					</c:if>
				
				</TD>
				<TD class="table-bordered td">${dto.count}</TD>
				<TD class="table-bordered td">${dto.regdate}</TD>
				<TD class="table-bordered td">
					<c:choose>
						<c:when test="${empty dto.filename }">
							파일없음
						</c:when>
						<c:otherwise>
							<a href="javascript:downFile('${dto.filename}')">${dto.filename}</a>
						</c:otherwise>
					</c:choose>
				</TD>
			</TR>
			</c:forEach>
		</TABLE>

	<DIV class='bottom'>
		<br>${paging}<br>
	<input type='button' value='등록' class='button' onclick="location.href='create'">
	</DIV>
	</div>
	<!-- *********************************************** -->
</body>
<!-- *********************************************** -->
</html>

