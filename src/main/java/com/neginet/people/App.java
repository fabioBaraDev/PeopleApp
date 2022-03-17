package com.neginet.people;

import java.util.Scanner;

import com.neginet.people.controller.Controller;
import com.neginet.people.repository.Repository;
import com.neginet.people.repository.impl.FileSystemRespoitory;
import com.neginet.people.service.Service;
import com.neginet.people.service.impl.ServiceImpl;

public class App 
{
	
	private static Repository repository;
	private static Service service;
	private static Controller controller;
	private static String absolutePath;
	
    public static void main( String[] args )
    {
    	scanInputData();
    	buildDependencies();
    	controller.run();	
    }
    
    private static void scanInputData() {
    	System.out.println("Input file absolute path: ");
        Scanner scanner = new Scanner(System.in);
        absolutePath = scanner.nextLine();
        scanner.close();
    }
    
    private static void buildDependencies() {
    	repository = new FileSystemRespoitory(absolutePath);
    	service = new ServiceImpl(repository);
    	controller = new Controller(service);
    }
}
	