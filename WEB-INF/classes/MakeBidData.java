import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/MakeBidData")

public class MakeBidData extends HttpServlet
{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");

		HttpSession session = request.getSession();
		if(session.getAttribute("username") == null || !session.getAttribute("usertype").toString().equals("buyer")){
			request.setAttribute("msg", "Please Login as a Buyer to make a bid");
			RequestDispatcher rd = request.getRequestDispatcher("/Login");
			rd.forward(request, response);
			return;
		}
		String uname = session.getAttribute("username").toString();

		String title = request.getParameter("ptitle");
		String new_bidAmount = request.getParameter("bidamt");

		String id = request.getParameter("pid");
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
		String auction_endDate = request.getParameter("pauctionenddate");
		String highest_bidBy = request.getParameter("phighestbidby");

		try{
			double old_price = Double.parseDouble(price.trim());
			double new_bid = Double.parseDouble(new_bidAmount.trim());

			if(new_bid <= old_price)
			{
				request.setAttribute("bid_msg", "Bid should be greater than current value");
				RequestDispatcher rd = request.getRequestDispatcher("/ViewProduct");  
				rd.forward(request, response);
				return;
			}
		}
		catch(Exception e) {
			System.out.println("Error converting values");

			request.setAttribute("bid_msg", "Enter proper bid amount");
			RequestDispatcher rd = request.getRequestDispatcher("/ViewProduct");
			rd.forward(request, response);
			return;
		}

		Product product = new Product(
			id,
			title,
			category,
			description,
			image,
			new_bidAmount,
			condition,
			negotiable,
			pickupaddress,
			postdate,
			uname,
			comments,
			zipcode,
			auction_endDate,
			uname,
			0);
		
		boolean inserted = MySqlDataStoreUtilities.updateProductBid(title, new_bidAmount, uname);
		
		String contentData = "<section id='content'><div class='row'>";
		if(inserted)
		{
			contentData += "<p style='color:green; font-size:20px; margin-bottom:10px;'>Bid for '"+title+"' sucessfully made!</p>";
			//add data to hash map
			//The new p will include all data like pid and order date
			//Product p = MySqlDataStoreUtilities.getProductData(title);
			//if(p != null)
				ProductsHashMap.updateProductInMap(category, title, product);
		}
		else
			contentData += "<p style='color:red; font-size:20px; margin-bottom:10px;'>Error making a bid!</p>";
		contentData += "</div></section>";
		
		pw.println(contentData);
		utility.printHtml("Footer.html");
	}
}