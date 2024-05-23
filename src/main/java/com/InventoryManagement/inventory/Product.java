package com.InventoryManagement.inventory;
import java.lang.reflect.Field;
import com.mongodb.DBCursor;
import java.util.ArrayList;
import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
public class Product {
		private String id;
		private String name;
		private String description;
		private double pricePerUnit;
		private double quantity;
		private double reorderLevel;
		private final static MongoClient client= MongodbConnection.getClient();
		@SuppressWarnings("deprecation")
		private final static DB db= client.getDB("inventory-management");
		private final static DBCollection collection= db.getCollection("products");
		
//		regular constructor
	public Product(String id,String name,String description, double pricePerUnit, double quantity, double reorderLevel) {
		this.id= id;
		this.name= name;
		this.description= description;
		this.pricePerUnit= pricePerUnit;
		this.quantity= quantity;
		this.reorderLevel= reorderLevel;
	}
	
//	another constructor which takes an array fields values
	public Product(Object[] objects) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields=this.getClass().getDeclaredFields();
		short count=0;
		 for(Field field : fields) {
//			 check if object elements haven't exhausted if true break loop
			 if(count > objects.length-1) break;
			 
//			 check if loop react on double values and convert them
			 if(count >=3) {
				 field.set(this, Double.parseDouble((String)objects[count]));
			 }else{
				 field.set(this, objects[count]);
			 };
			 count++;
		 }
	}
	
	// getter and setters
	public void setId(String id) {
		this.id= id;
	}
	public String getId() {
		return this.id;
	}
	public void setName(String name) {
		this.name= name.toUpperCase();
	}
	public String getName() {
		return this.name;
	}
	
	public void setDescription(String description) {
		this.description= description;
	}
	public String getDescription() {
		return this.description;
	}
	public void setPrice(double price) {
		this.pricePerUnit= price;
	}
	public double getPrice() {
		return this.pricePerUnit * this.quantity;
	}
	public void setQuantiy(double qty) {
		this.quantity= qty;
	}
	
	public double getQuantity() {
		return this.quantity;
	}
	public void setReorderLevel(double reorderLevel) {
		this.reorderLevel= reorderLevel;
	}
	public double getReorderLevel() {
		return this.reorderLevel;
		
	}
	// end getter and setters
	
	// CRUD methods
	
	public void addProduct() {
		BasicDBObject newProductDoc= new BasicDBObject();
		newProductDoc.append("name", getName());
		newProductDoc.append("description", getDescription());
		newProductDoc.append("quantity", getQuantity());
		newProductDoc.append("pricePerUnit", getPrice());
		newProductDoc.append("reorderLevel", getReorderLevel());
		collection.insert(newProductDoc);
	}
	
	
	public static boolean available(String id,double qty) {
		ObjectId _id= new ObjectId(id);
		DBObject product= collection.findOne(_id);
		if (product ==null) return false;
		return (double)product.get("quantity")>=qty?true:false;
	}
	
	public static void reduceStock(String id, double qty) {
		ObjectId _id= new ObjectId(id);
		if(Product.available(id,qty)) {
			DBObject product= collection.findOne(_id);
			if(product==null) return;
			double quantity= (double) product.get("quantity");
			quantity -=qty;
			product.put("quantity", quantity);
			BasicDBObject query = new BasicDBObject();
			query.append("_id", _id);
			collection.update(query, product);	
		}
			 
	}
	public static ArrayList<Product> getProducts(){
		ArrayList<Product> products = new ArrayList<>();
		DBCursor productsCursor= collection.find();
		while(productsCursor.hasNext()) {
			DBObject currPro= productsCursor.next();
			Product product = new Product(
					currPro.get("_id").toString(), 
					currPro.get("name").toString(),
					currPro.get("description").toString(),
					(double)currPro.get("pricePerUnit"),
					(double)currPro.get("quantity"),
					(double)currPro.get("reorderLevel")
					
					);
			products.add(product);
		}
		return products;
	}
	
	public static Product getProduct(String id) {
		ObjectId _id= new ObjectId(id);
		DBObject productDoc= collection.findOne(_id);
		if (productDoc==null)return null;
		Product product = new Product(
				productDoc.get("_id").toString(), 
				productDoc.get("name").toString(),
				productDoc.get("description").toString(),
				(double)productDoc.get("pricePerUnit"),
				(double)productDoc.get("quantity"),
				(double)productDoc.get("reorderLevel")
				
				);
		return product;
	}
	
	public static void  increaseStock(String id,double qty) {
		ObjectId _id= new ObjectId(id);
		DBObject product= collection.findOne(_id);
		if (product==null) return;
		double quantity= (double) product.get("quantity");
		quantity +=qty;
		product.put("quantity", quantity);
		BasicDBObject query = new BasicDBObject();
		query.append("_id", _id);
		collection.update(query, product);	
	}
	
	public  boolean updateProduct(String id) {
		ObjectId _id= new ObjectId(id);
		DBObject product= collection.findOne(_id);
		if (product==null) return false;
		product.put("name", getName());
		product.put("description", getDescription());
		product.put("quantiy", getQuantity());
		product.put("pricePerUnit", getPrice());
		product.put("reorderLevel", getReorderLevel());
		BasicDBObject query = new BasicDBObject();
		query.append("_id", _id);
		collection.update(query, product);
		return true;
	}
	
	public static boolean deleteProduct(String id) {
		ObjectId _id= new ObjectId(id);
		DBObject product= collection.findOne(_id);
		if (product==null) return false;
		BasicDBObject query = new BasicDBObject();
		query.append("_id", _id);
		collection.remove(query);
		return true;
		
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public String[] toList() {
		String[] product= {
							this.id, this.name, 
							this.description, 
							Double.toString(this.pricePerUnit),
							Double.toString(this.quantity),
							Double.toString(this.getPrice())
						};
		return product;
		
	}
	

}
