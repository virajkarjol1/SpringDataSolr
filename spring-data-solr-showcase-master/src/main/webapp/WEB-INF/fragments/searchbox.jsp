<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<form:form name="searchform" action="/search" method="GET">
	<img src="<c:url value="/resources/images/showcase-logo.png" />" height="50px" title="sping-data-solr-showcase"/> Search for: <input id="queryField" name="q" size="12" type="text" value="${query}">
	<script type="text/javascript">
    $(function() {
    	$( "#queryField" ).autocomplete({
    		source: '<c:url value="/autocomplete" />',
			minLength: 1,
    		select: function( event, ui ) {   
    			$( "#queryField" ).value=ui.item.value; 
    		    document.searchform.submit();  
    		}
    	});
    });
    </script>
    <select name="queryItem">
	  	<option value="name">Name</option>
	  	<option value="id">ID</option>
	  	<option value="cat">Category</option>
	</select>
	<div>
	<input type="checkbox" name="Id" value="Id">Id<br>
	<input type="checkbox" name="Name" value="Name">Name<br>
	<input type="checkbox" name="Price" value="Price">Price<br>
	<input type="checkbox" name="Popularity" value="Popularity">Popularity<br>
	</div>
	<br>
	<input type="submit" value="Search" />
</form:form>
