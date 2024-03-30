package entity;

import java.util.List;

public class Address {
	private String building;
	private List<Double> coord;
	private String street;
	private String zipcode;
	@Override
	public String toString() {
		return "Address [building=" + building + ", coord=" + coord + ", street=" + street + ", zipcode=" + zipcode
				+ "]";
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public List<Double> getCoord() {
		return coord;
	}
	public void setCoord(List<Double> coord) {
		this.coord = coord;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public Address(String building, List<Double> list, String street, String zipcode) {
		super();
		this.building = building;
		this.coord = list;
		this.street = street;
		this.zipcode = zipcode;
	}
	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}


}
