package com.neginet.people.domain;

public class CommonNames extends Person {
	
	private Integer occurrenceQuantities;

	public CommonNames(Integer occurrenceQuantities, Person person){
		super(person);
		this.occurrenceQuantities = occurrenceQuantities;	
	}
	
	public Integer getOccurrenceQuantities() {
		return occurrenceQuantities;
	}
}
