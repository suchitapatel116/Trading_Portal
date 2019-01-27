import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/SendMessage")

public class SendMessage extends HttpServlet
{
	public String receiver;
	public String product;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
	{	
		HttpSession session = request.getSession(true);
		//String userName = session.getAttribute("username").toString();
		if(session.getAttribute("username") == null  ){
			request.setAttribute("msg", "Please Login to Send Message");
			RequestDispatcher rd = request.getRequestDispatcher("/Login");
			rd.forward(request, response);
			return;
		}
			
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.println(printData(null));
		utility.printHtml("Footer.html");
	}

	String printData(String msg)
	{
		String contentData = "<section id='content'><div class='row'>"
		+"<h2 class='title'>Send Message</h2>"
        +"<fieldset>"
            +"<form action='MessageData' method='post'>";

            if(msg != null)
            	contentData += "<p style='color:red; font-size:20px; margin-left: 200px; margin-bottom:10px;'>" + msg
            				+"</p>";

		contentData +="<p>"
                	
                +"<p>"
                	+"<label for='receiver'>To:</label>"
					+"<label'>"+receiver+"</label>"
                    +"<input type='hidden' name='receiver' value='"+receiver+"'/>"
                +"</p><p>"
				  	+"<label for='product'>About Product:</label>"
					+"<label'>"+product+"</label>"
                    +"<input type='hidden' name='product' value='"+product+"'/>"
                +"</p><p>"
                    +"<label for='subject'>Subject:</label>"
                    +"<input type='subject' name='subject' placeholder='Enter Message Subject' style='width: 30%;' required/>"
                +"</p><p>"
                    +"<label for='message'>Message:</label>"
                    +"<input type='textbox' name='message' value='' placeholder='Enter Message' style='width: 30%;' required />"
                +"</p><p>"
                +"<p id='test'><input name='submit' style='margin-left: 200px; width: 30%;' class='main-btn' value='Send Message' type='submit' /></p>"
            +"</form>"
        +"</fieldset>"
        +"</div></section>";

        return contentData;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
	{
		HttpSession session = request.getSession(true);
		if(session.getAttribute("username") == null ){
			request.setAttribute("msg", "Please Login to Send Message");
			RequestDispatcher rd = request.getRequestDispatcher("/Login");
			rd.forward(request, response);
			return;
		}
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		receiver = request.getParameter("ppostedBy");
		product = request.getParameter("ptitle");
		String error_msg = null;
		if(request.getAttribute("msg") != null)
		{
			error_msg = (String)request.getAttribute("msg");
			request.setAttribute("msg","");
		}
	
		pw.println(printData(error_msg));
		utility.printHtml("Footer.html");
	}
}