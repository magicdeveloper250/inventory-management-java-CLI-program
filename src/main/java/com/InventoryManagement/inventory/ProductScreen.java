package com.InventoryManagement.inventory;

import java.util.ArrayList;
import java.util.Scanner;

public class ProductScreen implements IScreen{
	private ArrayList<Product> products= Product.getProducts();
	private Scanner mainScanner= App.mainScanner;

	@Override
	public void show() {
		int count= 1;
		for (Product product : products) {
			System.out.print(count);
			System.out.println(" "+product);
			count++;
		}
	}

	@Override
	public void add() {
		 
			System.out.println("Create new product");
			System.out.print("Name: ");
			String name= mainScanner.nextLine();
			System.out.print("Description: ");
			String description= mainScanner.nextLine();
			System.out.print("Quantity: ");
			double quantity= mainScanner.nextDouble();
			System.out.print("Price: ");
			double price= mainScanner.nextDouble();
			System.out.print("Reorder: ");
			double reorder= mainScanner.nextDouble();
			Product product= new Product(null,name, description, quantity, price, reorder);
			this.confimProduct(product);
		 
	}

	@Override
	public void update() {
			System.out.print("Select product to update: ");
			String choice = mainScanner.nextLine();
			Product product= products.get(Integer.parseInt(choice)-1);
			System.out.println("Update product");
			System.out.print("Name: ");
			String name= mainScanner.nextLine();
			System.out.print("Description: ");
			String description= mainScanner.nextLine();
			System.out.print("Quantity: ");
			double quantity= mainScanner.nextDouble();
			System.out.print("Price: ");
			double price= mainScanner.nextDouble();
			System.out.print("Reorder: ");
			double reorder= mainScanner.nextDouble();
			product.setName(name);
			product.setDescription(description);
			product.setPrice(price);
			product.setQuantiy(quantity);
			product.setReorderLevel(reorder);
			product.updateProduct(product.getId());
			System.out.println("Product updated successfully"); 
		
	}

	@Override
	public void delete() {
		 
			System.out.print("Select product to delete: ");
			String choice = mainScanner.nextLine();
			Product product= products.get(Integer.parseInt(choice)-1);
			Product.deleteProduct(product.getId());
			System.out.println("Product deleted successfully");
	}

	
	
	private Object confimProduct(Product product) {
		System.out.println("Do you want to save this product? Y/N: ");
		String confirm= mainScanner.nextLine();
		switch(confirm.toLowerCase()) {
		case "y":
			product.addProduct();
			System.out.println("New Product saved successfully.");
			break;
		case "n":
			return null;
		default:
			System.out.println("You have chosen a wrong choice");
			return this.confimProduct( product);
			
		}
		return null;
		
	}
	public void reduceStock() {
		this.show();
		System.out.println("Select product to reduce qty");
		System.out.print("Enter your choice: ");
		String choice= mainScanner.nextLine();
		Product product= products.get(Integer.parseInt(choice)-1);
		System.out.println("Enter Qty: ");
		double qty= mainScanner.nextDouble();
		if(product.available(qty)) {
			Product.reduceStock(product.getId(), qty);
			System.out.println("Product quantity updated successfully");
		}
				
		else {System.out.println("The value provided is higher than actual");}
	}
	
	public void increaseStock() {
		this.show();
		System.out.println("Select product to increase qty");
		System.out.print("Enter your choice: ");
		String choice= mainScanner.nextLine();
		Product product= products.get(Integer.parseInt(choice)-1);
		System.out.println("Enter Qty: ");
		double qty= mainScanner.nextDouble();
		Product.increaseStock(product.getId(), qty);
		System.out.println("Product qty updated successfully");
	}
	
	@Override
	public void start() {
		System.out.println("PRODUCT MANAGEMENT");
		System.out.println("____________________\n");
		
			this.show();
			System.out.println();
			while(true) {
				System.out.print("What do you want to do: \n");
				System.out.println("1. Add new product");
				System.out.println("2. Update product");
				System.out.println("3. Delete product");
				System.out.println("4. Show products");
				System.out.println("5. reduce stock");
				System.out.println("6. increase stock");
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
						this.products= Product.getProducts();
						this.show();
						break;
					case "5":
						 
						this.reduceStock();
						break;
					case "6":
						this.increaseStock();
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
