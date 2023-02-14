package com.uniquedeveloper.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class contacts
 */
@WebServlet("/contacts")
public class contacts extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String fname = request.getParameter("name");
		String email = request.getParameter("email");
		String phoneno=  request.getParameter("phone");
	    String msg = request.getParameter("message");
	   RequestDispatcher dispatcher = null;
	   Connection con=null; 

	   try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/visitors?useSSL=false", "root", "1234");
			PreparedStatement pst = con.prepareStatement("insert into contacts(fname,email,phoneno,msg) values(?,?,?,?) ");
			pst.setString(1, fname);
			pst.setString(2,email);
			pst.setString(3,phoneno);
			pst.setString(4,msg);
			
			int rowcount = pst.executeUpdate();
			dispatcher = request.getRequestDispatcher("index.jsp");
			if(rowcount > 0) {
				request.setAttribute("status","success");
			}
			else
			{
				request.setAttribute("status","failed");
			}
			dispatcher.forward(request, response);
		}
    catch(Exception e){
    	e.printStackTrace();
    }
	finally {
		if(con==null) {
			return;
		}
		try {
		   con.close();
		}
		catch(SQLException e)
		{
		   e.printStackTrace();
    	}
	}
}

}
