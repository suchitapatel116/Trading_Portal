import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.*;

@WebServlet("/ViewCart")

public class ViewCart extends HttpServlet
{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		
		HttpSession session = request.getSession();
		if(session.getAttribute("username") == null || !session.getAttribute("usertype").toString().equals("buyer")){
			request.setAttribute("msg", "Please Login as a Buyer to Add Products in WishList");
			RequestDispatcher rd = request.getRequestDispatcher("/Login");
			rd.forward(request, response);
			return;
		}
		String uname = session.getAttribute("username").toString();
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");


		StringBuffer sb = new StringBuffer();
		sb.append("<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'>");
		sb.append("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>");
		sb.append("<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js'></script>");
		
		sb.append("<section id='content'><div class='row'>");
		sb.append("<h3 class='title'>Your WishList</h3>");

		//called from additemtocart
		if(request.getAttribute("newItem") != null)
		{
			request.setAttribute("newItem","");
			
			String name = request.getParameter("pname");
			String price = request.getParameter("pprice");
		}

		String no_cart_data = null;

		List<Product> items = CartData.getUserCart(uname);
		if(items == null)
			no_cart_data = "Your WishList is empty!!";
		else
		{
			sb.append("<div class='blog'>");
			sb.append("</br><table style='border:none;'>");
			
			Product p;
			for(int i=0; i<items.size(); i++)
			{
				p = items.get(i);
				
				sb.append("<tr><td>"+p.getTitle()+"</td><td>$ "+p.getPrice()+"</td><td>"+p.getPostedBy()+"</td>");
					sb.append("<td><form action='SendMessage' method='post'>"
								+"<input type='submit' name='buy' class='main-btn' value='Buy'>"
								+"<input type='hidden' name='ptitle' value='"+p.getTitle()+"'>"
								+"<input type='hidden' name='ppostedBy' value='"+p.getPostedBy()+"'>"
								+"</form></td>");
					sb.append("<td><form action='RemoveFromCart' method='post'>"
								+"<input type='hidden' name='ptitle' value='"+p.getTitle()+"'>"
								+"<input type='submit' name='remove' class='main-btn' value='Remove'>"
								+"</form></td></tr>");
			}
			sb.append("</table></div>");
		}

		//Carousel
		List<Product> list = ProductsHashMap.getAllProducts();
		Set<Product> hset = new HashSet<Product>(list);

		if(hset != null)
		{
        	sb.append("</br></br><table class='carousel_table'><tr><td>"
        			+"<div id='carousel_holder' class='carousel slide' data-ride='carousel'>"
        			+"<div class='carousel-inner'>");

        	int i=0;
        	for(Product pp: hset)
        	{
        		if(i==0){
        			sb.append("<div class='item active'>"
									+"<div style='text-align: center'>"
										+"<img class='product-image' style='width: 150px; height:150px;' src='images/"+pp.getImage()+"'>"
									+"</div>"
									+"<div style='text-align: center'>"
										+pp.getTitle()+"<br/>$"+pp.getPrice()
										+"<form action='AddToCart' method='post'>"
											+"<input type='submit' name='buybutton' class='main-btn' value='Add to WishList'>"
											+"<input type='hidden' name='pid' value='"+pp.getId()+"'>"
											+"<input type='hidden' name='ptitle' value='"+pp.getTitle()+"'>"
											+"<input type='hidden' name='pcategory' value='"+pp.getCategory()+"'>"
											+"<input type='hidden' name='pdescription' value='"+pp.getDescription()+"'>"
											+"<input type='hidden' name='pprice' value='"+pp.getPrice()+"'>"
											+"<input type='hidden' name='pimage' value='"+pp.getImage()+"'>"
											+"<input type='hidden' name='pcondition' value='"+pp.getCondition()+"'>"
											+"<input type='hidden' name='pnegotiable' value='"+pp.getNegotiable()+"'>"
											+"<input type='hidden' name='ppickupaddress' value='"+pp.getPickupaddress()+"'>"
											+"<input type='hidden' name='ppostdate' value='"+pp.getPostdate()+"'>"
											+"<input type='hidden' name='ppostedBy' value='"+pp.getPostedBy()+"'>"
											+"<input type='hidden' name='pcomments' value='"+pp.getComments()+"'>"
											+"<input type='hidden' name='pzipcode' value='"+pp.getZipcode()+"'>"
										+"</form>"
									+"</div>"
								+"</div>");
					i++;
        		}
        		else{
        			sb.append("<div class='item'>"
									+"<div style='text-align: center'>"
										+"<img class='product-image' style='width: 150px; height:150px;' src='images/"+pp.getImage()+"'>"
									+"</div>"
									+"<div style='text-align: center'>"
										+pp.getTitle()+"<br/>$"+pp.getPrice()
										+"<form action='AddToCart' method='post'>"
											+"<input type='submit' name='buybutton' class='main-btn' value='Add to WishList'>"
											+"<input type='hidden' name='pid' value='"+pp.getId()+"'>"
											+"<input type='hidden' name='ptitle' value='"+pp.getTitle()+"'>"
											+"<input type='hidden' name='pcategory' value='"+pp.getCategory()+"'>"
											+"<input type='hidden' name='pdescription' value='"+pp.getDescription()+"'>"
											+"<input type='hidden' name='pprice' value='"+pp.getPrice()+"'>"
											+"<input type='hidden' name='pimage' value='"+pp.getImage()+"'>"
											+"<input type='hidden' name='pcondition' value='"+pp.getCondition()+"'>"
											+"<input type='hidden' name='pnegotiable' value='"+pp.getNegotiable()+"'>"
											+"<input type='hidden' name='ppickupaddress' value='"+pp.getPickupaddress()+"'>"
											+"<input type='hidden' name='ppostdate' value='"+pp.getPostdate()+"'>"
											+"<input type='hidden' name='ppostedBy' value='"+pp.getPostedBy()+"'>"
											+"<input type='hidden' name='pcomments' value='"+pp.getComments()+"'>"
											+"<input type='hidden' name='pzipcode' value='"+pp.getZipcode()+"'>"
										+"</form>"
									+"</div>"
								+"</div>");
        		}
        	}

        	sb.append("<a class='left carousel-control' href='#carousel_holder' data-slide='prev'>"
							+"<span class='glyphicon glyphicon-chevron-left'></span>"
							+"<span class='sr-only'>Previous</span>"
					  	+"</a>"
					  	+"<a class='right carousel-control' href='#carousel_holder' data-slide='next'>"
							+"<span class='glyphicon glyphicon-chevron-right'></span>"
							+"<span class='sr-only'>Next</span>"
					  	+"</a>");
        
        	sb.append("</div></div></td></tr></table>");
		}





		sb.append("</div></section>");
		String contentData = sb.toString();

		pw.println(contentData);
		utility.printHtml("Footer.html");
	}
}