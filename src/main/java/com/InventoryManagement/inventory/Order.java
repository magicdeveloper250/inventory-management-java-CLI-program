package com.InventoryManagement.inventory;

import java.util.ArrayList;
import java.util.Date;
import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import org.bson.types.ObjectId;
import com.mongodb.BasicDBList;
public class Order {
		private String id;
		private String  customerId;
		private Cart cart;
		private Date  date;
		private OrderStatus status;
		private final static MongoClient client= MongodbConnection.getClient();
		@SuppressWarnings("deprecation")
		private final static DB db= client.getDB("inventory-management");
		private final static DBCollection collection= db.getCollection("orders");
		
//	Basic constructor	
	public Order(String id, String customerId, Cart cart) {
		this.id= id;
		this.customerId= customerId;
		this.date= new Date();
		this.status= OrderStatus.Placed;
		this.cart= cart;
		
	}
	
	
//	Another constructor with order status
	public Order(String id, String customerId, Cart cart, OrderStatus status) {
		this.id= id;
		this.customerId= customerId;
		this.date= new Date();
		this.status= status;
		this.cart= cart;
	}
	
	
//	getters and setters
	
	public void setId(String id) {
		this.id= id;
	}
	public  String getId() {
		return this.id;
	}
	public void setCustomerId( String customerId) {
		this.customerId= customerId;
	}
	public Customer getCustomer() {
		return Customer.getCustomer(customerId);
	}
	@SuppressWarnings("static-access")
	public  ArrayList<String[]> getProducts() {
		return this.cart.toList();
	}
	public OrderStatus getStatus() {
		return this.status;
	}
	
	
//	 CRUD operation methods
	
	public void placeOrder() {
		BasicDBObject orderDoc= new BasicDBObject();
		orderDoc.append("customerId", this.customerId);
		orderDoc.append("products", this.getProducts());
		orderDoc.append("date", this.date);
		orderDoc.append("status", this.getStatus().toString());
		orderDoc.append("value", this.cart.totalAmount());
		collection.insert(orderDoc);
	}
	
	public static Order getOrder(String orderId) {
		ObjectId _id = new ObjectId(orderId); 
		ArrayList<Product> products= new ArrayList<>();
		DBObject orderDoc= collection.findOne(_id);
		for(Object details: (BasicDBList) orderDoc.get("products")) {
			// converting details into BasicDBList in to get a way of converting it into ArrayList easily.
			 BasicDBList detailsList= (BasicDBList) details; 
			try {
				Product product= new Product(detailsList.toArray());
				products.add(product);
			}catch(Exception err) {
				System.out.println(err);
			}
		}
		// create a cart object and instantiate it with products as argument.
		Cart orderCart= new Cart(products);
		
		// create an order object and instantiate it with data processed 
		Order order= new Order(
				orderDoc.get("_id").toString(), 
				orderDoc.get("customerId").toString(), 
				orderCart, 
				OrderStatus.valueOf((String)orderDoc.get("status")) );
		return order;
	}
	
	public static ArrayList<Order> getOrders(){
		DBCursor ordersCursor= collection.find();
		ArrayList<Order> orders= new  ArrayList<>();
		while(ordersCursor.hasNext()) {
			ArrayList<Product> products= new ArrayList<>();
			DBObject orderDoc= ordersCursor.next();
			for(Object details: (BasicDBList) orderDoc.get("products")) {
				 BasicDBList detailsList= (BasicDBList) details;
				try {
					Product product= new Product(detailsList.toArray());
					products.add(product);
				}catch(Exception err) {
					System.out.println(err);
				}
			}
			Cart orderCart= new Cart(products);
			Order order= new Order(
					orderDoc.get("_id").toString(), 
					orderDoc.get("customerId").toString(), 
					orderCart, 
					OrderStatus.valueOf((String)orderDoc.get("status")) );
			orders.add(order);
		}
		return orders;
		 
	}
  
	public static boolean updateStatus(String orderId,OrderStatus newStatus) {
		ObjectId _id=new ObjectId(orderId);
		DBObject orderDoc= collection.findOne(_id);
		if (orderDoc==null) return false;
		orderDoc.put("status", newStatus.toString());
		BasicDBObject query= new BasicDBObject();
		query.append("_id",_id );
		collection.update(query, orderDoc);
		return true;
		
	}
	
	public static boolean deleteOrder(String orderId) {
		ObjectId _id = new ObjectId(orderId);
		DBObject orderDoc= collection.findOne(_id);
		if(orderDoc==null)return false;
		BasicDBObject query= new BasicDBObject();
		query.append("_id", _id);
		collection.remove(query);
		return true;
	}
	
	@Override
	public String toString() {
		return this.id + " " + this.date +" "+this.status;
	}
	 
	
}



