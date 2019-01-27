import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/ViewProduct")

public class ViewProduct extends HttpServlet
{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		HttpSession session = request.getSession();
		
		String id = request.getParameter("pid");
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
		String auction_endDate = request.getParameter("pauctionenddate");
		String highest_bidBy = request.getParameter("phighestbidby");
		String markSold = request.getParameter("markSold");

		StringBuffer sb = new StringBuffer();
		sb.append("<section id='content'><div class='row'>");
		sb.append("<h3 class='title'>"+title+"</h3>");

		if(request.getAttribute("bid_msg") != null )
		{
			String error_msg = (String)request.getAttribute("bid_msg");			
			request.setAttribute("bid_msg","");

			sb.append("<p style='color:red; font-size:20px; margin-bottom:10px;'>" + error_msg +"</p>");
		}

		sb.append("<div class='blog'>");
			sb.append("<div class='blog-img'><img class='img-responsive' src='./images/"+image+"'></div>");
			sb.append("<p>"+description+"</p>");
			sb.append("</br><table style='border:none;'>");
				if(category.equals("auction"))
				{
					sb.append("<tr><td>Current Highest Bid: </td><td>$ "+price+"</td></tr>");
					if(highest_bidBy == null || highest_bidBy.equals("") || highest_bidBy.equals("null"))
						sb.append("<tr><td>Highest Bid by: </td><td>No bids yet</td></tr>");
					else
						sb.append("<tr><td>Highest Bid by: </td><td>"+highest_bidBy+"</td></tr>");
					sb.append("<tr><td>Auction closes at: </td><td>"+auction_endDate+"</td></tr>");
				}
				else
					sb.append("<tr><td>Price: </td><td>$ "+price+"</td></tr>");
				sb.append("<tr><td>Posted By: </td><td>"+postedBy+"</td></tr>");
				sb.append("<tr><td>Post Date: </td><td>"+postdate+"</td></tr>");
				if(!category.equals("giveaway"))
				{
					sb.append("<tr><td>Condition: </td><td>"+condition+"</td></tr>");
					if(!category.equals("auction"))
						sb.append("<tr><td>Negotiable: </td><td>"+negotiable+"</td></tr>");
				}
					if(markSold != null && markSold.equals("1"))
						{
							sb.append("<tr><td>Availability: </td><td>Sold</td></tr>");
						}
						else
						{
							sb.append("<tr><td>Availability: </td><td>On Sale</td></tr>");
						}				
				sb.append("<tr><td>Pickup Address: </td><td>"+pickupaddress+"</td></tr>");
				sb.append("<tr><td>Zipcode: </td><td>"+zipcode+"</td></tr>");
			sb.append("</table>");
			if(!category.equals("giveaway") && !category.equals("auction"))
			{
				sb.append("</br><form action='AddToCart' method='post' style='float: left;'>");
				sb.append("<input type='hidden' name='pid' value='"+id+"'>");
				sb.append("<input type='hidden' name='ptitle' value='"+title+"'>");
				sb.append("<input type='hidden' name='pcategory' value='"+category+"'>");
				sb.append("<input type='hidden' name='pdescription' value='"+description+"'>");
				sb.append("<input type='hidden' name='pimage' value='"+image+"'>");
				sb.append("<input type='hidden' name='pprice' value='"+price+"'>");
				sb.append("<input type='hidden' name='pcondition' value='"+condition+"'>");
				sb.append("<input type='hidden' name='pnegotiable' value='"+negotiable+"'>");
				sb.append("<input type='hidden' name='ppickupaddress' value='"+pickupaddress+"'>");
				sb.append("<input type='hidden' name='ppostdate' value='"+postdate+"'>");
				sb.append("<input type='hidden' name='ppostedBy' value='"+postedBy+"'>");
				sb.append("<input type='hidden' name='pcomments' value='"+comments+"'>");
				sb.append("<input type='hidden' name='pzipcode' value='"+zipcode+"'>");
				sb.append("<input type='hidden' name='pauctionenddate' value='"+auction_endDate+"'>");
				sb.append("<input type='hidden' name='phighestbidby' value='"+highest_bidBy+"'>");
				sb.append("<input type='submit' class='main-btn' name='wishListBtn' value='Add to WishList'>");
				sb.append("</form>");
			
			sb.append("<form action='SendMessage' method='post'>");
				sb.append("<input type='hidden' name='pid' value='"+id+"'>");
				sb.append("<input type='hidden' name='ptitle' value='"+title+"'>");
				sb.append("<input type='hidden' name='pcategory' value='"+category+"'>");
				sb.append("<input type='hidden' name='pdescription' value='"+description+"'>");
				sb.append("<input type='hidden' name='pimage' value='"+image+"'>");
				sb.append("<input type='hidden' name='pprice' value='"+price+"'>");
				sb.append("<input type='hidden' name='pcondition' value='"+condition+"'>");
				sb.append("<input type='hidden' name='pnegotiable' value='"+negotiable+"'>");
				sb.append("<input type='hidden' name='ppickupaddress' value='"+pickupaddress+"'>");
				sb.append("<input type='hidden' name='ppostdate' value='"+postdate+"'>");
				sb.append("<input type='hidden' name='ppostedBy' value='"+postedBy+"'>");
				sb.append("<input type='hidden' name='pcomments' value='"+comments+"'>");
				sb.append("<input type='hidden' name='pzipcode' value='"+zipcode+"'>");
				sb.append("<input type='hidden' name='pauctionenddate' value='"+auction_endDate+"'>");
				sb.append("<input type='hidden' name='phighestbidby' value='"+highest_bidBy+"'>");
				sb.append("<input type='submit' class='main-btn' name='contactSeller' value='Contact Seller'>");
			sb.append("</form>");
		sb.append("</div>");
		}

