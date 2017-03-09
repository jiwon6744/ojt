<%@ page contentType="text/html; charset=UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	String root = request.getContextPath();
%>

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
	<section id="create" name="contact"></section>
	<div id="w">
		<div class="title"><h3>글쓰기</h3></DIV>
		<form name='frm' method='POST' enctype="multipart/form-data"
			action='./create'>
			<TABLE class="table_width_40">
				<TR>
					<TH class="table-bordered th">글쓴이</TH>
					<TD class="table-bordered td"><input type="text" name="name"></TD>
				</TR>
				<TR>
					<TH class="table-bordered th">제목</TH>
					<TD class="table-bordered td"><input type="text" name="subject" size="30"></TD>
				</TR>
				<TR>
					<TH class="table-bordered th">내용</TH>
					<TD class="table-bordered td"><textarea rows="10" cols="30" name="content"></textarea></TD>
				</TR>
				<TR>
					<TH class="table-bordered th">비밀번호</TH>
					<TD class="table-bordered td"><input type="password" name="passwd"></TD>
				</TR>
				<TR>
					<TH class="table-bordered th">파일</TH>
					<TD class="table-bordered td"><input type="file" name="fileMF"></TD>
				</TR>
			</TABLE>

			<DIV class='bottom'>
				<input type='submit' value='등록' class="button">  
				<input type='button' value='목록' class="button" onclick="location.href='./list'">
			</DIV>
		</FORM>
	</div>
	<!-- *********************************************** -->
</body>
<!-- *********************************************** -->
</html>

