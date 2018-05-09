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
	<h2>Station Info</h2>
	
	<form action="/com.bart/station" >
 		<select name=source>
			<option value=""> -- StationInfo -- </option>
				<c:forEach var="stationlist" items="${stationList}">
							<option value= <c:out value="${stationlist.stationShortForm}"/> ><c:out value="${stationlist.stationName}"/></option>
				</c:forEach>
		</select>
		<input type="submit">
	</form>

</body>
</html>