<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<link href="${pageContext.request.contextPath}/assets/css/main.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/font-awesome.min.css">
</head>
<!-- *********************************************** -->
<body>
	<!-- *********************************************** -->
	<section id="update" name="contact"></section>
	<div id="w">
		<div class="title"><h3>글 수정</h3></DIV>
		<form name='frm' method='POST' enctype="multipart/form-data"
			action='./update'>
			<input type="hidden" name="num" value="${dto.num}">
			<input type="hidden" name="oldfile" value="${dto.filename}">
			<input type="hidden" name="nowPage" value="${param.nowPage}">
			<input type="hidden" name="col" value="${param.col}">
			<input type="hidden" name="word" value="${param.word}">
			<TABLE class="alignCenter">
				<TR>
					<TH class="table-bordered th">글쓴이</TH>
					<TD class="table-bordered td"><input type="text" name="name" value="${dto.name}"></TD>
				</TR>
				<TR>
					<TH class="table-bordered th">제목</TH>
					<TD class="table-bordered td"><input type="text" name="subject" size="30" value="${dto.subject}"></TD>
				</TR>
				<TR>
					<TH class="table-bordered th">내용</TH>
					<TD class="table-bordered td"><textarea rows="10" cols="30" name="content">${dto.content}</textarea></TD>
				</TR>
				<TR>
					<TH class="table-bordered th">비밀번호</TH>
					<TD class="table-bordered td"><input type="password" name="passwd"></TD>
				</TR>
				<TR>
					<TH class="table-bordered th">파일</TH>
					<TD class="table-bordered td"><input type="file" name="fileMF">(${dto.filename})</TD>
				</TR>
			</TABLE>

			<DIV class='bottom'>
				<input type='submit' value='수정' class="button">  
				<input type='button' value='목록' class="button" onclick="location.href='./list'">
			</DIV>
		</FORM>
	</div>
	<!-- *********************************************** -->
</body>
<!-- *********************************************** -->
</html>

