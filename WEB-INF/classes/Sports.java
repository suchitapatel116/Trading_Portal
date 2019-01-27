import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.*;

@WebServlet("/Sports")

public class Sports extends HttpServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		HttpSession session = request.getSession();

		StringBuffer sb = new StringBuffer();
		sb.append("<section id='content'><div class='row'>");
		sb.append("<div class='section-header text-center'><h2 class='title'>Sports</h2></div>");

		List<Product> electronics_list = new ArrayList<>();
		electronics_list.addAll(ProductsHashMap.getSportsList());
		electronics_list.addAll(ProductsHashMap.getGymList());

		Product product;
		if(electronics_list == null)
		{
			sb.append("<p>null data</p>");
		}
		else
		{
			for(int i=0; i<electronics_list.size(); i++)
			{
				product = (Product)electronics_list.get(i);
				//session.setAttribute(SESSION_ADDTOCART, product);
				
				sb.append("<div class='col-md-4'>");
					sb.append("<div class='blog'>");
						sb.append("<div class='blog-img'><img class='img-responsive' src='./images/"+product.getImage()+"'></div>");
						sb.append("<div class='blog-content'>");
						sb.append("<ul class='blog-meta'>");
							sb.append("<li><i class='fa fa-user'></i>"+product.getPostedBy()+"</li>");
							sb.append("<li><i class='fa fa-clock-o'></i>"+product.getPostdate().substring(0, product.getPostdate().lastIndexOf("-"))+"</li>");
							sb.append("<li><i class='fa fa-money'></i>$"+product.getPrice()+"</li>");
						sb.append("</ul><h3>"+product.getTitle()+"</h3><p></p>");
						
						sb.append("<form action='ViewProduct' method='post' style='float:left;'>");
						sb.append("<input type='hidden' name='pid' value='"+product.getId()+"'>");
						sb.append("<input type='hidden' name='ptitle' value='"+product.getTitle()+"'>");
						sb.append("<input type='hidden' name='pcategory' value='"+product.getCategory()+"'>");
						sb.append("<input type='hidden' name='pdescription' value='"+product.getDescription()+"'>");
						sb.append("<input type='hidden' name='pimage' value='"+product.getImage()+"'>");
						sb.append("<input type='hidden' name='pprice' value='"+product.getPrice()+"'>");
						sb.append("<input type='hidden' name='pcondition' value='"+product.getCondition()+"'>");
						sb.append("<input type='hidden' name='pnegotiable' value='"+product.getNegotiable()+"'>");
						sb.append("<input type='hidden' name='ppickupaddress' value='"+product.getPickupaddress()+"'>");
						sb.append("<input type='hidden' name='ppostdate' value='"+product.getPostdate()+"'>");
						sb.append("<input type='hidden' name='ppostedBy' value='"+product.getPostedBy()+"'>");
						sb.append("<input type='hidden' name='pcomments' value='"+product.getComments()+"'>");
						sb.append("<input type='hidden' name='pzipcode' value='"+product.getZipcode()+"'>");
						sb.append("<input type='hidden' name='pauctionenddate' value='"+product.getAuctionEndDate()+"'>");
						sb.append("<input type='hidden' name='phighestbidby' value='"+product.getHighestBidBy()+"'>");
						sb.append("<input type='hidden' name='markSold' value='"+product.getMarkSold()+"'>");
						sb.append("<input type='submit' name='viewItemBtn' class='main-btn' value='View'>");
						sb.append("</form>");

						sb.append("<form action='AddToCart' method='post'>");
						sb.append("<input type='hidden' name='pid' value='"+product.getId()+"'>");
						sb.append("<input type='hidden' name='ptitle' value='"+product.getTitle()+"'>");
						sb.append("<input type='hidden' name='pcategory' value='"+product.getCategory()+"'>");
						sb.append("<input type='hidden' name='pdescription' value='"+product.getDescription()+"'>");
						sb.append("<input type='hidden' name='pimage' value='"+product.getImage()+"'>");
						sb.append("<input type='hidden' name='pprice' value='"+product.getPrice()+"'>");
						sb.append("<input type='hidden' name='pcondition' value='"+product.getCondition()+"'>");
						sb.append("<input type='hidden' name='pnegotiable' value='"+product.getNegotiable()+"'>");
						sb.append("<input type='hidden' name='ppickupaddress' value='"+product.getPickupaddress()+"'>");
						sb.append("<input type='hidden' name='ppostdate' value='"+product.getPostdate()+"'>");
						sb.append("<input type='hidden' name='ppostedBy' value='"+product.getPostedBy()+"'>");
						sb.append("<input type='hidden' name='pcomments' value='"+product.getComments()+"'>");
						sb.append("<input type='hidden' name='pzipcode' value='"+product.getZipcode()+"'>");
						sb.append("<input type='hidden' name='pauctionenddate' value='"+product.getAuctionEndDate()+"'>");
						sb.append("<input type='hidden' name='phighestbidby' value='"+product.getHighestBidBy()+"'>");
						sb.append("<input type='submit' name='wishListBtn' class='main-btn' value='Add to List'>");
						sb.append("</form>");

						//Write Reviews Section
						String userName = (String) session.getAttribute("username");
						
						if(userName != null && !(userName.isEmpty()))
						{
							sb.append("<form action='WriteReview' method='post'>");
							sb.append("<input type='hidden' name='pid' value='"+product.getId()+"'>");
							sb.append("<input type='hidden' name='ptitle' value='"+product.getTitle()+"'>");
							sb.append("<input type='hidden' name='uname' value='"+userName+"'>");
							sb.append("<input type='hidden' name='pcategory' value='"+product.getCategory()+"'>");
							sb.append("<input type='hidden' name='pdescription' value='"+product.getDescription()+"'>");
							sb.append("<input type='hidden' name='pimage' value='"+product.getImage()+"'>");
							sb.append("<input type='hidden' name='pprice' value='"+product.getPrice()+"'>");
							sb.append("<input type='hidden' name='pcondition' value='"+product.getCondition()+"'>");
							sb.append("<input type='hidden' name='pnegotiable' value='"+product.getNegotiable()+"'>");
							sb.append("<input type='hidden' name='ppickupaddress' value='"+product.getPickupaddress()+"'>");
							sb.append("<input type='hidden' name='ppostdate' value='"+product.getPostdate()+"'>");
							sb.append("<input type='hidden' name='ppostedBy' value='"+product.getPostedBy()+"'>");
							sb.append("<input type='hidden' name='pcomments' value='"+product.getComments()+"'>");
							sb.append("<input type='hidden' name='pzipcode' value='"+product.getZipcode()+"'>");
							sb.append("<input type='submit' name='writereviewBtn' class='main-btn' style='width:96%;' value='Write Review'>");		
							sb.append("</form>");							
						}

				sb.append("</div></div></div>");
			}
		}
		sb.append("</div></section>");

		String contentData = sb.toString();
		pw.println(contentData);
		utility.printHtml("Footer.html");
	}
}
