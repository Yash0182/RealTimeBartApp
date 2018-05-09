<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@ page isELIgnored="false" %>
     <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Trip Details</title>
</head>
<body>
	<h3>Your Trip: ${trip} has fare: ${fare} dollars</h3>
	<h4>BART will Arrive:</h4>
	<ul>
		<c:forEach var="time" items="${timings}">
					<li>in ${time} minutes</li>
		</c:forEach>			
	</ul>
</body>
</html>