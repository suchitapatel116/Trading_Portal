import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

public class CartData
{
	//<username, cart items>
	private static HashMap<String, List<Product>> user_cart = new HashMap<>();

	public static List<Product> getUserCart(String user){		
		return (user_cart.get(user));
	}
	public static void setUserCart(String user, List<Product> list){
		user_cart.put(user, list);		
	}
	public static void removeUser(String user){
		user_cart.remove(user);
	}
	
	public static void removeUserItem(String user, String pname)
	{
		List<Product> list = user_cart.get(user);

		if(list != null) {
			Product p;
			for(int i=0; i<list.size(); i++)
			{
				p = list.get(i);
				if(p.getTitle().equalsIgnoreCase(pname)){
					list.remove(i);
					break;
				}
			}
			user_cart.put(user, list);
		}
	}
	
	public static int getUserCartCount(String user)
	{
		List<Product> list = user_cart.get(user);

		if(list == null){
			return 0;
		}
		else{
			return list.size();
		}
	}

}