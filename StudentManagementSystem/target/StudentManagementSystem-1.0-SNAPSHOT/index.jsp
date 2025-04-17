<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html>
 <html>
 <head>
    <meta charset="UTF-8">
    <title>Student Management System</title>
    <style>
        .hero {
            background: #333;
            color: #fff;
            padding: 3rem 0; /* Fix: added space between 3rem and 0 */
            text-align: center;
        }

        .hero p {
            font-size: 1.2rem;
            max-width: 800px;
            margin: 0 auto; /* Fix: added space between 0 and auto */
        }

        .container {
            width: 80%;
            margin: 2rem auto; /* Fix: added space between values */
            flex: 1;
        }

        .feature-box {
            background: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* Fix: fixed shadow syntax */
            padding: 1.5rem;
            width: 300px;
            text-align: center;
        }

        .btn {
            display: inline-block;
            background: #333;
            color: #fff;
            border: none;
            padding: 0.7rem 1.5rem; 
            margin-top: 1rem;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            font-size: 1rem;
            transition: background 0.3s;
        }

    </style>
 </head>
 <body>
    <div class="hero">
        <h1>Student Management System</h1>
        <p>A comprehensive web application for managing student information using JSP, Servlets, MVC architecture, and MySQL database.</p>
    </div>
    
    <div class="container">
        <div class="features">
            <div class="feature-box">
                <h3>View Students</h3>
                <p>Access and view all students currently in the system.</p>
                <a href="${pageContext.request.contextPath}/students"
 class="btn">View All Students</a>
            </div>
            
            <div class="feature-box">
                <h3>Add New Student</h3>
                <p>Register a new student with their details and course 
information.</p>
                <a href="${pageContext.request.contextPath}/student/new"
 class="btn">Add Student</a>
                
            </div>
            
            <div class="feature-box">
                <h3>Manage Records</h3>
                <p>Edit and update existing student records or remove outdated entries.</p>
                <a href="${pageContext.request.contextPath}/students" class="btn">Manage Students</a>
            </div>
            
            <div class="feature-box">
                <h3>View Courses</h3>
                <p>Edit and update existing courses records or remove outdated entries.</p>
                <a href="${pageContext.request.contextPath}/courses" class="btn">Manage Courses</a>
            </div>
        </div>
    </div>
    
    <footer>
        &copy; 2025 Student Management System
    </footer>
 </body>
 </html>