		HashMap<String, String> map = MySqlDataStoreUtilities.getLatLong(pickupaddress.trim());
		if(map != null || !map.isEmpty())
		{
			String lat= "", longi = "";
			for (Map.Entry<String,String> entry : map.entrySet())  
			{
            	lat = entry.getKey();
            	longi = entry.getValue();
            	break;
    		} 
    		sb.append("<br><p><iframe src=\"https://maps.google.com/maps?q="+lat+","+longi+"&hl=es;z=14&amp;output=embed\" width='100%' height='400' frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe></p></br>");
			//sb.append("<p><iframe src=\"https://maps.google.com/maps?q=41.881832,-87.623177&hl=es;z=14&amp;output=embed\" width='100%' height='400' frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe></p></br>");
		}

		if(category.equals("giveaway"))
		{
			if(session.getAttribute("username") == null || !session.getAttribute("usertype").toString().equals("buyer")){
				request.setAttribute("msg", "Please Login as a Buyer to Subscribe");
				RequestDispatcher rd = request.getRequestDispatcher("/Login");
				rd.forward(request, response);
				return;
			}
			String userName = session.getAttribute("username").toString();

			String subject = "Giveaway subscription by user: "+userName;

			sb.append("</br><form action='GiveawayMsg' method='post'>");
			sb.append("<h3 class='title'>Enroll for the Giveaway</h3>");
			sb.append("<p>Answer in 2-3 line why you want this product and subscribe</p>");
			sb.append("<textarea name='message' style='width: 100%;' placeholder='Add Your Message' required></textarea>");
			sb.append("<input type='hidden' name='subject' value='"+subject+"'>");
			sb.append("<input type='hidden' name='toSeller' value='"+postedBy+"'>");
			sb.append("<input type='hidden' name='subscriber' value='"+userName+"'>");
			sb.append("<input type='hidden' name='product' value='"+title+"'>");
			sb.append("<input type='submit' class='main-btn' name='subscribe' value='Subscribe'>");
			sb.append("</form><br>");
		}
		else if(category.equals("auction"))
		{
			sb.append("</br><form action='MakeBidData' method='post'>");
			sb.append("<p><label for='bidamt'>Your biding amount: &nbsp;&nbsp;</label>");
			sb.append("<input type='tel' name='bidamt' placeholder='Enter your bid amount' style='width: 30%;' required />");
				sb.append("<input type='hidden' name='pid' value='"+id+"'>");
				sb.append("<input type='hidden' name='ptitle' value='"+title+"'>");
				sb.append("<input type='hidden' name='pcategory' value='"+category+"'>");
				sb.append("<input type='hidden' name='pdescription' value='"+description+"'>");
				sb.append("<input type='hidden' name='pimage' value='"+image+"'>");
				sb.append("<input type='hidden' name='pprice' value='"+price+"'>");
				sb.append("<input type='hidden' name='pcondition' value='"+condition+"'>");
				sb.append("<input type='hidden' name='pnegotiable' value='"+negotiable+"'>");
				sb.append("<input type='hidden' name='ppickupaddress' value='"+pickupaddress+"'>");
				sb.append("<input type='hidden' name='ppostdate' value='"+postdate+"'>");
				sb.append("<input type='hidden' name='ppostedBy' value='"+postedBy+"'>");
				sb.append("<input type='hidden' name='pcomments' value='"+comments+"'>");
				sb.append("<input type='hidden' name='pzipcode' value='"+zipcode+"'>");
				sb.append("<input type='hidden' name='pauctionenddate' value='"+auction_endDate+"'>");
				sb.append("<input type='hidden' name='phighestbidby' value='"+highest_bidBy+"'>");
			sb.append("</p><p><input name='makebid' style='margin-left: 166px; width: 30%;' class='main-btn' value='Make Bid' type='submit' /></p>");
			sb.append("</form><br>");
		}

		MongoDbDataStoreUtilities mdb = new MongoDbDataStoreUtilities();
		mdb.connect();
		HashMap<String,ArrayList<ReviewDetail>> reviews = mdb.getAllReviews();
		
		if(reviews.containsKey(id))
		{
			sb.append("<h3 class='title'><span style='color:blue;'>"+"Reviews for "+title+"</span></h3><br>");
			ArrayList<ReviewDetail> list = (ArrayList<ReviewDetail>)reviews.get(id);
			int i=1;
			for(ReviewDetail review : list)
			{
					sb.append("<h4 style='color:red'>Review #"+i+" Details</h4>");
					sb.append("<b>User:</b> "+ review.getUserName()+"</br>");
					sb.append("<b>Review Text: </b>"+ review.getReviewText()+"</br>");
					
					i++;
			}
		}
		else
		{
			sb.append("<h3><span style='color:blue;'>"+"No Reviews for "+title+"</span></h3>");
		}

		sb.append("</div></section>");
		String contentData = sb.toString();
		pw.println(contentData);
		utility.printHtml("Footer.html");
	}
}