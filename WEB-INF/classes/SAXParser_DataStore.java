import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.*;

public class SAXParser_DataStore extends DefaultHandler
{
	public static String HOME_DIR = System.getenv("ANT_HOME");
	public static String PRODUCT_CATALOG_FILE = HOME_DIR + "\\webapps\\project2_merged\\ProductCatalog.xml";

	Product product = null;
	String elementValue = null;

	//<product type, product list>
	static HashMap<String, List<Product>> products_map;
	static List<Product> television_list;
	static List<Product> laptop_list;
	static List<Product> phones_list;
	static List<Product> gaming_list;
	static List<Product> vaccuumCleaner_list;
	static List<Product> book_academic_list;
	static List<Product> book_nonacademic_list;
	static List<Product> sofa_list;
	static List<Product> beds_list;
	static List<Product> cupboard_list;
	static List<Product> chair_list;
	static List<Product> table_list;
	static List<Product> automobile_list;
	static List<Product> sports_list;
	static List<Product> gym_list;
	static List<Product> miscellaneous_list;

	boolean titleFlag = false;
	boolean imageFlag = false;
	boolean priceFlag = false;
	boolean conditionFlag = false;
	boolean negotiableFlag = false;
	boolean sellerFlag = false;
	boolean descriptionFlag = false;
	boolean pickupaddressFlag = false;
	boolean postdateFlag = false;
	boolean zipcodeFlag = false;

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

