import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/MessageData")

public class MessageData extends HttpServlet
{
	private String errorMsg;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
	{
		response.setContentType("text/html");
		HttpSession session = request.getSession(true);
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		String contentData =  "<section id='content'><div class='row'>";

		String receiver = request.getParameter("receiver");
		String subject = request.getParameter("subject");
		String product = request.getParameter("product");
		String msg = request.getParameter("message");
		String sender = session.getAttribute("username").toString();
		String status = "unread";

		Message message = new Message(sender, receiver, subject, msg, status);
		HashMap<String,User> hm_userdetails = new HashMap<String,User>();
		try
		{
			if(MySqlDataStoreUtilities.getConnection())
			{
				hm_userdetails = MySqlDataStoreUtilities.getUsers();

				
				if(!hm_userdetails.containsKey(receiver))
				{
					errorMsg = "Username Not Found!";
					request.setAttribute("msg",errorMsg);
					RequestDispatcher rd = request.getRequestDispatcher("/SendMessage");
					rd.forward(request, response);
				}
				else{
					//hm_userdetails.put(userName, user);
					if(MySqlDataStoreUtilities.newMsg(sender, receiver, subject, msg, status,product))
						contentData += "<h3>Message Sent successfully!!</h3>"
									+"</br><p>Go back to <a href='Inbox'>Inbox</a></p>";
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