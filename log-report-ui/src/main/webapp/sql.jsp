<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Code Report</title>
<link href="css/styles.css" rel="stylesheet" type="text/css" />
</head>
<body onload="setAction()">
	<div id="container" align="center">
		<div id="input">
			<table>
				<tr>
					<th colspan="6">GTD Sql Report (<a href="./code.html">Switch to GTD Code Report</a>)</th>
				</tr>
				<tr>
					<td>AUTHOR</td>
					<td><input type="text" id="author"></td>
					<td>DATE</td>
					<td><input type="text" id="date"></td>
					<td>REVISION_NUMBER</td>
					<td><input type="text" id="revisionNumber"></td>
					<td><button id="querySqlRecord">Query</button></td>
				</tr>
				<tr>
					<td>MODULE_NAME</td>
					<td><input type="text" id="moduleName"></td>
					<td>DB_USER</td>
					<td><input type="text" id="dbUser"></td>
					<td>TAG_NAME</td>
					<td><input type="text" id="tagName"></td>
				</tr>
			</table>
		</div>
		<div id="table"></div>
	</div>

	<script src="js/util.js" type="text/javascript"></script>
</body>
</html>