import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Utilities")

public class Utilities extends HttpServlet
{
	String url;
	PrintWriter pw;
	HttpServletRequest request;
	HttpSession session;

	public Utilities(HttpServletRequest req, PrintWriter p)
	{
		this.request = req;
		this.pw = p;
		this.url = getFullUrl();
		this.session = req.getSession(true);
	}

	public String getFullUrl()
	{
		String url = "http://localhost:80/" + request.getContextPath() + "/";
		return url;
	}

	public String printHtml(String htmlFile)
	{
		String fileData = ReadHtmlToString(htmlFile);
		if(htmlFile == "Header.html")
		{
			String startStr = fileData.substring(0, fileData.indexOf("<body onload=\"init()\">"));

			String top_row = "<body onload=\"init()\">"
						+"<script type=\"text/javascript\" src=\"javascript_autoComplete.js\"></script>"
    					+"<div class='top_row'>"
        					+"<div class='logo_small'>"
            					+"<img style='width: 160px; height: 50px;' class='logo-alt' src='images/logo2.png' alt='logo' /></li>"
        					+"</div>"
        					+"<ul>";

        	if(session.getAttribute("username") != null)
			{
				String userName = session.getAttribute("username").toString();

				if(userName.equalsIgnoreCase("admin"))
				{
        top_row += "<li><a href='Logout'>Logout</a></li>"
					+"<li><a href='ViewOrders'><i class='glyphicon glyphicon-heart'></i>&nbsp;Wishlist()</a></li>"
							+"<li><a href=\"Inbox\"><i class='glyphicon glyphicon-envelope'></i>&nbsp;Inbox()</a></li>"
            				+"<li><a href=\"Admin\">Hi, "+userName+"</a></li>";
				}
				else
				{					
					int count = CartData.getUserCartCount(userName);
					
          int msgCount=0;
					msgCount = MySqlDataStoreUtilities.getMessageCount(userName);
					if(msgCount==0)
					{
					top_row += "<li><a href='Logout'>Logout</a></li>"
					+"<li><a href='ViewOrders'><i class='glyphicon glyphicon-heart'></i>&nbsp;Wishlist("+count+")</a></li>"
							+"<li><a href=\"Inbox\"><i class='glyphicon glyphicon-envelope'></i>&nbsp;Inbox("+msgCount+")</a></li>"
            				+"<li><a href=\"ViewPosts\">Hi, "+userName+"</a></li>";
							
          }
					else
					{
						
						top_row += "<li><a href='Logout'>Logout</a></li>"
						+"<li><a href='ViewOrders'><i class='glyphicon glyphicon-heart'></i>&nbsp;Wishlist("+count+")</a></li>"
							+"<li><a href=\"Inbox\"><i class='glyphicon glyphicon-envelope'></i>&nbsp;Inbox(<font color='red'>"+msgCount+" new messages</font>)</a></li>"
            				+"<li><a href=\"ViewPosts\">Hi, "+userName+"</a></li>";
					}	
				}
			}
			else
			{
				top_row += "<li><a href='ViewOrders'><i class='glyphicon glyphicon-heart'></i>&nbsp;Wishlist</a></li>"
            				+"<li><a href='Register'>Register</a></li>"
            				+"<li><a href='Login'>Login</a></li>";
			}
            top_row += "</ul></div>";

			String endStr = fileData.substring(fileData.indexOf("<div id='container'>"));

			fileData = startStr + top_row + endStr;
		}
		else if(htmlFile == "LeftNavigationBar.html")
		{
			String startStr = fileData.substring(0, fileData.indexOf("<div class=\"widget-category\">"));

			String top_row = "<div class=\"widget-category\">";
        	if(session.getAttribute("usertype") != null)
			{
				String userName = session.getAttribute("usertype").toString();
				if(userName.equalsIgnoreCase("admin"))
				{}
				else if(userName.equalsIgnoreCase("seller"))
					top_row += "<a href='ViewPosts'>View Posted Adds<span></span></a>";
				else if(userName.equalsIgnoreCase("buyer"))
					top_row += "<a href='ViewOrders'>View Bought Products<span></span></a>";	
			}
            top_row += "<a href='Trending'>Trending<span></span></a>";
            	top_row	+= "<a href='Ratings'>Ratings<span></span></a>"
            		+"</div></div>";

			String endStr = fileData.substring(fileData.indexOf("<ul>"));

			fileData = startStr + top_row + endStr;
		}
		else if(htmlFile == "Content.html")
		{
			return fileData;
		}

		pw.print(fileData);
		return null;
	}

	public String ReadHtmlToString(String htmlFile)
	{
		String fileContent = null;
		try {
			String webPage = url + htmlFile;
			URL url = new URL(webPage);
			URLConnection conn = url.openConnection();
			InputStream is = conn.getInputStream();
			InputStreamReader reader = new InputStreamReader(is);

			int numCharRead;
			char[] ch = new char[1024];
			StringBuffer sb = new StringBuffer();
			while((numCharRead = reader.read(ch)) > 0)
				sb.append(ch, 0, numCharRead);
			fileContent = sb.toString();
		}
		catch(Exception e)
		{ System.out.println(e.getMessage()); }
		return fileContent;
	}

	public void logout()
	{
		session.removeAttribute("username");
		session.removeAttribute("usertype");
	}
}