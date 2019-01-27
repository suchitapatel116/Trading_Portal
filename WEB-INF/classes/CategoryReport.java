import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/CategoryReport")

public class CategoryReport extends HttpServlet
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
									
			//String getMsg = "SELECT * from product ORDER BY ppostedby;";
			String getMsg ="select pcategory, COUNT(*) AS pcount from product GROUP BY pcategory ";
			PreparedStatement pst = conn.prepareStatement(getMsg);
			ResultSet rs    =   pst.executeQuery();
			response.setContentType("text/html");
			//int count=MySqlDataStoreUtilities.getMessageCount(current_user);
			
			
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<section id='content'><div class='row'>"
		+"<h2 class='title'>All Posts filtered by Category</h2>");
			pw.print("<a href=\"Admin\"><i class='glyphicon glyphicon-user'></i>&nbsp;(<font color='red'>Back to Admin Center</font>)</a>");
	
			pw.print("<table>"+
 " <tr>"+
   "<th>Product Category</th>"+
 " <th>Total Posts</th>"+

  "</tr>");

		
			while(rs.next()){
				pw.print("<form method='post' action='DeleteBuyer'>" );
				pw.print("<tr>");
				
				pw.print("<td>"+rs.getString("pcategory")+"</td>");
				pw.print("<td>"+rs.getString("pcount")+"</td>");
				
				
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