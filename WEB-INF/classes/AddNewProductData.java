import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/AddNewProductData")

public class AddNewProductData extends HttpServlet
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

		String title = request.getParameter("ptitle");
		String category = request.getParameter("pcategory");
		String description = request.getParameter("pdescription");
		String image = request.getParameter("pimage");
		String price = request.getParameter("pprice");
		String condition = request.getParameter("pcondition");
		String negotiable = request.getParameter("pnegotiable");
		String pickupaddress = request.getParameter("ppickupaddress");
		//String comments = request.getParameter("pcomments");	//nothing to do
		String zipcode = request.getParameter("pzipcode");
		String auction_enddate = request.getParameter("pauctionenddate");
		//String highest_bidby = request.getParameter("phighestbidby");	//will be populated when user makes bid
		//boolean markedSold = false;
		int markSold = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		Date date = new Date();
		String postdate = sdf.format(date);

		try{
			int days = Integer.parseInt(auction_enddate.trim());
			//Fetch the current date and add next 14 days date
			sdf = new SimpleDateFormat("MM-dd-yyyy");
			Calendar cal = Calendar.getInstance();		//Getting current date					
		    cal.add(Calendar.DAY_OF_MONTH, days);		//Number of Days to adding					
			auction_enddate = sdf.format(cal.getTime());	//Date after adding the days to the current date
		}
		catch(Exception e){
			e.printStackTrace();
		}

		Product product = new Product(
			null,
			title,
			category,
			description,
			image,
			price,
			condition,
			negotiable,
			pickupaddress,
			postdate,
			uname,
			null,
			zipcode,
			auction_enddate,
			null,
			markSold);

		boolean inserted = MySqlDataStoreUtilities.insertNewProductInDB(product);
		
		String contentData = "<section id='content'><div class='row'>";
		if(inserted)
		{
			contentData += "<p style='color:green; font-size:20px; margin-bottom:10px;'>Product '"+title+"' sucessfully added!</p>";
			//add data to hash map
			//The new p will include all data like pid and order date
			//Product p = MySqlDataStoreUtilities.getProductData(title);
			//if(p != null)
				ProductsHashMap.addProductInMap(product);
		}
		else
			contentData += "<p style='color:red; font-size:20px; margin-bottom:10px;'>Error inserting product!</p>";
		contentData += "</div></section>";
		
		pw.println(contentData);
		utility.printHtml("Footer.html");
	}
}