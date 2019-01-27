public class RatingDetail implements java.io.Serializable{

	private String buyer;
	private String seller;
	private String sellerRating;

	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	
	public String getSellerRating() {
		return sellerRating;
	}
	public void setSellerRating(String sellerRating) {
		this.sellerRating = sellerRating;
	}
	
}
