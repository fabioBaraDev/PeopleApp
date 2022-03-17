package com.neginet.people.service;

import java.util.List;

import com.neginet.people.domain.CommonNames;
import com.neginet.people.domain.People;


public interface Service {
	public People getCardinality();
	public List<CommonNames> getMostCommonFirstNames();
	public List<CommonNames> getMostCommonLastNames();
	public People getModifiedNames();
	public Boolean hasData();
}
