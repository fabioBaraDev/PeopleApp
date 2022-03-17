package com.neginet.people.controller;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.neginet.people.domain.CommonNames;
import com.neginet.people.domain.People;
import com.neginet.people.domain.Person;
import com.neginet.people.service.Service;

public class Controller {
	
	private List<CommonNames> mostCommonLastName;
	private List<CommonNames> mostCommonFirstName;
	private List<Person> modifiedNames;
	private Service service;
	
	public Controller(Service service) {
		this.service = service;
	}
	
	public void run() {
		
		if (service.hasData()) {
			runThreads();
			printData(mostCommonLastName, mostCommonFirstName, modifiedNames);
		}else {
			System.out.println(" ** No Data to process ** ");
		}
	}
	
	private void runThreads() {
		printCardinality();
		
		List<Thread> threads = getThreads();
		
		ExecutorService executor = Executors.newFixedThreadPool(3);
		
		threads.forEach(row -> executor.execute(row));
	    executor.shutdown();
		
	    try {
			if (!executor.awaitTermination(5, TimeUnit.MINUTES)) {
			    executor.shutdownNow();
			}
		} catch (InterruptedException e) {
			System.out.println("Something wrong with: " + e.getMessage());
		}
	}
	
	private List<Thread> getThreads(){
		Thread tr1 = new Thread() {
			@Override
		    public void run() {
				mostCommonLastName = service.getMostCommonLastNames();
			}
		};
		
		Thread tr2 = new Thread() {
			@Override
		    public void run() {
				mostCommonFirstName = service.getMostCommonFirstNames();
			}
		};
		
		Thread tr3 = new Thread() {
			@Override
		    public void run() {
				modifiedNames = service.getModifiedNames().getPeople();
			}
		};	
		
		return Arrays.asList(tr1, tr2, tr3);
	}
	
	private void printData(List<CommonNames> mostCommonLastName, List<CommonNames> mostCommonFirstName, List<Person> modifiedNames) {
		
		System.out.println("\n** The most common last names are:\n");
		mostCommonLastName.forEach(row -> System.out.println(row.getLastName() + ": " + row.getOccurrenceQuantities()));
		
		System.out.println("\n** The most common first names are:\n");
		mostCommonFirstName.forEach(row -> System.out.println(row.getFirstName() + ": " + row.getOccurrenceQuantities()));
		
		System.out.println("\n** List of Modified Names:\n");
		service.getModifiedNames().getPeople().forEach(row -> System.out.println(row.getLastName() + ", " + row.getFirstName()));;
	}
	
	private void printCardinality() {
		People cardinality = service.getCardinality();
		
		System.out.println("** The names cardinality for full, last, and first names:\n");
		System.out.println("Full names: " + cardinality.getUniqueFullNamesSize());
		System.out.println("Last names: " + cardinality.getUniqueLastNamesSize());
		System.out.println("First names: " + cardinality.getUniqueFirstNamesSize());
		
		System.out.println("\n** Most Common\n");
		
	}
}
