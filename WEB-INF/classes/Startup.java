import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

@WebServlet("/Startup")

public class Startup extends HttpServlet
{
	public void init() throws ServletException
    {
		SAXParser_DataStore productsDataStore = new SAXParser_DataStore();
		Map<String, List<Product>> map = productsDataStore.getProductMap();

		MySqlDataStoreUtilities.insertProductsInDB(map);
		ProductsHashMap.setProductsMapFromDB();
    }
}