import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/DeleteProduct")

public class DeleteProduct extends HttpServlet
{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");

		String pid = request.getParameter("pid");
		String title = request.getParameter("ptitle");
		String category = request.getParameter("pcategory");

		StringBuffer sb = new StringBuffer();
		sb.append("<section id='content'><div class='row'>");
		sb.append("<div class='section-header text-center'><h3 class='title'>Your Posted Adds</h3></div>");

		if(MySqlDataStoreUtilities.deleteProduct(pid))
		{
			//update product hashmap
			ProductsHashMap.removeProductInMap(category, title);

			sb.append("<p style='color:green; font-size:20px; margin-bottom:10px;'>Post for '"+title+"' deleted sucessfully!</p>");
		}
		else
			sb.append("<p style='color:red; font-size:20px; margin-left: 200px; margin-bottom:10px;'>Error deleting post.</p>");

		sb.append("</div></section>");
		String contentData = sb.toString();
		pw.println(contentData);
		utility.printHtml("Footer.html");
	}
}
