import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.*;

@WebServlet("/Auctions")

public class Auctions extends HttpServlet
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
		sb.append("<div class='section-header text-center'><h2 class='title'>Current held Auctions</h2></div>");

		List<Product> auctions_list = new ArrayList<>();
		auctions_list = ProductsHashMap.getAuctionsList();

		Product product;
		if(auctions_list == null || auctions_list.size() == 0)
		{
			sb.append("<p style='font-size:20px; margin-bottom:10px;'>Currently there are no Auctions being conducted. Please come back later.</p>");
		}
		else
		{
			for(int i=0; i<auctions_list.size(); i++)
			{
				product = (Product)auctions_list.get(i);
				
				sb.append("<div class='col-md-4'>");
					sb.append("<div class='blog'>");
						sb.append("<div class='blog-img'><img class='img-responsive' src='./images/"+product.getImage()+"'></div>");
						sb.append("<div class='blog-content'>");
						sb.append("<ul class='blog-meta'>");
							sb.append("<li><i class='fa fa-user'></i>"+product.getPostedBy()+"</li>");
							sb.append("<li><i class='fa fa-clock-o'></i>"+product.getPostdate().substring(0, product.getPostdate().lastIndexOf("-"))+"</li>");
							sb.append("<li><i class='fa fa-money'></i>$"+product.getPrice()+"</li>");
						sb.append("</ul><h3>"+product.getTitle()+"</h3>");
							sb.append("<p>"+product.getDescription()+"</p>");
						sb.append("<p>Auction ends on "+product.getAuctionEndDate()+"</p>");
						
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

						sb.append("<form action='ViewProduct' method='post'>");
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
						sb.append("<input type='submit' name='wishListBtn' class='main-btn' value='Add Bid'>");
						sb.append("</form>");

				sb.append("</div></div></div>");
			}
		}
		sb.append("</div></section>");

		String contentData = sb.toString();
		pw.println(contentData);
		utility.printHtml("Footer.html");
	}
}
