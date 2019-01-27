import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.*;

@WebServlet("/RateSeller")

public class RateSeller extends HttpServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();

		String seller = request.getParameter("seller");
		String buyer = (String) session.getAttribute("username");
		String rrating = request.getParameter("rrating");
		
		RatingDetail ratingDetail = new RatingDetail();
		ratingDetail.setBuyer(buyer);
		ratingDetail.setSeller(seller);
		ratingDetail.setSellerRating(rrating);
		
		MongoDbDataStoreUtilities mdb = new MongoDbDataStoreUtilities();
		mdb.connect();
		mdb.addSellerRating(ratingDetail);
		response.sendRedirect("Home");
	}
}