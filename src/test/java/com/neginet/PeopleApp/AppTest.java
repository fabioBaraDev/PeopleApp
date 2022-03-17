package com.neginet.PeopleApp;

import java.util.List;

import com.neginet.people.domain.CommonNames;
import com.neginet.people.domain.People;
import com.neginet.people.domain.Person;
import com.neginet.people.repository.Repository;
import com.neginet.people.repository.impl.FileSystemRespoitory;
import com.neginet.people.service.Service;
import com.neginet.people.service.impl.ServiceImpl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
	private static Repository repository;
	private static Service service;
		
    public AppTest( String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
    	
    	String filePath = AppTest.class.getClassLoader().getResource("coding-test-data-dev.txt").getFile();
    	repository = new FileSystemRespoitory(filePath);
    	service = new ServiceImpl(repository);
    	
        return new TestSuite( AppTest.class );
    }
    
    public void testCardinality() {
    	
    	People cardinality = service.getCardinality();
		
    	assertTrue(cardinality.getUniqueFullNamesSize() == 5 );
    	assertTrue(cardinality.getUniqueLastNamesSize() == 4 );
    	assertTrue(cardinality.getUniqueFirstNamesSize() == 3 );
    	
    }	
    public void testCommonLastNames() {
    	
    	List<CommonNames> names = service.getMostCommonLastNames();
    	
    	assertTrue( names.size() == 4  );
    	
    	assertTrue( names.get(0).getOccurrenceQuantities() == 2  );
    	assertTrue( names.get(1).getOccurrenceQuantities() == 1  );
    	assertTrue( names.get(2).getOccurrenceQuantities() == 1  );
    	assertTrue( names.get(3).getOccurrenceQuantities() == 1  );
    }	
    public void testCommonFirstNames() {
    	
    	List<CommonNames> names = service.getMostCommonFirstNames();
    	
    	assertTrue( names.size() == 3  );
    	
    	assertTrue( names.get(0).getOccurrenceQuantities() == 3  );
    	assertTrue( names.get(1).getOccurrenceQuantities() == 1  );
    	assertTrue( names.get(2).getOccurrenceQuantities() == 1  );
    }	
    
    public void testModifiedNames() {
    	
    	List<Person> people = service.getCardinality().getPeople();
    	People modified = service.getModifiedNames();
    	
    	people.forEach(row -> assertTrue(modified.isItANewName(row)));
    	
    }	
}
