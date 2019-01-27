import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/Inbox")

public class Inbox extends HttpServlet
{
	public static Connection conn = null;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
	{
		
		HttpSession session = request.getSession(true);
		String current_user = session.getAttribute("username").toString();
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		try{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TradingPortalDB", "root", "root");
		}
		catch(Exception e)
		{}
		
			try
			{
									
			String getMsg = "SELECT * from messages WHERE receiver=?;";
			PreparedStatement pst = conn.prepareStatement(getMsg);
			pst.setString(1, current_user);
			ResultSet rs    =   pst.executeQuery();
			response.setContentType("text/html");
			int count=MySqlDataStoreUtilities.getMessageCount(current_user);
			
			
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<section id='content'><div class='row'>"
		+"<h2 class='title'>Inbox</h2>");
		if(count==0)
			pw.print("(No new messages)");
		else
			pw.print("<h4 style=\"color: red\">("+count+" New Messages)</h3>");
			pw.print("<table>"+
 " <tr>"+
   "<th>From</th>"+
 " <th>Subject</th>"+

    "<th>Status</th>"+
	"<th>Action</th>"+
  "</tr>");

		
			while(rs.next()){
				pw.print("<form method='post' action='InboxData'>" );
				pw.print("<tr>");
				
				pw.print("<td>"+rs.getString("sender")+"</td>");
				pw.print("<td>"+rs.getString("subject")+"</td>");
				pw.print("<td>"+rs.getString("status")+"</td>");
				pw.print("<input type='hidden' name='id' value='"+rs.getString("id")+"'>");
				pw.print("<td><input type='submit' value='View Message'></form></td>");
				pw.print("</tr>");
				
				
				
			}
			}
			catch(SQLException e)
			{}
			  pw.print("</table>");
			pw.print("</div></section>");
			utility.printHtml("Footer.html");
			
		
	
}
	
}