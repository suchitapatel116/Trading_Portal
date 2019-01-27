import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.*;

@WebServlet("/AboutUs")

public class AboutUs extends HttpServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		HttpSession session = request.getSession();

		StringBuffer sb = new StringBuffer();
		sb.append("<section id='content'><div class='row'>");
		sb.append("<div class='section-header text-center'><h2 class='title'>About Us</h2></div>");
		
		sb.append("<p> Trading portal is a site where you can easily sell goods from used electronics to automobiles books to furniture and more.");
		sb.append("</p>");
		sb.append("<p> You will find deals much cheaper than stores and can also take part in auctions being held and giveaways.");
		sb.append("</p>");
		sb.append("<p> Start buying and selling by simply registering <a href='Register'><u>here</u></a>");
		sb.append("</p>");
		
		sb.append("<p> Meet Admins of the Website:");
		sb.append("</p>");
		sb.append("<b><p> Suchita Patel </b>");
		sb.append("</p>");
		sb.append("<b><p> Helly Modi </b>");
		sb.append("</p>");
		sb.append("<b><p> Zaman Mohammed </b>");
		sb.append("</p>");
		
		
		sb.append("</div></section>");

		String contentData = sb.toString();
		pw.println(contentData);
		utility.printHtml("Footer.html");
	}
	
}