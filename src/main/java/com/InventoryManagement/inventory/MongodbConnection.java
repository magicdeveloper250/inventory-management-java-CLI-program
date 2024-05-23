package com.InventoryManagement.inventory;
import com.mongodb.MongoClient;
public class MongodbConnection{
	private static MongoClient client= null;
	private static String host="localhost";
	private static int port=27017;
	
	public static MongoClient getClient() {
		
		try {
			MongodbConnection.client = new MongoClient(host, port);
			System.out.println("Connection to the database established.");
		}catch(Exception error) {
			System.out.println("Connection to the database refused");
			System.exit(1);
		}
		return MongodbConnection.client != null?MongodbConnection.client:null;
	}
	
	 
 
	
	
}