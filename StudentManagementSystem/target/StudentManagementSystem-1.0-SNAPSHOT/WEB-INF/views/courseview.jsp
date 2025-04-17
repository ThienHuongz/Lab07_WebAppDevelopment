<%@ include file="header.jsp" %>

<h2>Course Details</h2>

<p><strong>ID:</strong> ${course.id}</p>
<p><strong>Code:</strong> ${course.code}</p>
<p><strong>Name:</strong> ${course.name}</p>
<p><strong>Credits:</strong> ${course.credits}</p>
<p><strong>Description:</strong> ${course.description}</p>

<a href="${pageContext.request.contextPath}/courses" class="btn">Back to Course List</a>

<%@ include file="footer.jsp" %>
