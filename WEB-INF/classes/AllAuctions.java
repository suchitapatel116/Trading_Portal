import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/AllAuctions")

public class AllAuctions extends HttpServlet
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
									
			String getMsg = "SELECT * from product WHERE  pcategory ='auction';";
			PreparedStatement pst = conn.prepareStatement(getMsg);
			ResultSet rs    =   pst.executeQuery();
			response.setContentType("text/html");
			//int count=MySqlDataStoreUtilities.getMessageCount(current_user);
			
			
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<section id='content'><div class='row'>"
		+"<h2 class='title'>All Sales</h2>");
	
	
			pw.print("<a href=\"Admin\"><i class='glyphicon glyphicon-user'></i>&nbsp;(<font color='red'>Back to Admin Center</font>)</a>");
			pw.print("<table>"+
 " <tr>"+
   "<th>Product Name</th>"+
 " <th>Category</th>"+
   "<th>Price</th>"+
    "<th>Posted By</th>"+
	"<th>Delete</th>"+
	"<th>View</th>"+
  "</tr>");

		
			while(rs.next()){
				
				pw.print("<tr>");
				
				pw.print("<td>"+rs.getString("ptitle")+"</td>");
				pw.print("<td>"+rs.getString("pcategory")+"</td>");
				pw.print("<td>"+rs.getString("pprice")+"</td>");
				pw.print("<td>"+rs.getString("ppostedby")+"</td>");
				pw.print("<form method='post' action='DeleteProduct'>" );
				pw.print("<input type='hidden' name='pid' value='"+rs.getString("pid")+"'>");
				pw.print("<input type='hidden' name='ptitle' value='"+rs.getString("ptitle")+"'>");
				pw.print("<input type='hidden' name='pcategory' value='"+rs.getString("pcategory")+"'>");
				pw.print("<td><input type='submit' value='Delete'></form></td>");
				pw.print("<form method='post' action='ViewProduct'>");
				pw.print("<input type='hidden' name='pid' value='"+rs.getString("pid")+"'>");
				pw.print("<input type='hidden' name='ptitle' value='"+rs.getString("ptitle")+"'>");
				pw.print("<input type='hidden' name='pcategory' value='"+rs.getString("pcategory")+"'>");
				pw.print("<input type='hidden' name='pdescription' value='"+rs.getString("pdescription")+"'>");
				pw.print("<input type='hidden' name='pimage' value='"+rs.getString("pimage")+"'>");
				pw.print("<input type='hidden' name='pprice' value='"+rs.getString("pprice")+"'>");
				pw.print("<input type='hidden' name='pcondition' value='"+rs.getString("pcondition")+"'>");
				pw.print("<input type='hidden' name='pnegotiable' value='"+rs.getString("pnegotiable")+"'>");
				pw.print("<input type='hidden' name='ppickupaddress' value='"+rs.getString("ppickupaddress")+"'>");
				pw.print("<input type='hidden' name='ppostdate' value='"+rs.getString("ppostdate")+"'>");
				pw.print("<input type='hidden' name='ppostedBy' value='"+rs.getString("ppostedby")+"'>");
				pw.print("<input type='hidden' name='pcomments' value='"+rs.getString("pcomments")+"'>");
				pw.print("<input type='hidden' name='pzipcode' value='"+rs.getString("zipcode")+"'>");
				pw.print("<input type='hidden' name='pauctionenddate' value='"+rs.getString("auction_endDate")+"'>");
				pw.print("<input type='hidden' name='phighestbidby' value='"+rs.getString("highest_bidBy")+"'>");
				pw.print("<input type='hidden' name='markSold' value='"+rs.getInt("markSold")+"'>");
				pw.print("<td><input type='submit'  value='View'></form></td>");
			
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