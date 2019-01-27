import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/EditProduct")

public class EditProduct extends HttpServlet
{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		HttpSession session = request.getSession();

		if(session.getAttribute("username") == null || !session.getAttribute("usertype").toString().equals("seller")){
			request.setAttribute("msg", "Please Login as a Seller to Add New Product");
			RequestDispatcher rd = request.getRequestDispatcher("/Login");
			rd.forward(request, response);
			return;
		}
		String uname = session.getAttribute("username").toString();

        String pid = request.getParameter("pid");
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
        String auction_enddate = request.getParameter("pauctionenddate");
        String highest_bidby = request.getParameter("phighestbidby");
        String mark_sold = request.getParameter("pmarksold");

		Map<String, List<Product>> map = ProductsHashMap.getProductMap();
		//if(map == null)
			//categories.add("miscellaneous");
		Set<String> categories = map.keySet();
		Iterator<String> itr = categories.iterator();

        StringBuffer sb = new StringBuffer();
		sb.append("<section id='content'><div class='row'>"
			+"<h2 class='title'>Edit Product</h2>"
        	+"<fieldset>"
            	+"<form action='EditProductData' method='post'>"
                    +"<input type='hidden' name='pid' value='"+pid+"' />"
                    +"<input type='hidden' name='ptitle' value='"+title+"'>"
                    +"<input type='hidden' name='ppostedBy' value='"+postedBy+"'>"
                    +"<input type='hidden' name='ppostdate' value='"+postdate+"'>"
                    +"<input type='hidden' name='phighestbidby' value='"+highest_bidby+"'>"
                    //+"<input type='hidden' name='pcomments' value='"+comments+"'>"
                    +"<input type='hidden' name='pmarksold' value='"+mark_sold+"'>");

        sb.append("<p>"
                	+"<label for='pcategory'>Product Category:</label>"
                    +"<select name='pcategory' style='width: 30%;' required>"
                    +"<option value='"+category+"' selected >"+category+"</option> ");
                    while(itr.hasNext()){
						String cat = itr.next().toString();
                    	sb.append("<option value='"+cat+"' >"+cat+"</option> ");
                    }
            sb.append("</select>"
                +"</p><p>"
                	+"<label for='ptitle'>Title:</label>"
                    //+"<input type='text' name='ptitle' placeholder='Enter Product Name' style='width: 30%;' required />"
                    +title
                +"</p><p>"
                	+"<label for='pdescription'>Description:</label>"
                    +"<input type='text' name='pdescription' placeholder='Enter Product Description' style='width: 30%;' value='"+description+"' />"
                +"</p><p>"
                	+"<label for='pimage'>Image:</label>"
                    +"<input type='file' name='pimage' accept='' style='width: 30%;' value='"+image+"' required />");
                if(category.equals("auction") && (highest_bidby == null || highest_bidby.equals("") || highest_bidby.equals("null")))
                {
                    sb.append("</p><p>"
                    +"<label for='pprice'>Price:</label>"+price);
                }
                else
                {
                    sb.append("</p><p>"
                	+"<label for='pprice'>Price:</label>"
                    +"<input type='text' name='pprice' placeholder='Enter Product Price' style='width: 30%;' value='"+price+"' required />");
                }
                if(!category.equals("giveaway"))
                {
                    sb.append("</p><p>"
                	+"<label for='pcondition'>Condition:</label>"
                    +"<input type='text' name='pcondition' placeholder='Enter Product Condition' style='width: 30%;' value='"+condition+"' required />");
                }
                if(!category.equals("giveaway") && !category.equals("auction"))
                {
                    sb.append("</p><p>"
                	+"<label for='pnegotiable'>Product Negotiable:</label>"
                    +"<select name='pnegotiable' style='width: 30%;' required>");
                
                    if(negotiable.equalsIgnoreCase("No"))
                    	sb.append("<option value='No' >No</option> "
                    	           +"<option value='Yes' >Yes</option> ");
                    else
                        sb.append("<option value='Yes' >Yes</option> "
                                   +"<option value='No' >No</option> ");
                }
            sb.append("</select>"
                +"</p><p>"
                	+"<label for='ppickupaddress'>Pickup Address:</label>"
                    +"<input type='text' name='ppickupaddress' placeholder='Enter Pickup Address' style='width: 30%;' value='"+pickupaddress+"' required />"    
                +"</p><p>"
                	+"<label for='pzipcode'>Zipcode:</label>"
                    +"<input type='text' name='pzipcode' placeholder='Enter Zipcode' style='width: 30%;' value='"+zipcode+"' required />");
                if(category.equals("auction"))
                {
                    sb.append("</p><p>"
                    +"<label for='pauctionenddate'>Auction end date:</label>"
                    +"<input type='text' name='pauctionenddate' placeholder='Enter Auction end date in MM-dd-yyyy format' style='width: 30%;' value='"+auction_enddate+"' required />");
                }
                else
                    sb.append("<input type='hidden' name='pauctionenddate' value='"+auction_enddate+"' />");
        sb.append("</p>");
            
                /*
                if(mark_sold.equals("true") || mark_sold.equals("1"))
                {            
                    sb.append("<p><label for='pmarksold'>Is product sold:</label>");
                    sb.append("<select name='pmarksold' style='width: 30%;' required>");
                    sb.append("<option value='false' selected >Not Sold</option> ");
                    sb.append("<option value='true' >Mark Sold</option> ");

                    sb.append("</select></p>");
                }
                else
                {
                    //sb.append("<option value='true' selected >Mark Sold</option> ");
                    //sb.append("<option value='false'>Not Sold</option> ");
                }
                */
            
        sb.append("<p id='test'><input name='update' style='margin-left: 200px; width: 30%;' class='main-btn' value='Update Product' type='submit' /></p>"
            +"</form>"
        +"</fieldset>"
        +"</div></section>");

        String contentData = sb.toString();
        pw.println(contentData);
		utility.printHtml("Footer.html");
	}
}