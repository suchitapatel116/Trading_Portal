import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/InboxData")

public class InboxData extends HttpServlet
{
	public static Connection conn = null;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
	{
		
		HttpSession session = request.getSession(true);
		String current_user = session.getAttribute("username").toString();
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		String msg_id = request.getParameter("id");
		try{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TradingPortalDB", "root", "root");
		}
		catch(Exception e)
		{}
		
			try
			{
			String updateRead= "UPDATE messages SET status = \"read\" WHERE id=?;";
			PreparedStatement pst2 = conn.prepareStatement(updateRead);
			pst2.setString(1, msg_id);
			pst2.execute();
			String getMsg = "SELECT * from messages WHERE id=?;";
			
			PreparedStatement pst = conn.prepareStatement(getMsg);
			pst.setString(1, msg_id);
			ResultSet rs    =   pst.executeQuery();
			
			
			
			response.setContentType("text/html");
			
			
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<section id='content'><div class='row'>"
		+"<h2 class='title'>Inbox</h2>");
			
			pw.print("<table style=\"border-collapse: collapse\">");
			while(rs.next()){
			
				
				pw.print("<font style=\"color: blue\">From:</font> "+rs.getString("sender")+"<br>");
				pw.print("<font style=\"color: blue\">Product:</font> "+rs.getString("product")+"<br>");
				pw.print("<font style=\"color: blue\">Subject:</font>"+rs.getString("subject")+"<br>");
				pw.print("<font style=\"color: blue\">Message:</font>"+rs.getString("msg")+"<br>");
				pw.print("<tr><td>");
				pw.print("<form method='post' action='DeleteMsg'>");
				pw.print("<input type='hidden' name='id' value='"+rs.getString("id")+"'>");
				pw.print("<li><input type='submit' value='Delete Message'></li></form>");
				pw.print("<form method='post' action='MarkMsg'>");
				pw.print("<input type='hidden' name='id' value='"+rs.getString("id")+"'>");
				pw.print("<li><input type='submit' value='Mark as Unread'></li></form>");
				pw.print("<form method='post' action='BackToInbox'>");
				pw.print("<input type='hidden' name='id' value='"+rs.getString("id")+"'>");
				pw.print("<li><input type='submit' value='Back to Messages'></li></form>");
				pw.print("<form method='post' action='ReplyMsg'>");
				pw.print("<input type='hidden' name='id' value='"+rs.getString("id")+"'>");
				pw.print("<input type='hidden' name='sender' value='"+rs.getString("sender")+"'>");
				pw.print("<input type='hidden' name='message' value='"+rs.getString("msg")+"'>");
				pw.print("<input type='hidden' name='subject' value='"+rs.getString("subject")+"'>");
				pw.print("<input type='hidden' name='product' value='"+rs.getString("product")+"'>");
				pw.print("<li><input type='submit' value='Reply'></li></form></td></tr>");
				
				
				
				
			}
			}
			catch(SQLException e)
			{}
			 pw.print("</table>");
			pw.print("</div></section>");
			utility.printHtml("Footer.html");
			try{
			conn.close();
			}
			catch(Exception e)
			{}
			
		
	
}
	
}