import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/AddGiveAway")

public class AddGiveAway extends HttpServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		HttpSession session = request.getSession();

		if(session.getAttribute("username") == null || !session.getAttribute("usertype").toString().equals("seller")){
			request.setAttribute("msg", "Please Login as a Seller to Add New Give Away");
			RequestDispatcher rd = request.getRequestDispatcher("/Login");
			rd.forward(request, response);
			return;
		}
		String uname = session.getAttribute("username").toString();

		String contentData = "<section id='content'><div class='row'>"
			+"<h2 class='title'>Add New Give Away Product</h2>"
        	+"<fieldset>"
            	+"<form action='AddNewProductData' method='post'>"
					+"<input type='hidden' name='pcategory' value='giveaway'>"
					+"<input type='hidden' name='pprice' value='0' />"
					//+"<input type='hidden' name='pcondition' value='' />"
					//+"<input type='hidden' name='pnegotiable' value='' />"
					//+"<input type='hidden' name='phighestbidby' />"
					//+"<input type='hidden' name='pauctionenddate' />"

                +"</p><p>"
                	+"<label for='ptitle'>Title:</label>"
                    +"<input type='text' name='ptitle' placeholder='Enter Product Name' style='width: 30%;' required />"
                +"</p><p>"
                	+"<label for='pdescription'>Description:</label>"
                    +"<input type='text' name='pdescription' placeholder='Enter Product Description' style='width: 30%;' />"
                +"</p><p>"
                	+"<label for='pimage'>Image:</label>"
                    +"<input type='file' name='pimage' accept='' style='width: 30%;' required />"
                +"</p><p>"
                	+"<label for='ppickupaddress'>Pickup Address:</label>"
                    +"<input type='text' name='ppickupaddress' placeholder='Enter Pickup Address' style='width: 30%;' required />"    
                +"</p><p>"
                	+"<label for='pzipcode'>Zipcode:</label>"
                    +"<input type='text' name='pzipcode' placeholder='Enter Zipcode' style='width: 30%;' required />"
                +"</p>"
                +"<p id='test'><input name='add' style='margin-left: 200px; width: 30%;' class='main-btn' value='Add GiveAway' type='submit' /></p>"
            +"</form>"
        +"</fieldset>"
        +"</div></section>";

        pw.println(contentData);
		utility.printHtml("Footer.html");
	}
}