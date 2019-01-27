import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/ReplyMsg")

public class ReplyMsg extends HttpServlet
{
	public static Connection conn = null;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
	{
		
		HttpSession session = request.getSession(true);
		String current_user = session.getAttribute("username").toString();
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		String msg_id = request.getParameter("id");
		String msg_sender = request.getParameter("sender");
		String msg_subject = request.getParameter("subject");
		String msg = request.getParameter("msg");
		String product = request.getParameter("product");
		
		
			
			
			
			response.setContentType("text/html");
			
			
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<section id='content'><div class='row'>"
		+"<h2 class='title'>Send Reply</h2>");
			
		
			
			
				
				pw.print("To: "+msg_sender+"<br>");
				pw.print("Subject: RE:"+msg_subject+"<br>");
				pw.print("Product: RE:"+product+"<br>");
				pw.print("<form method='post' action='MessageData'>");
				//pw.print("<input type='hidden' name='id' value='"+msg_id+"'>");
				pw.print("<input type='hidden' name='receiver' value='"+msg_sender+"'>");
				pw.print("<input type='hidden' name='subject' value='"+msg_subject+"'>");
				pw.print("<textarea name='message' value='' placeholder='Enter Message' style='width: 30%;' required /></textarea>");
				pw.print("<br><input type='submit' value='Send Reply'></form>");
				
				
				
			
			
			 
			pw.print("</div></section>");
			utility.printHtml("Footer.html");
			
		
	
}
	
}