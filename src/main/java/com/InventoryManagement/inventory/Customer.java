package com.InventoryManagement.inventory;

import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import java.util.ArrayList;
import org.bson.types.ObjectId;

public class Customer {
		@SuppressWarnings({ "unused"})
		private String id;
		private String name;
		private String address;
		private String contact;
		private String details;
		private final static MongoClient client= MongodbConnection.getClient();
		@SuppressWarnings("deprecation")
		private final static DB db= client.getDB("inventory-management");
		private final static DBCollection collection= db.getCollection("customers");
		
		
	public Customer(String id,String name, String address, String contact, String details) {
		this.id=id;
		this.name= name;
		this.address= address;
		this.contact= contact;
		this.details= details;
	}
	
	// setter and getters
	public String getId() {
		return this.id;
	}
	public void setName(String name) {
		this.name= name;
	}
	public String getName() {
		return this.name;
	}
	
	public void setAddress(String address) {
		this.address= address;
	}
	public String getAddress() {
		return this.address;
	}
	
	public void setContact(String contact) {
		this.contact= contact;
	}
	public String getContact() {
		return this.contact;
	}
	
	public void setDetails(String details) {
		this.details= details;
	}
	public String getDetails() {
		return this.details;
	}
	
	// CRUD operations
	
	// method for adding new customer to the database
	public void addCustomer() {
		BasicDBObject newCustomerDoc= new BasicDBObject();
		newCustomerDoc.append("name", getName());
		newCustomerDoc.append("address", getAddress());
		newCustomerDoc.append("details", getDetails());
		newCustomerDoc.append("contact", getContact());
		collection.insert(newCustomerDoc);
		 
	}
	
	// method for getting one customer from database
	public static Customer getCustomer(String id) {
		DBObject currDoc= collection.findOne(new ObjectId(id));
		Customer cust= new Customer(
				currDoc.get("_id").toString(),
				(String) currDoc.get("name"), 
				(String)currDoc.get("address"),
				(String) currDoc.get("contact"),
				(String) currDoc.get("details"));	 
		return cust;
		
	}
	// method for getting all customers from database
	public static ArrayList<Customer> getCustomers(){
		ArrayList<Customer> customers= new ArrayList<>();
		DBCursor custCursor= collection.find();
		while (custCursor.hasNext()) {
			DBObject currDoc=custCursor.next();
			Customer cust= new Customer(
					currDoc.get("_id").toString(),
					(String) currDoc.get("name"), 
					(String)currDoc.get("address"),
					(String) currDoc.get("contact"),
					(String) currDoc.get("details"));
			customers.add(cust);
		}
		return customers;
	}
	// method for updating customer 
	public boolean updateCustomer(String id) {
		ObjectId _id= new ObjectId(id);
		DBObject custDoc= collection.findOne(_id);
		// check if customer exists in database, if not return false
		if(custDoc==null) return false;
		custDoc.put("name", getName());
		custDoc.put("address", getAddress());
		custDoc.put("details", getDetails());
		custDoc.put("contact", getContact());	
		BasicDBObject query= new BasicDBObject();
		query.append("_id", _id);
		collection.update(query, custDoc);
		return true;
	}
	// method for deleting customer from database
	public static boolean deleteCustomer(String id) {
		DBObject custDoc= collection.findOne(new ObjectId(id));
		// check if customer found in database, if not return false
		if(custDoc==null) return false;
		BasicDBObject query= new BasicDBObject();
		query.append("_id", new ObjectId(id));
		collection.remove(query);
		return true;
	}
	

	@Override
	public String toString() {
		return name;
	}
	
	 
}
