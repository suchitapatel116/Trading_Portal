import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/SearchedProductData")

public class SearchedProductData extends HttpServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");

		Product product = (Product)request.getAttribute("product_data");

		StringBuffer sb = new StringBuffer();
		sb.append("<section id='content'><div class='row'>");
		sb.append("<div class='section-header text-center'><h2 class='title'>Electronics</h2></div>");

		if(!product.getCategory().equals("auction") && !product.getCategory().equals("giveaway"))
		{
			sb.append("<div class='col-md-4'>");
					sb.append("<div class='blog'>");
						sb.append("<div class='blog-img'><img class='img-responsive' src='./images/"+product.getImage()+"'></div>");
						sb.append("<div class='blog-content'>");
						sb.append("<ul class='blog-meta'>");
							sb.append("<li><i class='fa fa-user'></i>"+product.getPostedBy()+"</li>");
							sb.append("<li><i class='fa fa-clock-o'></i>"+product.getPostdate().substring(0, product.getPostdate().lastIndexOf("-"))+"</li>");
							sb.append("<li><i class='fa fa-money'></i>$"+product.getPrice()+"</li>");
						sb.append("</ul><h3>"+product.getTitle()+"</h3>");

						sb.append("<p><table>");
						sb.append("<tr><td>Category: </td><td>"+product.getCategory()+"</td></tr>");
						//if(product.getDescription() != null || !product.getDescription().toString().trim().equals(""))
							sb.append("<tr><td>Description: </td><td>"+product.getDescription()+"</td></tr>");
						if(product.getMarkSold()==1)
						{
							sb.append("<tr><td>Availability: </td><td>Sold</td></tr>");
						}
						else
						{
							sb.append("<tr><td>Availability: </td><td>On Sale</td></tr>");
						}
						
						sb.append("<tr><td>Condition: </td><td>"+product.getCondition()+"</td></tr>");
						sb.append("<tr><td>Negotiable: </td><td>"+product.getNegotiable()+"</td></tr>");
						sb.append("<tr><td>Address: </td><td>"+product.getPickupaddress()+"</td></tr>");
						sb.append("<tr><td>Zipcode: </td><td>"+product.getZipcode()+"</td></tr>");
						if(product.getCategory().equals("auction")){
							sb.append("<tr><td>Auction end date: </td><td>"+product.getAuctionEndDate()+"</td></tr>");
							sb.append("<tr><td>Highest bid by: </td><td>"+product.getHighestBidBy()+"</td></tr>");
						}
						sb.append("</table></p>");
						
					sb.append("<form action='SendMessage' method='post'>");
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
					//sb.append("<input type='hidden' name='pcomments' value='"+product.getco+"'>");
					sb.append("<input type='hidden' name='pzipcode' value='"+product.getZipcode()+"'>");
					sb.append("<input type='hidden' name='pauctionenddate' value='"+product.getAuctionEndDate()+"'>");
					sb.append("<input type='hidden' name='phighestbidby' value='"+product.getHighestBidBy()+"'>");
					sb.append("<input type='submit' class='main-btn' name='contactSeller' value='Contact Seller'>");
					sb.append("</form>");	
					
				sb.append("</div></div></div>");
		}


		sb.append("</div></section>");
		String contentData = sb.toString();
		pw.println(contentData);
		utility.printHtml("Footer.html");
	}
}