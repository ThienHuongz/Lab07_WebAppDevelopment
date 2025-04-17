package com.university.dao;
 import com.university.model.Student;
 import com.university.util.DBUtil;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.sql.Statement;
 import java.time.LocalDateTime;
 import java.util.ArrayList;
 import java.util.List;
public class StudentDAO {
    
    // Get all students
    public List<Student>getAllStudents(){
        List<Student> students =new ArrayList<>();
        String sql = "SELECT * FROM students";
        
        try(Connection conn =DBUtil.getConnection();
             Statement stmt =conn.createStatement();
             ResultSet rs =stmt.executeQuery(sql)){
            
            while (rs.next()){
                Student student =new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
                int courseId = rs.getInt("course_id");
                CourseDAO courseDAO = new CourseDAO();
                student.setCourse(courseDAO.getCourseById(courseId));
                // Convert SQL timestamp to LocalDateTime
                if(rs.getTimestamp("registration_date")!=null){
                    
                student.setRegistrationDate(rs.getTimestamp("registration_date").toLocalDateTime());
                }
                students.add(student);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        
        return students;
    }
    
    // Get student by ID
    public Student getStudentById(int id){
        String sql = "SELECT * FROM students WHERE id = ?";
        Student student = null;
        
        try(Connection conn =DBUtil.getConnection();
             PreparedStatement pstmt =conn.prepareStatement(sql)){
            
            pstmt.setInt(1,id);
            try(ResultSet rs =pstmt.executeQuery()){
                if(rs.next()){
                    student =new Student();
                    student.setId(rs.getInt("id"));
                    student.setName(rs.getString("name"));
                    student.setEmail(rs.getString("email"));
                    int courseId = rs.getInt("course_id");
                    CourseDAO courseDAO = new CourseDAO();
                    student.setCourse(courseDAO.getCourseById(courseId));
                    // Convert SQL timestamp to LocalDateTime
                    if(rs.getTimestamp("registration_date")!= null){
                        
                    student.setRegistrationDate(rs.getTimestamp("registration_date").toLocalDateTime());
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        
        return student;
    }
    
    // Add a new student
    public boolean addStudent(Student student){
        String sql = "INSERT INTO students (name, email, course_id) VALUES (?, ?, ?)";
        
        try(Connection conn =DBUtil.getConnection();
             PreparedStatement pstmt =conn.prepareStatement(sql)){
            
            pstmt.setString(1,student.getName());
            pstmt.setString(2,student.getEmail());
            pstmt.setInt(3, student.getCourse().getId());
            
            int affectedRows =pstmt.executeUpdate();
            return affectedRows >0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    
    // Update an existing student
    public boolean updateStudent(Student student){
        String sql = "UPDATE students SET name = ?, email = ?, course_id = ? WHERE id = ?";
        
        try(Connection conn =DBUtil.getConnection();
             PreparedStatement pstmt =conn.prepareStatement(sql)){
            
            pstmt.setString(1,student.getName());
            pstmt.setString(2,student.getEmail());
            pstmt.setInt(3, student.getCourse().getId());
            pstmt.setInt(4,student.getId());
            
            int affectedRows =pstmt.executeUpdate();
            return affectedRows >0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    
    // Delete a student
    public boolean deleteStudent(int id){
        String sql = "DELETE FROM students WHERE id = ?";
        
        try(Connection conn =DBUtil.getConnection();
             PreparedStatement pstmt =conn.prepareStatement(sql)){
            
            pstmt.setInt(1,id);
            
            int affectedRows =pstmt.executeUpdate();
            return affectedRows >0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
 }