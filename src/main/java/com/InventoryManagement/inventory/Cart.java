package com.InventoryManagement.inventory;
import java.util.*;

public class Cart {
	private  ArrayList<Product> products = new ArrayList<>();
	
	public Cart(ArrayList<Product> products) {
		this.products= products;
	}
	public Cart() {};
	public  void addProduct(Product product) {
		this.products.add(product);
	}
	public  void removeProduct(String productId) {
		for(Product p: products)
			if(p.getId()==productId) this.products.remove(p);
	}
	
	public  ArrayList<Product> getProducts(){
		return this.products;
	}
	
	public  double totalAmount() {
		double total= 0.0;
		for(Product product : products)
			total+=product.getPrice();
		return total;
	}
	
	public  ArrayList<String[]> toList(){
		ArrayList<String[]> productList= new ArrayList<>();
		for(Product product: products) {
			productList.add(product.toList());
		}
		return productList;
			
	}
	

}
