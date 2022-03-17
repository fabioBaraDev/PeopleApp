package com.neginet.people.service.impl;

import java.util.List;

import com.neginet.people.domain.CommonNames;
import com.neginet.people.domain.People;
import com.neginet.people.domain.Person;
import com.neginet.people.domain.mappers.PeopleMapper;
import com.neginet.people.domain.mappers.PersonMapper;
import com.neginet.people.repository.Repository;
import com.neginet.people.service.Service;

public class ServiceImpl implements Service{

	private Repository repository;
	private static People people;
	
	
	public ServiceImpl(Repository repository){
		this.repository = repository;
		people = new People(getAllValidNames());
	}
	
	private List<Person> getAllValidNames() {
		List<String> names = repository.getNames();
		return PersonMapper.getPersonList(names);
	}

	@Override
	public People getCardinality() {
		return people;
	}

	@Override
	public List<CommonNames> getMostCommonFirstNames() {
		PeopleMapper mapper = new PeopleMapper();
		return mapper.getCommonFirstName(people).getDistinctFirstName().getTop10Names();
	}

	@Override
	public List<CommonNames> getMostCommonLastNames() {
		PeopleMapper mapper = new PeopleMapper();
		return mapper.getCommonLastName(people).getDistinctLastName().getTop10Names();
	}
	
	@Override
	public People getModifiedNames(){
		PeopleMapper mapper = new PeopleMapper();
		People distinctNameAndLastNames = people.getPeopleWithDistinctNameAndLastName();
		return mapper.getModifiedNames(distinctNameAndLastNames);
	}

	@Override
	public Boolean hasData() {
		return people.getPeople().size() > 0;
	}
}
