<%@ include file="header.jsp" %>

<c:choose>
    <c:when test="${student != null}">
        <h2>Edit Student</h2>
        <c:set var="formAction" value="${pageContext.request.contextPath}/student/update"/>
    </c:when>
    <c:otherwise>
        <h2>Add New Student</h2>
        <c:set var="formAction" value="${pageContext.request.contextPath}/student/add"/>
    </c:otherwise>
</c:choose>

<form action="${formAction}" method="post">
    <c:if test="${student != null}">
        <input type="hidden" name="id" value="${student.id}" />
    </c:if>

    <div class="form-group">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" value="${student.name}" required />
    </div>

    <div class="form-group">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" value="${student.email}" required />
    </div>

    <div class="form-group">
        <label for="course">Course:</label>
            <select name="courseId">
        <c:forEach var="course" items="${courseList}">
            <option value="${course.id}"
                <c:if test="${student.course != null && student.course.id == course.id}">selected</c:if>>
                ${course.name}
            </option>
        </c:forEach>
    </select><br>
    </div>

    <button type="submit" class="btn">Save</button>
    <a href="${pageContext.request.contextPath}/students" class="btn">Cancel</a>
</form>

<%@ include file="footer.jsp" %>
