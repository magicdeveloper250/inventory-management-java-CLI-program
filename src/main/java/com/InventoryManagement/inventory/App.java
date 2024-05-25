package com.InventoryManagement.inventory;

import java.util.Scanner;
import com.mongodb.MongoClient;
import com.mongodb.DB;

public class App {
	private static MongoClient client= MongodbConnection.getClient();
	public static Scanner mainScanner= new Scanner(System.in);
	@SuppressWarnings("deprecation")
	public static DB  db = client.getDB("inventory-management");
	
	public static void main(String[] args) {
		
		ProductScreen productScreen= new ProductScreen();
		CustomerScreen customerScreen= new CustomerScreen();
		OrderScreen orderScreen = new OrderScreen();
		System.out.println("Welcome to Inventory management CLI app");
		System.out.println("________________________________________");
		System.out.println();
		while(true) {
			System.out.println();
			System.out.println("INVENTORY MANAGEMENT");
			System.out.println("____________________");
			System.out.println();
			System.out.println("What do you want to do:");
			System.out.println("1. Product Management");
			System.out.println("2. Customer Management");
			System.out.println("3. Order Management");
			System.out.println("q. Quit");
			System.out.print("Enter your choice: ");
			String choice = mainScanner.nextLine();
			System.out.println();
			if(choice.compareToIgnoreCase("q")==0) break;
			try {
				switch(choice) {
				case "1":
					productScreen.start();
					break;
				case "2":
					customerScreen.start();;
					break;
				case "3":
					orderScreen.start();
					break;
				case "q":
					 mainScanner.close();
					 System.out.println("Program terminated:(");
					 System.exit(0);
				default:
					System.out.println("Wrong choice");
					break;
				}	
			}catch(Exception e) {
				continue;
			}
			 
	
 }
	}
	}
