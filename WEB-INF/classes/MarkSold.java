import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/MarkSold")

public class MarkSold extends HttpServlet
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
			request.setAttribute("msg", "Please Login as a Seller to Mark the products sold");
			RequestDispatcher rd = request.getRequestDispatcher("/Login");
			rd.forward(request, response);
			return;
		}
		String uname = session.getAttribute("username").toString();

		String id = request.getParameter("pid");
        String title = request.getParameter("ptitle");
        String category = request.getParameter("pcategory");
        //boolean markSold = true;
        int markSold = 1;

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
		String auction_endDate = request.getParameter("pauctionenddate");
		String highest_bidBy = request.getParameter("phighestbidby");

		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		Date date = new Date();
		String orderDate = sdf.format(date);

		StringBuffer sb = new StringBuffer();
		sb.append("<section id='content'><div class='row'>");

		HashMap<String, User> users_map = MySqlDataStoreUtilities.getUsers();
		
		sb.append("<fieldset>");
		sb.append("<form action='MarkSoldData' method='post'>");
		sb.append("<input type='hidden' name='pid' value='"+id+"' />");
		sb.append("<input type='hidden' name='ptitle' value='"+title+"' />");
		sb.append("<input type='hidden' name='pcategory' value='"+category+"' />");
		sb.append("<input type='hidden' name='pdescription' value='"+description+"' />");
		sb.append("<input type='hidden' name='pimage' value='"+image+"' />");
		sb.append("<input type='hidden' name='pprice' value='"+price+"' />");
		sb.append("<input type='hidden' name='pcondition' value='"+condition+"' />");
		sb.append("<input type='hidden' name='pnegotiable' value='"+negotiable+"' />");
		sb.append("<input type='hidden' name='ppickupaddress' value='"+pickupaddress+"' />");
		sb.append("<input type='hidden' name='ppostdate' value='"+postdate+"' />");
		sb.append("<input type='hidden' name='ppostedBy' value='"+postedBy+"' />");
		sb.append("<input type='hidden' name='pcomments' value='"+comments+"' />");
		sb.append("<input type='hidden' name='pzipcode' value='"+zipcode+"' />");
		sb.append("<input type='hidden' name='pauctionenddate' value='"+auction_endDate+"' />");
		sb.append("<input type='hidden' name='phighestbidby' value='"+highest_bidBy+"' />");

		if(users_map == null || users_map.equals(""))
			sb.append("<p>"
                	+"<label for='pbuyer'>Enter buyer:</label>"
                    +"<input type='text' name='pbuyer' placeholder='Enter the buyer name' style='width: 30%;' required />"
                	+"</p>");
		else
		{
			Iterator itr = users_map.entrySet().iterator();
			Set<String> set = new HashSet<>();
			User user;

			while(itr.hasNext())
			{
				Map.Entry entry = (Map.Entry)itr.next();
				user = (User) entry.getValue();
				if(user.getUserType().equals("buyer"))
					set.add(entry.getKey().toString());
			}
			sb.append("<p><label for='pbuyer'>Select buyer:</label>");
			sb.append("<select name='pbuyer' style='width: 30%;' required>");
			for(String entry : set)
			{
				sb.append("<option value='"+entry+"' >"+entry+"</option> ");
			}
			sb.append("</select></p>");
		}
		sb.append("<p><input name='marksold' style='margin-left: 200px; width: 30%;' class='main-btn' value='Mark Item Sold' type='submit' /></p>");
		sb.append("</form>");
		sb.append("</fieldset>");

		sb.append("</div></section>");
		String contentData = sb.toString();
		pw.println(contentData);
		utility.printHtml("Footer.html");
	}
}