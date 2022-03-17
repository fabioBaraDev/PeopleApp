package com.neginet.people.domain;

public class Person {
	
	private String firstName;


	private String lastName;
	
	public Person(){
	}
	
	public Person(String firstName, String lastName){
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Person(Person person){
		this.firstName = person.firstName;
		this.lastName = person.lastName;
	}
	
	public void setNameByPattern(String line) {
			String[] splitedLine = line.replaceFirst(", ", " ").split(" ");
			this.firstName = splitedLine[1].trim();
			this.lastName = splitedLine[0].trim();
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}
	
	public String getFullName() {
		return this.firstName + this.lastName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
}
