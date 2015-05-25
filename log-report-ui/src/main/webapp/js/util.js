function getRequestObject() {
	if (window.XMLHttpRequest) {
		return (new XMLHttpRequest());
	} else if (window.ActiveXObject) {
		return (new ActiveXObject("Microsoft.XMLHTTP"));
	} else {
		// Don't throw Error: this part is for very old browsers,
		// and Error was implemented starting in JavaScript 1.5.
		return (null);
	}
}

function ajaxAlert(address) {
	var request = getRequestObject();
	request.onreadystatechange = function() {
		showResponseAlert(request);
	};
	request.open("GET", address, true);
	request.send(null);
}

function showResponseAlert(request) {
	if ((request.readyState == 4) && (request.status == 200)) {
		alert(request.responseText);
	}
}

function ajaxResult(address, resultRegion) {
	var request = getRequestObject();
	request.onreadystatechange = function() {
		showResponseText(request, resultRegion);
	};
	request.open("GET", address, true);
	request.send(null);
}

function showResponseText(request, resultRegion) {
	if ((request.readyState == 4) && (request.status == 200)) {
		htmlInsert(resultRegion, request.responseText);
	}
}

function htmlInsert(id, htmlData) {
	document.getElementById(id).innerHTML = htmlData;
}

function ajaxPost(address, data, responseHandler) {
	var request = getRequestObject();
	request.onreadystatechange = function() {
		responseHandler(request);
	};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type",
			"application/x-www-form-urlencoded");
	request.send(data);
}

function setAction() {
	document.getElementById("queryCodeRecord").onclick = queryCodeRecord();
	document.getElementById("querySqlRecord").onclick = querySqlRecord();
}

function queryCodeRecord() {
	var codeReport = getCodeQueryCondition();
	
	document.getElementById("table").innerHTML = "<table><tr><td>" + codeReport.author + "</td></tr></table>";
}

function querySqlRecord() {
	var sqlReport = getSqlQueryCondition();
	
	document.getElementById("table").innerHTML = "<table><tr><td>123</td></tr></table>";
}

function getCodeQueryCondition() {
	var author = document.getElementById("author").innerHTML;
	var date = document.getElementById("date").innerHTML;
	var revisionNumber = document.getElementById("revisionNumber").innerHTML;
	var generateFlag = document.getElementById("generateFlag").innerHTML;
	var moduleName = document.getElementById("moduleName").innerHTML;
	var tagName = document.getElementById("tagName").innerHTML;
	
	var codeReport = new CodeReport(author, date, revisionNumber, generateFlag, moduleName, tagName);
	
	return codeReport;
}

function getSqlQueryCondition() {
	var author = document.getElementById("author").innerHTML;
	var date = document.getElementById("date").innerHTML;
	var revisionNumber = document.getElementById("revisionNumber").innerHTML;
	var moduleName = document.getElementById("moduleName").innerHTML;
	var generateFlag = document.getElementById("dbUser").innerHTML;
	var tagName = document.getElementById("tagName").innerHTML;
	
	var sqlReport = new SqlReport(author, date, revisionNumber, generateFlag, moduleName, tagName);
	
	return sqlReport;
}

function CodeReport(author, date, revisionNumber, generateFlag, moduleName, tagName) {
	this.author = author;
	this.date = date;
	this.revisionNumber = revisionNumber;
	this.generateFlag = generateFlag;
	this.moduleName = moduleName;
	this.tagName = tagName;
}

function SqlReport(author, date, revisionNumber, moduleName, dbUser, tagName) {
	this.author = author;
	this.date = date;
	this.revisionNumber = revisionNumber;
	this.moduleName = moduleName;
	this.dbUser = dbUser;
	this.tagName = tagName;
}
