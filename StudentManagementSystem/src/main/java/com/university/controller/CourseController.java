package com.university.controller;

import com.university.dao.CourseDAO;
import com.university.model.Course;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level; // Import Level
import java.util.logging.Logger; // Import Logger

@WebServlet(name = "CourseController", urlPatterns = {"/courses", "/course/*"})
public class CourseController extends HttpServlet {

    private CourseDAO courseDAO;
    // Add a logger
    private static final Logger LOGGER = Logger.getLogger(CourseController.class.getName());

    @Override
    public void init() {
        courseDAO = new CourseDAO();
    }

    // --- doGet and other methods remain the same ---
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        String action = pathInfo == null ? "/" : pathInfo; // Handle null pathInfo

        // Log entry into doGet
        LOGGER.log(Level.INFO, "doGet called with action: {0}", action);


        switch (action) {
            case "/new":
                showNewForm(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/view":
                viewCourse(request, response);
                break;
            case "/delete":
                deleteCourse(request, response); // Consider if delete should be POST/DELETE method
                break;
            case "/": // Default action is listing courses
            default: // Handle unrecognized paths or root path
                 if ("/".equals(action) || action.isEmpty()) {
                     listCourses(request, response);
                 } else {
                    LOGGER.log(Level.WARNING, "Unknown GET action requested: {0}", action);
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                 }
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        String action = pathInfo == null ? "/" : pathInfo; // Handle null pathInfo

        // Log entry into doPost
        LOGGER.log(Level.INFO, "doPost called with action: {0}", action);


        switch (action) {
            case "/add":
                addCourse(request, response);
                break;
            case "/update":
                updateCourse(request, response);
                break;
            // Usually POST is not used for viewing or listing, but handle defensively
            case "/view":
                 LOGGER.log(Level.WARNING, "Attempted POST to /view action.");
                 viewCourse(request, response); // Or redirect to GET view
                 break;
            case "/":
                 LOGGER.log(Level.WARNING, "Attempted POST to / action.");
                 listCourses(request, response); // Or send error
                 break;
            default:
                 LOGGER.log(Level.WARNING, "Unknown POST action requested: {0}", action);
                 response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

     private void listCourses(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Executing listCourses...");
        List<Course> courses = courseDAO.getAllCourses();
        request.setAttribute("courses", courses);
        request.getRequestDispatcher("/WEB-INF/views/courselist.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
         request.setAttribute("course", new Course());  
         request.setAttribute("formAction", "add");   
         request.getRequestDispatcher("/WEB-INF/views/courseform.jsp").forward(request, response);
     }


    // --- REVISED addCourse Method ---
    private void addCourse(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        LOGGER.info("Attempting to add course...");
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String creditsStr = request.getParameter("credits"); // Get as String first

        // Basic Validation (Example)
        if (code == null || code.trim().isEmpty() || name == null || name.trim().isEmpty() || creditsStr == null || creditsStr.trim().isEmpty()) {
             LOGGER.warning("Add course failed: Missing required fields.");
             // Redirect back with an error message - Use URL encoding for parameters
             response.sendRedirect(request.getContextPath() + "/course/new?error=Missing+required+fields");
             return; // Stop processing
         }


        try {
            int credits = Integer.parseInt(creditsStr.trim());

            // Additional validation (e.g., credits must be positive)
             if (credits < 0) {
                 LOGGER.warning("Add course failed: Credits cannot be negative.");
                 response.sendRedirect(request.getContextPath() + "/course/new?error=Credits+cannot+be+negative");
                 return;
             }

            Course course = new Course();
            course.setCode(code.trim());
            course.setName(name.trim());
            course.setDescription(description.trim());
            course.setCredits(credits);

            LOGGER.info("Calling courseDAO.addCourse for code: " + course.getCode());
            boolean success = courseDAO.addCourse(course); // <<< CAPTURE THE RESULT

            if (success) {
                LOGGER.info("Course added successfully (DAO returned true). Redirecting to /courses.");
                // Redirect to list page after successful add
                response.sendRedirect(request.getContextPath() + "/courses?message=Course+added+successfully"); // Optional success message
            } else {
                // DAO returned false, indicating a failure (likely SQLException logged by DAO)
                LOGGER.warning("Course add failed (DAO returned false). Check DAO logs for SQL errors.");
                // Redirect back to the form with a generic error message
                response.sendRedirect(request.getContextPath() + "/course/new?error=Failed+to+save+course.+Check+logs+or+data.");
            }

        } catch (NumberFormatException e) {
            // Log the error
            LOGGER.log(Level.WARNING, "Add course failed: Invalid format for credits value '" + creditsStr + "'", e);
             // Redirect back with specific error
            response.sendRedirect(request.getContextPath() + "/course/new?error=Invalid+format+for+credits");
        } catch (Exception e) {
            // Catch any other unexpected exceptions during DAO call or processing
            LOGGER.log(Level.SEVERE, "Unexpected error during add course processing", e);
             // Redirect back with generic error
            response.sendRedirect(request.getContextPath() + "/course/new?error=An+unexpected+error+occurred");
        }
    }
    // --- showEditForm, updateCourse, deleteCourse, viewCourse remain the same ---
     private void showEditForm(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
        LOGGER.info("Executing showEditForm...");
         try {
            int id = Integer.parseInt(request.getParameter("id"));
            Course course = courseDAO.getCourseById(id);
            if (course != null) {
                 request.setAttribute("course", course);
                 request.setAttribute("formAction", "update");  // Ensure formAction is 'update' when editing
                 request.getRequestDispatcher("/WEB-INF/views/courseform.jsp").forward(request, response);
            } else {
                LOGGER.warning("Course not found for editing with ID: " + id);
                response.sendRedirect(request.getContextPath() + "/courses?error=Course+not+found");
            }
         } catch (NumberFormatException e) {
              LOGGER.log(Level.WARNING, "Invalid ID format for editing: " + request.getParameter("id"), e);
              response.sendRedirect(request.getContextPath() + "/courses?error=Invalid+course+ID");
         }
     }

     private void updateCourse(HttpServletRequest request, HttpServletResponse response)
             throws IOException {
         LOGGER.info("Attempting to update course...");
         String idStr = request.getParameter("id");
         String code = request.getParameter("code");
         String name = request.getParameter("name");
         String description = request.getParameter("description");
         String creditsStr = request.getParameter("credits");

         LOGGER.info("Received parameters - id: [" + idStr + "], code: [" + code + "], name: [" + name + "], description: [" + description + "], credits: [" + creditsStr + "]");


         // Basic Validation
        if (idStr == null || idStr.trim().isEmpty() || code == null || code.trim().isEmpty() || name == null || name.trim().isEmpty() || creditsStr == null || creditsStr.trim().isEmpty()) {
             LOGGER.warning("Update course failed: Missing required fields.");
             // Consider redirecting back to edit form with error, requires passing ID back
              String redirectUrl = request.getContextPath() + "/courses?error=Update+failed+missing+fields"; // Default redirect if ID is missing
              if(idStr != null && !idStr.trim().isEmpty()){
                   // Try to redirect back to edit form if ID is available
                   redirectUrl = request.getContextPath() + "/course/edit?id=" + idStr + "&error=Missing+required+fields";
              }
              response.sendRedirect(redirectUrl);

             return; // Stop processing
         }

        try {
            int id = Integer.parseInt(idStr.trim());
            int credits = Integer.parseInt(creditsStr.trim());

            if (credits < 0) {
                 LOGGER.warning("Update course failed: Credits cannot be negative.");
                 response.sendRedirect(request.getContextPath() + "/course/edit?id=" + id + "&error=Credits+cannot+be+negative");
                 return;
             }

            Course course = new Course();
            course.setId(id); // Set ID for update
            course.setCode(code.trim());
            course.setName(name.trim());
            course.setDescription(description.trim());
            course.setCredits(credits);

            LOGGER.info("Calling courseDAO.updateCourse for ID: " + course.getId());
            boolean success = courseDAO.updateCourse(course); // Capture result

            if (success) {
                 LOGGER.info("Course updated successfully for ID: " + id);
                 response.sendRedirect(request.getContextPath() + "/courses?message=Course+updated+successfully");
             } else {
                LOGGER.warning("Course update failed (DAO returned false) for ID: " + id);
                 response.sendRedirect(request.getContextPath() + "/course/edit?id=" + id + "&error=Failed+to+update+course");
            }

        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Update course failed: Invalid format for ID or credits. ID='" + idStr + "', Credits='" + creditsStr + "'", e);
            String redirectUrl = request.getContextPath() + "/courses?error=Invalid+ID+or+credits+format";
             // Try to redirect back to edit form if ID seems valid
            try {
                if(idStr != null && !idStr.isEmpty()) {
                    Integer.parseInt(idStr.trim()); // Check if ID part is numeric
                    redirectUrl = request.getContextPath() + "/course/edit?id=" + idStr + "&error=Invalid+credits+format";
                }
            } catch (NumberFormatException idNfe) { /* ID was not numeric */ }
             response.sendRedirect(redirectUrl);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error during update course processing for ID: " + idStr, e);
            response.sendRedirect(request.getContextPath() + "/courses?error=An+unexpected+error+occurred+during+update");
        }
     }

     private void deleteCourse(HttpServletRequest request, HttpServletResponse response)
             throws IOException {
          LOGGER.info("Attempting to delete course...");
          String idStr = request.getParameter("id");
          LOGGER.info("Received parameter id: [" + idStr + "]");

          try {
             int id = Integer.parseInt(idStr.trim());
             LOGGER.info("Calling courseDAO.deleteCourse for ID: " + id);
             boolean success = courseDAO.deleteCourse(id);

             if (success) {
                 LOGGER.info("Course deleted successfully for ID: " + id);
                 response.sendRedirect(request.getContextPath() + "/courses?message=Course+deleted+successfully");
             } else {
                 LOGGER.warning("Course delete failed (DAO returned false) for ID: " + id);
                 response.sendRedirect(request.getContextPath() + "/courses?error=Failed+to+delete+course");
             }
          } catch (NumberFormatException e) {
                LOGGER.log(Level.WARNING, "Delete course failed: Invalid ID format '" + idStr + "'", e);
                 response.sendRedirect(request.getContextPath() + "/courses?error=Invalid+ID+format+for+delete");
          } catch (Exception e) {
             LOGGER.log(Level.SEVERE, "Unexpected error during delete course processing for ID: " + idStr, e);
             response.sendRedirect(request.getContextPath() + "/courses?error=An+unexpected+error+occurred+during+delete");
          }
     }

     private void viewCourse(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
         LOGGER.info("Executing viewCourse...");
         String idStr = request.getParameter("id");
         LOGGER.info("Received parameter id: [" + idStr + "]");

          try {
             int id = Integer.parseInt(idStr.trim());
             Course course = courseDAO.getCourseById(id);
             if (course != null) {
                 request.setAttribute("course", course);
                 request.getRequestDispatcher("/WEB-INF/views/courseview.jsp").forward(request, response);
             } else {
                 LOGGER.warning("Course not found for viewing with ID: " + id);
                 response.sendRedirect(request.getContextPath() + "/courses?error=Course+not+found");
             }
         } catch (NumberFormatException e) {
              LOGGER.log(Level.WARNING, "Invalid ID format for viewing: " + idStr, e);
              response.sendRedirect(request.getContextPath() + "/courses?error=Invalid+course+ID");
         } catch (Exception e) {
             LOGGER.log(Level.SEVERE, "Unexpected error during view course processing for ID: " + idStr, e);
             response.sendRedirect(request.getContextPath() + "/courses?error=An+unexpected+error+occurred+during+view");
         }
     }
}