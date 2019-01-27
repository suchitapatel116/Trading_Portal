import java.io.*;
import java.util.*;

public class ProductsHashMap implements Serializable
{
    //<product type, products list>
	private static Map<String, List<Product>> products_map = new HashMap<>();

    /*
	public static Map<String, List<Product>> getProductsMap(String user){
		return products_map;
	}*/
	public static void setProductsMap(Map<String, List<Product>> map){
		products_map.putAll(map);
	}
    public static void setProductsMapFromDB()
    {
        List<Product> list_all = MySqlDataStoreUtilities.getDBProductsList(null, true);

        Map<String, List<Product>> map = new HashMap<>();
        Product pd;
        List<Product> list = new ArrayList<>();
        String category = "";
        for(int i=0; i<list_all.size(); i++)
        {
            pd = list_all.get(i);
            if(category.equals("")){
                category = pd.getCategory().toString();
            }
            if(category.equalsIgnoreCase(pd.getCategory().toString())){             
                list.add(pd);
            }
            else {
                map.put(category, list);
                list = new ArrayList<>();
                category = pd.getCategory().toString();
                list.add(pd);
            }
        }
        map.put(category, list);
        products_map.putAll(map);
    }
	public static void clearProductsMap(){
		products_map.clear();
		products_map = null;
		products_map = new HashMap<>();
	}
    public static void addProductInMap(Product pd)
    {
        String type = pd.getCategory();
        List<Product> list = products_map.get(type);
        if(list == null)
            list = new ArrayList<>();

        list.add(pd);

        products_map.remove(type);
        products_map.put(type, list);      
    }
    public static void removeProductInMap(String type, String pname)
    {
        List<Product> prod_list = products_map.get(type);
        if(prod_list == null)
            return;
        Product pd;
        for(int i=0; i<prod_list.size(); i++)
        {
            pd = (Product)prod_list.get(i);
            if(pname.equals(pd.getTitle().toString()))
            {
                prod_list.remove(i);
                products_map.put(type, prod_list);
                break;
            }
        }
    }
    public static void updateProductInMap(String type, String pname, Product prod)
    {
        List<Product> prod_list = products_map.get(type);
        if(prod_list == null)
            return;
        Product pd;
        for(int i=0; i<prod_list.size(); i++)
        {
            pd = (Product)prod_list.get(i);
            if(pname.equals(pd.getTitle().toString()))
            {
                prod_list.remove(i);
                prod_list.add(prod);
                products_map.put(type, prod_list);
                break;
            }
        }
    }

    final static String KEY_TV = "television";
    final static String KEY_LAPTOP = "laptop";
    final static String KEY_PHONE = "phone";
    final static String KEY_GAME = "game";
    final static String KEY_VACCUUM_CLEANER = "vaccuum_cleaner";
    final static String KEY_ACADEMIC_BOOK = "academic_book";
    final static String KEY_NON_ACADEMIC_BOOK = "non_academic_book";
    final static String KEY_SOFA = "sofa";
    final static String KEY_BED = "bed";
    final static String KEY_CUPBOARD = "cupboard";
    final static String KEY_CHAIR = "chair";
    final static String KEY_TABLE = "table";
    final static String KEY_AUTOMOBILE = "automobile";
    final static String KEY_SPORTS = "sports";
    final static String KEY_GYM = "gym";
    final static String KEY_MISCELLANEOUS = "miscellaneous";
    final static String KEY_GIVEAWAY = "giveaway";
    final static String KEY_AUCTION = "auction";

    public static List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        list.addAll(products_map.get(KEY_TV));
        list.addAll(products_map.get(KEY_LAPTOP));
        list.addAll(products_map.get(KEY_PHONE));
        list.addAll(products_map.get(KEY_GAME));
        list.addAll(products_map.get(KEY_VACCUUM_CLEANER));
        list.addAll(products_map.get(KEY_ACADEMIC_BOOK));
        list.addAll(products_map.get(KEY_NON_ACADEMIC_BOOK));
        list.addAll(products_map.get(KEY_SOFA));
        list.addAll(products_map.get(KEY_BED));
        list.addAll(products_map.get(KEY_CUPBOARD));
        list.addAll(products_map.get(KEY_CHAIR));
        list.addAll(products_map.get(KEY_TABLE));
        list.addAll(products_map.get(KEY_AUTOMOBILE));
        list.addAll(products_map.get(KEY_SPORTS));
        list.addAll(products_map.get(KEY_GYM));
        list.addAll(products_map.get(KEY_MISCELLANEOUS));

        return list;
    }
    public static Map<String, List<Product>> getProductMap(){
        return products_map;
    }
    public static List<Product> getTelevisionList(){
        return (products_map.get(KEY_TV));
    }
    public static List<Product> getLaptopList(){
        return (products_map.get(KEY_LAPTOP));
    }
    public static List<Product> getPhoneList(){
        return (products_map.get(KEY_PHONE));
    }
    public static List<Product> getGameList(){
        return (products_map.get(KEY_GAME));
    }
    public static List<Product> getVaccuumCleanerList(){
        return (products_map.get(KEY_VACCUUM_CLEANER));
    }
    public static List<Product> getAcademicBookList(){
        return (products_map.get(KEY_ACADEMIC_BOOK));
    }
    public static List<Product> getNonAcademicBookList(){
        return (products_map.get(KEY_NON_ACADEMIC_BOOK));
    }
    public static List<Product> getSofaList(){
        return (products_map.get(KEY_SOFA));
    }
    public static List<Product> getBedsList(){
        return (products_map.get(KEY_BED));
    }
    public static List<Product> getCupboardList(){
        return (products_map.get(KEY_CUPBOARD));
    }
    public static List<Product> getChairList(){
        return (products_map.get(KEY_CHAIR));
    }
    public static List<Product> getTableList(){
        return (products_map.get(KEY_TABLE));
    }
    public static List<Product> getAutomobileList(){
        return (products_map.get(KEY_AUTOMOBILE));
    }
    public static List<Product> getSportsList(){
        return (products_map.get(KEY_SPORTS));
    }
    public static List<Product> getGymList(){
        return (products_map.get(KEY_GYM));
    }
    public static List<Product> getMiscellaneousList(){
        return (products_map.get(KEY_MISCELLANEOUS));
    }
    
    public static List<Product> getGiveAwaysList(){
        return (products_map.get(KEY_GIVEAWAY));
    }
    public static List<Product> getAuctionsList(){
        return (products_map.get(KEY_AUCTION));
    }

}