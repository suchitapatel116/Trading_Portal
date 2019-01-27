import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/Register")

public class Register extends HttpServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
	{
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
		+"<h2 class='title'>Registration Form</h2>"
        +"<fieldset>"
            +"<form action='RegisterData' method='post'>";

            if(msg != null)
            	contentData += "<p style='color:red; font-size:20px; margin-left: 200px; margin-bottom:10px;'>" + msg
            				+"</p>";

		contentData +="<p>"
                	+"<label for='userType'>User Type:</label>"
                    +"<select name='userType' style='width: 30%;' required>"
                    	+"<option value='buyer' >Buyer</option> "
				 		+"<option value='seller' >Seller</option> "
				 	+"</select>"
                +"</p><p>"
                	+"<label for='uname'>Name:</label>"
                    +"<input type='text' name='uname' placeholder='Enter Username' style='width: 30%;' required />"
                +"</p><p>"
                    +"<label for='email'>Email:</label>"
                    +"<input type='email' name='email' placeholder='Enter Email ID' style='width: 30%;' required/>"
                +"</p><p>"
                    +"<label for='password'>Password:</label>"
                    +"<input type='password' name='password' value='' placeholder='Enter Password' style='width: 30%;' required />"
                +"</p><p>"
                	+"<label for='contactno'>Contact No:</label>"
                    +"<input type='tel' name='contactno' value='' placeholder='Enter Contact No' style='width: 30%;' required />"
                +"</p>"
                +"<p id='test'><input name='register' style='margin-left: 200px; width: 30%;' class='main-btn' value='Register' type='submit' /></p>"
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
		if(request.getAttribute("msg") != null)
		{
			error_msg = (String)request.getAttribute("msg");
			request.setAttribute("msg","");
		}
	
		pw.println(printData(error_msg));
		utility.printHtml("Footer.html");
	}
}