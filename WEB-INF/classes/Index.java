import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/Index")

public class Index extends HttpServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Index.html");
	}

}