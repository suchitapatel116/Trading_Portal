import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/BackToInbox")

public class BackToInbox extends HttpServlet
{
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
	{
		
		HttpSession session = request.getSession(true);
		
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		response.sendRedirect("Inbox"); return;
				
			
		
	
}
	
}