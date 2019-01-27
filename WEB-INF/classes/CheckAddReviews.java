import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.*;

@WebServlet("/CheckAddReviews")

public class CheckAddReviews extends HttpServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();

		String id = request.getParameter("pid");
		String title = request.getParameter("ptitle");
		String userName = (String) session.getAttribute("username");
		String rtext = request.getParameter("rtext");
		
		ReviewDetail reviewDetail = new ReviewDetail();
		reviewDetail.setProductId(id);
		reviewDetail.setProductTitle(title);
		reviewDetail.setUserName(userName);
		reviewDetail.setReviewText(rtext);
		
		MongoDbDataStoreUtilities mdb = new MongoDbDataStoreUtilities();
		mdb.connect();
		mdb.addReview(reviewDetail);
		response.sendRedirect("Electronics");
	}
}