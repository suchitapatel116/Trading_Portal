import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/EditProductData")

public class EditProductData extends HttpServlet
{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");

		HttpSession session = request.getSession();
		if(session.getAttribute("username") == null || !session.getAttribute("usertype").toString().equals("seller")){
			request.setAttribute("msg", "Please Login as a Seller to Add New Product");
			RequestDispatcher rd = request.getRequestDispatcher("/Login");
			rd.forward(request, response);
			return;
		}
		String uname = session.getAttribute("username").toString();

		String pid = request.getParameter("pid");
        String title = request.getParameter("ptitle");
        String category = request.getParameter("pcategory");
        String description = request.getParameter("pdescription");
        String image = request.getParameter("pimage");
        String price = request.getParameter("pprice");
        String condition = request.getParameter("pcondition");
        String negotiable = request.getParameter("pnegotiable");
        String pickupaddress = request.getParameter("ppickupaddress");
        String postdate = request.getParameter("ppostdate");
        String postedBy = request.getParameter("ppostedBy");
        String comments = request.getParameter("pcomments");
        String zipcode = request.getParameter("pzipcode");
        String auction_enddate = request.getParameter("pauctionenddate");
        String highest_bidby = request.getParameter("phighestbidby");
        String marksold = request.getParameter("pmarksold");

        int isSold = 0;
        if(marksold != null && marksold.equals("1"))
        	isSold = 1;
        

		Product product = new Product(
			pid,
			title,
			category,
			description,
			image,
			price,
			condition,
			negotiable,
			pickupaddress,
			postdate,
			postedBy,
			comments,
			zipcode,
			auction_enddate,
			highest_bidby,
			isSold);

		boolean updated = MySqlDataStoreUtilities.updateProduct(product);
		
		String contentData = "<section id='content'><div class='row'>";
		if(updated)
		{
			contentData += "<p style='color:green; font-size:20px; margin-bottom:10px;'>Post '"+title+"' updated sucessfully!</p>";
			//add data to hash map
				ProductsHashMap.updateProductInMap(category, title, product);
		}
		else
			contentData += "<p style='color:red; font-size:20px; margin-bottom:10px;'>Error updating product!</p>";
		contentData += "</div></section>";
		
		pw.println(contentData);
		utility.printHtml("Footer.html");
	}
}