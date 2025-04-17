<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Management System</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
        }
        .container {
            width: 80%;
            margin: 0 auto;
            padding: 20px;
        }
        header {
            background: #333;
            color: #fff;
            padding: 1rem;
            text-align: center;
        }
        .nav {
            background: #444;
            color: #fff;
            padding: 0.5rem;
        }
        .nav a {
            color: #fff;
            text-decoration: none;
            padding: 0.5rem 1rem;
            display: inline-block;
        }
        .nav a:hover {
            background: #555;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 1rem 0;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 0.7rem;
            text-align: left;
        }
        th {
            background: #333;
            color: #fff;
        }
        tr:nth-child(even) {
            background: #f2f2f2;
        }
        .btn {
            display: inline-block;
            background: #333;
            color: #fff;
            border: none;
            padding: 0.5rem 1rem;
            margin: 0.5rem 0;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            font-size: 1rem;
        }
        .btn:hover {
            background: #555;
            text-decoration: none;
        }
        .form-group {
            margin-bottom: 1rem;
        }
        label {
            display: block;
            margin-bottom: 0.5rem;
        }
        input[type="text"],
        input[type="email"],
        select {
            width: 100%;
            padding: 0.5rem;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
    </style>
</head>
<body>
    <header>
        <h1>Student Management System</h1>
    </header>
    <div class="nav">
        <a href="${pageContext.request.contextPath}/students">All Students</a>
        <a href="${pageContext.request.contextPath}/student/new">Add Student</a>
        <a href="${pageContext.request.contextPath}/course/new">Add Course</a>
        <a href="${pageContext.request.contextPath}/courses">All Courses</a>
        
        
    </div>
    <div class="container">
