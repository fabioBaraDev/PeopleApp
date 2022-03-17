package com.neginet.people.domain.mappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.neginet.people.domain.CommonNames;
import com.neginet.people.domain.People;
import com.neginet.people.domain.Person;

enum TransactionType {
	FIRST_NAME, LAST_NAME;
}

public class PeopleMapper {
	private Function<Person, String> firstNameOperation = row -> row.getFirstName();
	private Function<Person, String> lastNameOperation = row -> row.getLastName();
	
	private Map<String, CommonNames> distinctListControl;
	private List<CommonNames> commonNames;
	
	public People getModifiedNames(People people) {
		People response = new People();
		people.getPeople().add(people.getPeople().get(0));
		
		for (int i = 1; i < people.getPeople().size(); i++) {
			
			String previousName = people.getPeople().get(i-1).getFirstName();
			String currentLastName = people.getPeople().get(i).getLastName();
			
			response.getPeople().add(new Person(previousName, currentLastName));
		}
		return response;
	}
	
	public PeopleMapper getCommonLastName(People people) {
		
		List<String> lastNameList = convertPersonToNameList(lastNameOperation, people);
		this.commonNames = getSortedCommonName(people, lastNameList, TransactionType.LAST_NAME);
		return this;
	}
	
	public PeopleMapper getCommonFirstName(People people) {
		
		List<String> firstNameList = convertPersonToNameList(firstNameOperation, people);
		this.commonNames = getSortedCommonName(people, firstNameList, TransactionType.FIRST_NAME);
		return this;
	}
	
	private static List<String> convertPersonToNameList(Function<Person, String> operation, People people){
		return people.getPeople().stream().map(operation).collect(Collectors.toList());
	}
	
	private static List<CommonNames> getSortedCommonName(People people, List<String> nameList, TransactionType type) {
		return people.getPeople().stream()
				 .map(row -> getNameOccurrences(nameList, row, type))
				 .sorted(comparePerson())
				 .collect(Collectors.toList());
	}
	
	public PeopleMapper getDistinctFirstName(){
		getDistinctName(TransactionType.FIRST_NAME);
		return this;
	}
	
	public PeopleMapper getDistinctLastName(){
		getDistinctName(TransactionType.LAST_NAME);
		return this;
	}

	private PeopleMapper getDistinctName(TransactionType type){
	
	distinctListControl = new LinkedHashMap<>();
	
	for (CommonNames commonName : this.commonNames) {
		String name = type == TransactionType.FIRST_NAME ? commonName.getFirstName() : commonName.getLastName();
		if(nameNotSavedInArray(name)) {
			distinctListControl.put(name, commonName);
		}
	}
	
	commonNames.clear();
	commonNames.addAll(distinctListControl.values());
	return this;
}
	
	private Boolean nameNotSavedInArray(String name) {
		return !distinctListControl.containsKey(name);
	}
	
	public List<CommonNames> getTop10Names(){
		List<CommonNames> result = new ArrayList<>();
		int numOfRep = this.commonNames.size() >= 10 ? 10 : this.commonNames.size();
		
		for (int i = 0; i < numOfRep; i++) {
			result.add(this.commonNames.get(i));
		}
		return result;
	}
	
	private static CommonNames getNameOccurrences(List<String> nameList, Person person, TransactionType type) {
		
		if(type.equals(TransactionType.FIRST_NAME)) {
			Integer occurrences = Collections.frequency(nameList, person.getFirstName());
			return new CommonNames(occurrences, person);
		}
		
		Integer occurrences = Collections.frequency(nameList, person.getLastName());
		return new CommonNames(occurrences, person);
	}
	
	private static Comparator<CommonNames> comparePerson(){
		return new Comparator<CommonNames>() {
			@Override
			public int compare(CommonNames pr1, CommonNames pr2) {
				return pr2.getOccurrenceQuantities() - pr1.getOccurrenceQuantities();
			}
		};
	}
}




















