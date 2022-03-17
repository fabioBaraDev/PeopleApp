package com.neginet.people.domain.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.neginet.people.domain.Person;

public class PersonMapper {

	public static final Pattern pattern = Pattern.compile("([A-Za-z]+),\\s([A-Za-z]+)\\s--\\s([A-Za-z]+)");
	
	public static List<Person> getPersonList(List<String> names) {
		List<Person> persons = new ArrayList<>();
		
		for (String row : names) {
			if (isItARowWithAValidName(row)) {
				persons.add(getPerson(row));
			}
		}
		return persons;
	}

	private static Boolean isItARowWithAValidName(String string) {
		return pattern.matcher(string).matches();
	}

	private static Person getPerson(String line) {
		Person person = new Person();
		person.setNameByPattern(line);
		return person;
	}
}
