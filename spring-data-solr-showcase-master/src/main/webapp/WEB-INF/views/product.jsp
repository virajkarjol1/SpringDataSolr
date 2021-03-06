<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false"%>
<html lang="us">
<head>
	<jsp:include page="../fragments/meta.jsp"></jsp:include>
	<script>
    $(function() {
        $( "#accordion" ).accordion();
    });
    </script>
</head>
<body>
	<jsp:include page="../fragments/searchbox.jsp"></jsp:include>
	<hr />
	<div style="margin-top: 5px; padding: 0 .7em;">
		<a href="javascript:history.back()">&lt;&lt; Back to List</a>
	</div>
	<br />	
	
	<div id="accordion">
		<h3>
			<c:forEach var="i" begin="1" end=""><span title="stars" class="ui-icon ui-icon-star" style="float:right">&nbsp;</span></c:forEach>
		</h3>
		<div>
			<p>	
				<c:forEach var="productResults" items="${productResult}">	
						${productResults} <br />	
				</c:forEach>
			</p>
		</div>
	</div>	
</body>