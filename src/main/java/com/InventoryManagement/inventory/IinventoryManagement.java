package com.InventoryManagement.inventory;

import java.util.ArrayList;

public interface IinventoryManagement {
	public void showOrder(Order order, Customer customer);
	public void showOrders();
	public int addOrder();
	public void showCart();
	public void removeFromCart();
	public void addToCart();
	public void showCustomers(ArrayList<Customer> customers);
	public void addCustomer();
	public void showProducts(ArrayList<Product> products);
	public void addProduct();
}
