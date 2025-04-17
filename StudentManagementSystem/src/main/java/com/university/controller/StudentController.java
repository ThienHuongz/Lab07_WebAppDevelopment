 package com.university.controller;
 import com.university.dao.StudentDAO;
 import com.university.dao.CourseDAO;
 import com.university.model.Student;
 import com.university.model.Course;
 import jakarta.servlet.ServletException;
 import jakarta.servlet.annotation.WebServlet;
 import jakarta.servlet.http.HttpServlet;
 import jakarta.servlet.http.HttpServletRequest;
 import jakarta.servlet.http.HttpServletResponse;
 import java.io.IOException;
 import java.util.List;
 @WebServlet(name ="StudentController",urlPatterns ={"/students",
 "/student/*"})
 public class StudentController extends HttpServlet {
    
    private StudentDAO studentDAO;
    @Override
    public void init(){
        studentDAO = new StudentDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse 
response)
            throws ServletException,IOException {
        
        String pathInfo =request.getPathInfo();
        String action =request.getParameter("action");
        
        if(pathInfo ==null &&action ==null){
            // List all students
            listStudents(request,response);
        }else if(pathInfo !=null &&pathInfo.equals("/new")) {
            // Show form to add a new student
            showNewForm(request,response);
        }else if(pathInfo !=null &&pathInfo.equals("/edit")){
            // Show form to edit an existing student
            showEditForm(request,response);
        }else if(pathInfo !=null &&pathInfo.equals("/delete")){
            // Delete a student
            deleteStudent(request,response);
        }else if(pathInfo !=null &&pathInfo.equals("/view")){
            // View a specific student
            viewStudent(request,response);
        }else{
            // Default -show all students
            listStudents(request,response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse 
response)
            throws ServletException,IOException {
        
        String pathInfo =request.getPathInfo();
        
        if(pathInfo !=null &&pathInfo.equals("/add")){
            // Add a new student
            addStudent(request,response);
        }else if(pathInfo !=null &&pathInfo.equals("/update")){
            // Update an existing student
            updateStudent(request,response);
        }else{
            // Default -show all students
            listStudents(request,response);
        }
    }
    
    private void listStudents(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException {
        List<Student>students =studentDAO.getAllStudents();
        request.setAttribute("students",students);
        request.getRequestDispatcher("/WEB-INF/views/studentlist.jsp").forward(request,response);
    }
    
    private void viewStudent(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException {
        int id =Integer.parseInt(request.getParameter("id"));
        Student student =studentDAO.getStudentById(id);
        request.setAttribute("student",student);
        request.getRequestDispatcher("/WEB-INF/views/studentview.jsp").forward(request,response);
    }
    
    private void showNewForm(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException {
        CourseDAO courseDAO = new CourseDAO();
        List<Course> courseList = courseDAO.getAllCourses();
        request.setAttribute("courseList", courseList);
        request.getRequestDispatcher("/WEB-INF/views/studentform.jsp").forward(request,response);
    }
    
    private void showEditForm(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException {
        int id =Integer.parseInt(request.getParameter("id"));
        Student student =studentDAO.getStudentById(id);
        request.setAttribute("student",student);
        request.getRequestDispatcher("/WEB-INF/views/studentform.jsp").forward(request,response);
    }
    
    private void addStudent(HttpServletRequest request,HttpServletResponse 
response)
            throws IOException {
        String name =request.getParameter("name");
        String email =request.getParameter("email");
        
        Student newStudent =new Student();
        newStudent.setName(name);
        newStudent.setEmail(email);
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        CourseDAO courseDAO = new CourseDAO();
        Course course = courseDAO.getCourseById(courseId);
        newStudent.setCourse(course);
        
        studentDAO.addStudent(newStudent);
        response.sendRedirect(request.getContextPath()+"/students");
    }
    
    private void updateStudent(HttpServletRequest request,
 HttpServletResponse response)
            throws IOException {
        int id =Integer.parseInt(request.getParameter("id"));
        String name =request.getParameter("name");
        String email =request.getParameter("email");
        
        Student student =new Student();
        student.setId(id);
        student.setName(name);
        student.setEmail(email);
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        CourseDAO courseDAO = new CourseDAO();
        Course course = courseDAO.getCourseById(courseId);
        student.setCourse(course);
        
        studentDAO.updateStudent(student);
        response.sendRedirect(request.getContextPath()+"/students");
    }
    
    private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id =Integer.parseInt(request.getParameter("id"));
        studentDAO.deleteStudent(id);
        response.sendRedirect(request.getContextPath()+"/students");
    }
 }