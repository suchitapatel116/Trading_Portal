import java.sql.*;
import java.util.*;

public class MySqlDataStoreUtilities
{
	static Connection conn = null;

	public static boolean getConnection()
	{
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

	public static boolean addUser(String uname, String password, String usertype, String email, String contact_no)
	{
		boolean isSuccessfull = true;
		try{
			getConnection();
			String insertUser_query = "INSERT into Registration(username, password, usertype, email, contactNo) VALUES(?,?,?,?,?);";

			PreparedStatement pst = conn.prepareStatement(insertUser_query);
			pst.setString(1, uname);
			pst.setString(2, password);
			pst.setString(3, usertype);
			pst.setString(4, email);
			pst.setString(5, contact_no);
			pst.execute();
		}
		catch(Exception e){
			System.out.println("Error: addUser: In user insertion");
			isSuccessfull = false;
		}
		return isSuccessfull;
	}

	public static boolean deleteUser(String uname)
	{
		boolean isSuccessfull = true;
		try{
			getConnection();
			String delUser_query = "DELETE FROM Registration where username=?";
			PreparedStatement pst = conn.prepareStatement(delUser_query);
			pst.setString(1, uname);
			pst.executeUpdate();
		}
		catch(Exception e){
			System.out.println("Error: deleteUser: In user deletion");
			isSuccessfull = false;
		}
		return isSuccessfull;
	}

	public static HashMap<String, User> getUsers()
	{
		HashMap<String, User> map = null;
		try{
			getConnection();
			String selectUser_query = "SELECT * FROM Registration";

			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(selectUser_query);
			User user;
			map = new HashMap<>();
			while(rs.next()){
				user = new User(rs.getString("username"), rs.getString("password"), rs.getString("usertype"), rs.getString("email"), rs.getString("contactNo"));
				map.put(rs.getString("username"), user);
			}
		}
		catch(Exception e){
			System.out.println("Error: getUsers: Cannot fetch the Users data");
		}
		return map;
	}

	//change
	
	public static boolean insertProductsInDB(Map<String, List<Product>> productmap)
	{
		int pid = getProductCount();
		boolean isSuccessfull = true;
		try{
			getConnection();
			String insert_query = "INSERT into Product(pid, ptitle, pcategory, pdescription, pimage, pprice, pcondition, pnegotiable,"
				+"ppickupaddress, ppostdate, ppostedby, pcomments, zipcode, auction_endDate, highest_bidBy, markSold) "
				+"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pst = conn.prepareStatement(insert_query);

			//String title, category, description, address, postedby, zipcode, highestbidby;

			List<Product> list = new ArrayList<>();
			for(String key: productmap.keySet())
			{
				list.clear();
				list.addAll(productmap.get(key));
				Product pd;

				for(int i=0; i<list.size(); i++)
				{
					try{					
						pd = list.get(i);

						pid += 1;
						pst.setInt(1, pid);
						String title = pd.getTitle().trim().replace("\n", "");
						pst.setString(2, title);
						pst.setString(3, key);
						pst.setString(4, pd.getDescription().replace("\n", ""));
						pst.setString(5, pd.getImage());
						pst.setDouble(6, Double.parseDouble(pd.getPrice()));
						pst.setString(7, pd.getCondition());
						pst.setString(8, pd.getNegotiable());
						String addr = pd.getPickupaddress().trim().replace("\n", "");
						pst.setString(9, addr);
						pst.setString(10, pd.getPostdate());
						String postedby = pd.getPostedBy().trim().replace("\n", "");
						pst.setString(11, postedby);
						pst.setString(12, pd.getComments());
						String zipcode = pd.getZipcode().trim().replace("\n", "");
						pst.setString(13, zipcode);
						pst.setString(14, pd.getAuctionEndDate());
						pst.setString(15, pd.getHighestBidBy());
						pst.setInt(16, pd.getMarkSold());

						pst.execute();
					}
					catch(Exception ex){ }
				}
			}
		}
		catch(Exception e){
			System.out.println("Error: insertProducts");
			System.out.println(e.getMessage());
			isSuccessfull = false;
		}
		return isSuccessfull;
	}

	public static boolean insertNewProductInDB(Product pd)
	{
		int pid = getProductCount();
		boolean isSuccessfull = true;
		try{
			getConnection();
			String insert_query = "INSERT into Product(pid, ptitle, pcategory, pdescription, pimage, pprice, pcondition, pnegotiable,"
				+"ppickupaddress, ppostdate, ppostedby, pcomments, zipcode, auction_endDate, highest_bidBy, markSold) "
				+"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pst = conn.prepareStatement(insert_query);
			
			pid += 1;
			pst.setInt(1, pid);
			String title = pd.getTitle().trim().replace("\n", "");
			pst.setString(2, title);
			pst.setString(3, pd.getCategory());
			pst.setString(4, pd.getDescription());
			pst.setString(5, pd.getImage());
			pst.setDouble(6, Double.parseDouble(pd.getPrice()));
			pst.setString(7, pd.getCondition());
			pst.setString(8, pd.getNegotiable());
			String addr = pd.getPickupaddress().trim().replace("\n", "");
			pst.setString(9, addr);

			pst.setString(10, pd.getPostdate());
			String postedby = pd.getPostedBy().trim().replace("\n", "");
			pst.setString(11, postedby);
			pst.setString(12, pd.getComments());
			String zipcode = pd.getZipcode().trim().replace("\n", "");
			pst.setString(13, zipcode);
			pst.setString(14, pd.getAuctionEndDate());
			pst.setString(15, pd.getHighestBidBy());
			pst.setInt(16, pd.getMarkSold());

			pst.execute();
		}
		catch(Exception e){
			System.out.println("Error: insertProduct");
			System.out.println(e.getMessage());
			isSuccessfull = false;
		}
		return isSuccessfull;
	}

	public static int getProductCount()
	{
		int count = 0;
		try{
			getConnection();
			String query = "SELECT count(*) AS count FROM Product;";
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			if(rs.next())
				count = rs.getInt("count");
			else
				count = 0;
		}
		catch(Exception e){		
			System.out.println("Error: getProductCount:");
			count = -1;
		}
		return count;
	}

	public static List<Product> getDBProductsList(String username, boolean isOrderByCategory)
	{
		List<Product> list = new ArrayList<>();
		try{
			getConnection();
			String products_query;
			if((username == null || username.equals("")) && isOrderByCategory == false)
				products_query = "SELECT * FROM Product WHERE markSold = 0";
			else if((username == null || username.equals("")) && isOrderByCategory == true)
				products_query = "SELECT * FROM Product WHERE markSold = 0 ORDER BY pcategory" ;
			else
				products_query = "SELECT * FROM Product WHERE ppostedby = '"+username+"';";

			PreparedStatement pst = conn.prepareStatement(products_query);
			ResultSet rs = pst.executeQuery();

			Product pd;
			while(rs.next())
			{
				pd = new Product(
					""+rs.getString("pid"),
					rs.getString("ptitle"),
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

				list.add(pd);
			}
		}
		catch(Exception e){
			System.out.println("Error: getDBProductsList: Cannot fetch products from database");
			list = null;
		}
		return list;
	}

	public static List<Product> getSoldProductsList()
	{
		List<Product> list = new ArrayList<>();
		try{
			getConnection();
			String products_query;
			products_query = "SELECT * FROM Product WHERE markSold = 1";
			
			PreparedStatement pst = conn.prepareStatement(products_query);
			ResultSet rs = pst.executeQuery();

			Product pd;
			while(rs.next())
			{
				pd = new Product(
					""+rs.getString("pid"),
					rs.getString("ptitle"),
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

				list.add(pd);
			}
		}
		catch(Exception e){
			System.out.println("Error: getSoldProductsList: Cannot fetch products from database");
			list = null;
		}
		return list;
	}

	public static boolean deleteProduct(String pid)
	{
		boolean isSuccessfull = true;
		try{
			getConnection();
			String delProduct_query = "DELETE FROM Product where pid = ?";
			PreparedStatement pst = conn.prepareStatement(delProduct_query);
			pst.setString(1, pid);
			pst.executeUpdate();
		}
		catch(Exception e){
			System.out.println("Error: deleteProduct: In product deletion");
			isSuccessfull = false;
		}
		return isSuccessfull;
	}

	public static boolean updateProduct(Product pd)
	{
		boolean isSuccessfull = true;
		try{
			getConnection();
			String update_query = "UPDATE Product SET pcategory = ?, pdescription = ?, pimage = ?, pprice = ?, pcondition = ?, "
				+"pnegotiable = ?, ppickupaddress = ?, ppostdate = ?, ppostedby = ?, pcomments = ?, zipcode = ?, "
				+"auction_endDate = ?, highest_bidBy = ?, markSold = ? WHERE ptitle = ?;";

			PreparedStatement pst = conn.prepareStatement(update_query);

			pst.setString(1, pd.getCategory());	
			pst.setString(2, pd.getDescription());
			pst.setString(3, pd.getImage());
			pst.setDouble(4, Double.parseDouble(pd.getPrice()));
			pst.setString(5, pd.getCondition());
			pst.setString(6, pd.getNegotiable());
			pst.setString(7, pd.getPickupaddress());
			pst.setString(8, pd.getPostdate());
			pst.setString(9, pd.getPostedBy());
			pst.setString(10, pd.getComments());
			pst.setString(11, pd.getZipcode());
			pst.setString(12, pd.getAuctionEndDate());
			pst.setString(13, pd.getHighestBidBy());
			pst.setInt(14, pd.getMarkSold());
			pst.setString(15, pd.getTitle());
		
			pst.execute();
		}
		catch(Exception e){
			System.out.println("Error: updateProduct in DB");
			System.out.println(e.getMessage());
			isSuccessfull = false;
		}
		return isSuccessfull;
	}

	public static boolean updateProductBid(String title, String bid_amount, String user)
	{
		boolean isSuccessfull = true;
		try{
			getConnection();
			String update_query = "UPDATE Product SET pprice = ?, highest_bidBy = ? WHERE ptitle = ?;";

			PreparedStatement pst = conn.prepareStatement(update_query);

			pst.setDouble(1, Double.parseDouble(bid_amount));
			pst.setString(2, user);
			pst.setString(3, title);
			
			pst.execute();
		}
		catch(Exception e){
			System.out.println("Error: updateProductBid:");
			System.out.println(e.getMessage());
			isSuccessfull = false;
		}
		return isSuccessfull;
	}

	public static boolean updateProduct_markSold(String title, int mark)
	{
		boolean isSuccessfull = true;
		try{
			getConnection();
			String update_query = "UPDATE Product SET markSold = ? WHERE ptitle = ?;";
			PreparedStatement pst = conn.prepareStatement(update_query);

			pst.setInt(1, mark);
			pst.setString(2, title);
			pst.execute();
		}
		catch(Exception e){
			System.out.println("Error: updateProduct_markSold:");
			System.out.println(e.getMessage());
			isSuccessfull = false;
		}
		return isSuccessfull;
	}

	public static boolean insertTradedGoods(String buyer, String seller, String title, String category, String price, String orderDate)
	{
		boolean isSuccessfull = true;
		try{
			getConnection();
			String query = "INSERT into TradedGoods (buyer, seller, productName, productCategory, productPrice, orderDate) VALUES (?,?,?,?,?,?);";
			PreparedStatement pst = conn.prepareStatement(query);

			pst.setString(1, buyer);
			pst.setString(2, seller);
			pst.setString(3, title);
			pst.setString(4, category);
			pst.setString(5, price);
			pst.setString(6, orderDate);
			
			pst.execute();
		}
		catch(Exception e){
			System.out.println("Error: insertTradedGoods:");
			System.out.println(e.getMessage());
			isSuccessfull = false;
		}
		return isSuccessfull;
	}

	public static boolean deleteFromTradedGoods(String title)
	{
		boolean isSuccessfull = true;
		try{
			getConnection();
			String query = "DELETE from TradedGoods WHERE productName = ? ;";
			PreparedStatement pst = conn.prepareStatement(query);

			pst.setString(1, title);			
			pst.execute();
		}
		catch(Exception e){
			System.out.println("Error: deleteFromTradedGoods:");
			System.out.println(e.getMessage());
			isSuccessfull = false;
		}
		return isSuccessfull;
	}

	public static HashSet<String> getUserOrderProducts(String buyer)
	{
		//map will contain the product name that is bought by the particular buyer
		HashSet<String> map = new HashSet<>();
		try{
			getConnection();
			String query = "SELECT * from TradedGoods WHERE buyer = '"+ buyer +"' ;";
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			Product pd;
			while(rs.next())
			{
				map.add(rs.getString("productName").toString());
			}

		}
		catch(Exception e){
			System.out.println("Error: getUserOrders:");
			System.out.println(e.getMessage());
			map = null;
		}
		return map;
	}

	public static boolean getUserOrderUser(String buyer)
	{
		boolean val = false;
		try{
			getConnection();
			String query = "SELECT * from TradedGoods WHERE buyer = '"+ buyer +"' ;";
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			Product pd;
			while(rs.next())
			{
				//map.add(rs.getString("productName").toString());
				val = true;
			}

		}
		catch(Exception e){
			System.out.println("Error: getUserOrderUser:");
			System.out.println(e.getMessage());
			val = false;
		}
		return val;
	}

	public static List<Product> getZipcodeProducts(String zipcode)
	{
		List<Product> list = new ArrayList<>();
		try{
			getConnection();
			String products_query;
			products_query = "SELECT * FROM Product WHERE zipcode LIKE '"+zipcode.substring(0,1)+"%'";
			
			PreparedStatement pst = conn.prepareStatement(products_query);
			ResultSet rs = pst.executeQuery();

			Product pd;
			while(rs.next())
			{
				pd = new Product(
					""+rs.getString("pid"),
					rs.getString("ptitle"),
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

				list.add(pd);
			}
		}
		catch(Exception e){
			System.out.println("Error: getSoldProductsList: Cannot fetch products from database");
			list = null;
		}
		return list;
	}


	public static boolean newMsg(String sender, String receiver, String subject, String message, String status, String product)
	{
		boolean isSuccessfull = true;
		try{
			getConnection();
			String insertMessage_query = "INSERT into Messages(sender, receiver, subject, msg, status,product) VALUES(?,?,?,?,?,?);";

			PreparedStatement pst = conn.prepareStatement(insertMessage_query);
			pst.setString(1, sender);
			pst.setString(2, receiver);
			pst.setString(3, subject);
			pst.setString(4, message);
			pst.setString(5, status);
			pst.setString(6, product);
			pst.execute();
		}
		catch(Exception e){
			System.out.println("Error: newMsg: In user insertion");
			isSuccessfull = false;
		}
		return isSuccessfull;
	}
	
	public static int getMessageCount(String receiver)
	{
		int count = 0;
		try{
			getConnection();
			String query = "SELECT count(*) AS count FROM messages WHERE receiver='"+receiver+"' AND status='unread';";
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			if(rs.next())
				count = rs.getInt("count");
			else
				count = 0;
		}
		catch(Exception e){		
			System.out.println("Error: getProductCount:");
			count = -1;
		}
		return count;
	}


	public static HashMap<String, String> getLatLong(String address)
	{
		HashMap<String, String> val = new HashMap<>();
		try{
			getConnection();
			String query = "SELECT * from latLong WHERE address = '"+ address.trim() +"' ;";
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			Product pd;
			while(rs.next())
			{
				rs.getString("latitude");
				rs.getString("longitude");
				val.put(rs.getString("latitude"), rs.getString("longitude"));
			}

		}
		catch(Exception e){
			System.out.println("Error: getUserOrderUser:");
			System.out.println(e.getMessage());
			val = null;
		}
		return val;

	}

	public static List<String> getZipcodes()
	{
		List<String> val = new ArrayList<>();
		try{
			getConnection();
			String query = "SELECT DISTINCT zipcode from Product;";
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			Product pd;
			while(rs.next())
			{
				val.add(rs.getString("zipcode"));
			}

		}
		catch(Exception e){
			System.out.println("Error: getZipcodes:");
			System.out.println(e.getMessage());
			val = null;
		}
		return val;
	}

	public static TreeMap<String, String> getTopZipcodeProducts()
	{
		TreeMap<String, String> map = new TreeMap<>();
		try{
			getConnection();
			String products_query;
			products_query = "SELECT zipcode, count(ptitle) as val FROM Product group by zipcode order by val desc;";
			
			PreparedStatement pst = conn.prepareStatement(products_query);
			ResultSet rs = pst.executeQuery();

			//Product pd;
			while(rs.next())
			{
				map.put(rs.getString("zipcode"), rs.getString("val"));
			}
		}
		catch(Exception e){
			System.out.println("Error: getSoldProductsList: Cannot fetch products from database");
			map = null;
		}
		return map;
	}


}