	SAXParser_DataStore()
	{
		products_map = new HashMap<>();
		television_list = new ArrayList<>();
		laptop_list = new ArrayList<>();
		phones_list = new ArrayList<>();
		gaming_list = new ArrayList<>();
		vaccuumCleaner_list = new ArrayList<>();
		book_academic_list = new ArrayList<>();
		book_nonacademic_list = new ArrayList<>();
		sofa_list = new ArrayList<>();
		beds_list = new ArrayList<>();
		cupboard_list = new ArrayList<>();
		chair_list = new ArrayList<>();
		table_list = new ArrayList<>();
		automobile_list = new ArrayList<>();
		sports_list = new ArrayList<>();
		gym_list = new ArrayList<>();
		miscellaneous_list = new ArrayList<>();

		parseDocument();
	}
	private void parseDocument()
	{
		try{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser SAXParser = factory.newSAXParser();
			
			DefaultHandler handler = this;
			File f = new File(PRODUCT_CATALOG_FILE);
			SAXParser.parse(f, handler);
		}
		catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            System.out.println("IO error");
        }
		catch(Exception e){}
	}

	@Override
	public void startElement(String uri, String localName, String elementName, Attributes attributes) throws SAXException
	{
		String item_id = null;
		if(elementName.equalsIgnoreCase("Television") ||
			elementName.equalsIgnoreCase("Laptop") ||
			elementName.equalsIgnoreCase("Phones") ||
			elementName.equalsIgnoreCase("Gaming") ||
			elementName.equalsIgnoreCase("VaccuumCleaner") ||
			elementName.equalsIgnoreCase("Academics") ||
			elementName.equalsIgnoreCase("NonAcademics") ||
			elementName.equalsIgnoreCase("Sofa") ||
			elementName.equalsIgnoreCase("Beds") ||
			elementName.equalsIgnoreCase("Cupboard") ||
			elementName.equalsIgnoreCase("Chair") ||
			elementName.equalsIgnoreCase("Table") ||
			elementName.equalsIgnoreCase("Automobile") ||
			elementName.equalsIgnoreCase("Sports") ||
			elementName.equalsIgnoreCase("Gym") ||
			elementName.equalsIgnoreCase("Miscellaneous"))
		{
			item_id = attributes.getValue("id");
			product = new Product();
			product.setId(item_id);
		}
		else if(elementName.equalsIgnoreCase("title"))
			titleFlag = true;
		else if(elementName.equalsIgnoreCase("image"))
			imageFlag = true;
		else if(elementName.equalsIgnoreCase("price"))
			priceFlag = true;
		else if(elementName.equalsIgnoreCase("condition"))
			conditionFlag = true;
		else if(elementName.equalsIgnoreCase("negotiable"))
			negotiableFlag = true;
		else if(elementName.equalsIgnoreCase("sellerName"))
			sellerFlag = true;
		else if(elementName.equalsIgnoreCase("description"))
			descriptionFlag = true;
		else if(elementName.equalsIgnoreCase("pickupaddress"))
			pickupaddressFlag = true;
		else if(elementName.equalsIgnoreCase("postdate"))
			postdateFlag = true;
		else if(elementName.equalsIgnoreCase("zipcode"))
			zipcodeFlag = true;
	}

	@Override
	public void endElement(String uri, String localName, String elementName) throws SAXException
	{
		if(elementName.equalsIgnoreCase("Television")) {
			television_list.add(product);
			products_map.put(KEY_TV, television_list);
		}
		else if(elementName.equalsIgnoreCase("Laptop")) {
			laptop_list.add(product);
			products_map.put(KEY_LAPTOP, laptop_list);
		}
		else if(elementName.equalsIgnoreCase("Phones")) {
			phones_list.add(product);
			products_map.put(KEY_PHONE, phones_list);
		}
		else if(elementName.equalsIgnoreCase("Gaming")) {
			gaming_list.add(product);
			products_map.put(KEY_GAME, gaming_list);
		}
		else if(elementName.equalsIgnoreCase("VaccuumCleaner")) {
			vaccuumCleaner_list.add(product);
			products_map.put(KEY_VACCUUM_CLEANER, vaccuumCleaner_list);
		}
		else if(elementName.equalsIgnoreCase("Academics")) {
			book_academic_list.add(product);
			products_map.put(KEY_ACADEMIC_BOOK, book_academic_list);
		}
		else if(elementName.equalsIgnoreCase("NonAcademics")) {
			book_nonacademic_list.add(product);
			products_map.put(KEY_NON_ACADEMIC_BOOK, book_nonacademic_list);
		}
		else if(elementName.equalsIgnoreCase("Sofa")) {
			sofa_list.add(product);
			products_map.put(KEY_SOFA, sofa_list);
		}
		else if(elementName.equalsIgnoreCase("Beds")) {
			beds_list.add(product);
			products_map.put(KEY_BED, beds_list);
		}
		else if(elementName.equalsIgnoreCase("Cupboard")) {
			cupboard_list.add(product);
			products_map.put(KEY_CUPBOARD, cupboard_list);
		}
		else if(elementName.equalsIgnoreCase("Chair")) {
			chair_list.add(product);
			products_map.put(KEY_CHAIR, chair_list);
		}
		else if(elementName.equalsIgnoreCase("Table")) {
			table_list.add(product);
			products_map.put(KEY_TABLE, table_list);
		}
		else if(elementName.equalsIgnoreCase("Automobile")) {
			automobile_list.add(product);
			products_map.put(KEY_AUTOMOBILE, automobile_list);
		}
		else if(elementName.equalsIgnoreCase("Sports")) {
			sports_list.add(product);
			products_map.put(KEY_SPORTS, sports_list);
		}
		else if(elementName.equalsIgnoreCase("Gym")) {
			gym_list.add(product);
			products_map.put(KEY_GYM, gym_list);
		}
		else if(elementName.equalsIgnoreCase("Miscellaneous")) {
			miscellaneous_list.add(product);
			products_map.put(KEY_MISCELLANEOUS, miscellaneous_list);
		}
	}

	@Override
    public void characters(char ch[], int start, int length) throws SAXException 
    {
        elementValue = new String(ch, start, length);

        if(titleFlag) {
        	product.setTitle(elementValue);
        	titleFlag = false;
        }
        else if(imageFlag) {
        	product.setImage(elementValue);
        	imageFlag = false;
        }
        else if(priceFlag) {
        	product.setPrice(elementValue);
        	priceFlag = false;
        }
        else if(conditionFlag) {
        	product.setCondition(elementValue);
        	conditionFlag = false;
        }
		else if(negotiableFlag) {
        	product.setNegotiable(elementValue);
        	negotiableFlag = false;
        }
        else if(sellerFlag) {
        	product.setPostedBy(elementValue);
        	sellerFlag = false;
        }
        else if(descriptionFlag) {
        	product.setDescription(elementValue);
        	descriptionFlag = false;
        }
        else if(pickupaddressFlag) {
        	product.setPickupaddress(elementValue);
        	pickupaddressFlag = false;
        }
        else if(postdateFlag) {
        	product.setPostdate(elementValue);
        	postdateFlag = false;
        }
        else if(zipcodeFlag) {
        	product.setZipcode(elementValue);
        	zipcodeFlag = false;
        }
    }

    public Map<String, List<Product>> getProductMap(){
    	return products_map;
    }
    public List<Product> getTelevisionList(){
    	return (products_map.get(KEY_TV));
    }
    public List<Product> getLaptopList(){
    	return (products_map.get(KEY_LAPTOP));
    }
    public List<Product> getPhoneList(){
    	return (products_map.get(KEY_PHONE));
    }
    public List<Product> getGameList(){
    	return (products_map.get(KEY_GAME));
    }
    public List<Product> getVaccuumCleanerList(){
    	return (products_map.get(KEY_VACCUUM_CLEANER));
    }
    public List<Product> getAcademicBookList(){
    	return (products_map.get(KEY_ACADEMIC_BOOK));
    }
    public List<Product> getNonAcademicBookList(){
    	return (products_map.get(KEY_NON_ACADEMIC_BOOK));
    }
    public List<Product> getSofaList(){
    	return (products_map.get(KEY_SOFA));
    }
	public List<Product> getBedsList(){
    	return (products_map.get(KEY_BED));
    }
    public List<Product> getCupboardList(){
    	return (products_map.get(KEY_CUPBOARD));
    }
    public List<Product> getChairList(){
    	return (products_map.get(KEY_CHAIR));
    }
    public List<Product> getTableList(){
    	return (products_map.get(KEY_TABLE));
    }
    public List<Product> getAutomobileList(){
    	return (products_map.get(KEY_AUTOMOBILE));
    }
    public List<Product> getSportsList(){
    	return (products_map.get(KEY_SPORTS));
    }
    public List<Product> getGymList(){
    	return (products_map.get(KEY_GYM));
    }
    public List<Product> getMiscellaneousList(){
    	return (products_map.get(KEY_MISCELLANEOUS));
    }

}