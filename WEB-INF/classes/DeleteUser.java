import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/DeleteUser")

public class DeleteUser extends HttpServlet
{
	public static Connection conn = null;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
	{
		
		HttpSession session = request.getSession(true);
		//String current_user = session.getAttribute("username").toString();
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		String del_user = request.getParameter("username");
		try{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TradingPortalDB", "root", "root");
		}
		catch(Exception e)
		{}
		
			try
			{
			
			String getMsg = "DELETE from registration WHERE username=?;";
			PreparedStatement pst = conn.prepareStatement(getMsg);
			pst.setString(1, del_user);
			pst.execute();
			
			
			
			response.sendRedirect("Admin"); return;
			
			}
			catch(SQLException e)
			{}
			 
			
			
		
	
}
	
}