package com.InventoryManagement.inventory;

import java.util.ArrayList;
import java.util.Scanner;

public class OrderScreen implements IScreen {
	private ArrayList<Order> orders= Order.getOrders();
	private Scanner mainScanner= App.mainScanner;
	private Cart cart= new Cart();

	@Override
	public void show() {
		int count= 1;
		for (Order order : orders) {
			System.out.print(count);
			System.out.println(" "+order);
			count++;
		}
		
	}

	@Override
	public void add() {
		 
		
			ArrayList<Customer>customers= Customer.getCustomers();
			Customer customer;
			System.out.print("Do you want to use existing customers Y/N: ");
			String choice = mainScanner.nextLine();
			if(choice.compareToIgnoreCase("y")==0) {
				this.showCustomers(customers);
				System.out.print("Select customer: ");
				String customerChoice=  mainScanner.nextLine();
				customer=customers.get( Integer.parseInt(customerChoice)-1);
				
			}else {
				this.addCustomer();
				this.showCustomers(null);
				System.out.print("Select customer: ");
				String customerChoice=  mainScanner.nextLine();
				customer=customers.get( Integer.parseInt(customerChoice)-1);
		
			}
			this.addToCart();
			Order order= new Order(null, customer.getId(),this.cart, OrderStatus.Placed );
			this.showOrder(order, customer);
			System.out.print("Do you want to save this order. Y/N: ");
			String confirm=  mainScanner.nextLine();
			if(confirm.compareToIgnoreCase("y")==0) {
				order.placeOrder();
				System.out.println("Order saved successfully");
			} 
			
	 
			
		 
		 
			
		 
		
	}
	
	public void addCustomer() {
	 CustomerScreen.addOut();
		
	}

	public void showCustomers(ArrayList<Customer> customers) {
		if (customers==null)
			customers= Customer.getCustomers();
		 int count=1;
		 for(Customer customer:customers) {
			 System.out.print(count+" ");
			 System.out.println(customer);
			 count++;
		 }
	}
	
	
	
	

	@Override
	public void update() {
		this.show();
		System.out.println("Select order to change status: ");
		System.out.print("Enter your choice: ");
		String choice = mainScanner.nextLine();
		Order order= orders.get(Integer.parseInt(choice)-1);
		System.out.println("change status");
		System.out.println("new status: ");
		int count =1;
		for(OrderStatus status: OrderStatus.values()) {
			System.out.println(count +" "+ status);
			count ++;
		}
		System.out.print("Enter your choice: ");
		String newStatus= mainScanner.nextLine();
		OrderStatus status= order.getStatus();
		switch(newStatus) {
		case "1":
			 status=OrderStatus.Placed;
			break;
		case "2":
			 status=OrderStatus.processing;
			break;
		case "3":
			status=OrderStatus.shipped;
			break;
		case "4":
			status=OrderStatus.delivered;
			break;
		case "5":
			status=OrderStatus.canceled;
			break;
		default:
			System.out.println("Invalid Choice");
			break;
		}
		
		Order.updateStatus(order.getId(), status);	
		System.out.println("Order status updated successfully");
		
}
		
		
				
		
	@Override
	public void delete() {
		this.show();
		System.out.println("Select order to remove from database: ");
		System.out.print("Enter your choice: ");
		String choice = mainScanner.nextLine();
		Order order= orders.get(Integer.parseInt(choice)-1);
		Order.deleteOrder(order.getId());
		System.out.println("Order removed successfully");
		
	}

	
	public void addToCart() {
		 
			ArrayList<Product> products= Product.getProducts();
			this.showProducts(products);
			String choice= "";
			System.out.println("Choose product number");
			while (true) {
				System.out.print("Type product number: ");
				choice= mainScanner.nextLine();
				if(choice.compareToIgnoreCase("q")==0) break;
				Product product= products.get(Integer.parseInt(choice)-1);
				System.out.print("Type Qty: ");
				double qty= Double.parseDouble( mainScanner.nextLine());
				if(product.available(qty)) {
					product.setQuantiy(qty);
					this.cart.addProduct(product);
				}else {
					System.out.println("Qty is greater than stock level");
				}
			}
			this.showCart();
		 
	}
	
	public void showProducts(ArrayList<Product> products) {
		if(products==null)
			products= Product.getProducts();
		int count= 1;
		for (Product product : products) {
			System.out.print(count);
			System.out.println(" "+product);
			count++;
		}
		
	 
	}
	
	public void showCart() {
		ArrayList<Product> cartProducts= this.cart.getProducts();
		System.out.println("Your shopping cart");
		for(Product product : cartProducts) {
			System.out.println(product);
		}
		
	}
	public void showOrder(Order order, Customer customer) {
		System.out.println("Order slip");
		System.out.println("Name: "+ customer.getName());
		System.out.println("Id: "+customer.getId());
		this.showCart();
		System.out.println("Total: "+ order.getCart().totalAmount());
		
		
	}
	
	
	@Override
	public void start() {
		System.out.println("ORDER MANAGEMENT");
		System.out.println("____________________\n");
		this.show();
		System.out.println();
		while(true) {
			System.out.print("What do you want to do: \n");
			System.out.println("1. Add new order");
			System.out.println("2. change order status");
			System.out.println("3. Delete order");
			System.out.println("4. Show orders");
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
					this.orders= Order.getOrders();
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

