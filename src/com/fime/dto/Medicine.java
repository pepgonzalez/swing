package com.fime.dto;

public class Medicine {

	private Long id;
	private String name;
	private Integer total;
	private Float price;
	private String expiration;
	
	public Medicine(){
		
	}
	
	public Medicine(Long id, String name, Integer total, Float price, String expiration){
		this.id = id;
		this.name = name;
		this.total = total;
		this.price = price;
		this.expiration = expiration;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public String getExpiration() {
		return expiration;
	}
	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}
}
