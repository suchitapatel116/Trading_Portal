import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.*;

@WebServlet("/AddToCart")

public class AddToCart extends HttpServlet
{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");

		HttpSession session = request.getSession();
		if(session.getAttribute("username") == null || !session.getAttribute("usertype").toString().equals("buyer")){
			request.setAttribute("msg", "Please Login as a Buyer to Add Products in WishList");
			RequestDispatcher rd = request.getRequestDispatcher("/Login");
			rd.forward(request, response);
			return;
		}
		String uname = session.getAttribute("username").toString();

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
			null,
			null,
			0);

		List<Product> items = CartData.getUserCart(uname);
		if(items == null){
			items = new ArrayList<>();
		}
		else{
			CartData.removeUser(uname);
		}
		
		items.add(product);

		CartData.setUserCart(uname, items);

		request.setAttribute("newItem", "New product");
		RequestDispatcher rd = request.getRequestDispatcher("/ViewCart");  
		rd.forward(request, response); 
	}
}