<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set value="${pageContext.request.contextPath}" var="root" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<link href="${root}/assets/css/main.css" rel="stylesheet">
<link href="${root}/assets/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" href="${root}/assets/css/font-awesome.min.css">
<style type="text/css">
.rcreate {
	font-size: 20px;
	font-weight: bold;
	text-align: left;
	border-style: solid; /* 실선 */
	border-width: 1px; /* 선 두께 */
	border-color: #AAAAAA; /* 선 색깔 */
	color: #000000; /* 글자 색깔 */
	width: 35%; /* 화면의 30% */
	padding: 10px; /* 위 오른쪽 아래 왼쪽: 시간 방향 적용 */
	/* padding: 50px 10px;  50px: 위 아래, 10px: 좌우 */
	/* padding-top: 30px;  상단만 간격을 30px 지정   */
	margin: 20px auto; /* 가운데 정렬 기능, 20px: 위 아래, auto: 오른쪽 왼쪽*/
}

.rlist {
	line-height: 1.2em;
	font-size: 15px;
	font-weight: bold;
	text-align: left;
	border-style: solid; /* 실선 */
	border-width: 1px; /* 선 두께 */
	border-color: #AAAAAA; /* 선 색깔 */
	color: #000000; /* 글자 색깔 */
	width: 35%; /* 화면의 30% */
	padding: 10px; /* 위 오른쪽 아래 왼쪽: 시간 방향 적용 */
	/* padding: 50px 10px;  50px: 위 아래, 10px: 좌우 */
	/* padding-top: 30px;  상단만 간격을 30px 지정   */
	margin: 20px auto; /* 가운데 정렬 기능, 20px: 위 아래, auto: 오른쪽 왼쪽*/
}

hr {
	text-align: center;
	border-style: solid; /* 실선 */
	border-width: 1px; /* 선 두께 */
	border-color: #AAAAAA; /* 선 색깔 */
	width: 45%; /* 화면의 30% */
}
</style>
<script type="text/javascript">
	function blist() {
		var url = "list";
		url += "?nowPage=${param.nowPage}";
		url += "&col=${param.col}";
		url += "&word=${param.word}";
		location.href = url;
	}
	function bupdate(num) {
		var url = "update";
		url += "?num=" + num;
		url += "&nowPage=${param.nowPage}";
		url += "&col=${param.col}";
		url += "&word=${param.word}";
		location.href = url;
	}
	function bdelete(num) {
		var url = "delete";
		url += "?num=" + num;
		url += "&oldfile=${dto.filename}";
		url += "&nowPage=${param.nowPage}";
		url += "&col=${param.col}";
		url += "&word=${param.word}";
		location.href = url;
	}
	function breply(num) {
		var url = "reply";
		url += "?num=" + num;
		url += "&nowPage=${param.nowPage}";
		url += "&col=${param.col}";
		url += "&word=${param.word}";
		location.href = url;
	}
	function downFile(filename) {
		var url = "${root}/download";
		url += "?dir=/storage";
		url += "&filename=" + filename;
		location.href = url;
	}
	//댓글관련자바스크립트 시작
	function rupdate(rnum, rcontent) {
		var f = document.rform;
		f.content.value = rcontent;
		f.rnum.value = rnum;
		f.rsubmit.value = "수정";
		f.action = "./rupdate"
	}
	function rdelete(rnum) {
		if (confirm("정말 삭제 하겠습니까?")) {
			var url = "./rdelete";
			url = url + "?rnum=" + rnum;
			url = url + "&num=${dto.num}";
			url = url + "&nowPage=${param.nowPage}";
			url = url + "&nPage=${nPage}";
			url = url + "&col=${param.col}";
			url = url + "&word=${param.word}";
			location.href = url;
		}
	}
	//댓글관련자바스크립트 끝
</script>
</head>
<!-- *********************************************** -->
<body>
	<!-- *********************************************** -->
	<section id="read" name="contact"></section>
	<div id="w">
		<DIV class="title">
			<h3>내용보기</h3>
		</DIV>
		<TABLE>
			<TR>
				<TH class="table-bordered th">글쓴이</TH>
				<TD class="table-bordered td">${dto.name}</TD>
			</TR>
			<TR>
				<TH class="table-bordered th">제목</TH>
				<TD class="table-bordered td">${dto.subject}</TD>
			</TR>
			<TR>
				<TH class="table-bordered th">내용</TH>
				<TD class="table-bordered td">${dto.content}</TD>
			</TR>
			<TR>
				<TH class="table-bordered th">조회수</TH>
				<TD class="table-bordered td">${dto.count}</TD>
			</TR>
			<TR>
				<TH class="table-bordered th">파일명</TH>
				<td class="table-bordered td"><c:choose>
						<c:when test="${empty dto.filename }">
					파일없음
					</c:when>
						<c:otherwise>
							<a href="javascript:downFile('${dto.filename}')">${dto.filename}</a>
						</c:otherwise>
					</c:choose></td>
			</TR>
		</TABLE>

		<DIV class='bottom'>
			<input type='button' value='목록' onclick="blist()" class="button">
			<input type='button' value='수정' onclick="bupdate('${dto.num}')"
				class="button"> <input type='button' value='답변'
				onclick="breply('${dto.num}')" class="button"> <input
				type='button' value='삭제' onclick="bdelete('${dto.num}')"
				class="button">
		</DIV>

		<c:forEach var="rdto" items="${rlist}">
			<div class="rlist">
				${rdto.id}<br />
				<p>${rdto.content}</p>
				${rdto.regdate} <span style="float: right;"> <a
					href="javascript:rupdate('${rdto.rnum}','${rdto.content }')">
						수정</a>|<a href="javascript:rdelete('${rdto.rnum}')">삭제</a>
				</span>
			</div>
		</c:forEach>
		<div class="rcreate">
			<form name="rform" action="./rcreate" method="post"
				onsubmit="return input(this)">
				<textarea rows="3" cols="28" name="content" onclick="rcheck(this)"></textarea>
				</br> <input type="submit" name="rsubmit" class="button_mini" value="등록">
				<input type="hidden" name="num" value="${dto.num}"> <input
					type="hidden" name="id" value="admin"> <input
					type="hidden" name="nowPage" value="${param.nowPage}"> <input
					type="hidden" name="nPage" value="${nPage}"> <input
					type="hidden" name="col" value="${param.col}"> <input
					type="hidden" name="word" value="${param.word}"> <input
					type="hidden" name="rnum" value="0">
			</form>
		</div>
		<div class="bottom">${paging}</div>
		<!-- *********************************************** -->
	</div>
</body>
<!-- *********************************************** -->
</html>

