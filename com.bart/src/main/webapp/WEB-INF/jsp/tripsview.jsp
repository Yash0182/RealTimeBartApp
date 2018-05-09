<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page isELIgnored="false" %>
     <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h2>Trip Details</h2>
	
	<form action="/com.bart/trips" >
 		<select name=source>
			<option value=""> -- Source -- </option>
				<c:forEach var="stationlist" items="${stationList}">
							<option value="${stationlist}" ><c:out value="${stationlist.stationName}"/></option>
				</c:forEach>
		</select>
	
		
		<select name=destination>
			<option value=""> -- Destination -- </option>
				<c:forEach var="stationlist" items="${stationList}">
							<option value="${stationlist}"><c:out value="${stationlist.stationName}"/></option>
				</c:forEach>
		</select>
		<br/>
		<input type="submit" value="View Detail">
	</form>

</body>
</html>