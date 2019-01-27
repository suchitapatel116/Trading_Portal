import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/Admin")

public class Admin extends HttpServlet
{
	public static Connection conn = null;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
	{
		
		HttpSession session = request.getSession(true);
		String current_user = session.getAttribute("username").toString();
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		response.setContentType("text/html");
			
			
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<section id='content'><div class='row'>"
		+"<h2 class='title'>Admin Control Pannel:</h2>");
		pw.print("<li><a href='AllBuyers'><i class='glyphicon glyphicon-user'></i>&nbsp;View All Buyers</a></li>"
						+"<li><a href='AllSellers'><i class='glyphicon glyphicon-user'></i>&nbsp;View All Sellers</a></li>"
							+"<li><a href='AllSales'><i class='glyphicon glyphicon-shopping-cart'></i>&nbsp;View All Ads</a></li>"
							+"<li><a href='SalesReport'><i class='glyphicon glyphicon-shopping-cart'></i>&nbsp;View All Ads(Filtered by Zipcode)</a></li>"
							+"<li><a href='CategoryReport'><i class='glyphicon glyphicon-shopping-cart'></i>&nbsp;View All Ads(Filtered by Category)</a></li>"
							+"<li><a href='DataVisualization'><i class='glyphicon glyphicon-shopping-cart'></i>&nbsp;View goods on sales Map</a></li>"							
							+"<li><a href='AllGiveaways'><i class='glyphicon glyphicon-gift'></i>&nbsp;View All Giveaways</a></li>"
							+"<li><a href='AllAuctions'><i class='glyphicon glyphicon-hammer'></i>&nbsp;View All Auctions</a></li>");
            				
			pw.print("</div></section>");
			utility.printHtml("Footer.html");
			
		
	
}
	
}