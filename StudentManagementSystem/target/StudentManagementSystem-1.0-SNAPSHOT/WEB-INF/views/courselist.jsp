<%@ include file="header.jsp" %>

<h2>Course List</h2>

<c:if test="${empty courses}">
    <p>No courses found.</p>
</c:if>

<c:if test="${not empty courses}">
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Code</th>
                <th>Name</th>
                <th>Credits</th>
                <th>Description</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="course" items="${courses}">
                <tr>
                    <td>${course.id}</td>
                    <td>${course.code}</td> <!-- Display course code -->
                    <td>${course.name}</td>
                    <td>${course.credits}</td>
                    <td>${course.description}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/course/view?id=${course.id}" class="btn">View</a>
                        <a href="${pageContext.request.contextPath}/course/edit?id=${course.id}" class="btn">Edit</a>
                        <a href="${pageContext.request.contextPath}/course/delete?id=${course.id}" class="btn"
                           onclick="return confirm('Are you sure you want to delete this course?')">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>

<a href="${pageContext.request.contextPath}/course/new" class="btn">Add New Course</a>

<%@ include file="footer.jsp" %>
