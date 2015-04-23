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
