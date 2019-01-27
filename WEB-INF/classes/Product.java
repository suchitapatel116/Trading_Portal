import java.io.*;
import java.util.*;

public class Product
{
	private String id; //?
	private String title;
	private String category;
	private String description;
	private String image;
	private String price;
	private String condition;
	private String negotiable;
	private String pickupaddress;
	private String postdate;
	private String postedBy; //manufacturer
	private String comments;
	private String zipcode;
	private String auctionEndDate;
	private String highestBidBy;
	private int markSold;
	
	public Product(){}

	public Product(String id, String title, String category, String description, String image, String price,
			String condition, String negotiable, String pickupaddress, String postdate,
			String postedBy, String comments, String zipcode, String auctionEndDate, String highestBidBy, int markSold) 
	{
		this.id = id;
		this.title = title;
		this.category = category;
		this.description = description;
		this.image = image;
		this.price = price;
		this.condition = condition;
		this.negotiable = negotiable;
		this.pickupaddress = pickupaddress;
		this.postdate = postdate;
		this.postedBy = postedBy;
		this.comments = comments;
		this.zipcode = zipcode;
		this.auctionEndDate = auctionEndDate;
		this.highestBidBy = highestBidBy;
		this.markSold = markSold;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getNegotiable() {
		return negotiable;
	}
	public void setNegotiable(String negotiable) {
		this.negotiable = negotiable;
	}
	public String getPickupaddress() {
		return pickupaddress;
	}
	public void setPickupaddress(String pickupaddress) {
		this.pickupaddress = pickupaddress;
	}
	public String getPostdate() {
		return postdate;
	}
	public void setPostdate(String postdate) {
		this.postdate = postdate;
	}
	public String getPostedBy() {
		return postedBy;
	}
	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	@Override
	public String toString() {
		return "Test [id=" + id + ", title=" + title + ", category=" + category + ", description=" + description
				+ ", image=" + image + ", price=" + price + ", condition=" + condition + ", negotiable=" + negotiable
				+ ", pickupaddress=" + pickupaddress + ", postdate=" + postdate
				+ ", postedBy=" + postedBy + ", comments=" + comments + ", zipcode=" + zipcode 
				+ ", auctionEndDate="+auctionEndDate+", highestBidBy="+highestBidBy+", markedSold="+markSold+" ]";
	}
	public String getAuctionEndDate() {
		return auctionEndDate;
	}
	public void setAuctionEndDate(String auctionEndDate) {
		this.auctionEndDate = auctionEndDate;
	}
	public String getHighestBidBy() {
		return highestBidBy;
	}
	public void setHighestBidBy(String highestBidBy) {
		this.highestBidBy = highestBidBy;
	}
	public int getMarkSold() {
		return markSold;
	}
	public void setMarkSold(int markSold) {
		this.markSold = markSold;
	}
}