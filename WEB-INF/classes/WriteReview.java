import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.*;

@WebServlet("/WriteReviews")

public class WriteReview extends HttpServlet
{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("username");
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
		
		
		StringBuffer sb = new StringBuffer();
		sb.append("<section id='content'><div class='row'>");
		sb.append("<h3 class='title'>"+title+"</h3>");

		sb.append("<div class='blog'>");
			sb.append("<div class='blog-img'><img class='img-responsive' src='./images/"+image+"'></div>");
			sb.append("<p>"+description+"</p>");
			sb.append("</br><table style='border:none;'>");
				sb.append("<tr><td>Price: </td><td>$ "+price+"</td></tr>");
				sb.append("<tr><td>Posted By: </td><td>"+postedBy+"</td></tr>");
				sb.append("<tr><td>Post Date: </td><td>"+postdate+"</td></tr>");
				sb.append("<tr><td>Condition: </td><td>"+condition+"</td></tr>");
				sb.append("<tr><td>Negotiable: </td><td>"+negotiable+"</td></tr>");
				sb.append("<tr><td>Pickup Address: </td><td>"+pickupaddress+"</td></tr>");
			sb.append("</table>");
			sb.append("</div>");
			sb.append("</div>");
			
		sb.append("<div class='row'>");
		
			MongoDbDataStoreUtilities mdb = new MongoDbDataStoreUtilities();
		mdb.connect();
		HashMap<String,ArrayList<RatingDetail>> ratings = mdb.getAllRatings();
		int flag=0;
		if(ratings.containsKey(postedBy.trim()))
		{
			ArrayList<RatingDetail> list = (ArrayList<RatingDetail>)ratings.get(postedBy.trim());
			for(RatingDetail rating : list)
			{
				if(rating.getBuyer().equals(userName))
				{
					sb.append("<h3 style='color:red'>You have rated this seller "+rating.getSellerRating()+" on scale of 5</h2>");
					flag=1;
				}
			}
			if(flag==0)
			{
				sb.append("</br><form action='RateSeller' method='get' style='float: left;'>");
				sb.append("<input type='hidden' name='seller' value='"+postedBy.trim()+"'>");
				sb.append("<label><b>Rate This Seller</b></label></br>");
				sb.append("<select style='width:100%;' name='rrating'><option value='1' selected>1</option><option value='2'>2</option><option value='3'>3</option><option value='4'>4</option><option value='5'>5</option></select>");	
				sb.append("</form>");
			}
			
		}
		else{
			sb.append("</br><form action='RateSeller' method='get' style='float: left;'>");
				sb.append("<input type='hidden' name='seller' value='"+postedBy.trim()+"'>");
				sb.append("<label><b>Rate This Seller</b></label></br>");
				sb.append("<select style='width:100%;' name='rrating'><option value='1' selected>1</option><option value='2'>2</option><option value='3'>3</option><option value='4'>4</option><option value='5'>5</option></select></br></br>");	
				sb.append("<input type='submit' class='main-btn' name='addRatings' value='Rate'>");
			sb.append("</form>");
		}
			sb.append("</div>");
			
		sb.append("<div class='row'>");
		
			sb.append("</br><form action='CheckAddReviews' method='get' style='float: left;'>");
				sb.append("<tr><td colspan=2><label><b>Write Review</b></label></br><textarea style='width:100%;' placeholder='Enter Review Text' name='rtext' required ></textarea></td></tr>");
				sb.append("<input type='hidden' name='pid' value='"+id+"'>");
				sb.append("<input type='hidden' name='ptitle' value='"+title+"'>");
				sb.append("<input type='submit' class='main-btn' name='addReviews' value='Add'>");
			sb.append("</form></br>");
			sb.append("</div>");
			
		
		/*
		sb.append("</br><form action='#' method='post'>");
		sb.append("<h3 class='title'>Contact Seller</h3>");
		sb.append("<textarea style='width: 100%;' placeholder='Add Your Message' required></textarea>");
			sb.append("<input type='submit' class='main-btn' name='send' value='Send'>");
		sb.append("</form>");
		*/

		sb.append("</section>");
		String contentData = sb.toString();
		pw.println(contentData);
		utility.printHtml("Footer.html");
	}
}