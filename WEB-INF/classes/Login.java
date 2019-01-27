import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/Login")

public class Login extends HttpServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		
		String error_msg = null;
		if(request.getAttribute("msg") != null )
		{
			error_msg = (String)request.getAttribute("msg");			
			request.setAttribute("msg","");
		}
		
		pw.println(printData(error_msg));
		utility.printHtml("Footer.html");
	}

	String printData(String msg)
	{
		String contentData = "<section id='content'><div class='row'>"
		+"<h2 class='title'>LOGIN</h2>"
        +"<fieldset>"
            +"<form action='LoginData' method='post'>";

            if(msg != null)
            	contentData += "<p style='color:red; font-size:20px; margin-left: 200px; margin-bottom:10px;'>" + msg
            				+"</p>";

		contentData	+="<div style='display: block; padding-left: 200px;'>"
            		+"<label class='radio-inline'><input type='radio' name='usertype' id='buyer' value='buyer' checked> Buyer </label>"
            		+"<label class='radio-inline'><input type='radio' name='usertype' id='seller' value='seller'> Seller </label>"
            		+"<label class='radio-inline'><input type='radio' name='usertype' id='admin' value='admin'> Administrator </label>"
            		+"</div>"

            		+"</br><p>"
                	+"<label for='uname'>Username:</label>"
                    +"<input type='text' name='uname' placeholder='Enter Username' style='width: 30%;' required />"
                +"</p><p>"
                    +"<label for='password'>Password:</label>"
                    +"<input type='password' name='password' value='' placeholder='Enter Password' style='width: 30%;' required />"
                +"</p>"
                +"<p id='test'><input name='login' style='margin-left: 200px; width: 30%;' class='main-btn' value='Login' type='submit' /></p>"
                +"</br>"
                +"<a href='Register' style='padding-left: 200px;'>Not a user? Register here</a>"
            +"</form>"
        +"</fieldset>"
        +"</div></section>";

        return contentData;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");

		String error_msg = null;
		if(request.getAttribute("msg") != null )
		{
			error_msg = (String)request.getAttribute("msg");			
			request.setAttribute("msg","");
		}

		pw.println(printData(error_msg));
		utility.printHtml("Footer.html");
	}
}