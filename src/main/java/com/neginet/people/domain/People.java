package com.neginet.people.domain;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class People {

	private static Function<Person, String> fullName = row -> row.getFullName();
	private static Function<Person, String> firstName = row -> row.getFirstName();
	private static Function<Person, String> lastName = row -> row.getLastName();
	
	private List<Person> people;

	private Set<String> firstNameControl = new LinkedHashSet<>();
	private Set<String> lastNameControl = new LinkedHashSet<>();

	public People(){
		this.people = new ArrayList<Person>();
	}
	
	public People(List<Person> people){
		this.people = people;
	}	
	
	public void setDistinctName(Person person) {
		firstNameControl.add(person.getFirstName());
		lastNameControl.add(person.getLastName());
		this.people.add(person);
	}
	
	public List<Person> getPeople() {
		return this.people;
	}
	
	public People getPeopleWithDistinctNameAndLastName(){

		People newPeople = new People();
		
		int listLimit = 0;
		for (Person person : people) {
			if (newPeople.isItANewName(person) && listLimit <= 25) {
				newPeople.setDistinctName(person);
				listLimit ++;
			}
		}
		
		return newPeople;
	}
	
	public Boolean isItANewName(Person person) {
		return !firstNameControl.contains(person.getFirstName()) && !lastNameControl.contains(person.getLastName());
	}
	
	public Integer getUniqueFullNamesSize() {
		return sizeDistinctOf(fullName);
	}

	public Integer getUniqueFirstNamesSize() {
		return sizeDistinctOf(firstName);
	}
	
	public Integer getUniqueLastNamesSize() {
		return sizeDistinctOf(lastName);
	}
	
	private Integer sizeDistinctOf(Function<Person, String> function) {
		return this.people.stream()
					 .map(function)
					 .collect(Collectors.toSet())
					 .size();	
	}
}
