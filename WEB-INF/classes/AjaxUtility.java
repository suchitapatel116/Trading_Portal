import java.io.*;
import java.util.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class AjaxUtility
{
	static Connection conn = null;
	boolean namesAdded = false;
	
	public static boolean getConnection(){
		boolean isConnected = true;
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TradingPortalDB", "root", "root");
		}
		catch(Exception e){
			System.out.println("Error: No connection to database");
			isConnected = false;
		}
		return isConnected;
	}

	public StringBuffer readData(String searchId)
	{
		StringBuffer sb = new StringBuffer();
		Product pd;
		HashMap<String, Product> data_map;
		data_map = getData();

		Iterator it = data_map.entrySet().iterator();	
		while (it.hasNext()) 
		{
			Map.Entry entry = (Map.Entry)it.next();
			if(entry != null)
			{
				pd = (Product)entry.getValue();                   
				if (pd.getTitle().toLowerCase().startsWith(searchId))
				{
					sb.append("<product>");
					sb.append("<id>" + pd.getTitle().trim() + "</id>");
					sb.append("<productName>" + pd.getTitle().trim() + "</productName>");
					sb.append("</product>");
                }
			}
       }
	   return sb;
	}

	public static HashMap<String, Product> getData()
	{
		HashMap<String, Product> map = new HashMap<>();
		try
		{
			getConnection();
			String selectProduct_query = "SELECT * FROM Product;";
			PreparedStatement pst = conn.prepareStatement(selectProduct_query);
			ResultSet rs = pst.executeQuery();

			Product pd;
			while(rs.next())
			{
				pd = new Product(
					""+rs.getString("pid"),
					rs.getString("ptitle").trim(),
					rs.getString("pcategory"),
					rs.getString("pdescription"),
					rs.getString("pimage"),
					""+rs.getString("pprice"),
					rs.getString("pcondition"),
					rs.getString("pnegotiable"),
					rs.getString("ppickupaddress"),
					rs.getString("ppostdate"),
					rs.getString("ppostedby"),
					rs.getString("pcomments"),
					rs.getString("zipcode"),
					rs.getString("auction_endDate"),
					rs.getString("highest_bidBy"),
					rs.getInt("markSold"));

				map.put(rs.getString("ptitle"), pd);
			}
		}
		catch(Exception e) {
			map = null;
			e.printStackTrace();
		}
		return map;			
	}

}