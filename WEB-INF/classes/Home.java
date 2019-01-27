import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.*;

@WebServlet("/Home")

public class Home extends HttpServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);

		//HttpSession session = request.getSession();
		String zipcode = request.getParameter("zipcode");
		//session.setAttribute("user_zipcode", zipcode);

		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		String fileData = utility.printHtml("Content.html");

			String startStr = fileData.substring(0, fileData.indexOf("<div class=\"row\">"));
			StringBuffer sb = new StringBuffer();
			sb.append("<div class=\"row\"><div class=\"section-header text-center\"><h2 class=\"title\">Welcome to Trading Portal</h2></div>");

			List<Product> list = MySqlDataStoreUtilities.getZipcodeProducts(zipcode);
			if(list == null || list.isEmpty()){
				list = ProductsHashMap.getAllProducts();
			}
			else{
				sb.append("<p>Goods on sale near you</p>");
			}
			Set<Product> hset = new HashSet<Product>(list);

			if(hset != null)
			{	        	
	        	sb.append("<table class='carousel_table'><tr><td>"
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
										+pp.getTitle()+"<br/>$"+pp.getPrice());
	        					sb.append("<form action='ViewProduct' method='post'>");
								sb.append("<input type='hidden' name='pid' value='"+pp.getId()+"'>");
								sb.append("<input type='hidden' name='ptitle' value='"+pp.getTitle()+"'>");
								sb.append("<input type='hidden' name='pcategory' value='"+pp.getCategory()+"'>");
								sb.append("<input type='hidden' name='pdescription' value='"+pp.getDescription()+"'>");
								sb.append("<input type='hidden' name='pimage' value='"+pp.getImage()+"'>");
								sb.append("<input type='hidden' name='pprice' value='"+pp.getPrice()+"'>");
								sb.append("<input type='hidden' name='pcondition' value='"+pp.getCondition()+"'>");
								sb.append("<input type='hidden' name='pnegotiable' value='"+pp.getNegotiable()+"'>");
								sb.append("<input type='hidden' name='ppickupaddress' value='"+pp.getPickupaddress()+"'>");
								sb.append("<input type='hidden' name='ppostdate' value='"+pp.getPostdate()+"'>");
								sb.append("<input type='hidden' name='ppostedBy' value='"+pp.getPostedBy()+"'>");
								sb.append("<input type='hidden' name='pcomments' value='"+pp.getComments()+"'>");
								sb.append("<input type='hidden' name='pzipcode' value='"+pp.getZipcode()+"'>");
								sb.append("<input type='hidden' name='pauctionenddate' value='"+pp.getAuctionEndDate()+"'>");
								sb.append("<input type='hidden' name='phighestbidby' value='"+pp.getHighestBidBy()+"'>");
								sb.append("<input type='hidden' name='markSold' value='"+pp.getMarkSold()+"'>");
								sb.append("<input type='submit' name='viewItemBtn' class='main-btn' value='View'>");
								sb.append("</form>");
								
								sb.append("<form action='AddToCart' method='post'>"
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
										+"</form>");
							sb.append("</div>"
								+"</div>");
						i++;
	        		}
	        		else{
	        			sb.append("<div class='item'>"
									+"<div style='text-align: center'>"
										+"<img class='product-image' style='width: 150px; height:150px;' src='images/"+pp.getImage()+"'>"
									+"</div>"
									+"<div style='text-align: center'>"
										+pp.getTitle()+"<br/>$"+pp.getPrice());
	        					sb.append("<form action='ViewProduct' method='post'>");
								sb.append("<input type='hidden' name='pid' value='"+pp.getId()+"'>");
								sb.append("<input type='hidden' name='ptitle' value='"+pp.getTitle()+"'>");
								sb.append("<input type='hidden' name='pcategory' value='"+pp.getCategory()+"'>");
								sb.append("<input type='hidden' name='pdescription' value='"+pp.getDescription()+"'>");
								sb.append("<input type='hidden' name='pimage' value='"+pp.getImage()+"'>");
								sb.append("<input type='hidden' name='pprice' value='"+pp.getPrice()+"'>");
								sb.append("<input type='hidden' name='pcondition' value='"+pp.getCondition()+"'>");
								sb.append("<input type='hidden' name='pnegotiable' value='"+pp.getNegotiable()+"'>");
								sb.append("<input type='hidden' name='ppickupaddress' value='"+pp.getPickupaddress()+"'>");
								sb.append("<input type='hidden' name='ppostdate' value='"+pp.getPostdate()+"'>");
								sb.append("<input type='hidden' name='ppostedBy' value='"+pp.getPostedBy()+"'>");
								sb.append("<input type='hidden' name='pcomments' value='"+pp.getComments()+"'>");
								sb.append("<input type='hidden' name='pzipcode' value='"+pp.getZipcode()+"'>");
								sb.append("<input type='hidden' name='pauctionenddate' value='"+pp.getAuctionEndDate()+"'>");
								sb.append("<input type='hidden' name='phighestbidby' value='"+pp.getHighestBidBy()+"'>");
								sb.append("<input type='hidden' name='markSold' value='"+pp.getMarkSold()+"'>");
								sb.append("<input type='submit' name='viewItemBtn' class='main-btn' value='View'>");
								sb.append("</form>");
								
								sb.append("<form action='AddToCart' method='post'>"
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
										+"</form>");
										
							sb.append("</div>"
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

			String endStr = fileData.substring(fileData.indexOf("<div class=\"row\">"));
			fileData = startStr + sb.toString() + endStr;

		pw.print(fileData);

		utility.printHtml("Footer.html");
	}

}