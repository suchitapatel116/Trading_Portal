import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.*;

@WebServlet("/Trending")

public class Trending extends HttpServlet
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
		sb.append("<h2 class='title'>Trending</h2>");

		sb.append("<h3>Top 5 zipcodes where products are sold</h3>");
		TreeMap<String, String> map_zipcode = MySqlDataStoreUtilities.getTopZipcodeProducts();
		if(!(map_zipcode == null && map_zipcode.isEmpty()))
		{
			sb.append("<table>"+" <tr>"+ "<th>Zipcode</th>"+" <th>Count</th>"+"</tr>");

			Iterator itr = map_zipcode.entrySet().iterator();
			int count = 0;
			while(itr.hasNext())
			{
				Map.Entry entry = (Map.Entry)itr.next();
				sb.append("<tr><td>"+entry.getKey()+"</td><td>"+entry.getValue()+"</td></tr>");
				count++;
				if(count > 5)
					break;
			}
			sb.append("</table>");
		}

		sb.append("<br><h3>Top 5 rated sellers</h3>");
		//<rating, seller name>
		MongoDbDataStoreUtilities mdb = new MongoDbDataStoreUtilities();
   		mdb.connect();
		HashMap<String, ArrayList<String>> map = mdb.getTopSellerRatings();
		if(!(map == null && map.isEmpty()))
		{
			sb.append("<table>"+" <tr>"+ "<th>Seller</th>"+" <th>Ratings</th>"+"</tr>");
			int i = 5, count = 0;
			while(i > 0)
			{
				ArrayList<String> sellerList = map.get(""+i);
				if(sellerList != null)
				{
					for(String name : sellerList){
						sb.append("<tr><td>"+name+"</td><td>"+i+"</td></tr>");
						count++;
						if(count > 5)
							i=1;	//break;
					}
				}
				else
					sb.append("no");
				i--;
			}
			sb.append("</table>");
		}


		sb.append("<br><h3>Top 5 rated buyers</h3>");
		//<rating, seller name>
		mdb = new MongoDbDataStoreUtilities();
   		mdb.connect();
		map = mdb.getTopBuyerRatings();
		if(!(map == null && map.isEmpty()))
		{
			sb.append("<table>"+" <tr>"+ "<th>Buyer</th>"+" <th>Ratings</th>"+"</tr>");
			int i = 5, count = 0;
			while(i > 0)
			{
				ArrayList<String> sellerList = map.get(""+i);
				if(sellerList != null)
				{
					for(String name : sellerList){
						sb.append("<tr><td>"+name+"</td><td>"+i+"</td></tr>");
						count++;
						if(count > 5)
							i=1;	//break;
					}
				}
				//else
					//sb.append("no");
				i--;
			}
			sb.append("</table>");
		}

		sb.append("</div></section>");
		String contentData = sb.toString();
		pw.println(contentData);
		utility.printHtml("Footer.html");
	}
}