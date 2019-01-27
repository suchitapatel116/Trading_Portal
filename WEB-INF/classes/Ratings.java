import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.*;

@WebServlet("/Ratings")

public class Ratings extends HttpServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("username");
		String usertype = (String) session.getAttribute("usertype");
		StringBuffer sb = new StringBuffer();
		sb.append("<section id='content'><div class='row'>");
		
		if(usertype.equals("buyer"))
		{
			MongoDbDataStoreUtilities mdb = new MongoDbDataStoreUtilities();
			mdb.connect();
			HashMap<String,ArrayList<RatingDetail>> reviews = mdb.getRatings();
			
			if(reviews.containsKey(userName))
			{
				sb.append("<h2 class='title'><span style='color:blue;'>"+"Ratings by "+userName+"</span></h2><br>");
				ArrayList<RatingDetail> list = (ArrayList<RatingDetail>)reviews.get(userName);
				int i=1;
				for(RatingDetail review : list)
				{
						sb.append("<h3 style='color:red'>Rating #"+i+" Details</h3>");
						sb.append("<b>Seller:</b> "+ review.getSeller()+"</br>");
						sb.append("<b>Ratings: </b>"+ review.getSellerRating()+"</br>");
						
						i++;
				}
			}
			else
			{
				sb.append(" <h1 align=\"center\"><span style='color:blue;'>"+userName+" has not rated any seller</span></h1> ");
			}
		}
		else{
			sb.append("<h1 align=\"center\"><span style='color:blue;'> Ratings are only available to buyers</span></h1> ");
		}
	
		sb.append("</div></section>");
		String contentData = sb.toString();
		pw.println(contentData);
		utility.printHtml("Footer.html");
	}
}