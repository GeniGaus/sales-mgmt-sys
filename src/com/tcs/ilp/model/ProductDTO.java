package com.tcs.ilp.model;

public class ProductDTO {
	private int productId;
	private String productName;
	private String description;
	private int categoryId;
	private double price;
	private int qty;
	private String status;
	
	public ProductDTO(){
		
	}
	public ProductDTO(int productId, String productName, String description,int categoryId,
			double price, String status) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.description=description;
		this.categoryId = categoryId;
		this.price = price;
		
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
