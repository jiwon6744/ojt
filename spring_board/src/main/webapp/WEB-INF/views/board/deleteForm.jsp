<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<link href="${pageContext.request.contextPath}/assets/css/main.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/css/bootstrap.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/font-awesome.min.css">
</head>
<body>
	<div id="w">
		<div class="title">
			<h3>비밀번호 입력</h3>
		</div>
		<div class="content">
			<c:choose>
				<c:when test="${flag }">
					답변이 있는 글이므로 삭제가 불가능합니다.<br>
					<br>
					<input type='button' value='다시 시도' class='button'
						onclick='history.back()'>
				</c:when>
				<c:otherwise>
					<FORM name='frm' method='POST' action='./delete'>
						<input type="hidden" name="num"	value="${param.num}">
						<input type="hidden" name="oldfile" value="${param.oldfile}">
						<input type="hidden" name="nowPage"	value="${param.nowPage}">
						<input type="hidden" name="col" value="${param.col}">
						<input type="hidden" name="word" value="${param.word}">
						<TABLE>
							<TR>
								<TH class="table-bordered th">비밀번호</TH>
								<TD class="table-bordered td"><input type="password"
									name="passwd"></TD>
							</TR>
						</TABLE>
						<DIV class='bottom'>
							<input type='submit' value='삭제' class="button"> <input
								type='button' value='목록' class="button"
								onclick="location.href='./list'">
						</DIV>
					</FORM>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>

