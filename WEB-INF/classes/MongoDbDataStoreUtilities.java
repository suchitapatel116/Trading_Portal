import com.mongodb.*;
import java.util.*;
import java.io.*;
import com.mongodb.client.MongoDatabase; 

public class MongoDbDataStoreUtilities {
	
	MongoClient mongo= null;
	DB db= null;
	DBCollection reviews = null;
	DBCollection ratings = null;
	public void connect()
	{
		try{
			mongo= new MongoClient("localhost",27017);
			db= mongo.getDB("review");
			reviews = db.getCollection("project");
			ratings = db.getCollection("rating");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void addReview(ReviewDetail reviewDetail)
	{
		BasicDBObject doc = new BasicDBObject("title", "project").
		
		append("productId",reviewDetail.getProductId()).
		append("productName",reviewDetail.getProductTitle()).
		
		append("userName",reviewDetail.getUserName()).
		append("reviewText", reviewDetail.getReviewText());
		reviews.insert(doc);
	
	}

	public void addSellerRating(RatingDetail ratingDetail)
	{
		BasicDBObject doc = new BasicDBObject("title", "rating").
		
		append("buyer",ratingDetail.getBuyer()).
		append("seller",ratingDetail.getSeller()).
		append("sellerRating",ratingDetail.getSellerRating());
	
		ratings.insert(doc);
	}	
	
	
	public HashMap getAllRatings()
	{
		HashMap<String,ArrayList<RatingDetail>> ratingDetails = new HashMap<String,ArrayList<RatingDetail>>();
		DBCursor cursor = ratings.find();
		
		while(cursor.hasNext())
		{
			BasicDBObject obj = (BasicDBObject)cursor.next();
			if(!ratingDetails.containsKey(obj.getString("seller")))
			{
				ArrayList<RatingDetail> listReview = new ArrayList<RatingDetail>();
				ratingDetails.put(obj.getString("seller"),listReview);
			}
			ArrayList<RatingDetail> ratinglist = ratingDetails.get(obj.getString("seller"));
			
			RatingDetail ratingDetail = new RatingDetail();
			
			ratingDetail.setSeller(obj.getString("seller"));
			ratingDetail.setBuyer(obj.getString("buyer"));
			ratingDetail.setSellerRating(obj.getString("sellerRating"));
			ratinglist.add(ratingDetail);
			
			ratingDetails.put(obj.getString("seller"),ratinglist);
		}		
		return ratingDetails;
	}
	
	public HashMap getRatings()
	{
		HashMap<String,ArrayList<RatingDetail>> ratingDetails = new HashMap<String,ArrayList<RatingDetail>>();
		DBCursor cursor = ratings.find();
		
		while(cursor.hasNext())
		{
			BasicDBObject obj = (BasicDBObject)cursor.next();
			if(!ratingDetails.containsKey(obj.getString("buyer")))
			{
				ArrayList<RatingDetail> listReview = new ArrayList<RatingDetail>();
				ratingDetails.put(obj.getString("buyer"),listReview);
			}
			ArrayList<RatingDetail> ratinglist = ratingDetails.get(obj.getString("buyer"));
			
			RatingDetail ratingDetail = new RatingDetail();
			
			ratingDetail.setSeller(obj.getString("seller"));
			ratingDetail.setBuyer(obj.getString("buyer"));
			ratingDetail.setSellerRating(obj.getString("sellerRating"));
			ratinglist.add(ratingDetail);
			
			ratingDetails.put(obj.getString("buyer"),ratinglist);
		}
		return ratingDetails;
	}
	
	
	public HashMap getAllReviews()
	{
		HashMap<String,ArrayList<ReviewDetail>> reviewDetails = new HashMap<String,ArrayList<ReviewDetail>>();
		DBCursor cursor = reviews.find();
		
		while(cursor.hasNext())
		{
			BasicDBObject obj = (BasicDBObject)cursor.next();
			if(!reviewDetails.containsKey(obj.getString("productId")))
			{
				ArrayList<ReviewDetail> listReview = new ArrayList<ReviewDetail>();
				reviewDetails.put(obj.getString("productId"),listReview);
			}
			ArrayList<ReviewDetail> reviewlist = reviewDetails.get(obj.getString("productId"));
			
			ReviewDetail reviewDetail = new ReviewDetail();
			
			reviewDetail.setProductTitle(obj.getString("productTitle"));
			reviewDetail.setUserName(obj.getString("userName"));
			reviewDetail.setReviewText(obj.getString("reviewText"));
			reviewDetail.setProductId(obj.getString("productId"));
			reviewlist.add(reviewDetail);
			
			reviewDetails.put(obj.getString("productId"),reviewlist);
		}
		return reviewDetails;
	}

	public HashMap getTopSellerRatings()
	{
		//<rating, seller name>
		HashMap<String, ArrayList<String>> map = new HashMap<>();
		HashMap<String,ArrayList<RatingDetail>> ratingDetails = new HashMap<String,ArrayList<RatingDetail>>();
		DBCursor cursor = ratings.find();
		
		while(cursor.hasNext())
		{
			BasicDBObject obj = (BasicDBObject)cursor.next();
			if(!ratingDetails.containsKey(obj.getString("seller")))
			{
				ArrayList<RatingDetail> listReview = new ArrayList<RatingDetail>();
				ratingDetails.put(obj.getString("seller"),listReview);
			}
			ArrayList<RatingDetail> ratinglist = ratingDetails.get(obj.getString("seller"));
			
			RatingDetail ratingDetail = new RatingDetail();
			
			ratingDetail.setSeller(obj.getString("seller"));
			ratingDetail.setBuyer(obj.getString("buyer"));
			ratingDetail.setSellerRating(obj.getString("sellerRating"));
			ratinglist.add(ratingDetail);
			
			ratingDetails.put(obj.getString("seller"),ratinglist);

			ArrayList<String> sellerNameList = new ArrayList<>();
			if(!map.containsKey(obj.getString("sellerRating").toString())){
				map.put(obj.getString("sellerRating"), sellerNameList);
				//sellerNameList = map.get("sellerRating");
			}
			else
				sellerNameList = map.get("sellerRating");
			if(obj.getString("seller") != null)
			{
				sellerNameList.add(ratingDetail.getSeller());
				map.put(obj.getString("sellerRating"), sellerNameList);
			}

		}		
		return map;
	}

	public HashMap getTopBuyerRatings()
	{
		//<rating, seller name>
		HashMap<String, ArrayList<String>> map = new HashMap<>();
		HashMap<String,ArrayList<RatingDetail>> ratingDetails = new HashMap<String,ArrayList<RatingDetail>>();
		DBCursor cursor = ratings.find();
		
		while(cursor.hasNext())
		{
			BasicDBObject obj = (BasicDBObject)cursor.next();
			if(!ratingDetails.containsKey(obj.getString("buyer")))
			{
				ArrayList<RatingDetail> listReview = new ArrayList<RatingDetail>();
				ratingDetails.put(obj.getString("buyer"),listReview);
			}
			ArrayList<RatingDetail> ratinglist = ratingDetails.get(obj.getString("buyer"));
			
			RatingDetail ratingDetail = new RatingDetail();
			
			ratingDetail.setSeller(obj.getString("seller"));
			ratingDetail.setBuyer(obj.getString("buyer"));
			ratingDetail.setSellerRating(obj.getString("sellerRating"));
			ratinglist.add(ratingDetail);
			
			ratingDetails.put(obj.getString("buyer"),ratinglist);

			ArrayList<String> sellerNameList = new ArrayList<>();
			if(!map.containsKey(obj.getString("sellerRating").toString())){
				map.put(obj.getString("sellerRating"), sellerNameList);
				//sellerNameList = map.get("sellerRating");
			}
			else
				sellerNameList = map.get("sellerRating");
			if(obj.getString("buyer") != null)
			{
				sellerNameList.add(ratingDetail.getSeller());
				map.put(obj.getString("sellerRating"), sellerNameList);
			}
			
		}
		return ratingDetails;
	}
	
}