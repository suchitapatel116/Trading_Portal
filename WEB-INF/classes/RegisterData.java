import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/RegisterData")

public class RegisterData extends HttpServlet
{
	private String errorMsg;

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
		String email = request.getParameter("email");
		String userType = request.getParameter("userType");
		String contact_no = request.getParameter("contactno");

		User user = new User(userName, password, userType, email, contact_no);
		HashMap<String,User> hm_userdetails = new HashMap<String,User>();
		try
		{
			if(MySqlDataStoreUtilities.getConnection())
			{
				hm_userdetails = MySqlDataStoreUtilities.getUsers();

				if(hm_userdetails == null){
					hm_userdetails = new HashMap<String,User>();
				}
				if(!hm_userdetails.isEmpty() && hm_userdetails.containsKey(userName))
				{
					errorMsg = "Username already taken!";
					request.setAttribute("msg",errorMsg);
					RequestDispatcher rd = request.getRequestDispatcher("/Register");
					rd.forward(request, response);
				}
				else{
					hm_userdetails.put(userName, user);
					if(MySqlDataStoreUtilities.addUser(userName, password, userType, email, contact_no))
						contentData += "<h3>Registered successfully!!</h3>"
									+"</br><p>You can <a href='Login'>Login here</a></p>";
					else
						contentData += "<h3>Login error! Please try again.</h3>";
				}			
			}
			else{
				contentData += "<h2>Database server is down. Please try again!</h2>";
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		contentData += "</div></section>";
		pw.println(contentData);
		utility.printHtml("Footer.html");
	}
}