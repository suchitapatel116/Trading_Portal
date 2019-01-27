import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/MarkSoldData")

public class MarkSoldData extends HttpServlet
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
			request.setAttribute("msg", "Please Login as a Seller to Mark the products sold");
			RequestDispatcher rd = request.getRequestDispatcher("/Login");
			rd.forward(request, response);
			return;
		}
		String uname = session.getAttribute("username").toString();

		String id = request.getParameter("pid");
        String title = request.getParameter("ptitle");
        String category = request.getParameter("pcategory");
        int markSold = 1;

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
		String buyer = request.getParameter("pbuyer");

		Product product = new Product(
			id,
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
			auction_endDate,
			highest_bidBy,
			markSold);

		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		Date date = new Date();
		String orderDate = sdf.format(date);

		StringBuffer sb = new StringBuffer();
		sb.append("<section id='content'><div class='row'>");
		
		if(MySqlDataStoreUtilities.updateProduct_markSold(title, markSold))
		{
			if(MySqlDataStoreUtilities.insertTradedGoods(buyer, uname, title, category, price, orderDate)) {
				sb.append("<p style='color:green; font-size:20px; margin-bottom:10px;'>Product '"+title+"' marked as sold.</p>");
				//update the hashmap
				ProductsHashMap.updateProductInMap(category, title, product);
			}
			else
				sb.append("<p style='color:red; font-size:20px; margin-bottom:10px;'>Error. Please try again.</p>");
		}
		else
			sb.append("<p style='color:red; font-size:20px; margin-bottom:10px;'>Error. Please try again.</p>");

		sb.append("</div></section>");
		String contentData = sb.toString();
		pw.println(contentData);
		utility.printHtml("Footer.html");
	}
}