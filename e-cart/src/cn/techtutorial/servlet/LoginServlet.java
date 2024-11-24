package cn.techtutorial.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import cn.techtutorial.connection.DbCon;
import cn.techtutorial.dao.UserDao;
import cn.techtutorial.model.User;

@WebServlet("/user-login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // Retrieve email and password from the request
            String email = request.getParameter("login-email");
            String password = request.getParameter("login-password");

            // Create a UserDao instance and call the userLogin method
            UserDao udao = new UserDao(DbCon.getConnection());
            User user = udao.userLogin(email, password);

            if (user != null) {
                // Set the user as a session attribute and redirect to index.jsp
                request.getSession().setAttribute("auth", user);
                response.sendRedirect("index.jsp");
            } else {
                // Respond with a simple error message
                out.println("Invalid email or password. Please try again.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
