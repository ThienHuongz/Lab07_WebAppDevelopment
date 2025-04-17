<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- Make sure JSTL core taglib is included --%>

<%--
    Determine the heading and action based on the 'formAction' attribute
    set by the CourseController servlet.
--%>
<c:choose>
    <c:when test="${formAction == 'update'}"> 
        <h2>Edit Course</h2>
    </c:when>
    <c:otherwise> 
        <h2>Add New Course</h2>
    </c:otherwise>
</c:choose>

<form action="${pageContext.request.contextPath}/course/${formAction}" method="post">

    <c:if test="${formAction == 'update'}">
        <input type="hidden" name="id" value="${course.id}" />
    </c:if>

    <%--
        The value attributes use Expression Language (EL).
        If 'course' is a new object (from showNewForm), course.code, course.name etc.
        will likely be null or default values (like 0 for int credits),
        which EL handles gracefully (outputs empty string or 0).
        If 'course' is an existing object (from showEditForm), the values will be populated.
    --%>
    <label for="code">Course Code:</label>
    <input type="text" id="code" name="code" value="${course.code}" required />

    <label for="name">Course Name:</label>
    <input type="text" id="name" name="name" value="${course.name}" required />

    <label for="description">Description:</label>
    <textarea id="description" name="description" required>${course.description}</textarea>

    <label for="credits">Credits:</label>
    <input type="number" id="credits" name="credits" value="${course.credits}" required min="0" /> <%-- Added min="0" for good practice --%>

    <button type="submit">Submit</button>
</form>

<%@ include file="footer.jsp" %>