import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/LoginData")

public class LoginData extends HttpServlet
{
	private String errorMsg = null;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		String contentData =  "<section id='content'><div class='row'>";

		String userName = request.getParameter("uname");
		String password = request.getParameter("password");
		String userType = request.getParameter("usertype");

		HashMap<String,User> hm_users = new HashMap<String,User>();
		try
		{
			if(MySqlDataStoreUtilities.getConnection()){
				hm_users = MySqlDataStoreUtilities.getUsers();

				if(hm_users == null || hm_users.isEmpty())
					errorMsg = "No user named '"+userName+"' Exists";
			}
			else{
				contentData += "<h2>Database server is down. Please try again!</h2>";
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}

		if(!hm_users.isEmpty() && hm_users.containsKey(userName))
		{
			User user = (User)hm_users.get(userName);
			if(user.getPassword().equals(password) && user.getUserType().equals(userType))
			{
				if(userType.equals("buyer"))
				{
					HttpSession session = request.getSession(true);
					session.setAttribute("username", user.getUserName());
					session.setAttribute("usertype", user.getUserType());
					
					response.sendRedirect("Home");
					return;
				}
				else if(userType.equals("seller"))
				{
					HttpSession session = request.getSession(true);
					session.setAttribute("username", user.getUserName());
					session.setAttribute("usertype", user.getUserType());
					
					response.sendRedirect("Home");
					return;
				}
				else if(userType.equals("admin"))
				{
					HttpSession session = request.getSession(true);
					session.setAttribute("username", user.getUserName());
					session.setAttribute("usertype", user.getUserType());
					
					response.sendRedirect("Admin");
					return;
				}
			}
			else
				errorMsg = "Invalid credentials! Try again";
		}
		else
			errorMsg = "No user named '"+userName+"' Exists";

		if(errorMsg != null)
		{
			request.setAttribute("msg",errorMsg);
			RequestDispatcher rd = request.getRequestDispatcher("/Login");  
			rd.forward(request, response); 
		}

		contentData += "</div></section>";
		pw.println(contentData);
		utility.printHtml("Footer.html");
	}
}