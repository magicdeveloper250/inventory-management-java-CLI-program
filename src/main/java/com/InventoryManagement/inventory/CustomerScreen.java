package com.InventoryManagement.inventory;

import java.util.ArrayList;
import java.util.Scanner;

public class CustomerScreen implements IScreen {
	private ArrayList<Customer> customers= Customer.getCustomers();
	private static Scanner mainScanner= App.mainScanner;

	@Override
	public void show() {
		int count=1;
		 for(Customer customer:customers) {
			 System.out.print(count+" ");
			 System.out.println(customer);
			 count++;
		 }
		
	}

	@Override
	public void add() {
		System.out.println("Add new Customer");
		System.out.print("Name: ");
		String name= mainScanner.nextLine();
		System.out.print("Address: ");
		String address=  mainScanner.nextLine();
		System.out.print("Contact: ");
		String contact=  mainScanner.nextLine();
		System.out.print("Details: ");
		String details =  mainScanner.nextLine();
		Customer customer = new Customer(null, name, address, contact,details);
		CustomerScreen.confimCustomer(customer);
		 
		
	}
	 
	public static void addOut() {
		System.out.println("Add new Customer");
		System.out.print("Name: ");
		String name= mainScanner.nextLine();
		System.out.print("Address: ");
		String address=  mainScanner.nextLine();
		System.out.print("Contact: ");
		String contact=  mainScanner.nextLine();
		System.out.print("Details: ");
		String details =  mainScanner.nextLine();
		Customer customer = new Customer(null, name, address, contact,details);
		CustomerScreen.confimCustomer(customer);
		 
		
	}
	
	private static Object confimCustomer(Customer customer) {
		System.out.println("Do you want to save this customer? Y/N: ");
		String confirm= mainScanner.next();
		switch(confirm.toLowerCase()) {
		case "y":
			customer.addCustomer();
			System.out.println("New customer saved successfully.");
			break;
		case "n":
			return null;
		default:
			System.out.println("You have chosen a wrong choice");
			return CustomerScreen.confimCustomer(customer);
			
		}
		return null;
	}

	@Override
	public void update() {
		this.show();
		System.out.println("Select customer to update: ");
		System.out.print("Enter your choice: ");
		String choice = mainScanner.nextLine();
		Customer customer= customers.get(Integer.parseInt(choice)-1);
		System.out.println("Update Customer");
		System.out.print("Name: ");
		String name= mainScanner.nextLine();
		System.out.print("Address: ");
		String address= mainScanner.nextLine();
		System.out.print("Contact: ");
		String contact= mainScanner.nextLine();
		System.out.print("Details: ");
		String details= mainScanner.nextLine();
		 
		customer.setName(name);
		customer.setDetails(details);
		customer.setAddress(address);
		customer.setDetails(details);
		customer.setContact(contact);
		customer.updateCustomer(customer.getId());
		System.out.println("customer updated successfully"); 
		
	}

	@Override
	public void delete() {
		this.show();
		System.out.println("Select Customer to remove from database: ");
		System.out.print("Enter your choice: ");
		String choice = mainScanner.nextLine();
		Customer customer= customers.get(Integer.parseInt(choice)-1);
		Customer.deleteCustomer(customer.getId());
		System.out.println("Customer removed successfully");
		
	}

	@Override
	public void start() {
		System.out.println("CUSTOMER MANAGEMENT");
		System.out.println("____________________\n");
		this.show();
		System.out.println();
		while(true) {
			System.out.print("What do you want to do: \n");
			System.out.println("1. Add new customer");
			System.out.println("2. Update customer");
			System.out.println("3. Delete customer");
			System.out.println("4. Show customers");
			System.out.println("q. Quit");
			System.out.print("Enter your choice: ");
			String choice = mainScanner.nextLine();
			if(choice.compareToIgnoreCase("q")==0) break;
			try {
				switch(choice) {
				case "1":
					this.add();
					break;
				case "2":
					this.update();
					break;
				case "3":
					this.delete();
					break;
				case "4":
					this.customers=Customer.getCustomers();
					this.show();
					break;
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
